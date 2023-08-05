package com.mehboob.crypto.ui.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.crypto.BuildConfig;
import com.mehboob.crypto.R;
import com.mehboob.crypto.databinding.FragmentAccountBinding;
import com.mehboob.crypto.databinding.FragmentHomeBinding;
import com.mehboob.crypto.ui.activities.InvitedActivity;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private DatabaseReference database, referalCountRef;
    private String code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance().getReference("Users");
        referalCountRef = FirebaseDatabase.getInstance().getReference("Referrals");
        binding.imgCopyReferCode.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtReferalCode.getText().toString());
        });
        binding.btnMore.setOnClickListener(v -> {
            //startActivity(new Intent(requireContext(), MyInvitationsActivity.class));
            startActivity(new Intent(requireContext(), InvitedActivity.class));
        });
        binding.btnShareApp.setOnClickListener(v -> {
            shareApp();
        });
        fetchReferralCode();


        fetchInvitedCount();
        return binding.getRoot();
    }

    private void fetchReferralCode() {

        database.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("referral_id")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            code = snapshot.getValue(String.class);
                            binding.txtReferalCode.setText(code);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void copyTextToClipboard(String text) {
        // Get the text from the TextView


        // Get the ClipboardManager
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // Create a new ClipData object to store the text
        ClipData clipData = ClipData.newPlainText("label", text);

        // Set the ClipData object as the clipboard data
        clipboardManager.setPrimaryClip(clipData);

        // Show a toast message to indicate that the text has been copied
        Toast.makeText(getContext(), "Text copied to clipboard " + text, Toast.LENGTH_SHORT).show();
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage = "\nLet me recommend you this application use the refer code \n " + binding.txtReferalCode.getText().toString() + "\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void fetchInvitedCount() {

        referalCountRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    int invited = (int) snapshot.getChildrenCount();

                    binding.txtInvited.setText(String.valueOf(invited));

                    binding.txtLevel.setText(checkTheLevelOfUser(String.valueOf(invited)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private String checkTheLevelOfUser(String invited) {
        int level = 0;

        int invite = Integer.parseInt(invited);


        if (invite > 0 && invite <= 10)
            level = 1;
        else if (invite > 10 && invite <= 20)
            level = 2;
        else if (invite > 20 && invite <= 30)
            level = 3;
        else if (invite > 30 && invite <= 40)
            level = 4;
        else if (invite > 40 && invite <= 50)
            level = 5;
        else if (invite > 50 && invite <= 60)
            level = 6;
        else if (invite > 60 && invite <= 70)
            level = 7;
        else if (invite > 70 && invite <= 80)
            level = 8;
        else if (invite > 80 && invite <= 90)
            level = 9;
        else if (invite > 90)
            level = 10;


        return String.valueOf(level);
    }
}