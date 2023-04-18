package com.mehboob.passiveincome.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.FragmentHomeBinding;
import com.mehboob.passiveincome.ui.activities.DepositActivity;
import com.mehboob.passiveincome.ui.activities.HomeActivity;
import com.mehboob.passiveincome.ui.activities.WithdrawActivity;
import com.mehboob.passiveincome.ui.models.User;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private DatabaseReference mRef;
    private User user;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRef = FirebaseDatabase.getInstance().getReference("Users");

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        loadData();
        binding.btnDeposit.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DepositActivity.class));
        });

        binding.btnWithdraw.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), WithdrawActivity.class));
        });


        binding.btnBasicPackage.setOnClickListener(v -> {
            startPackage("Basic");
        });
        binding.btnStandardPackage.setOnClickListener(v -> {
            startPackage("Standard");
        });

        binding.btnPremiumPackage.setOnClickListener(v -> {
            startPackage("Premium");
        });


        return binding.getRoot();
    }

    private void loadData() {
        Context context = ((HomeActivity) getActivity()).getContext();

        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                  user=  snapshot.getValue(User.class);
                    Glide.with(context).load(user.getUser_image()).placeholder(R.drawable.profile).into(binding.userImage);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startPackage(String package_name) {
        Intent intent = new Intent(getContext(), DepositActivity.class);
        intent.putExtra("package", package_name);
        getContext().startActivity(intent);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}