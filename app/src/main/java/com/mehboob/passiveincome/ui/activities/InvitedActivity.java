package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityInvitedBinding;
import com.mehboob.passiveincome.ui.adapters.ReferralAdapter;
import com.mehboob.passiveincome.ui.models.Referrals;

import java.util.ArrayList;

public class InvitedActivity extends AppCompatActivity {
    private ActivityInvitedBinding binding;
    private DatabaseReference databaseReference;
    private ArrayList<String> list;
private ReferralAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvitedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list= new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Referrals");

        fetchReferrals();


    }

    private void fetchReferrals() {
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();

                            for (DataSnapshot snap : snapshot.getChildren()) {
                                Referrals referrals = snap.getValue(Referrals.class);
                                list.add(referrals.getUserId());

                            }
                            adapter= new ReferralAdapter(InvitedActivity.this,list);
                            binding.ecyclerRefferal.setLayoutManager(new LinearLayoutManager(InvitedActivity.this));
                            binding.ecyclerRefferal.setAdapter(adapter);
                        }else{
                            Toast.makeText(InvitedActivity.this, "No referral exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}