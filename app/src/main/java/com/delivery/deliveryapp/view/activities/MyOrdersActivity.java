package com.delivery.deliveryapp.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Order;
import com.delivery.deliveryapp.util.SharedPrefManager;
import com.delivery.deliveryapp.view.adapters.AddressAdapter;
import com.delivery.deliveryapp.view.adapters.OrderAdapter;
import com.delivery.deliveryapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {
    ImageView back;
    Button addAddress;
    RecyclerView recyclerView;
    UserViewModel userViewModel;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        back = findViewById(R.id.back);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AddressAdapter(new ArrayList<>()));

        userViewModel = new UserViewModel();
        sharedPrefManager = new SharedPrefManager(this);

        String userId = sharedPrefManager.getUser().getUserId();
        userViewModel.getUserOrders(userId).observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                OrderAdapter ordersAdapter = new OrderAdapter(orders);
                recyclerView.setAdapter(ordersAdapter);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}