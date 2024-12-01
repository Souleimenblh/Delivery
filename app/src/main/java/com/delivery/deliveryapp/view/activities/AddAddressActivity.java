package com.delivery.deliveryapp.view.activities;

import 
        android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.Address;
import com.delivery.deliveryapp.util.SharedPrefManager;
import com.delivery.deliveryapp.viewmodel.UserViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AddAddressActivity extends AppCompatActivity {
    private WebView webView;
    private EditText addressInput, rueInput, codePostalInput;
    private RadioGroup radioGroup;
    private Button saveLocation;
    private UserViewModel userViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        webView = findViewById(R.id.webView);
        addressInput = findViewById(R.id.addressInput);
        rueInput = findViewById(R.id.rueInput);
        codePostalInput = findViewById(R.id.codePostalInput);
        radioGroup = findViewById(R.id.radioGroup);
        saveLocation = findViewById(R.id.saveLocation);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(), "Android");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/leafletmap.html");

        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager sharedPrefManager = new SharedPrefManager(AddAddressActivity.this);
                String userId = sharedPrefManager.getUser().getUserId();
                String label = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                String address = addressInput.getText().toString();
                String rue = rueInput.getText().toString();
                String codePostal = codePostalInput.getText().toString();

                Address newAddress = new Address(null, label, address, rue, codePostal, latitude, longitude);
                userViewModel.addAddress(userId, newAddress);
                Toast.makeText(AddAddressActivity.this, "Address saved", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddAddressActivity.this, MyAddressActivity.class));
                finish();
            }
        });

        // Checkina idha el fields m3ebin fi kol changement mtaa text
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFieldsForEmptyValues();
            }
        };

        addressInput.addTextChangedListener(textWatcher);
        rueInput.addTextChangedListener(textWatcher);
        codePostalInput.addTextChangedListener(textWatcher);

        // set location lel position mtaana
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getCurrentLocation();
        }
    }

    private class JavaScriptInterface {
        @JavascriptInterface
        public void onLocationSelected(double latitude, double longitude) {
            AddAddressActivity.this.latitude = latitude;
            AddAddressActivity.this.longitude = longitude;
            updateAddressInput(latitude, longitude);
        }
    }

    private void getCurrentLocation() {
        try {
            Task<Location> locationTask = fusedLocationClient.getLastLocation();
            locationTask.addOnSuccessListener(location -> {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    webView.loadUrl("javascript:moveMarker(" + latitude + "," + longitude + ")");
                    updateAddressInput(latitude, longitude);
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void updateAddressInput(double latitude, double longitude) {
        //geaocoder hiya class ndeterminiw minha logitude w latitude fi adresse physiqye
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (!addresses.isEmpty()) {
                addressInput.setText(addresses.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFieldsForEmptyValues() {
        String address = addressInput.getText().toString();
        String rue = rueInput.getText().toString();
        String codePostal = codePostalInput.getText().toString();

        if (address.isEmpty() || rue.isEmpty() || codePostal.isEmpty()) {
            saveLocation.setVisibility(View.GONE);
        } else {
            saveLocation.setVisibility(View.VISIBLE);
        }
    }
}