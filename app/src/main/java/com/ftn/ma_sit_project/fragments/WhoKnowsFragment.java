package com.ftn.ma_sit_project.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.TempGetData;

import java.util.Locale;
import java.util.Objects;


public class WhoKnowsFragment extends Fragment {

    View view;

    TextView roundText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_who_knows, container, false);


//        Button btnNext = view.findViewById(R.id.btn_who_knows);
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getParentFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, new HyphensFragment())
//                        .setReorderingAllowed(true)
//                        .commit();
//            }
//        });

        roundText = view.findViewById(R.id.round_text);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView scoreTimer = getActivity().findViewById(R.id.score_timer);

//        TempGetData.setWhoKnows();

        TempGetData.getWhoKnows();

        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000) % 60;
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                scoreTimer.setText(format);
            }

            @Override
            public void onFinish() {
                scoreTimer.setText("00:00");
            }
        }.start();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        ((AppCompatActivity) getActivity()).findViewById(R.id.score_board).setVisibility(View.VISIBLE);

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onStop() {
        super.onStop();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        getActivity().findViewById(R.id.score_board).setVisibility(View.GONE);

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}