package com.delivery.deliveryapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.delivery.deliveryapp.R;
import com.delivery.deliveryapp.model.User;
import com.delivery.deliveryapp.util.SharedPrefManager;
import com.delivery.deliveryapp.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private Button signup;
    private Button loginSubmit;
    private EditText emailInput, passwordInput;
    private UserViewModel userViewModel;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signup = findViewById(R.id.signup);
        loginSubmit = findViewById(R.id.loginSubmit);
        loginSubmit.setEnabled(false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.isLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.emailInput)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();

                userViewModel.loginUser(email, password).observe(LoginActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user != null) {
                            sharedPrefManager.saveUser(user);


                            sharedPrefManager.saveOnboardingComplete(true);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentification failed. Please try again.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEmail();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPassword();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void checkEmail() {
        String email = emailInput.getText().toString();
        boolean validEmail = !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!validEmail) {
            emailInput.setError("Invalid Email Address");
        }
        loginSubmit.setEnabled(validEmail && isPasswordValid());
    }

    private void checkPassword() {
        String password = passwordInput.getText().toString();
        boolean validPassword = password.length() > 6;
        if (!validPassword) {
            passwordInput.setError("Password must be more than 6 characters");
        }
        loginSubmit.setEnabled(isEmailValid() && validPassword);
    }

    private boolean isEmailValid() {
        String email = emailInput.getText().toString();
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid() {
        String password = passwordInput.getText().toString();
        return password.length() > 6;
    }

}