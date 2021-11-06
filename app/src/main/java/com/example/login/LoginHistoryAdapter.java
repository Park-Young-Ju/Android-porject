package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class LoginHistoryAdapter extends RecyclerView.Adapter<LoginHistoryAdapter.ViewHolder> {

    private ArrayList<String> mDataName = null ;

    public LoginHistoryAdapter(ArrayList<String> list0) {
        mDataName = list0 ;
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_address,tv_phone ;
        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    @Override
    public LoginHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.history_adapter, parent, false) ;
        LoginHistoryAdapter.ViewHolder vh = new LoginHistoryAdapter.ViewHolder(view) ;
        return vh ;
    }

    @Override
    public void onBindViewHolder(LoginHistoryAdapter.ViewHolder holder, int position) {
        String text = mDataName.get(position) ;
        holder.tv_name.setText(text);
    }

    @Override
    public int getItemCount() {
        return mDataName.size() ;
    }

}
