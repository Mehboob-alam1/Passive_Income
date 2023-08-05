package com.mehboob.crypto.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mehboob.crypto.databinding.ActivityDepositDetailsBinding;
import com.mehboob.crypto.ui.models.Deposit;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositDetailsActivity extends AppCompatActivity {
private ActivityDepositDetailsBinding binding;
private String user;
private Deposit deposit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDepositDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      user=  getIntent().getStringExtra("user");
        Gson gson = new Gson();
        Type type = new TypeToken<Deposit>(){}.getType();
         deposit = gson.fromJson(user, type);


         binding.btnBack.setOnClickListener(v -> {
             finish();
         });

         try{
             Glide.with(this)
                     .load(deposit.getScreenShot())
                     .into(binding.imgScreenShot);
         }catch (IllegalArgumentException e){

         }

         binding.txtDepositThrough.setText(deposit.getDepositAccount());
         binding.txtBalanceDeposit.setText(deposit.getDepositBalance());
         binding.txtTotalBalance.setText(deposit.getTotalBalance());
        Date date = new Date(Long.parseLong(deposit.getTimeStamp()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        binding.txtDateDeposit.setText(dateString);

        if (deposit.isApproved()){
            binding.txtVerified.setVisibility(View.VISIBLE);
            binding.txtNotVerified.setVisibility(View.GONE);
        }else{
            binding.txtVerified.setVisibility(View.GONE);
            binding.txtNotVerified.setVisibility(View.VISIBLE);
        }

    }
}