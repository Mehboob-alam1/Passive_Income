package com.mehboob.passiveincome.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mehboob.passiveincome.R;
import com.mehboob.passiveincome.utils.SharedPref;

import java.util.ArrayList;

public class AccountsListAdapter extends RecyclerView.Adapter<AccountsListAdapter.AccountHolder> implements Filterable {
private Context context;
private ArrayList<String> listAccounts;
private SharedPref sharedPref;
    private ArrayList<String> mDataListFiltered;
    private OnItemClickListener mListener;

    public AccountsListAdapter(Context context, ArrayList<String> listAccounts) {
        this.context = context;
        this.listAccounts = listAccounts;
        sharedPref=new SharedPref(context);
        mDataListFiltered=new ArrayList<>();
    }

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_accounts,parent,false);
        return new AccountHolder(view);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {

     String account=   listAccounts.get(position);
     holder.tv.setText(account);

     holder.itemView.setOnClickListener(v -> {
         Toast.makeText(context, ""+listAccounts.get(position), Toast.LENGTH_SHORT).show();
         sharedPref.saveAccount(listAccounts.get(position));
         if (mListener != null) {
             mListener.onItemClick(holder.getAdapterPosition());
         }
     });

    }

    @Override
    public int getItemCount() {
        return listAccounts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                ArrayList<String> filteredList = new ArrayList<>();
                if (query.isEmpty()) {
                    filteredList.addAll(listAccounts);
                } else {
                    for (String data : listAccounts) {

                            filteredList.add(data);

                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataListFiltered.clear();
                mDataListFiltered.addAll((ArrayList<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class AccountHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.txt_account);

        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
