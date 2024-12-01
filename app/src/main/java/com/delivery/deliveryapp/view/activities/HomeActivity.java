package com.delivery.deliveryapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Category;
import com.delivery.deliveryapp.util.SharedPrefManager;
import com.delivery.deliveryapp.view.adapters.CategoryAdapter;
import com.delivery.deliveryapp.viewmodel.CategoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {
    private RecyclerView listCategories;
    private CategoryAdapter adapter;
    private CategoryViewModel categoryViewModel;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefManager = new SharedPrefManager(this);
        String userName = sharedPrefManager.getUser().getName();
        TextView welcome = findViewById(R.id.welcome);
        welcome.setText("Good Evening, " + userName + "!");

        listCategories = findViewById(R.id.listCategories);
        listCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategories().observe(this, categories -> {
            adapter = new CategoryAdapter(HomeActivity.this, categories, HomeActivity.this);
            listCategories.setAdapter(adapter);
        });
        BottomNavigationView bottomNav = findViewById(R.id.menu);
        bottomNav.setSelectedItemId(R.id.navigation_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    return true;
                } else if (itemId == R.id.navigation_discover) {
                    startActivity(new Intent(HomeActivity.this, DiscoverActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCategoryClick(Category category) {
        // Handle the category click in HomeActivity
        Toast.makeText(HomeActivity.this, "Selected category: " + category.getName(), Toast.LENGTH_SHORT).show();
    }
}
