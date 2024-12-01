package com.delivery.deliveryapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.delivery.deliveryapp.model.User;

public class SharedPrefManager {
    //shared preference hiya na3mlouha bech njariw preferences partagees
    private static final String SHARED_PREF_NAME = "my_shared_preferences";
    private SharedPreferences sharedPreferences;
    private Context context;// context instance bech na5thou beha les preferences partagees

    public SharedPrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", user.getUserId());
        editor.putString("userName", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phoneNumber", user.getPhoneNumber());
        editor.putString("password", user.getPassword());
        editor.apply();
    }

    public User getUser() {
        String userId = sharedPreferences.getString("userId", null);
        String userName = sharedPreferences.getString("userName", null);
        String email = sharedPreferences.getString("email", null);
        String phoneNumber = sharedPreferences.getString("phoneNumber", null);
        String password = sharedPreferences.getString("password", null);
        return new User(userId, userName, email, phoneNumber, password);
    }

    public boolean isLoggedIn() {
        //methide athi ntestiw beha ithakan user mlogi wala le
        return sharedPreferences.getString("userId", null) != null;
    }


    public void clear() { // On va utiliser cette méthode quand l'utilisateur se déconnecte
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveOnboardingComplete(boolean isComplete) { // nstockiw la valeur de isComplete pour qu'on l'utilise dans la méthode suivante
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("onboardingComplete", isComplete);
        editor.apply();
    }

    public boolean isOnboardingComplete() {
        return sharedPreferences.getBoolean("onboardingComplete", false);
    }
}
