package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class Frag1 extends Fragment {
    private View view;

    @Nullable
    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false);
        // toggle 버튼을 id 로 찾아서 변수에 지정
        ToggleButton tButton = view.findViewById(R.id.tButton);
        // 시크바 id 로 찾아서 변수에 지정
        SeekBar Seek1 = view.findViewById(R.id.Seek1);
        TextView Text1 = view.findViewById(R.id.Text1);



        // 시크바
        Seek1.setMax(2);
        Seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Text1.setText("변경된값: " + i);
                ((MainActivity)getActivity()).sendData(Text1.getText().toString());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        // toggle에 setOnCheckedListener 달아주기
        tButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String toastMessage;
                        if(isChecked){
                            toastMessage = "열림";
                            ((MainActivity)getActivity()).sendData("9");
                            tButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.aa));
                        }
                        else{
                            toastMessage = "잠김";
                            ((MainActivity)getActivity()).sendData("8");
                            tButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.bb));
                        }
                        Toast.makeText(getActivity(), toastMessage,Toast.LENGTH_SHORT).show();
                    }
                }
        );


        return view;
    }

}
