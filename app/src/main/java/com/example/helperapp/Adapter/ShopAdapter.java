package com.example.helperapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperapp.R;
import com.example.helperapp.Shop.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private List<Shop> shopList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String shopName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ShopAdapter(List<Shop> shopList) {
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.board, parent, false);
        return new ShopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Shop shop = shopList.get(position);

        if (holder.shopNameTextView != null) {
            holder.shopNameTextView.setText(shop.getEt_name());
        }

        if (holder.shopTelTextView != null) {
            holder.shopTelTextView.setText(shop.getEt_telenum());
        }

        if (holder.shopTitleTextView != null) {
            holder.shopTitleTextView.setText(shop.getEt_title());
        }

        if (holder.shopConTextView != null) {
            holder.shopConTextView.setText(shop.getEt_content());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(shop.getEt_name());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {
        public TextView shopNameTextView;
        public TextView shopTelTextView;
        public TextView shopConTextView;
        public TextView shopTitleTextView;

        public ShopViewHolder(View view) {
            super(view);
            shopNameTextView = view.findViewById(R.id.et_name);
            shopTitleTextView = view.findViewById(R.id.et_title);
            shopTelTextView = view.findViewById(R.id.et_telenum);
            shopConTextView = view.findViewById(R.id.et_content);
        }
    }
}