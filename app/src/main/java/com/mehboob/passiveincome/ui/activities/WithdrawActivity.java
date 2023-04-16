package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mehboob.passiveincome.databinding.ActivityWithdrawBinding;
import com.mehboob.passiveincome.utils.SharedPref;

public class WithdrawActivity extends AppCompatActivity {
private ActivityWithdrawBinding binding;
private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
sharedPref= new SharedPref(this);

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.lineAccount.setOnClickListener(v -> {
            startActivity(new Intent(this,AccountSelectionActivity.class));
        });
binding.txtAccountName.setText(sharedPref.fetchAccount());
        binding.btnConfirmWithDraw.setOnClickListener(v -> {
            if (binding.etAmount.getText().toString().isEmpty())
                Toast.makeText(this, "Enter amount ", Toast.LENGTH_SHORT).show();
            else if (binding.etAccountNumber.getText().toString().isEmpty())
                Toast.makeText(this, "Enter account number", Toast.LENGTH_SHORT).show();
            else if (binding.etOwnerName.getText().toString().isEmpty())
                Toast.makeText(this, "Enter real name of owner", Toast.LENGTH_SHORT).show();
            else
                requestWithdraw(binding.etAmount.getText().toString(),
                         binding.etAccountNumber.getText().toString(),
                        binding.etOwnerName.getText().toString());
        });
    }

    private void requestWithdraw(String amount, String accountNumber, String ownerName) {
    }
}