package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mehboob.passiveincome.databinding.ActivityDepositBinding;

public class DepositActivity extends AppCompatActivity {
private ActivityDepositBinding binding;
private String selectedPackage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDepositBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });


      selectedPackage=  getIntent().getStringExtra("package");

      if (selectedPackage!=null){
          binding.linePackage.setVisibility(View.VISIBLE);
          binding.txtSelectedPackage.setText(selectedPackage);
      }else{
          binding.linePackage.setVisibility(View.GONE);
      }

        binding.imgCopyBankNumber.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtBankAccountNumber.getText().toString());
        });
        binding.imgCopyBankName.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtBankAccountName.getText().toString());

        });

        binding.imgCopyEasypaisaNumber.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtEasyPaisaAccountNumber.getText().toString());
        });
        binding.imgCopyEasypaisaName.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtEasyPaisaAccountName.getText().toString());
        });
        binding.imgCopyJazzcashNumber.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtJazzCashAccountNumber.getText().toString());
        });

        binding.imgCopyJazzcashName.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtJazzCashAccountName.getText().toString());
        });
    }

    private void copyTextToClipboard(String text) {
        // Get the text from the TextView


        // Get the ClipboardManager
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        // Create a new ClipData object to store the text
        ClipData clipData = ClipData.newPlainText("label", text);

        // Set the ClipData object as the clipboard data
        clipboardManager.setPrimaryClip(clipData);

        // Show a toast message to indicate that the text has been copied
        Toast.makeText(this, "Text copied to clipboard " +text, Toast.LENGTH_SHORT).show();
    }

}