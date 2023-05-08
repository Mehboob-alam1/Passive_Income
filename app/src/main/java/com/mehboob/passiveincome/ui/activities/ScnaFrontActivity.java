package com.mehboob.passiveincome.ui.activities;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mehboob.passiveincome.databinding.ActivityScnaFrontBinding;

import java.io.ByteArrayOutputStream;

public class ScnaFrontActivity extends AppCompatActivity {
    private ActivityScnaFrontBinding binding;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap selectedImage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScnaFrontBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storageReference = FirebaseStorage.getInstance().getReference("Users");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        binding.nextStepbtn1.setOnClickListener(v -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] finalImage = baos.toByteArray();
            StorageReference path;
            path = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Documents").child(finalImage + "jpg");
            UploadTask uploadTask = path.putBytes(finalImage);
//
            uploadTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    uploadTask.addOnSuccessListener(taskSnapshot -> path.getDownloadUrl().addOnSuccessListener(uri -> {

                        downloadUrl = String.valueOf(uri);
                        Log.d("ScanActivity", "Success");

                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("cnic_front").setValue(downloadUrl).addOnCompleteListener(task1 -> {
                                    if (task1.isComplete() && task1.isSuccessful()) {
                                        Intent intent = new Intent(ScnaFrontActivity.this, ScanBackActivity.class);

                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(ScnaFrontActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }));
                } else {
                    Log.d("ScanActivity", "Failed");
                    Toast.makeText(ScnaFrontActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.scanDocumentbtn1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            selectedImage = (Bitmap) data.getExtras().get("data");
            binding.idscanImgFront.setImageBitmap(selectedImage);
            binding.scanDocumentbtn1.setVisibility(View.INVISIBLE);
            binding.nextStepbtn1.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Complete the profile", Toast.LENGTH_SHORT).show();
    }
}