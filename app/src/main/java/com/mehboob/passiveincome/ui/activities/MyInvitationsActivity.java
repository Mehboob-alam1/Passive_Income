package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.material.tabs.TabLayout;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivityMyInvitationsBinding;
import com.mehboob.passiveincome.ui.adapters.InvitationFragmentAdapter;

public class MyInvitationsActivity extends AppCompatActivity {
private ActivityMyInvitationsBinding binding;
private FragmentManager fragmentManager;
private InvitationFragmentAdapter adapter;
private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMyInvitationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
tabLayout=findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Level 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Level 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Level 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Level 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Level 5"));

        fragmentManager= getSupportFragmentManager();
        adapter= new InvitationFragmentAdapter(fragmentManager,getLifecycle());
        binding.viewpager2.setAdapter(adapter);
binding.btnBack.setOnClickListener(v ->{
    finish();
});

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }
}