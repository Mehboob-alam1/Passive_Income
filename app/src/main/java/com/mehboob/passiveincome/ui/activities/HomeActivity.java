package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivityHomeBinding;
import com.mehboob.passiveincome.ui.fragments.AccountFragment;
import com.mehboob.passiveincome.ui.fragments.ContactUsFragment;
import com.mehboob.passiveincome.ui.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomeBinding binding;
   private Fragment fragment;
   private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


         bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_UNLABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();





    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_profile:
                fragment = new AccountFragment();
                break;
            case R.id.nav_contact_us:
                fragment = new ContactUsFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
        return true;
    }
    public Context getContext() {
        return this;
    }

}
