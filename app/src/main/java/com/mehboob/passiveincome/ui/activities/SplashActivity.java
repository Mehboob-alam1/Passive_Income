package com.mehboob.passiveincome.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
private ActivitySplashBinding binding;
private Animation topAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//Set animation to elements
        binding.imageView.setAnimation(topAnim);
     binding.   textView.setAnimation(bottomAnim);
       binding. textView2.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);

                startActivity(i);

                // close this activity

                finish();

            }

        }, 5*1000);
    }
}