package com.delivery.deliveryapp.view.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.viewmodel.RestaurantViewModel;

public class MoreInfosActivity extends AppCompatActivity {
    private RestaurantViewModel viewModel;
    private TextView Address, ratingsTV, heuresTV;
    private double latitude = 10.0;
    private double longitude = 37.0;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        Address = findViewById(R.id.Address);
        ratingsTV = findViewById(R.id.ratingsTV);
        heuresTV = findViewById(R.id.heuresTV);

        String restaurantId = getIntent().getStringExtra("RESTAURANT_ID");

        ImageView imageView8 = findViewById(R.id.imageView8);

        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to go back to the RestaurantActivity
                Intent intent = new Intent(MoreInfosActivity.this, RestaurantActivity.class);
                intent.putExtra("RESTAURANT_ID", restaurantId);

                startActivity(intent);
                finish();
            }
        });

        Toast.makeText(this, "restaurantId: " + restaurantId, Toast.LENGTH_LONG).show();

        viewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        webView = findViewById(R.id.webView);
        // Enable JavaScript in WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set up a WebChromeClient for logging JavaScript console messages
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                // Log JavaScript console messages
                Log.d("WebView", consoleMessage.message());
                return true;
            }
        });

        viewModel.getRestaurant(restaurantId).observe(this, restaurant -> {
            Toast.makeText(this, restaurant.toString(), Toast.LENGTH_SHORT).show();
            Address.setText(restaurant.getAddress());
            ratingsTV.setText("Rated: " + restaurant.getRatings());
            heuresTV.setText("Open until " + restaurant.getCloseTiming());

            latitude = restaurant.getLatitude();
            longitude = restaurant.getLongitude();
            Toast.makeText(this, "longitude: " + longitude + " latitude: " + latitude, Toast.LENGTH_LONG).show();

            // Build the HTML content with latitude and longitude
            String htmlContent = "<html><head>" +
                    "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet/dist/leaflet.css\" />" +
                    "<script src=\"https://unpkg.com/leaflet/dist/leaflet.js\"></script>" +
                    "</head><body>" +
                    "<div id=\"map\" style=\"height: 100vh;\"></div>" +
                    "<script type=\"text/javascript\">" +
                    "var map = L.map('map').setView([" + latitude + ", " + longitude + "], 13);" +
                    "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {" +
                    "attribution: '© OpenStreetMap contributors'" +
                    "}).addTo(map);" +
                    "L.marker([" + latitude + ", " + longitude + "]).addTo(map)" +
                    ".bindPopup('" + restaurant.getName() + "')" +
                    ".openPopup();" +
                    "</script></body></html>";

            // Load HTML content into WebView
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);

            ImageView copyImageView = findViewById(R.id.copyIMG);
            copyImageView.setOnClickListener(view -> {
                // Copy the address to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Address", restaurant.getAddress());
                clipboard.setPrimaryClip(clip);

                // Notify the user that the address has been copied
                Toast.makeText(MoreInfosActivity.this, "Address copied to clipboard", Toast.LENGTH_SHORT).show();
            });
        });

        // Set a separate WebChromeClient for console messages after the map is loaded
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                // Log JavaScript console messages
                Log.d("WebView", consoleMessage.message());
                return true;
            }
        });
    }
}
