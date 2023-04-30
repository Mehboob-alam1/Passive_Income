package com.mehboob.passiveincome.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.ui.activities.DepositDetailsActivity;
import com.mehboob.passiveincome.ui.models.Deposit;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class MyDepositAdapter extends RecyclerView.Adapter<MyDepositAdapter.DepositHolder> {
    private Context context;
    private ArrayList<Deposit> list;


    public MyDepositAdapter(Context context, ArrayList<Deposit> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DepositHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_my_deposit, parent, false);

        return new DepositHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepositHolder holder, int position) {

        Deposit deposit = list.get(position);

        try {
            Glide.with(context)
                    .load(deposit.getScreenShot())
                    .into(holder.imgScreenShot);

        } catch (IllegalArgumentException e) {

        }
        holder.txtDepositBalance.setText(deposit.getDepositBalance());

        Date date = new Date(Long.parseLong(deposit.getTimeStamp()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(date);
        holder.txtDepositDate.setText(dateString);
        holder.txtDepositAccount.setText(deposit.getDepositAccount());

        if (deposit.isApproved()) {
            holder.txtVerified.setVisibility(View.VISIBLE);
            holder.txtPending.setVisibility(View.GONE);
        } else {
            holder.txtVerified.setVisibility(View.GONE);
            holder.txtPending.setVisibility(View.VISIBLE);
        }
        holder.btnDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, DepositDetailsActivity.class);
            Gson gson = new Gson();
            String jsonUser = gson.toJson(deposit);
            intent.putExtra("user",jsonUser);
            context.startActivity(intent);
        });

    }

    private String getDate(String timeStamp) {

        Date date = new Date(timeStamp);

// Create a SimpleDateFormat object to format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

// Format the date as a string
        return dateFormat.format(date);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DepositHolder extends RecyclerView.ViewHolder {

        private TextView txtDepositBalance, txtDepositDate, txtDepositAccount;
        private TextView txtVerified, txtPending;
        private ImageView imgScreenShot;
        private TextView btnDetails;


        public DepositHolder(@NonNull View itemView) {
            super(itemView);
            txtDepositBalance = itemView.findViewById(R.id.txtDepositBalance);
            txtDepositDate = itemView.findViewById(R.id.txtDepositDate);
            txtDepositAccount = itemView.findViewById(R.id.txtDepositAccount);
            txtVerified = itemView.findViewById(R.id.txtVerified);
            txtPending = itemView.findViewById(R.id.txtNotVerified);
            btnDetails = itemView.findViewById(R.id.btnViewDetails);
            imgScreenShot = itemView.findViewById(R.id.imageScreenShot);

        }
    }
}
