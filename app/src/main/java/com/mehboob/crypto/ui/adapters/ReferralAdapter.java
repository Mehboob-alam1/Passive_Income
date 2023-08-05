package com.mehboob.crypto.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mehboob.crypto.R;
import com.mehboob.crypto.ui.activities.ViewReferralActivity;

import java.util.ArrayList;

public class ReferralAdapter extends RecyclerView.Adapter<ReferralAdapter.ReferralHolder> {
    private Context context;
    private ArrayList<String> list;

    public ReferralAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReferralHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_referrals, parent, false);
        return new ReferralHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReferralHolder holder, int position) {
        String data = list.get(position);
        holder.txtId.setText(data);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewReferralActivity.class);
            intent.putExtra("uid",data);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReferralHolder extends RecyclerView.ViewHolder {
        TextView txtId;

        public ReferralHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtId);
        }
    }
}
