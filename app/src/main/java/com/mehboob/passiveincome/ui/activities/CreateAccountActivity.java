package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivityCreateAccountBinding;
import com.mehboob.passiveincome.ui.models.User;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "CreateAccountActivity";
    private DatabaseReference mRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Users");

        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.btnCreateAccount.setOnClickListener(v -> {
            if (binding.etEmail.getText().toString().isEmpty())
                Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
            else if (binding.etPassword.getText().toString().isEmpty())
                Toast.makeText(this, "Password required", Toast.LENGTH_SHORT).show();
            else if (binding.etFirstName.getText().toString().isEmpty())
                Toast.makeText(this, "First name required", Toast.LENGTH_SHORT).show();
            else if (binding.etSurname.getText().toString().isEmpty())
                Toast.makeText(this, "Surname required", Toast.LENGTH_SHORT).show();
            else if (binding.etPhoneNumber.getText().toString().isEmpty())
                Toast.makeText(this, "Phone number required", Toast.LENGTH_SHORT).show();
            else if (binding.etAddress.getText().toString().isEmpty())
                Toast.makeText(this, "Address required", Toast.LENGTH_SHORT).show();
            else
                createAccount(binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString(),
                        binding.etFirstName.getText().toString(),
                        binding.etSurname.getText().toString(),
                        binding.etPhoneNumber.getText().toString(),
                        binding.etReferralId.getText().toString(),
                        binding.etAddress.getText().toString());
        });
        binding.txtSingIn.setOnClickListener(v -> {
            finish();
        });

        binding.etPassword.setOnTouchListener((view, event) -> {
            // Check if the user clicked the password toggle drawable
            if (event.getAction() == MotionEvent.ACTION_UP && event.getRawX() >= binding.etPassword.getRight() - binding.etPassword.getCompoundDrawables()[2].getBounds().width()) {
                // Toggle the password visibility
                if (binding.etPassword.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    // Hide the password
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_password_toggle_off, 0);
                } else {
                    // Show the password
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_password_toggle, 0);
                }
                return true;
            } else {
                return false;
            }
        });
    }

    private void createAccount(String email, String password, String first_name, String sur_name, String phone_number, String referral_id, String address) {
binding.textCreate.setVisibility(View.GONE);
binding.progressSignUp.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                        binding.textCreate.setVisibility(View.VISIBLE);
                        binding.progressSignUp.setVisibility(View.GONE);
                        uploadData(email, password, first_name, sur_name, phone_number, referral_id, address,userId);
                    } else {
                        // If sign in fails, display a message to the user.
                        binding.textCreate.setVisibility(View.VISIBLE);
                        binding.progressSignUp.setVisibility(View.GONE);
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void uploadData(String email, String password, String first_name, String sur_name, String phone_number, String referral_id, String address,String user_id) {
        User user = new User(email, password, first_name, sur_name, phone_number, referral_id, address,user_id);

        mRef.child(userId).setValue(user).addOnCompleteListener(task -> {
            if (task.isComplete() && task.isSuccessful()) {
                updateUI();
                Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CreateAccountActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(CreateAccountActivity.this, "Error adding data : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateUI() {
        startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
        finish();
    }

    
}