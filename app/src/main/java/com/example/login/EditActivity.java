package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.ID;

public class EditActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;      // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;  // 실시간 데이터베이스
    private Button mBtnEdit;             // 회원가입 버튼
    private EditText mEtEmail, mEtPwd, mEtPhone, mEtBirth, mEtName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mEtName = findViewById(R.id.et_name);
        mEtPhone = findViewById(R.id.et_phone);
        mEtBirth = findViewById(R.id.et_birth);
        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("앱이름");
        UserAccount account = new UserAccount();

        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount ua = dataSnapshot.getValue(UserAccount.class);

                //각각의 값 받아오기 get어쩌구 함수들은 Together_group_list.class에서 지정한것
                String email = ua.getEmailId();
                String pwd = ua.getPassword();
                String phone = ua.getPhone();
                String name = ua.getUserName();
                String birth = ua.getBirth();

                //텍스트뷰에 받아온 문자열 대입하기
                mEtEmail.setText(email);
                mEtPwd.setText(pwd);
                mEtPhone.setText(phone);
                mEtName.setText(name);
                mEtBirth.setText(birth);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });


        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); //현재 회원가입된 유저를 갸져옴

        mBtnEdit = findViewById(R.id.btn_edit);
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = mEtName.getText().toString();
                String strPhone = mEtPhone.getText().toString();
                String strBirth = mEtBirth.getText().toString();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("userName", strName);
                childUpdates.put("phone", strPhone);
                childUpdates.put("birth", strBirth);
                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).updateChildren(childUpdates);

                Intent intent = new Intent(EditActivity.this, UserActivity.class);
                startActivity(intent);
                
            }
        });


    }


}