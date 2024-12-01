package com.delivery.deliveryapp.repository;

import androidx.lifecycle.LiveData;

import com.delivery.deliveryapp.model.Address;
import com.delivery.deliveryapp.model.Order;
import com.delivery.deliveryapp.model.User;

import java.util.List;

public interface UserRepository {
    LiveData<User> loginUser(String email, String password);
    //live data hiya class de donnees il respect le cycle de vie mta3 les differentes activites w fragments
    //w ta3mil l mise ajour automatique mta3 les donnees de l'interface graphique
    LiveData<User> registerUser(User user);
    void logoutUser();
    LiveData<User> getUserData();

    void addAddress(String userId, Address address);
    LiveData<List<Address>> getUserAddresses(String userId);

    void addOrder(String userId, Order order);

    LiveData<List<Order>> getUserOrders(String userId);

}
