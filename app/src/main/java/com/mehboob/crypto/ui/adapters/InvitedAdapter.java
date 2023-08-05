package com.mehboob.crypto.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mehboob.crypto.R;
import com.mehboob.crypto.ui.models.Invited;

import java.util.ArrayList;

public class InvitedAdapter extends RecyclerView.Adapter<InvitedAdapter.InviteHolder> {

    private Context context;
    private ArrayList<Invited> invitedArrayList;

    public InvitedAdapter(Context context, ArrayList<Invited> invitedArrayList) {
        this.context = context;
        this.invitedArrayList = invitedArrayList;
    }

    @NonNull
    @Override
    public InviteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_level, parent, false);
        return new InviteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteHolder holder, int position) {

        Invited invited = invitedArrayList.get(position);

        holder.txtUserNameAccount.setText(invited.getTxtUserNameAccount());
        holder.txtUserAmountAccount.setText(invited.getTxtUserAmountAccount());
        holder.txtUserJoinTimeAccount.setText(invited.getTxtUserJoinTimeAccount());

    }

    @Override
    public int getItemCount() {
        return invitedArrayList.size();
    }

    public class InviteHolder extends RecyclerView.ViewHolder {
        TextView txtUserNameAccount, txtUserAmountAccount, txtUserJoinTimeAccount;

        public InviteHolder(@NonNull View itemView) {
            super(itemView);

            txtUserNameAccount = itemView.findViewById(R.id.txtUserNameAccount);
            txtUserAmountAccount = itemView.findViewById(R.id.txtUserAmountAccount);
            txtUserJoinTimeAccount = itemView.findViewById(R.id.txtUserJoinTimeAccount);
        }
    }
}
