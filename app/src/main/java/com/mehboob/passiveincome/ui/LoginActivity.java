package com.mehboob.passiveincome.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
        });


        binding.btnSingIn.setOnClickListener(v -> {
            if (binding.etEmail.getText().toString().isEmpty())
                Toast.makeText(this, "Email field cannot be empty", Toast.LENGTH_SHORT).show();
            else if (binding.etpassword.getText().toString().isEmpty())
                Toast.makeText(this, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
            else
                singIn(binding.etEmail.getText().toString(),binding.etpassword.getText().toString());
        });

    }

    private void singIn(String email, String password) {
    }
}