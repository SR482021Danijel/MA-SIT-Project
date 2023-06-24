package com.ftn.ma_sit_project.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;

import java.io.IOException;

public class HomeFragment extends Fragment {
    View view;

    AppCompatActivity activity;

    MqttHandler mqttHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnPlay = view.findViewById(R.id.play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!isOnline()) {
//                    Toast.makeText(activity.getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
//                } else {
                if (Data.loggedInUser == null) {
                    Toast.makeText(activity.getApplicationContext(), "Please log in/register", Toast.LENGTH_SHORT).show();
                } else {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new LoadingScreenFragment())
                            .setReorderingAllowed(true)
                            .commit();
                }

            }


//        }
        });
        Button btnSolo = view.findViewById(R.id.play_solo);
        btnSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SkockoFragment())
                        .setReorderingAllowed(true)
                        .commit();
            }
        });


        return view;
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        mqttHandler = new MqttHandler();
    }
}