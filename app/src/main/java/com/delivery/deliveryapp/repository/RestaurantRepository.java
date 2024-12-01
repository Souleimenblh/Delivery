package com.delivery.deliveryapp.repository;

import androidx.lifecycle.LiveData;
import com.delivery.deliveryapp.model.Restaurant;
import java.util.List;

public interface RestaurantRepository {
    LiveData<List<Restaurant>> getRestaurants();
    public LiveData<Restaurant> getRestaurantById(String restaurantId);
}
