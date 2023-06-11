package com.example.helperapp.ShopAndMenuList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperapp.Adapter.ShopAdapter;
import com.example.helperapp.MainActivity;
import com.example.helperapp.R;
import com.example.helperapp.Shop.Shop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ShopListActivity extends AppCompatActivity implements ShopAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private ShopAdapter adapter;
    private List<Shop> shopList;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shop");



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        recyclerView = findViewById(R.id.recyclerr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopList = new ArrayList<>();
        adapter = new ShopAdapter(shopList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopList.clear();

                for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                    Shop shop = shopSnapshot.getValue(Shop.class);
                    if (shop != null) {
                        shopList.add(new Shop(shop.getEt_name(), shop.getEt_telenum(), shop.getEt_title(), shop.getEt_content()));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 오류 처리
            }
        });
    }

    @Override
    public void onItemClick(String shopName) {
        Intent intent = new Intent(ShopListActivity.this, MainActivity.class);
        intent.putExtra("shopName", shopName);
        startActivity(intent);
    }
}




