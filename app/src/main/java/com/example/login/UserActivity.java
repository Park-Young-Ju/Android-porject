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

import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;      // 파이어베이스 인증처리
    private DatabaseReference mDatabaseRef;  // 실시간 데이터베이스
    private String mEtEmail;
    private String mEtPwd;
    private String mEtName;
    private String mEtBirth;
    private String mEtPhone;       // 회원가입 입력필드
    private Button mBtnEdit;             // 회원가입 버튼


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("앱이름");

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); //현재 회원가입된 유저를 갸져옴
        UserAccount account = new UserAccount();


        mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount ua = dataSnapshot.getValue(UserAccount.class);

                //각각의 값 받아오기 get어쩌구 함수들은 Together_group_list.class에서 지정한것
                mEtEmail = ua.getEmailId();
                mEtPwd = ua.getPassword();
                mEtPhone = ua.getPhone();
                mEtName = ua.getUserName();
                mEtBirth = ua.getBirth();

                //텍스트뷰에 받아온 문자열 대입하기
                TextView email = (TextView) findViewById(R.id.et_email);
                TextView pwd = (TextView) findViewById(R.id.et_pwd);
                TextView name = (TextView) findViewById(R.id.et_name);
                TextView phone = (TextView) findViewById(R.id.et_phone);
                TextView birth = (TextView) findViewById(R.id.et_birth);
                email.setText(mEtEmail);
                pwd.setText(mEtPwd);
                phone.setText(mEtPhone);
                name.setText(mEtName);
                birth.setText(mEtBirth);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        mBtnEdit = findViewById(R.id.btn_edit);

    }

    public void btn(View v) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

}