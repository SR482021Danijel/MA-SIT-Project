package com.ftn.ma_sit_project.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Region;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.Draggable;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;
import com.ftn.ma_sit_project.commonUtils.TempGetData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SkockoFragment extends Fragment {

    View view;
    CountDownTimer countDownTimer;
    TextView player1Score, player2UserName, p1UserName;
    AppCompatActivity activity;
    GridLayout gridLayout;
    ArrayList<ImageView> activeSlots = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<String> guesses = new ArrayList<>();
    ArrayList<ImageView> circleAnswers = new ArrayList<>();
    int score = 0;
    int attempt = 0;
    int j = 0;
    MqttHandler mqttHandler;
    boolean isMyTurn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_skocko, container, false);

        TempGetData.getSkocko(new TempGetData.FireStoreCallback() {
            @Override
            public void onCallBack(ArrayList<String> list) {
                answers.addAll(list);
            }
        });

        gridLayout = view.findViewById(R.id.targets);
        ViewGroup linear = view.findViewById(R.id.skocko_options);

        ImageView skocko = view.findViewById(R.id.option_skocko);
        ImageView rectangle = view.findViewById(R.id.option_rectangle);
        ImageView circle = view.findViewById(R.id.option_circle);
        ImageView heart = view.findViewById(R.id.option_heart);
        ImageView triangle = view.findViewById(R.id.option_triangle);
        ImageView star = view.findViewById(R.id.option_star);

        if (Data.loggedInUser != null && !player2UserName.getText().toString().equals("Guest")) {
            colorAllTiles(gridLayout, isMyTurn);
            colorAllTiles(linear, isMyTurn);
            if (isMyTurn) {
                Draggable.makeDraggable(skocko, "1");
                Draggable.makeDraggable(rectangle, "2");
                Draggable.makeDraggable(circle, "3");
                Draggable.makeDraggable(heart, "4");
                Draggable.makeDraggable(triangle, "5");
                Draggable.makeDraggable(star, "6");
            }
        } else {
            Draggable.makeDraggable(skocko, "1");
            Draggable.makeDraggable(rectangle, "2");
            Draggable.makeDraggable(circle, "3");
            Draggable.makeDraggable(heart, "4");
            Draggable.makeDraggable(triangle, "5");
            Draggable.makeDraggable(star, "6");
        }

        j = setNewTargets(gridLayout, activeSlots);

        if (Data.loggedInUser != null) {
            p1UserName.setText(Data.loggedInUser.getUsername());
        }


        Button btnNext = view.findViewById(R.id.btn_skocko);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFull = true;
                for (ImageView imageView : activeSlots) {
                    if (imageView != null && imageView.getTag() != null) {
                        imageView.setOnDragListener(null);
                        guesses.add((String) imageView.getTag());
                        Log.i("tag", imageView.getTag() + "");
                    } else {
                        isFull = false;
                        Log.i("error", "empty");
                    }
                }
                if (isFull) {

                    boolean isCorrect = guesses.equals(answers);
                    Log.i("answer", isCorrect + "");
                    if (isCorrect) {
                        score = Integer.parseInt((String) player1Score.getText());
                        switch (attempt) {
                            case 1:
                            case 2:
                                score += 20;
                                break;
                            case 3:
                            case 4:
                                score += 15;
                                break;
                            case 5:
                            case 6:
                                score += 10;
                                break;
                        }
                        player1Score.setText(score + "");
                        Toast.makeText(activity.getApplicationContext(), "Correct! Points: +" + score, Toast.LENGTH_SHORT).show();
                        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                            @Override
                            public void onTick(long l) {
                                Long min = ((l / 1000) % 3600) / 60;
                                Long sec = (l / 1000) % 60;
                            }

                            @Override
                            public void onFinish() {
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, new StepByStepFragment())
                                        .setReorderingAllowed(true)
                                        .commit();
                            }
                        }.start();
                    } else {
                        displayAnswer();
                        guesses.clear();
                        activeSlots.clear();
                        circleAnswers.clear();
                        j = setNewTargets(gridLayout, activeSlots);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        TextView scoreTimer = activity.findViewById(R.id.score_timer);

        p1UserName = activity.findViewById(R.id.player_1_user_name);
        player1Score = activity.findViewById(R.id.player_1_score);
        player2UserName = activity.findViewById(R.id.player_2_user_name);

        ShowHideElements.showScoreBoard(activity);

        mqttHandler = new MqttHandler();

        isMyTurn = mqttHandler.getTurnPlayer();

        countDownTimer = new CountDownTimer(90000, 1000) {
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

    public int setNewTargets(ViewGroup parent, ArrayList<ImageView> list) {
        int i;
        for (i = j; i < j + 5; i++) {
            final View child = parent.getChildAt(i);
            if (!(child instanceof ViewGroup)) {
                if (child != null) {
                    Draggable.makeTarget((ImageView) child);
                    list.add((ImageView) child);
                }
            } else {
                for (int k = 0; k < 4; k++) {
                    View circleChild = ((ViewGroup) child).getChildAt(k);
                    if (circleChild != null) {
                        circleAnswers.add((ImageView) circleChild);
                    }
                }
            }
        }
        attempt++;
        return j = i;
    }

    public void displayAnswer() {
        ArrayList<Integer> statusList = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            if (Objects.equals(answers.get(i), guesses.get(i))) {
                statusList.add(1);
            } else if (guesses.contains(answers.get(i))) {
                statusList.add(2);
            }
        }
        Collections.sort(statusList);
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i) == 1) {
                circleAnswers.get(i).setColorFilter(Color.RED);
            } else if (statusList.get(i) == 2) {
                circleAnswers.get(i).setColorFilter(Color.YELLOW);
            }
            Log.i("status", statusList.get(i).toString());
        }
    }

    public void colorAllTiles(ViewGroup parent, boolean isMyTurn) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (!(child instanceof ViewGroup)) {
                if (!isMyTurn) {
                    child.setBackgroundColor(Color.RED);
                } else {
                    child.setBackgroundColor(Color.BLUE);
                }
            }
        }
    }
}