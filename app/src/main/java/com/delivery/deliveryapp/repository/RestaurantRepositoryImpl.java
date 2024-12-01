package com.delivery.deliveryapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.delivery.deliveryapp.model.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepositoryImpl implements RestaurantRepository{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    public RestaurantRepositoryImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        database = FirebaseDatabase.getInstance();
    }
    @Override
    public LiveData<List<Restaurant>> getRestaurants() {
        MutableLiveData<List<Restaurant>> data = new MutableLiveData<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Restaurant> restaurants = new ArrayList<>();
                for (DataSnapshot restaurantSnapshot : dataSnapshot.getChildren()) {
                    Restaurant restaurant = restaurantSnapshot.getValue(Restaurant.class);
                    if (restaurant != null) {
                        restaurants.add(restaurant);
                    }
                }
                data.setValue(restaurants);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                data.setValue(null);
            }
        });
        return data;
    }

    @Override
    public LiveData<Restaurant> getRestaurantById(String restaurantId) {
        MutableLiveData<Restaurant> restaurantData = new MutableLiveData<>();
        database.getReference("Restaurants")
                .child(restaurantId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                        restaurantData.setValue(restaurant);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle possible errors.
                    }
                });
        return restaurantData;
    }
}
