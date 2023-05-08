package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivityMainBinding;
import com.mehboob.passiveincome.ui.adapters.ViewPagerAdapter;
import com.mehboob.passiveincome.ui.models.ViewPagerClass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
    private ViewPagerAdapter adapter;
    private DatabaseReference userRef;

    private ArrayList<ViewPagerClass> list;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();

        userRef= FirebaseDatabase.getInstance().getReference("Users");

        mAuth=FirebaseAuth.getInstance();


        list.add(new ViewPagerClass(R.drawable.slider_img,"Quick and secure", "Deposit and withdraw Payments easily.\n" +
                "We got you covered with industry\n" +
                "standard security."));
        list.add(new ViewPagerClass(R.drawable.slider_img,"Quick and secure" ,"We provide good percentage with.\n" +
                "your team "));
        list.add(new ViewPagerClass(R.drawable.slider_img,"Quick and secure" , "Get bonus with every new member \n" +
                "All the best!"));



        adapter = new ViewPagerAdapter(MainActivity.this);
        binding.viewpager2.setAdapter(adapter);
        binding.dotsIndicator.attachTo(binding.viewpager2);
        binding.viewpager2.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position){
                    case 0:
                        controlButtons();
                        break;
                    case 1:
                        controlButtons();
                        break;
                    case 2:
                        binding.btnNext.setVisibility(View.INVISIBLE);
                        binding.btnStart.setVisibility(View.VISIBLE);

                        binding.btnStart.setOnClickListener(v -> {
                            // Intent intent = new Intent(BoardingActivity.this, LoginActivity.class);
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        });
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(BoardingActivity.this, "Clicked successfully", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(BoardingActivity.this, LoginActivity.class);
//                startActivity(intent);
                binding.viewpager2.setCurrentItem(binding.viewpager2.getCurrentItem() + 1, true);
            }
        });


    }

    private void controlButtons() {

        binding.btnNext.setVisibility(View.VISIBLE);
        binding.btnStart.setVisibility(View.INVISIBLE);

    }

    private void updateUI() {

        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            checkIsActive(currentUser);

        }
    }

    private void checkIsActive(FirebaseUser user) {
        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("block").exists()){
                boolean isBlock= (snapshot.child("block").getValue(boolean.class));
                  if (isBlock){
                      binding.btnNext.setClickable(false);
                      binding.btnStart.setClickable(false);
                      Toast.makeText(MainActivity.this, "You are blocked contact admin", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(MainActivity.this,UserBlockedActivity.class));
                      finish();
                  }else{
                      updateUI();
                  }
                }else{
                    Toast.makeText(MainActivity.this, "Something went wrong try checking your internet", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}