package com.mehboob.crypto.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mehboob.crypto.R;
import com.mehboob.crypto.ui.models.Level;

import java.util.ArrayList;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.LevelHolder>{

    private ArrayList<Level> list;
    private Context context;

    public LevelsAdapter(ArrayList<Level> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LevelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_level,parent,false);
        return new LevelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelHolder holder, int position) {

    Level level=    list.get(position);
holder.userName.setText(level.getUserName());
holder.amount.setText(level.getAmount());
holder.joinTime.setText(level.getJoinTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LevelHolder extends RecyclerView.ViewHolder{
private TextView userName,amount,joinTime;
        public LevelHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.txtUserNameAccount);
            amount=itemView.findViewById(R.id.txtUserAmountAccount);
            joinTime=itemView.findViewById(R.id.txtUserJoinTimeAccount);
        }
    }
}
