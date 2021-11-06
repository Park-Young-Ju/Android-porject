package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;      // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;  // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtName, mEtPwd1, mEtPhone ,mEtBirth;       // 회원가입 입력필드
    private Button mBtnRegister;             // 회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("앱이름");

        mEtEmail = findViewById(R.id.et_email);
        mEtName = findViewById(R.id.et_name);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtPwd1 = findViewById(R.id.et_pwd1);
        mEtPhone = findViewById(R.id.et_phone);
        mEtBirth = findViewById(R.id.et_birth);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String pattern1 = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
                String strEmail = mEtEmail.getText().toString();
                String strName = mEtName.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strPwd1 = mEtPwd1.getText().toString();
                String strPhone = mEtPhone.getText().toString();
                String strBirth = mEtBirth.getText().toString();

                // Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); //현재 회원가입된 유저를 갸져옴
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setUserName(strName);
                            account.setPassword(strPwd);
                            account.setPhone(strPhone);
                            account.setBirth(strBirth);

                            // setValue : database에 insert(삽입)하는 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        } else if (strPwd.length() < 6) {
                            Toast.makeText(RegisterActivity.this, "비밀번호를 6자리 이상으로 입력하세요.", Toast.LENGTH_SHORT).show();
                        } else if (strPwd != strPwd1) {
                            Toast.makeText(RegisterActivity.this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                        } else if (strName == "0") {
                            Toast.makeText(RegisterActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                        } else if (strEmail != pattern1) {
                            Toast.makeText(RegisterActivity.this, "이메일 형식으로 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}