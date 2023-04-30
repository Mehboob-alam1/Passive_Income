package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityMyDepositsBinding;
import com.mehboob.passiveincome.ui.adapters.MyDepositAdapter;
import com.mehboob.passiveincome.ui.models.Deposit;

import java.util.ArrayList;

public class MyDepositsActivity extends AppCompatActivity {
    private ActivityMyDepositsBinding binding;

    private MyDepositAdapter adapter;
    private ArrayList<Deposit> list;
    private DatabaseReference depositRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyDepositsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list = new ArrayList<>();
        depositRef = FirebaseDatabase.getInstance().getReference("Deposit");
        checkForDeposits();
    }

    private void checkForDeposits() {

        depositRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot snap : snapshot.getChildren()) {

                                Deposit deposits = snap.getValue(Deposit.class);
                                list.add(new Deposit(deposits.getScreenShot(), deposits.getUserId(), deposits.getTimeStamp(),
                                        deposits.getDepositBalance(), deposits.isApproved(),
                                        deposits.getPushId(), deposits.getTotalBalance(),
                                        deposits.getDepositAccount()));

                            }
                            adapter = new MyDepositAdapter(MyDepositsActivity.this, list);
                            binding.recyclerDeposit.setLayoutManager(new LinearLayoutManager(MyDepositsActivity.this));
                            binding.recyclerDeposit.setAdapter(adapter);
                        } else {
                            binding.txtNoData.setVisibility(View.VISIBLE);
                            binding.recyclerDeposit.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}