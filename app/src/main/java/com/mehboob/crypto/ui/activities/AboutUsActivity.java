package com.mehboob.crypto.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mehboob.crypto.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
private ActivityAboutUsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot())
        ;
    }
}