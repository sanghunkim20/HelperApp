package com.example.helperapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperapp.Board.Board;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BoardActivity extends AppCompatActivity {

    // 입력 받을 이메일과 비밀번호 인스턴스 선언
    private EditText et_name, et_teleNum, et_title, et_content, et_x, et_y;
    // 버튼 인스턴스 선언
    private Button btn_register;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        et_name = findViewById(R.id.et_name);
        et_teleNum = findViewById(R.id.et_telenum);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
       // et_x = findViewById(R.id.et_x);
       // et_y = findViewById(R.id.et_y);
        btn_register = findViewById(R.id.btn_register);

        // 버튼 누르면 값을 저장
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //에딧 텍스트 값을 문자열로 바꾸어 함수에 넣어줍니다.
                addBoard(et_name.getText().toString(),et_teleNum.getText().toString()
                        ,et_title.getText().toString(),et_content.getText().toString()
                        ,et_x.getText().toString(), et_y.getText().toString());
                //메뉴 페이지로 넘어가기
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // 값을 파이어베이스 Realtime database로 넘기는 함수
    public void addBoard(String et_name, String et_teleNum, String et_title, String et_content
    , String et_x, String et_y) {

        // 여기에서 직접 변수를 만들어서 값을 직접 넣는것도 가능.
        // ex) 갓 만들어진 가게의 이름을 입력해서 String shopName = FoodCamp; 등을 넣는 경우

        // Shop.java에서 선언했던 함수
        Board board = new Board(et_name, et_teleNum, et_title, et_content, et_x, et_y);

        // child는 해당 키 위치로 이동하는 함수
        // 키가 없는데 "Shop"와 name같이 값을 지정한 경우 자동으로 생성
        databaseReference.child("Board").child(et_name).setValue(board);
    }
}