package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityViewReferralBinding;

public class ViewReferralActivity extends AppCompatActivity {
    private ActivityViewReferralBinding binding;
    private String userId;
    private DatabaseReference databaseReference,balanceRef;
    private FirebaseAuth mAuth;
    private  long authTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewReferralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userId = getIntent().getStringExtra("uid");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        balanceRef=FirebaseDatabase.getInstance().getReference("Balance");
mAuth=FirebaseAuth.getInstance();
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        fetchActive();

        fetchDetails();
        fetchBalance();
    }

    private void fetchActive() {
    }

    private void fetchBalance() {
        balanceRef.child(userId)
                .child("totalBalance")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                          String balance=  snapshot.getValue(String.class);
                          binding.txtUserAmountAccount.setText(balance);
                        }else{
                            binding.txtUserAmountAccount.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

//    private void fetchTime() {
//        mAuth.fetchSignInMethodsForEmail(userId)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        // Check if the user exists
//                        if (task.getResult().getSignInMethods() != null && !task.getResult().getSignInMethods().isEmpty()) {
//                            // User exists, retrieve the user object
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            if (user != null) {
//                                // Get the user's authentication time
//                                 authTime = user.getMetadata().getCreationTimestamp();
//                                 binding.txtUserJoinTimeAccount.setText(String.valueOf(authTime));
//                                // Do something with the authentication time
//
//                            }
//                        } else {
//                            // User does not exist
//                            System.out.println("User does not exist");
//                        }
//                    } else {
//                        // Handle the error
//                        Exception exception = task.getException();
//                        if (exception != null) {
//                            System.out.println("Error: " + exception.getMessage());
//                        }
//                    }
//                });
//
//
//
//
//
//
//
//
//    }

    private void fetchDetails() {
        databaseReference.child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String firstName = snapshot.child("first_name").getValue(String.class);
                            String lastName = snapshot.child("sur_name").getValue(String.class);
                            boolean block = snapshot.child("block").getValue(boolean.class);
                                   binding.txtUserNameAccount.setText(firstName + " "+lastName);

                                   if (block){
                                       binding.txtUserJoinTimeAccount.setText("Blocked");
                                   }else{
                                       binding.txtUserJoinTimeAccount.setText("Active");
                                   }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}