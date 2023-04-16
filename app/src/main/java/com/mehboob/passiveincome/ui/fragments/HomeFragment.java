package com.mehboob.passiveincome.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.FragmentHomeBinding;
import com.mehboob.passiveincome.ui.activities.DepositActivity;
import com.mehboob.passiveincome.ui.activities.WithdrawActivity;


public class HomeFragment extends Fragment {
private FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding=FragmentHomeBinding.inflate(inflater,container,false);


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

    private void startPackage(String package_name){
        Intent intent= new Intent(getContext(),DepositActivity.class);
        intent.putExtra("package",package_name);
        getContext().startActivity(intent);
    }
}