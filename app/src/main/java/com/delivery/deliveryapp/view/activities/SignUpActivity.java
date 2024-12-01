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
import com.delivery.deliveryapp.viewmodel.UserViewModel;

public class SignUpActivity extends AppCompatActivity {
    private Button login;
    private EditText nameInput, emailInput, passwordInput, phoneNumberInput;
    private Button signUpSubmit;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login = findViewById(R.id.login);
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        signUpSubmit = findViewById(R.id.signUpSubmit);
        signUpSubmit.setEnabled(false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        signUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user data from the EditTexts
                String name = ((EditText) findViewById(R.id.nameInput)).getText().toString();
                String email = ((EditText) findViewById(R.id.emailInput)).getText().toString();
                String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();
                String phoneNumber = ((EditText) findViewById(R.id.phoneNumberInput)).getText().toString();

                User user = new User(null, name, email, phoneNumber, password);


                userViewModel.registerUser(user).observe(SignUpActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if (user != null) {
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                finish();
            }
        });
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkName();
            }
            @Override
            public void afterTextChanged(Editable s) {
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

        phoneNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPhoneNumber();
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
    private void checkName() {
        String name = nameInput.getText().toString();
        boolean validName = !name.isEmpty();
        if (!validName) {
            nameInput.setError("Name cannot be empty");
        }
        signUpSubmit.setEnabled(validName && isEmailValid() && isPasswordValid() && isPhoneNumberValid());
    }
    private void checkEmail() {
        String email = emailInput.getText().toString();
        boolean validEmail = !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!validEmail) {
            emailInput.setError("Invalid Email Address");
        }
        signUpSubmit.setEnabled(validEmail && isPasswordValid() && isNameValid() && isPhoneNumberValid());
    }
    private void checkPhoneNumber() {
        String phoneNumber = phoneNumberInput.getText().toString();
        boolean validPhoneNumber = phoneNumber.length() == 8;
        if (!validPhoneNumber) {
            phoneNumberInput.setError("Invalid Phone Number");
        }
        signUpSubmit.setEnabled(isEmailValid() && isPasswordValid() && isNameValid() && validPhoneNumber);
    }
    private void checkPassword() {
        String password = passwordInput.getText().toString();
        boolean validPassword = password.length() > 6;
        if (!validPassword) {
            passwordInput.setError("Password must be more than 6 characters");
        }
        signUpSubmit.setEnabled(isEmailValid() && validPassword && isNameValid() && isPhoneNumberValid());
    }
    private boolean isNameValid() {
        String name = nameInput.getText().toString();
        return !name.isEmpty();
    }
    private boolean isEmailValid() {
        String email = emailInput.getText().toString();
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isPasswordValid() {
        String password = passwordInput.getText().toString();
        return password.length() > 6;
    }
    private boolean isPhoneNumberValid() {
        String phoneNumber = phoneNumberInput.getText().toString();
        return phoneNumber.length() == 8;
    }
}