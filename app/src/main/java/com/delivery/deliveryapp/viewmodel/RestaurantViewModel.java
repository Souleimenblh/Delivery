package com.delivery.deliveryapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.delivery.deliveryapp.model.Restaurant;
import com.delivery.deliveryapp.repository.RestaurantRepository;
import com.delivery.deliveryapp.repository.RestaurantRepositoryImpl;

import java.util.List;

public class RestaurantViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;
    private LiveData<List<Restaurant>> restaurants;
    private LiveData<Restaurant> restaurant;

    public RestaurantViewModel() {
        restaurantRepository = new RestaurantRepositoryImpl();
        refreshRestaurants();
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        if (restaurants == null) {
            refreshRestaurants();
        }
        return restaurants;
    }

    public LiveData<List<Restaurant>> getRestaurantsLiveData() {
        if (restaurants == null) {
            refreshRestaurants(); // Ensure LiveData is initialized
        }
        return restaurants;
    }

    public LiveData<Restaurant> getRestaurant(String id) {
        if (restaurant == null) {
            restaurant = restaurantRepository.getRestaurantById(id);
        }
        return restaurant;
    }

    private void refreshRestaurants() {
        restaurants = restaurantRepository.getRestaurants();
    }
}
