package com.mehboob.passiveincome.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mehboob.passiveincome.databinding.ActivityWithdrawBinding;
import com.mehboob.passiveincome.ui.models.Withdraw;
import com.mehboob.passiveincome.utils.SharedPref;

import java.util.UUID;

public class WithdrawActivity extends AppCompatActivity {
    private ActivityWithdrawBinding binding;
    private SharedPref sharedPref;
    private DatabaseReference withdrawRef, balanceRef;
    private long currentTimeStamp;
    private String pushId;
    private ProgressDialog dialog;
    private String totalBalance;
    private int userCurrentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait......");

        sharedPref = new SharedPref(this);
userCurrentBalance=Integer.parseInt(getIntent().getStringExtra("totalBalance"));

        withdrawRef = FirebaseDatabase.getInstance().getReference("Withdraws");
        balanceRef = FirebaseDatabase.getInstance().getReference("Balance");
        binding.btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
        checkTotalBalance();
        binding.lineAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, AccountSelectionActivity.class));
        });

        binding.txtCurrentBalance.setText("Available balance " +userCurrentBalance);
        binding.txtAccountName.setText(sharedPref.fetchAccount());
        binding.btnConfirmWithDraw.setOnClickListener(v -> {
            if (binding.etAmount.getText().toString().isEmpty())
                Toast.makeText(this, "Enter amount ", Toast.LENGTH_SHORT).show();
            else if (binding.etAccountNumber.getText().toString().isEmpty())
                Toast.makeText(this, "Enter account number", Toast.LENGTH_SHORT).show();
            else if (binding.etOwnerName.getText().toString().isEmpty())
                Toast.makeText(this, "Enter real name of owner", Toast.LENGTH_SHORT).show();
            else if (Integer.parseInt(binding.etAmount.getText().toString()) > userCurrentBalance)
                Toast.makeText(this, "Not much balance in wallet", Toast.LENGTH_SHORT).show();
            else if (Integer.parseInt(binding.etAmount.getText().toString())<200)
                Toast.makeText(this, "Minimum withdraw balance is 200", Toast.LENGTH_SHORT).show();
            else {
                currentTimeStamp = System.currentTimeMillis();
                pushId = UUID.randomUUID().toString();
                withdrawRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("timeStamp").exists()) {
                            Withdraw deposit = snapshot.getValue(Withdraw.class);

                            long lastCompletionTime = Long.parseLong(deposit.getTimeStamp());
                            long timeSinceCompletion = currentTimeStamp - lastCompletionTime;
                            int hoursDifference = (int) (timeSinceCompletion / (1000 * 60 * 60));
                            if (hoursDifference >= 24) {
                                dialog.show();
                                requestWithdraw(FirebaseAuth.getInstance().getCurrentUser().getUid(), pushId,
                                        false, binding.etAmount.getText().toString(), binding.etAccountNumber.getText().toString(), binding.etOwnerName.getText().toString(), String.valueOf(currentTimeStamp));
                            } else {
                                Toast.makeText(WithdrawActivity.this, "You can deposit only once in 24 hours", Toast.LENGTH_SHORT).show();
                            }

//                        else {
//                            Toast.makeText(WithdrawActivity.this, "Try again !", Toast.LENGTH_SHORT).show();
//                        }

                        } else {
                            dialog.show();
                            requestWithdraw(FirebaseAuth.getInstance().getCurrentUser().getUid(), pushId,
                                    false, binding.etAmount.getText().toString(), binding.etAccountNumber.getText().toString(), binding.etOwnerName.getText().toString(), String.valueOf(currentTimeStamp));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(WithdrawActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.txtAccountName.setText(sharedPref.fetchAccount());

    }

    private void requestWithdraw(String userId, String pushId, boolean isVerified, String withDrawAmount, String withDrawAccountNumber, String withDrawAccountName, String timeStamp) {

        Withdraw withdraw = new Withdraw(userId, pushId, isVerified, withDrawAmount, withDrawAccountNumber, withDrawAccountName, timeStamp);

        withdrawRef.child(userId)

                .setValue(withdraw)
                .addOnCompleteListener(task -> {
                    if (task.isComplete() && task.isSuccessful()) {
                        dialog.dismiss();
                        Toast.makeText(this, "Withdraw request send to admin", Toast.LENGTH_SHORT).show();


                        int totalBalanceUser = Integer.parseInt(totalBalance);
                        int withdrawBalance = Integer.parseInt(withDrawAmount);

                        int remainingBalance = totalBalanceUser - withdrawBalance;
                        balanceRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("totalBalance")
                                .setValue(String.valueOf(remainingBalance));
                        finish();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void checkTotalBalance() {

        balanceRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("totalBalance")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                             totalBalance = snapshot.getValue(String.class);





                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}