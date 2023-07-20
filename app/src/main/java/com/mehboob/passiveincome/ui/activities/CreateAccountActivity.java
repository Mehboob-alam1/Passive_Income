package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivityCreateAccountBinding;
import com.mehboob.passiveincome.ui.models.User;
import com.mehboob.passiveincome.utils.SharedPref;

import java.util.UUID;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "CreateAccountActivity";
    private DatabaseReference mRef;
    private String userId;
    private static final int IMAGE_REQUEST = 13;
    private Uri uri;
    private StorageReference storageReference;
    private String userReferralCode;
    private boolean isProfileCompleted;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
sharedPref= new SharedPref(this);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("Users");
        storageReference= FirebaseStorage.getInstance().getReference("Users");
        userReferralCode = UUID.randomUUID().toString().substring(0, 8);
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        binding.imgUserProfile.setOnClickListener(v -> {
            pickImage();
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
            else if (uri==null)
                Toast.makeText(this, "Add your original picture", Toast.LENGTH_SHORT).show();
            else if (FirebaseAuth.getInstance().getCurrentUser()==null)
                createAccount(binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString(),
                        binding.etFirstName.getText().toString(),
                        binding.etSurname.getText().toString(),
                        binding.etPhoneNumber.getText().toString(),
                        binding.etReferralId.getText().toString(),
                        binding.etAddress.getText().toString());
            else if (FirebaseAuth.getInstance().getCurrentUser() !=null && binding.textCreate.getText().toString().equals("Authenticated ! Save the data")){
                uploadImage(uri,new User(binding.etEmail.getText().toString(), binding.etPassword.getText().toString(), binding.etFirstName.getText().toString(), binding.etSurname.getText().toString(), binding.etPhoneNumber.getText().toString(), userReferralCode, binding.etAddress.getText().toString(), userId,"","","",false,false));
            }else
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

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

    private void pickImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, IMAGE_REQUEST);

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
                        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        binding.textCreate.setVisibility(View.VISIBLE);
                        binding.progressSignUp.setVisibility(View.GONE);
         binding.textCreate.setText("Authenticated ! Save the data");

                       // uploadData(email, password, first_name, sur_name, phone_number, referral_id, address, userId);
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

    private void uploadData(User user) {


        mRef.child(userId).setValue(user).addOnCompleteListener(task -> {
            if (task.isComplete() && task.isSuccessful()) {
                binding.textCreate.setVisibility(View.VISIBLE);
                binding.progressSignUp.setVisibility(View.GONE);
                sharedPref.saveIsUser(true);
                updateUI();
                Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
            } else {
                binding.textCreate.setVisibility(View.VISIBLE);
                binding.progressSignUp.setVisibility(View.GONE);
                Toast.makeText(CreateAccountActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(CreateAccountActivity.this, "Error adding data : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateUI() {


        Intent i = new Intent(CreateAccountActivity.this,CheckReferralActivity.class);
        i.putExtra("code",binding.etReferralId.getText().toString());
        startActivity(i);

//        startActivity(new Intent(CreateAccountActivity.this, CheckReferralActivity.class));
//        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            binding.imgUserProfile.setImageURI(uri);
        }
    }
    private void uploadImage(Uri imageUri,User user) {

        // Create a reference to the image file in Firebase Storage
        String imageName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageReference=        storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Image");

        binding.textCreate.setVisibility(View.GONE);
        binding.progressSignUp.setVisibility(View.VISIBLE);
        // Upload image to Firebase Storage
        UploadTask uploadTask = imageReference.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Get the download URL of the image from Firebase Storage
            imageReference.getDownloadUrl().addOnSuccessListener(downloadUrl -> {

              uploadData(new User(user.getEmail(),user.getPassword(),user.getFirst_name(),user.getSur_name(),user.getPhone_number(),user.getReferral_id(),user.getAddress(),user.getUser_id(),downloadUrl.toString(),"","",false,false));

                Toast.makeText(getApplicationContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            // Display an error message to the user
            binding.textCreate.setVisibility(View.VISIBLE);
            binding.progressSignUp.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }



    private void checkIfProfileCompleted(String userId) {

        mRef.child(userId)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                             User user =snapshot.getValue(User.class);
                             if (user.getCnic_front().isEmpty()){
                                 Toast.makeText(CreateAccountActivity.this, "Cnic not added", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(CreateAccountActivity.this,HomeActivity.class));
                             }else {

                             }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

    }
}