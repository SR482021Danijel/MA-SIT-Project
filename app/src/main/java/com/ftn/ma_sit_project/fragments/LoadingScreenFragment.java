package com.ftn.ma_sit_project.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.MainActivity;
import com.ftn.ma_sit_project.Model.Asocijacije;
import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingScreenFragment extends Fragment {

    View view;
    AppCompatActivity activity;
    //    HyphensFragment hyphensFragment = new HyphensFragment();
//    StepByStepFragment stepByStepFragment = new StepByStepFragment();
    AssociationsFragment associationsFragment = new AssociationsFragment();
    MqttHandler mqttHandler;
    CountDownTimer countDownTimer;
    TextView p1Name, p2Name, loadingText, tokenTextView;
    boolean localPlayerReady = false, localOpponentReady = false;
    User p2;
    Button btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        p1Name = activity.findViewById(R.id.player_1_user_name);

        p2Name = activity.findViewById(R.id.player_2_user_name);

        activity.getSupportActionBar().hide();

        ShowHideElements.lockDrawerLayout(activity);

        mqttHandler = new MqttHandler();

        mqttHandler.connect();

        mqttHandler.startMatchmaking();

        mqttHandler.pointShareSubscribe();

        mqttHandler.decideTurnPlayer(new MqttHandler.RoundListCallback() {
            @Override
            public void onCallback(boolean isMyTurn) {
                if (!isMyTurn) {
                    mqttHandler.roundListSubscribe();
                } else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    mqttHandler.roundListPublish();
                }
            }
        });

        mqttHandler.startGameSubscribe(new MqttHandler.StartGameCallback() {
            @Override
            public void onCallback(boolean opponentReady) {
                localOpponentReady = opponentReady;
                if (p2 != null && !Objects.equals(p2.getUsername(), Data.loggedInUser.getUsername())) {
                    if (localPlayerReady && localOpponentReady) {
//                        Toast.makeText(activity.getApplicationContext(), "Start Game", Toast.LENGTH_SHORT).show();
                        p2Name.setText(p2.getUsername());
                        p1Name.setText(Data.loggedInUser.getUsername());
//                    mainActivity.subtractOneToken(); TODO
                        associationsFragment.setIsOnline(true);
//                    stepByStepFragment.setIsOnline(true);
//                    associationsFragment.setIsOnline(true);
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new WhoKnowsFragment())
                                .setReorderingAllowed(true)
                                .commit();
                    }
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_loading_screen, container, false);

        btnCancel = view.findViewById(R.id.loading_btn);

        loadingText = view.findViewById(R.id.loading_text);

        MainActivity mainActivity = (MainActivity) getActivity();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .setReorderingAllowed(true)
                        .commit();
                mqttHandler.disconnect();
            }
        });

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
//                if (loadingText.getText().toString().equals("Loading...")) {
//                    loadingText.setText("Loading");
//                } else {
//                    loadingText.append(".");
//                }
            }

            @Override
            public void onFinish() {
                p2 = mqttHandler.getP2Username();
                localPlayerReady = true;
//                localOpponentReady = true;
                mqttHandler.StartGamePublish();

                if (p2 != null && !Objects.equals(p2.getUsername(), Data.loggedInUser.getUsername())) {
                    if (localPlayerReady && localOpponentReady) {
//                        Toast.makeText(activity.getApplicationContext(), "Start Game", Toast.LENGTH_SHORT).show();
                        p2Name.setText(p2.getUsername());
                        p1Name.setText(Data.loggedInUser.getUsername());
//                    mainActivity.subtractOneToken(); TODO
                        associationsFragment.setIsOnline(true);
//                    stepByStepFragment.setIsOnline(true);
//                    associationsFragment.setIsOnline(true);
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new WhoKnowsFragment())
                                .setReorderingAllowed(true)
                                .commit();
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "No opponent found", Toast.LENGTH_SHORT).show();
                    btnCancel.performClick();
                }
            }
        }.start();

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        countDownTimer.cancel();

        activity.getSupportActionBar().show();

        ShowHideElements.unlockDrawerLayout(activity);
    }
}