package com.example.helperapp.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.helperapp.R;
import com.example.helperapp.User.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    // FirebaseAuth 인스턴스를 선언 Firebase 인증(Authentication) 기능
    private FirebaseAuth mFirebaseAuth;
    // DatabaseReference 인스턴스를 선언, Realtime Database에 접근하기 위한 참조(reference)를 생성하는 데 사용
    private DatabaseReference mDatabaseReference;
    // 입력 받을 이메일과 비밀번호 인스턴스 선언
    private EditText et_email, et_pwd, et_name, et_teleNum;
    // 버튼 인스턴스 선언
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // FirebaseAuth의 인스턴스를 초기화, Firebase 인증 기능을 사용하기 위해 필요한 인스턴스를 가져 오는 것
        mFirebaseAuth = FirebaseAuth.getInstance();
        // FirebaseDatabase의 인스턴스를 가져온 다음, "FirebaseEmailAccount" 경로를 참조하는 DatabaseReference를 초기화
        // 사용자 계정 정보를 저장할 위치를 나타냄
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("FirebaseEmailAccount");

        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        et_name = findViewById(R.id.et_name);
        et_teleNum = findViewById(R.id.et_telenum);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(v -> {
            String strEmail = et_email.getText().toString();
            String strPwd = et_pwd.getText().toString();
            String strName = et_name.getText().toString();
            String strTeleNum = et_teleNum.getText().toString();

            // Firebase 인증 기능을 사용하여 사용자를 생성, 입력한 이메일 주소와 비밀번호를 사용하여 회원가입을 시도
            //createUserWithEmailAndPassword() 메서드의 작업이 완료될 때 실행되는 리스너를 추가
            // AuthResult는 인증 작업 결과를 나타내는 객체, OnCompleteListener의 onComplete() 메서드에서 회원가입 작업의 결과를 처리
            mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                // OnCompleteListener의 onComplete() 메서드 회원가입 작업이 완료되면 호출되며,
                // Task<AuthResult> 객체를 매개변수로 받음 task 객체를 통해 작업의 성공 여부와 결과 데이터에 액세스할 수 있음
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) { //유저가 다 만들어졌을 때
                    if (task.isSuccessful()) {
                        //회원가입이 성공한 경우, 현재 로그인된 사용자를 가져온다. getCurrentUser() 메서드는 FirebaseUser 객체를 반환.
                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                        UserAccount account = new UserAccount(); // UserAccount 객체 생성
                        // UserAccount 객체에 set 해주기
                        account.setEmail(firebaseUser.getEmail());
                        account.setPassword(strPwd);
                        account.setName(strName);
                        account.setTeleNum(strTeleNum);
                        account.setIdToken(firebaseUser.getUid());

                        // DB에 저장
                        mDatabaseReference.child("userAccount").child(firebaseUser.getUid()).setValue(account);

                        // LoginActivity로 가기 위한 이벤트 처리
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);

                        Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                    // 실패한 경우
                    else {
                        Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}