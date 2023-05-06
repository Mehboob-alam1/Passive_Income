package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
        fetchDetails();


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