package com.example.helperapp.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.helperapp.MainActivity;
import com.example.helperapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // // FirebaseAuth 인스턴스를 선언 Firebase 인증(Authentication) 기능
    // 각각의 텍스트, 버튼 인스턴스 선언
    private EditText et_email, et_pwd;
    private Button btn_login;
    private TextView btn_register_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register_1 = findViewById(R.id.btn_register_1);
        // 회원가입 이벤트 처리
        btn_register_1.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // FirebaseAuth의 인스턴스를 초기화, Firebase 인증 기능을 사용하기 위해 필요한 인스턴스를 가져옴
        mFirebaseAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);

        // 로그인 버튼 이벤트 처리
        btn_login.setOnClickListener(v -> {
            String strEmail = et_email.getText().toString();
            String strPwd = et_pwd.getText().toString();
            // Firebase 인증 기능을 사용하여 사용자 로그인을 시도, 입력한 이메일 주소와 비밀번호를 사용하여 로그인을 시도 함
            mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // 성공한 경우
                    if(task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    // 실패한 경우
                    else {
                        Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}


