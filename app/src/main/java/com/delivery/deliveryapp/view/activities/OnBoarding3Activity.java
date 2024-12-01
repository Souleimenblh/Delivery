package com.delivery.deliveryapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.util.SharedPrefManager;

public class OnBoarding3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding3);
        Button next = findViewById(R.id.next);
        TextView skip = findViewById(R.id.skip);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.isOnboardingComplete()) {
            // If the onboarding is complete, navigate to the login activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoarding3Activity.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding3Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}