package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mehboob.passiveincome.databinding.ActivityPaymentRulesBinding;

public class PaymentRulesActivity extends AppCompatActivity {
private ActivityPaymentRulesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPaymentRulesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}