package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityCheckReferralBinding;
import com.mehboob.passiveincome.ui.models.Referrals;
import com.mehboob.passiveincome.ui.models.User;

import java.util.UUID;

public class CheckReferralActivity extends AppCompatActivity {
private ActivityCheckReferralBinding binding;
  private   DatabaseReference userRef;
  private boolean isProfileCompleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityCheckReferralBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    userRef   = FirebaseDatabase.getInstance().getReference().child("Users");

        binding.btnCheckReferral.setOnClickListener(v -> {
            if (binding.etReferralId.getText().toString().isEmpty()){
                Toast.makeText(this, "Enter referral id", Toast.LENGTH_SHORT).show();
            }else{
                checkReferral(binding.etReferralId.getText().toString());
            }
        });

        binding.btnSkip.setOnClickListener(v -> {
            updateUI();
        });

    }

    private void checkReferral(String referralCode) {
        Query referralCodeQuery = userRef.orderByChild("referral_id").equalTo(referralCode);
        referralCodeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Referral code is valid
                    DataSnapshot referrerSnapshot = dataSnapshot.getChildren().iterator().next();
                    String referrerId = referrerSnapshot.child("user_id").getValue(String.class);
                    String referrerCode = referrerSnapshot.child("referral_id").getValue(String.class);
                    Toast.makeText(CheckReferralActivity.this, ""+referrerCode + " " +referrerId, Toast.LENGTH_SHORT).show();
                   handleValidReferralCode(referrerId, referrerCode);
                } else {
                    // Invalid referral code
                   // handleInvalidReferralCode();
                    Toast.makeText(CheckReferralActivity.this, "Invalid id", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error occurred while validating referral code
               // handleReferralCodeValidationFailure(databaseError.getMessage());
                Toast.makeText(CheckReferralActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleValidReferralCode(String referrerId, String referrerCode) {
        // Update the referrer's tree with the new user
        DatabaseReference referrerTreeRef = FirebaseDatabase.getInstance().getReference().child("referralTrees").child(referrerId);
        referrerTreeRef.child("referralCount").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Integer referralCount = mutableData.getValue(Integer.class);
                if (referralCount == null) {
                    // If the referral count is null, initialize it to 1
                    referralCount = 1;
                } else {
                    // Increment the referral count
                    referralCount++;
                }
                mutableData.setValue(referralCount);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {
                if (committed) {
                    // User added to the referrer's tree successfully
                   handleReferralAddedToTree(referrerId);
                } else {
                    // Failed to add user to the referrer's tree
                  //  handleReferralTreeUpdateFailure(databaseError.getMessage());
                }
            }
        });
    }

    private void handleReferralAddedToTree(String referrerId) {
        String pushId= UUID.randomUUID().toString();
        Referrals referrals = new Referrals(FirebaseAuth.getInstance().getCurrentUser().getUid(),pushId);
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference("Referrals");
          reference.child(referrerId)
                .child(pushId)
                .setValue(referrals).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if (task.isComplete()&& task.isSuccessful()){
                              updateUI();
                          }else {
                              Toast.makeText(CheckReferralActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                          }
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                      }
                  });


    }


    private void updateUI() {
        startActivity(new Intent(CheckReferralActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
//            updateUI();
//           // checkIfProfileCompleted(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        }
      //  checkIfProfileCompleted(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
    private void checkIfProfileCompleted(String userId) {

        userRef.child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            User user =snapshot.getValue(User.class);
                            if (user.getCnic_front().isEmpty()){
                                Toast.makeText(CheckReferralActivity.this, "Cnic not added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CheckReferralActivity.this,HomeActivity.class));
                                finishAffinity();
                            }else {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//        mRef.child(userId)
//                .child("complete")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()){
//                            isProfileCompleted=   snapshot.getValue(boolean.class);
//                            if (isProfileCompleted)
//                                updateUI();
//
//                        }else if (FirebaseAuth.getInstance().getCurrentUser()!=null ){
//                           updateUI();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
    }
}