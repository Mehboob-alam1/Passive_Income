package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityUserBlockedBinding;

public class UserBlockedActivity extends AppCompatActivity {
private ActivityUserBlockedBinding binding;
private DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityUserBlockedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userRef= FirebaseDatabase.getInstance().getReference("Users");

fetchWhyBlocked();

binding.btnBack.setOnClickListener(v -> {
    finishAffinity();
});

    }

    private void fetchWhyBlocked() {
        userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("reasonBlocked").exists()){
                         String reason=   snapshot.child("reasonBlocked").getValue(String.class);

                         binding.txtWhyAccountBanned.setText(reason);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}