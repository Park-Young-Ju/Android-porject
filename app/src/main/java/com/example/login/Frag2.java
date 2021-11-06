package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class Frag2 extends Fragment implements OnMapReadyCallback {

    private Marker currLocationMarker;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    public double receive1;
    public double receive2;

    public String[] array = new String[0];

    public Frag2() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag2, container, false);

        TextView textViewReceive1 = view.findViewById(R.id.receive1);
        TextView textViewReceive2 = view.findViewById(R.id.receive2);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            bundle = getArguments();

            String text = bundle.getString("text");

            array = text.split(",", 3);

            textViewReceive1.setText(array[0]);
            textViewReceive2.setText(array[1]);

            receive1 = Double.parseDouble(array[0]);
            receive2 = Double.parseDouble(array[1]);
        }
        return view;
    }
    public void gmap() {

        if (receive1 > 0 && receive2 > 0) {
            if (currLocationMarker != null) {
                currLocationMarker.remove();
            }
            LatLng loc = new LatLng(receive1, receive2);
            currLocationMarker = mMap.addMarker(new MarkerOptions().position(loc).title("내위치"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
            return;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng loc = new LatLng(receive1, receive2);
        mMap.addMarker(new MarkerOptions().position(loc).title("내위치"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
        gmap();
    }
}