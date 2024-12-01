    package com.delivery.deliveryapp.view.activities;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.lifecycle.ViewModelProvider;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.delivery.deliveryapp.R;
    import com.delivery.deliveryapp.model.Category;
    import com.delivery.deliveryapp.model.Product;
    import com.delivery.deliveryapp.view.adapters.CategoryAdapter;
    import com.delivery.deliveryapp.view.adapters.ProductAdapter;
    import com.delivery.deliveryapp.viewmodel.CategoryViewModel;
    import com.delivery.deliveryapp.viewmodel.RestaurantViewModel;
    import com.squareup.picasso.Picasso;

    import java.util.ArrayList;
    import java.util.List;

    public class RestaurantActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener,
            ProductAdapter.OnProductClickListener {

        private RecyclerView listCategories;
        private CategoryAdapter categoryAdapter;
        private CategoryViewModel categoryViewModel;
        private RecyclerView listProducts;
        private ProductAdapter productAdapter;

        private RestaurantViewModel viewModel;
        private TextView restaurantName, address;
        private ImageView profileIMG, moreInformationIMG, backgroudIMG;

        private TextView ratingsTV, DeliversTV, SpecialityTV;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_restaurant);

            String restaurantId = getIntent().getStringExtra("DISCOVERY_ID");
            viewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

            restaurantName = findViewById(R.id.restaurantName);
            address = findViewById(R.id.address);
            profileIMG = findViewById(R.id.profileIMG);
            backgroudIMG = findViewById(R.id.backgroudIMG);
            ratingsTV = findViewById(R.id.ratingsTV);
            DeliversTV = findViewById(R.id.DeliversTV);
            SpecialityTV = findViewById(R.id.FamousCategoryTV);
            moreInformationIMG = findViewById(R.id.moreInformationIMG);

            moreInformationIMG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RestaurantActivity.this, MoreInfosActivity.class);
                    intent.putExtra("RESTAURANT_ID", restaurantId);
                    startActivity(intent);
                }
            });

            viewModel.getRestaurant(restaurantId).observe(this, restaurant -> {
                Toast.makeText(this, restaurant.toString(), Toast.LENGTH_SHORT).show();
                if (restaurant != null) {
                    restaurantName.setText(restaurant.getName());
                    address.setText(restaurant.getAddress());
                    ratingsTV.setText("Ratings: " + restaurant.getRatings());
                    DeliversTV.setText("Delievers in " + restaurant.getDeliversIn());
                    SpecialityTV.setText("Famous by:    " + restaurant.getSpeciality());

                    // Load restaurant icon using Picasso with error handling
                    Picasso.get().load(restaurant.getIcon()).error(R.drawable.profile).into(profileIMG);

                    // Load restaurant background image using Picasso with error handling
                    Picasso.get().load(restaurant.getBgIMG()).error(R.drawable.rounded_background).into(backgroudIMG);
                }
            });

            listCategories = findViewById(R.id.listCategories);
            listCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
            categoryViewModel.getCategories().observe(this, categories -> {
                categoryAdapter = new CategoryAdapter(this, categories, this);
                listCategories.setAdapter(categoryAdapter);
            });

            listProducts = findViewById(R.id.listProducts);
            listProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            productAdapter = new ProductAdapter(this, new ArrayList<>(), this);
            listProducts.setAdapter(productAdapter);
        }

        @Override
        public void onCategoryClick(Category category) {
            Toast.makeText(this, "Selected category: " + category.getName(), Toast.LENGTH_SHORT).show();

            // Retrieve the list of products for the selected category
            List<Product> products = category.getProducts();

            // Update the product adapter with the new list of products
            if (productAdapter != null) {
                productAdapter.setProducts(products);
                productAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onProductClick(Product product) {
            Intent intent = new Intent(this, OrderingActivity.class);

            intent.putExtra("PRODUCT_ICON", product.getIcon());
            intent.putExtra("PRODUCT_NAME", product.getName());
            intent.putExtra("PRODUCT_PRICE", product.getPrice());
            intent.putExtra("PRODUCT_DESCRIPTION", product.getDescription());

            startActivity(intent);
        }
    }
