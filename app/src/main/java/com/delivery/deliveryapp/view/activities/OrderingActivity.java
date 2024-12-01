package com.delivery.deliveryapp.view.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Order;
import com.delivery.deliveryapp.model.Product;
import com.delivery.deliveryapp.util.SharedPrefManager;
import com.delivery.deliveryapp.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.UUID;

public class OrderingActivity extends AppCompatActivity {
    private ImageView productIMG;
    private UserViewModel userViewModel;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander_produit);

        String productIcon = getIntent().getStringExtra("PRODUCT_ICON");
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        double productPrice = getIntent().getDoubleExtra("PRODUCT_PRICE", 0.0);
        String productDescription = getIntent().getStringExtra("PRODUCT_DESCRIPTION");


        TextView productNameTextView = findViewById(R.id.nomProduitTV);
        Button productPriceTextView = findViewById(R.id.orderProduitBTN);
        TextView productDescriptionTextView = findViewById(R.id.descriptionTitleTV);

        productIMG = findViewById(R.id.productImage);
        productNameTextView.setText(productName);
        productPriceTextView.setText("Add to Bag    $" + String.valueOf(productPrice));
        productDescriptionTextView.setText(productDescription);


        Picasso.get().load(productIcon).error(R.drawable.profile).into(productIMG);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        Button orderButton = findViewById(R.id.orderProduitBTN);
        orderButton.setOnClickListener(view -> addOrderToUser());
    }

    private void addOrderToUser() {
        sharedPrefManager = new SharedPrefManager(this);
        String userId = sharedPrefManager.getUser().getUserId();

        // Get other product details
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        double productPrice = getIntent().getDoubleExtra("PRODUCT_PRICE", 0.0);
        String productIcon = getIntent().getStringExtra("PRODUCT_ICON");
        String productDescription = getIntent().getStringExtra("PRODUCT_DESCRIPTION");


        // Create an Order object
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString()); // You might want to use a unique ID
        order.setProduct(new Product(productName, productDescription, productPrice, productIcon));
        order.setOrderDate(new Date());

        // Add the order to the user using the ViewModel
        userViewModel.addOrder(userId, order);

        // Provide feedback to the user (optional)
        Toast.makeText(this, "Order added to Bag", Toast.LENGTH_SHORT).show();
    }
}
