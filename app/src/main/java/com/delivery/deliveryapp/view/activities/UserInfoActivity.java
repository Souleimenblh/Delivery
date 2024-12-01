package com.delivery.deliveryapp.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.util.SharedPrefManager;

public class UserInfoActivity extends AppCompatActivity {
    ImageView back;
    String userName , userEmail , userPhone;
    TextView name,nameInfo , emailInfo , phoneInfo ;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        back = findViewById(R.id.back);
        name = findViewById(R.id.name);
        nameInfo = findViewById(R.id.nameInfo);
        emailInfo = findViewById(R.id.emailInfo);
        phoneInfo = findViewById(R.id.phoneInfo);
        sharedPrefManager = new SharedPrefManager(this);
        userName = sharedPrefManager.getUser().getName();
        userEmail = sharedPrefManager.getUser().getEmail();
        userPhone = sharedPrefManager.getUser().getPhoneNumber();
        name.setText(userName);
        nameInfo.setText(userName);
        emailInfo.setText(userEmail);
        phoneInfo.setText(userPhone);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}