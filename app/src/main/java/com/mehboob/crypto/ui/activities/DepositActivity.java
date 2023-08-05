package com.mehboob.crypto.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mehboob.crypto.databinding.ActivityDepositBinding;
import com.mehboob.crypto.ui.models.Accounts;
import com.mehboob.crypto.ui.models.Deposit;
import com.mehboob.crypto.utils.SharedPref;

import java.util.UUID;

public class DepositActivity extends AppCompatActivity {
    private ActivityDepositBinding binding;
    private String selectedPackage;
    private DatabaseReference accountRef, depositRef;
    private static final int IMAGE_REQUEST = 13;
    private Uri uri;
    private StorageReference storageReference;
    private long currentTimeStamp;
    private ProgressDialog dialog;
    private String pushId;
    private String balance,account;
    private SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDepositBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait.....");
        sharedPref=new SharedPref(this);

       balance= getIntent().getStringExtra("balance");
       account =getIntent().getStringExtra("account");
      //  selectedPackage = getIntent().getStringExtra("package");

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
       switch (account){

           case "usd":
               binding.lineEth.setVisibility(View.GONE);
               binding.lineBtc.setVisibility(View.GONE);
               break;
           case "btc":
               binding.lineUsdt.setVisibility(View.GONE);
               binding.lineEth.setVisibility(View.GONE);
               break;
           case "eth":
               binding.lineUsdt.setVisibility(View.GONE);
               binding.lineBtc.setVisibility(View.GONE);
               break;

       }
        accountRef = FirebaseDatabase.getInstance().getReference("Accounts");
        storageReference = FirebaseStorage.getInstance().getReference("Deposit");
        depositRef = FirebaseDatabase.getInstance().getReference("Deposit");
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });




        fetchAccounts();
//        if (selectedPackage != null) {
//            binding.linePackage.setVisibility(View.VISIBLE);
//            binding.txtSelectedPackage.setText(selectedPackage);
//        } else {
//            binding.linePackage.setVisibility(View.GONE);
//        }

        binding.imgCopyUsdtNumber.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtUsdtAccountNumber.getText().toString());
        });



        binding.imgCopyBtcNumber.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtBtcAccountNumber.getText().toString());
        });

        binding.imgCopyEthNumber.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtEthAccountNumber.getText().toString());
        });




        binding.btnUploadScreenShot.setOnClickListener(v -> {
            currentTimeStamp = System.currentTimeMillis();
            pushId = UUID.randomUUID().toString();

            getScreenShot();
//            depositRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    if (snapshot.child("timeStamp").exists()) {
////                        Deposit deposit = snapshot.getValue(Deposit.class);
////
////                        long lastCompletionTime = Long.parseLong(deposit.getTimeStamp());
////                        long timeSinceCompletion = currentTimeStamp - lastCompletionTime;
////                        int hoursDifference = (int) (timeSinceCompletion / (1000 * 60 * 60));
////                        if (hoursDifference >= 24) {
////                            getScreenShot();
////                        } else {
////                            Toast.makeText(DepositActivity.this, "You can deposit only once in 24 hours", Toast.LENGTH_SHORT).show();
////                        }
////                    } else {
////                        getScreenShot();
////                    }
//
//                    if (sn)
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(DepositActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });

        });
    }

    private void getScreenShot() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void fetchAccounts() {
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Accounts accounts = snapshot.getValue(Accounts.class);

                    binding.txtUsdtAccountNumber.setText(accounts.getUsdtNumber());

                    binding.txtEthAccountNumber.setText(accounts.getEthNumber());

                    binding.txtBtcAccountNumber.setText(accounts.getBtcNumber());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            binding.imgScreenShot.setVisibility(View.VISIBLE);
            binding.imgScreenShot.setImageURI(uri);
            uploadImage(uri);
        }
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
        Toast.makeText(this, "Text copied to clipboard " + text, Toast.LENGTH_SHORT).show();
    }

    private void uploadImage(Uri imageUri) {
        dialog.show();
        // Create a reference to the image file in Firebase Storage

        StorageReference imageReference = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ScreenShot").child(pushId+"Image");


        // Upload image to Firebase Storage
        UploadTask uploadTask = imageReference.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Get the download URL of the image from Firebase Storage
            imageReference.getDownloadUrl().addOnSuccessListener(downloadUrl -> uploadData(downloadUrl.toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), currentTimeStamp, balance,sharedPref.fetchBalance(),account));
        }).addOnFailureListener(e -> {
            // Display an error message to the user
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void uploadData(String downloadUrl, String uid, long currentTimeStamp, String balance,String totalUserBalance,String account) {

        depositRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(pushId).setValue(new Deposit(downloadUrl, uid, String.valueOf(currentTimeStamp), balance, false,pushId,totalUserBalance,account))
                .addOnCompleteListener(task -> {
                    if (task.isComplete()) {

                        Toast.makeText(this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    } else {
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).addOnFailureListener(e -> {

                    Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });

    }
}