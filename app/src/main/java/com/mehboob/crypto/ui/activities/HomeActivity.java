package com.mehboob.crypto.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.mehboob.crypto.R;
import com.mehboob.crypto.databinding.ActivityHomeBinding;
import com.mehboob.crypto.ui.fragments.AccountFragment;
import com.mehboob.crypto.ui.fragments.ContactUsFragment;
import com.mehboob.crypto.ui.fragments.HomeFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityHomeBinding binding;
    private Fragment fragment;
    public static BottomNavigationView bottomNavigationView;
    public static int UPDATE_CODE = 22;
    AppUpdateManager appUpdateManager;


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


        inAppUpdate();

    }

    private void inAppUpdate() {

        appUpdateManager= AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> task =appUpdateManager.getAppUpdateInfo();
        task.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                 && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){

                    try {
                        appUpdateManager.startUpdateFlowForResult(result,AppUpdateType.FLEXIBLE,
                                HomeActivity.this,UPDATE_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
appUpdateManager.registerListener(stateUpdatedListener);
    }
    InstallStateUpdatedListener stateUpdatedListener = installState ->{

        if (installState.installStatus() == InstallStatus.DOWNLOADED){
            popup();
        }

    };

    private void popup() {

        Snackbar snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "App update almost done",
                Snackbar.LENGTH_INDEFINITE
        );

        snackbar.setAction("Relod", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUpdateManager.completeUpdate();
            }
        });

        snackbar.setTextColor(Color.parseColor("#FF0000"));
        snackbar.show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_CODE){


            if (resultCode !=RESULT_OK){

            }
        }
    }
}
