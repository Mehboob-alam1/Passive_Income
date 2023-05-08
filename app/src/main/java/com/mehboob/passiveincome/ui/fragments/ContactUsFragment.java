package com.mehboob.passiveincome.ui.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.FragmentAccountBinding;
import com.mehboob.passiveincome.databinding.FragmentContactUsBinding;
import com.mehboob.passiveincome.ui.activities.HomeActivity;
import com.mehboob.passiveincome.ui.activities.LoginActivity;
import com.mehboob.passiveincome.ui.activities.MyDepositsActivity;
import com.mehboob.passiveincome.ui.activities.MyWithdrawActivity;
import com.mehboob.passiveincome.ui.models.User;


public class ContactUsFragment extends Fragment {

    private FragmentContactUsBinding binding;
    private DatabaseReference mRef;
    private User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactUsBinding.inflate(inflater, container, false);

        mRef = FirebaseDatabase.getInstance().getReference("Users");


        fetchData();
        binding.imgCopyReferCode.setOnClickListener(v -> {
            copyTextToClipboard(binding.txtReferralCode.getText().toString());
        });

        binding.btnLogout.setOnClickListener(v -> {


            showAlertDialog();

        });
        binding.txtInviteFriend.setOnClickListener(v -> {
            Fragment fragment = new AccountFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            HomeActivity.bottomNavigationView.setSelectedItemId(R.id.nav_profile);
        });

        binding.btnDeposits.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), MyDepositsActivity.class));
        });

        binding.btnWithdrawRequest.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), MyWithdrawActivity.class));
        });

        return binding.getRoot();
    }

    private void fetchData() {
        Context context = ((HomeActivity) getActivity()).getContext();
        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            user = snapshot.getValue(User.class);

                            Glide.with(context).load(user.getUser_image()).placeholder(R.drawable.profile)
                                    .into(binding.profileImage);

                            binding.txtUserName.setText(user.getFirst_name() + " " + user.getSur_name());
                            binding.txtPhoneNumber.setText(user.getPhone_number());
                            binding.txtGmail.setText(user.getEmail());
                            binding.txtAddress.setText(user.getAddress());
                            binding.txtReferralCode.setText(user.getReferral_id());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getContext().startActivity(intent);
        getActivity().finishAffinity();
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
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        final View customLayout = getLayoutInflater().inflate(R.layout.item_exit, null);
        alertDialog.setView(customLayout);

        TextView btnYes = customLayout.findViewById(R.id.btnYes);
        TextView  btnNo = customLayout.findViewById(R.id.btnNo);

        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        btnYes.setOnClickListener(v -> {

signOut();
        });

        btnNo.setOnClickListener(v -> {
            alert.dismiss();
        });

    }
}