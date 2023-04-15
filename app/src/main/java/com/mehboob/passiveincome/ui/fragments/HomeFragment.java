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



        return binding.getRoot();
    }
}