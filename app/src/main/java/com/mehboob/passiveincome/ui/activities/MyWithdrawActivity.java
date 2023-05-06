package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityMyWithdrawBinding;
import com.mehboob.passiveincome.ui.models.Withdraw;
import com.mehboob.passiveincome.utils.Constant;

public class MyWithdrawActivity extends AppCompatActivity {
    private ActivityMyWithdrawBinding binding;
    private DatabaseReference withDrawRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        withDrawRef = FirebaseDatabase.getInstance().getReference("Withdraws");

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnWhatsapp.setOnClickListener(v -> {
            openWhatsApp();

        });
        fetchDetails();


    }

    private void openWhatsApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + Constant.PHONE_NUMBER_ADMIN + "&text=" + Uri.encode(Constant.DEFAULT_MESSAGE)));

// Verify that WhatsApp is installed on the device
        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            // WhatsApp is installed, start the activity
            startActivity(intent);
        } else {
            // WhatsApp is not installed, display an error message or redirect to the Play Store
            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchDetails() {

        withDrawRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {


                            Withdraw withdraw = snapshot.getValue(Withdraw.class);

                            if (withdraw.isVerified()) {
                                binding.txtVerified.setVisibility(View.VISIBLE);
                                binding.txtNotVerified.setVisibility(View.GONE);
                            } else {
                                binding.txtVerified.setVisibility(View.GONE);
                                binding.txtNotVerified.setVisibility(View.VISIBLE);
                            }

                            binding.txtwithdrawAmount.setText(withdraw.getWithDrawAmount());
                            binding.txtwithdrawAccount.setText(withdraw.getWithDrawAccountName());
                            binding.withDrawAccountNumber.setText(withdraw.getWithDrawAccountNumber());

                        } else {
                            Toast.makeText(MyWithdrawActivity.this, "No withdraw request", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MyWithdrawActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}