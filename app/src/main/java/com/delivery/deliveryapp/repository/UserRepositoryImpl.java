package com.delivery.deliveryapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.delivery.deliveryapp.model.Address;
import com.delivery.deliveryapp.model.Order;
import com.delivery.deliveryapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    //3lamana declaration mta3 les deux variable pour gere l authentification w database reference pour interagir m3a base
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public UserRepositoryImpl() {
        firebaseAuth = FirebaseAuth.getInstance();
        //creation l instannce mta3 firebaseAuth lil authentification mta3 user
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //creation l instannce mta3 databaseReference lil base de donnees mta3 firebase en specifiant l'arbre Users
    }
    @Override
    public LiveData<User> loginUser(String email, String password) {
        // MutableLiveData fi wist ha donnees mta3 user w hiya sous class de livadata expose les méthodes setValue(T) et postValue(T).
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    //addOnCompleteListener zidna lisner yitnada marra barka une fois il tache de connection sera terminier
                    if (task.isSuccessful()) {
                        //nverifiw tache connexion resolu avec succes
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            //recuperation les donnee mta3 user
                            databaseReference.child(userId).get().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    User user = task1.getResult().getValue(User.class);
                                    if (user != null) {
                                        user.setUserId(userId); // nit2akdou userId is set
                                        userMutableLiveData.setValue(user);
                                    } else {
                                        userMutableLiveData.setValue(null); // User data mal9ahech
                                    }
                                } else {
                                    userMutableLiveData.setValue(null); // Failed to fetch  user data
                                }
                            });
                        } else {
                            userMutableLiveData.setValue(null); // firebaseUser is null
                        }
                    } else {
                        userMutableLiveData.setValue(null); // Login failed
                    }
                });
        return userMutableLiveData;
    }
    @Override
    public LiveData<User> registerUser(User user) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String userId = firebaseUser != null ? firebaseUser.getUid() : null;
                        user.setUserId(userId);
                        databaseReference.child(userId).setValue(user);
                        userMutableLiveData.setValue(user);
                    } else {
                        userMutableLiveData.setValue(null);
                    }
                });
        return userMutableLiveData;
    }

    @Override
    public void logoutUser() {
        firebaseAuth.signOut();
    }

    @Override
    public LiveData<User> getUserData() {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            databaseReference.child(firebaseUser.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = task.getResult().getValue(User.class);
                            userMutableLiveData.setValue(user);
                        } else {
                            userMutableLiveData.setValue(null);
                        }
                    });
        }
        return userMutableLiveData;
    }

    @Override
    public void addAddress(String userId, Address address) {
        databaseReference.child(userId).child("addresses").push().setValue(address);
    }

    @Override
    public LiveData<List<Address>> getUserAddresses(String userId) {
        MutableLiveData<List<Address>> addressesMutableLiveData = new MutableLiveData<>();
        databaseReference.child(userId).child("addresses").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Address> addresses = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Address address = snapshot.getValue(Address.class);
                    addresses.add(address);
                }
                addressesMutableLiveData.setValue(addresses);
            } else {
                addressesMutableLiveData.setValue(null);
            }
        });
        return addressesMutableLiveData;
    }

    @Override
    public void addOrder(String userId, Order order) {
        databaseReference.child(userId).child("orders").push().setValue(order);
    }

    @Override
    public LiveData<List<Order>> getUserOrders(String userId) {
        MutableLiveData<List<Order>> ordersMutableLiveData = new MutableLiveData<>();
        databaseReference.child(userId).child("orders").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    orders.add(order);
                }
                ordersMutableLiveData.setValue(orders);
            } else {
                ordersMutableLiveData.setValue(null);
            }
        });
        return ordersMutableLiveData;
    }
}