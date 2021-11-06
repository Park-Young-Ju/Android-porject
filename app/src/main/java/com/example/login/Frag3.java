package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class Frag3 extends Fragment {

    private View view;
    Context mContext;
    @BindView(R.id.template_recycler)
    RecyclerView template_recycler;
    private FirebaseAuth mFirebaseAuth;      // 파이어베이스 인증처리

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("message");
    private DatabaseReference mDatabaseRef;  // 실시간 데이터베이

    private String time;

    ArrayList<String> aTime = new ArrayList<>();

    public Frag3(Context context) {
        mContext = context;
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        ButterKnife.bind(this,view);

        //InitRecylerView();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("앱이름");
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        UserAccount account = new UserAccount();
        reference = mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("time1");


        reference.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chatListener(dataSnapshot);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                chatListener(dataSnapshot);
            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    //RecyclerView
    public void InitRecylerView(){

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }
        template_recycler.setLayoutManager(new LinearLayoutManager(mContext)) ;
        LoginHistoryAdapter adapter = new LoginHistoryAdapter(list) ;
        template_recycler.setAdapter(adapter) ;
    }

    private void chatListener(DataSnapshot dataSnapshot) {
        // dataSnapshot 밸류값 가져옴
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            time = (String) ((DataSnapshot) i.next()).getValue();
            aTime.add(time);
            // 유저이름, 메시지를 가져와서 array에 추가
            //arrayAdapter.add(chat_user + " : " + str_msg);
        }


        template_recycler.setLayoutManager(new LinearLayoutManager(mContext)) ;
        LoginHistoryAdapter adapter = new LoginHistoryAdapter(aTime) ;
        template_recycler.setAdapter(adapter) ;

        // 변경된값으로 리스트뷰 갱신
        adapter.notifyDataSetChanged();


    }
}
