package com.example.helperapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperapp.R;
import com.example.helperapp.User.UserAccount;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<UserAccount> userList;

    public UserAdapter(List<UserAccount> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserAccount user = userList.get(position);
        holder.tvEmail.setText("" + user.getEmail());
        holder.tvName.setText("" + user.getName());
        holder.tvTeleNum.setText("전화번호 : " + user.getTeleNum());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvName, tvTeleNum;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvName = itemView.findViewById(R.id.tvName);
            tvTeleNum = itemView.findViewById(R.id.tvTeleNum);
        }
    }
}
