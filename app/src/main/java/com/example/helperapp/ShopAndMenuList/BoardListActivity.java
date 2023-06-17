package com.example.helperapp.ShopAndMenuList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperapp.Adapter.BoardAdapter;
import com.example.helperapp.BoardActivity;
import com.example.helperapp.MainActivity;
import com.example.helperapp.R;
import com.example.helperapp.Shop.Board;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BoardListActivity extends AppCompatActivity implements BoardAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private BoardAdapter adapter;
    private List<Board> boardList;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Board");



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        recyclerView = findViewById(R.id.recyclerr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        boardList = new ArrayList<>();
        adapter = new BoardAdapter(boardList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boardList.clear();

                for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                    Board board = shopSnapshot.getValue(Board.class);
                    if (board != null) {
                        boardList.add(new Board(board.getEt_name(), board.getEt_telenum(), board.getEt_title(), board.getEt_content()));
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
        Intent intent = new Intent(BoardListActivity.this, MainActivity.class);
        intent.putExtra("shopName", shopName);
        startActivity(intent);
    }
}




