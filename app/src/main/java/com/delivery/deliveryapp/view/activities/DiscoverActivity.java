package com.delivery.deliveryapp.view.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Address;
import com.delivery.deliveryapp.model.Restaurant;
import com.delivery.deliveryapp.model.WebAppInterface;
import com.delivery.deliveryapp.viewmodel.RestaurantViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;

public class DiscoverActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private Address addressHolder;
    private com.delivery.deliveryapp.viewmodel.RestaurantViewModel restaurantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        // Initialize RestaurantViewModel
        restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

        // Get the WebView
        WebView webView = findViewById(R.id.webView);

        // Enable JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);


        // Observe the LiveData of restaurants
        restaurantViewModel.getRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                Gson gson = new Gson();
                String json = gson.toJson(restaurants);
                Log.e("JSON", json);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webView.evaluateJavascript("javascript:setRestaurants('" + json + "')", null);
                    }
                }, 500);
            }
        });

        addressHolder = new Address(); // Create an instance of AddressHolder

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        } else {
            getLocation();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_discover) {
                    return true;
                } else if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(DiscoverActivity.this, HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(DiscoverActivity.this, ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    return true;
                }
                return false;
            }
        });

        bottomNav.setSelectedItemId(R.id.navigation_discover);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            // Handle the case when the user denies location permission
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // Handle the location update
        handleLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Handle provider enabled if needed
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {
                handleLocation(lastKnownLocation);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void handleLocation(Location location) {
        try {
            addressHolder.setLatitude(location.getLatitude());
            addressHolder.setLongitude(location.getLongitude());
            WebView webView = findViewById(R.id.webView);
            webView.getSettings().setDomStorageEnabled(true);
            webView.addJavascriptInterface(new WebAppInterface(this, addressHolder) {
                @JavascriptInterface
                @Override
                public void onMarkerClick(String discoveryName) {
                    DiscoverActivity.this.onMarkerClick(discoveryName);
                }
            }, "Android");

            if (location != null) {
                webView.evaluateJavascript("initializeMap(" + location.getLatitude() + ", " + location.getLongitude() + ")", null);
            }

            webView.loadUrl("file:///android_asset/discovery.html");

            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @JavascriptInterface
    public void onMarkerClick(final String discoveryID) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(DiscoverActivity.this)
                        .setTitle("Confirm Message")
                        .setMessage("Do you confirm the access?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                Intent intent = new Intent(DiscoverActivity.this, RestaurantActivity.class);
                                intent.putExtra("DISCOVERY_ID", discoveryID);
                                startActivity(intent);

                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
}