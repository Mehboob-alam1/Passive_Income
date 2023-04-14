package com.mehboob.passiveincome.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.FragmentAccountBinding;
import com.mehboob.passiveincome.databinding.FragmentContactUsBinding;


public class ContactUsFragment extends Fragment {

private FragmentContactUsBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentContactUsBinding.inflate(inflater,container,false);




        return binding.getRoot();
    }
}