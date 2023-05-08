package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityUserBlockedBinding;
import com.mehboob.passiveincome.utils.Constant;

public class UserBlockedActivity extends AppCompatActivity {
    private ActivityUserBlockedBinding binding;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBlockedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userRef = FirebaseDatabase.getInstance().getReference("Users");

        fetchWhyBlocked();

        binding.btnBack.setOnClickListener(v -> {
            finishAffinity();
        });

        binding.btnWhatsapp.setOnClickListener(v -> {
            openWhatsApp();
        });

    }

    private void fetchWhyBlocked() {
        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("reasonBlocked").exists()) {
                            String reason = snapshot.child("reasonBlocked").getValue(String.class);

                            binding.txtWhyAccountBanned.setText(reason);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
}