package com.ftn.ma_sit_project.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;

import java.util.Locale;

public class AssociationsFragment extends Fragment {
    View view;
    Dialog dialog;

    CountDownTimer countDownTimer;

    AppCompatActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_associations, container, false);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pop_up_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button ok = dialog.findViewById(R.id.ok_dialog);
        Button cancel = dialog.findViewById(R.id.cancel_dialog);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText((getActivity()), "Potvrdi", Toast.LENGTH_SHORT).show();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SkockoFragment())
                        .setReorderingAllowed(true)
                        .commit();

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView a_button = view.findViewById(R.id.a_field);
        TextView b_button = view.findViewById(R.id.b_field);
        TextView c_button = view.findViewById(R.id.c_field);
        TextView d_button = view.findViewById(R.id.d_field);

        a_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        b_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        c_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        d_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        TextView scoreTimer = activity.findViewById(R.id.score_timer);

        ShowHideElements.showScoreBoard(activity);

        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long l) {
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000);
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                scoreTimer.setText(format);
            }

            @Override
            public void onFinish() {
                scoreTimer.setText("00:00");
            }
        }.start();

        activity.getSupportActionBar().hide();

        ShowHideElements.lockDrawerLayout(activity);
    }

    @Override
    public void onStop() {
        super.onStop();

        countDownTimer.cancel();

        ShowHideElements.hideScoreBoard(activity);

        activity.getSupportActionBar().show();

        ShowHideElements.unlockDrawerLayout(activity);
    }
}