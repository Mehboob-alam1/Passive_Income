
package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mehboob.passiveincome.databinding.ActivityChooseAccountBinding;

public class ChooseAccountActivity extends AppCompatActivity {
private ActivityChooseAccountBinding binding;
private String balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityChooseAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        balance=getIntent().getStringExtra("balance");
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });



        binding.lineBank.setOnClickListener(v -> {
            Intent intent = new Intent(this,DepositActivity.class);
            intent.putExtra("account","Bank");
            intent.putExtra("balance",balance);
            startActivity(intent);
        });

        binding.lineEasy.setOnClickListener(v -> {
            Intent intent = new Intent(this,DepositActivity.class);
            intent.putExtra("account","Easy");
            intent.putExtra("balance",balance);
            startActivity(intent);
        });

        binding.lineJazz.setOnClickListener(v -> {
            Intent intent = new Intent(this,DepositActivity.class);
            intent.putExtra("account","Jazz");
            intent.putExtra("balance",balance);
            startActivity(intent);
        });
    }
}