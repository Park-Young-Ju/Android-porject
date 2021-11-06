package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class  LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;      // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;  // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;       // 로그인 입력필드

    public long mNow = System.currentTimeMillis();
    public Date mReDate = new Date(mNow);
    public SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String formatDate = mFormat.format(mReDate);

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("앱이름");


        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        String time = formatDate;

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();


                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); //현재 회원가입된 유저를 갸져옴
                        UserAccount account = new UserAccount();
                        account.setTime(time);
                        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("time").setValue(time);
                        reference = mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("time1");


                        Map<String, Object> map = new HashMap<String, Object>();

                        // key로 데이터베이스 오픈
                        String key = reference.push().getKey();
                        reference.updateChildren(map);
                        DatabaseReference dbRef = reference.child(key);
                        Map<String, Object> objectMap = new HashMap<String, Object>();
                        objectMap.put("str_name", time);
                        dbRef.updateChildren(objectMap);
                        //et_msg.setText("");


                        if (task.isSuccessful()) {
                            //로그인 성공
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // 현재 액티비티 파괴
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "로그인 실패..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}