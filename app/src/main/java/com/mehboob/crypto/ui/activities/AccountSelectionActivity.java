package com.mehboob.crypto.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.mehboob.crypto.databinding.ActivityAccountSelectionBinding;
import com.mehboob.crypto.ui.adapters.AccountsListAdapter;

import java.util.ArrayList;

public class AccountSelectionActivity extends AppCompatActivity {
private ActivityAccountSelectionBinding binding;
private AccountsListAdapter adapter;
private ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAccountSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list= new ArrayList<>();
        list.add("USDT (BEP 20)");
        list.add("Etherium");
        list.add("Bitcoin");



        adapter= new AccountsListAdapter(this,list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AccountsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Code to finish the Activity
                finish();
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });




    }
}