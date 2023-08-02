package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivityLoginBinding;
import com.mehboob.passiveincome.utils.SharedPref;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private static final String TAG = "LoginActivity";
    private boolean isProfileCompleted;
    private SharedPref sharedPref;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        sharedPref = new SharedPref(this);
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        binding.txtSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
        });


        binding.btnSingIn.setOnClickListener(v -> {
            if (binding.etEmail.getText().toString().isEmpty())
                Toast.makeText(this, "Email field cannot be empty", Toast.LENGTH_SHORT).show();
            else if (binding.etPassword.getText().toString().isEmpty())
                Toast.makeText(this, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
            else
                singIn(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
        });
        binding.etPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
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
            }
        });
    }

    private void singIn(String email, String password) {
        dialog= new ProgressDialog(this);
        dialog.setTitle("Signing");
        dialog.setMessage("Please wait.....");
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                           dialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                           dialog.dismiss();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void updateUI() {

        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            updateUI();

            //checkIfProfileCompleted(currentUser.getUid());
        }
    }

    private void checkIfProfileCompleted(String userId) {
        userRef.child(userId)
                .child("complete")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            isProfileCompleted = snapshot.getValue(boolean.class);
                            if (isProfileCompleted)
                                updateUI();

                        } else {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}