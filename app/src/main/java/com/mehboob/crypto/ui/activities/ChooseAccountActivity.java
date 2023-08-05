
package com.mehboob.crypto.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.mehboob.crypto.databinding.ActivityChooseAccountBinding;

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



        binding.lineUsdt.setOnClickListener(v -> {
            Intent intent = new Intent(this,DepositActivity.class);
            intent.putExtra("account","usd");
            intent.putExtra("balance",balance);
            startActivity(intent);
        });

        binding.lineBtc.setOnClickListener(v -> {
            Intent intent = new Intent(this,DepositActivity.class);
            intent.putExtra("account","btc");
            intent.putExtra("balance",balance);
            startActivity(intent);
        });

        binding.lineEth.setOnClickListener(v -> {
            Intent intent = new Intent(this,DepositActivity.class);
            intent.putExtra("account","eth");
            intent.putExtra("balance",balance);
            startActivity(intent);
        });
    }
}