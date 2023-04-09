package com.mehboob.passiveincome.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.mehboob.passiveincome.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {
private ActivityCreateAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

binding.btnBack.setOnClickListener(v -> {
    onBackPressed();
});
binding.btnCreatAccount.setOnClickListener(v -> {
    Toast.makeText(this, "Not yet", Toast.LENGTH_SHORT).show();
});
binding.txtSingIn.setOnClickListener(v -> {
    finish();
});
    }
}