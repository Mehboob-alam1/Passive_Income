package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mehboob.passiveincome.databinding.ActivityDepositBinding;

public class DepositActivity extends AppCompatActivity {
private ActivityDepositBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDepositBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}