package com.delivery.deliveryapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.util.SharedPrefManager;
import com.delivery.deliveryapp.viewmodel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private SharedPrefManager sharedPrefManager;
    private UserViewModel userViewModel;
    String userName;
    TextView name , logout, info,addresses, orders;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.name);
        logout = findViewById(R.id.logout);
        bottomNav = findViewById(R.id.menu);
        info = findViewById(R.id.info);
        addresses= findViewById(R.id.addresses);
        orders= findViewById(R.id.orders);

        sharedPrefManager = new SharedPrefManager(this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userName = sharedPrefManager.getUser().getName();
        name.setText(userName);
        logout.setOnClickListener(v -> {
            userViewModel.logoutUser();
            sharedPrefManager.clear();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        info.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UserInfoActivity.class);
            startActivity(intent);
        });
        addresses.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MyAddressActivity.class);
            startActivity(intent);
        });

        orders.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MyOrdersActivity.class);
            startActivity(intent);
        });

        bottomNav.setSelectedItemId(R.id.navigation_profile);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    return true;
                } else if (itemId == R.id.navigation_discover) {
                    startActivity(new Intent(ProfileActivity.this, DiscoverActivity.class));
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    return true;
                }
                return false;
            }
        });
    }
}