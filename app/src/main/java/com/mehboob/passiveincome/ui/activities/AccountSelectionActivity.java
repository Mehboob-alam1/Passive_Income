package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.mehboob.passiveincome.databinding.ActivityAccountSelectionBinding;
import com.mehboob.passiveincome.ui.adapters.AccountsListAdapter;

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
        list.add("Jazz cash");
        list.add("Easy paisa");
        list.add("Allied Bank limited");
        list.add("Askri Bank");
        list.add("Bank alfalah limited");
        list.add("Bank al-Habib limited");
        list.add("Bank islami pakistan limited");
        list.add("Dubai islamic Bank  pakistan limited");
        list.add("Faysal Bank limited");
        list.add("Js Bank limited");
        list.add("MCB Bank limited");
        list.add("Meezan Bank limited");
        list.add("National  Bank of pakistan");
        list.add("United Bank limited");


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