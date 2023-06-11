package com.example.helperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helperapp.Shop.Shop;
import com.example.helperapp.ShopAndMenuList.ShopListActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentPage4 extends Fragment {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    private Button btn_register;
    private EditText et_name, et_title, et_telenum, et_content;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_board, container, false);

        btn_register = view.findViewById(R.id.btn_register);
        et_name = view.findViewById(R.id.et_name);
        et_telenum = view.findViewById(R.id.et_telenum);
        et_title = view.findViewById(R.id.et_title);
        et_content = view.findViewById(R.id.et_content);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addShop(et_name.getText().toString(), et_telenum.getText().toString(),
                        et_title.getText().toString(), et_content.getText().toString());
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void addShop(String et_name, String et_telenum, String et_title, String et_content) {
        Shop shop = new Shop(et_name, et_telenum, et_title, et_content);
        databaseReference.child("Shop").child(et_name).setValue(shop);
    }
}