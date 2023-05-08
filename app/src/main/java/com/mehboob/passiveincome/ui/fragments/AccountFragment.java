package com.mehboob.passiveincome.ui.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.security.ConfirmationNotAvailableException;
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
import com.mehboob.passiveincome.BuildConfig;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.FragmentAccountBinding;
import com.mehboob.passiveincome.databinding.FragmentHomeBinding;
import com.mehboob.passiveincome.ui.activities.InvitedActivity;
import com.mehboob.passiveincome.ui.activities.MyInvitationsActivity;


public class AccountFragment extends Fragment {

private FragmentAccountBinding binding;
private DatabaseReference database;
private String code;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAccountBinding.inflate(inflater,container,false);
        database=FirebaseDatabase.getInstance().getReference("Users");
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
        return binding.getRoot();
    }

    private void fetchReferralCode() {

        database.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("referral_id")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                           code= snapshot.getValue(String.class);
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
        Toast.makeText(getContext(), "Text copied to clipboard " +text, Toast.LENGTH_SHORT).show();
    }
    private void shareApp(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage= "\nLet me recommend you this application use the refer code \n "+binding.txtReferalCode.getText().toString()+ "\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}