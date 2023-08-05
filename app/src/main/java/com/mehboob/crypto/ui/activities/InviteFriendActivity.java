package com.mehboob.crypto.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mehboob.crypto.databinding.ActivityInviteFriendBinding;

public class InviteFriendActivity extends AppCompatActivity {
private ActivityInviteFriendBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInviteFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}