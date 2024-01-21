package com.ftn.ma_sit_project.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.WhoKnows;
import com.ftn.ma_sit_project.Model.WhoKnowsAnswer;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;
import com.ftn.ma_sit_project.commonUtils.TempGetData;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class WhoKnowsFragment extends Fragment {

    View view;
    TextView roundText, roundQuestion, player1Score, player2Score;
    AppCompatActivity activity;
    CountDownTimer mainCountDownTimer;
    MqttHandler mqttHandler;
    boolean opponentAnswered = false, playerAnswered = false;
    int roundCounter = 1;

    public static WhoKnowsFragment newInstance(int round) {

        Bundle args = new Bundle();
        args.putInt("ROUND", round);

        WhoKnowsFragment fragment = new WhoKnowsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_who_knows, container, false);

        roundText = view.findViewById(R.id.round_text);
        roundQuestion = view.findViewById(R.id.round_question);

        if (getArguments() != null) {
            roundCounter = getArguments().getInt("ROUND");
            roundText.setText("Question " + roundCounter + ":");
        }

        GridLayout gridLayout = view.findViewById(R.id.answers);

//        TempGetData.setWhoKnows();

        TempGetData.getWhoKnows(mqttHandler.getRoundList().get(roundCounter - 1), new TempGetData.WhoFireStoreCallback() {
            @Override
            public void onCallback(WhoKnows whoKnows) {

                roundQuestion.setText(whoKnows.getQuestion());

                for (int i = 0; i < 4; i++) {
                    final Button child = (Button) gridLayout.getChildAt(i);
                    if (child != null) {
                        child.setText(whoKnows.getAnswers().get(i).getText());
                        child.setTag(whoKnows.getAnswers().get(i));
                        child.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                WhoKnowsAnswer answer = new WhoKnowsAnswer();
                                if (view.getTag() != null) {
                                    answer = (WhoKnowsAnswer) view.getTag();
//                                    Log.i("TAG", answer.getText() + " " + answer.isCorrect());
                                }

                                mqttHandler.whoKnowsPublish(answer);

                                if (answer.isCorrect()) {
                                    if (opponentAnswered) {
                                        Toast.makeText(activity.getApplicationContext(), "Too slow!", Toast.LENGTH_SHORT).show();
                                        child.setBackgroundColor(Color.YELLOW);
                                    } else {
                                        Toast.makeText(activity.getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                                        child.setBackgroundColor(Color.GREEN);
                                        giveScore(true, answer.isCorrect());
                                    }

                                    for (int i = 0; i < 4; i++) {
                                        final Button child = (Button) gridLayout.getChildAt(i);
                                        if (child != null) {
                                            child.setClickable(false);
                                        }
                                    }

                                } else {
                                    Toast.makeText(activity.getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                                    child.setBackgroundColor(Color.RED);

                                    for (int i = 0; i < 4; i++) {
                                        final Button child = (Button) gridLayout.getChildAt(i);
                                        if (child != null) {
                                            child.setClickable(false);
                                        }
                                    }
                                    giveScore(true, answer.isCorrect());
                                }
                                playerAnswered = true;

                            }
                        });
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
        player1Score = activity.findViewById(R.id.player_1_score);
        player2Score = activity.findViewById(R.id.player_2_score);

        if (Data.userStatus == 2) {
            mqttHandler = new MqttHandler();

            mqttHandler.getRoundList();

            mqttHandler.whoKnowsSubscribe(new MqttHandler.WhoKnowsCallback() {
                @Override
                public void onCallback(WhoKnowsAnswer answer) {
                    if (answer.isCorrect() && !playerAnswered) {
                        opponentAnswered = true;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                giveScore(false, answer.isCorrect());
                            }
                        });
                    } else if (!answer.isCorrect()) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                giveScore(false, answer.isCorrect());
                            }
                        });
                    }
                }
            });
        }

        mainCountDownTimer = new CountDownTimer(8000, 1000) {
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

                if (roundCounter < 5) {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, WhoKnowsFragment.newInstance(roundCounter + 1))
                            .setReorderingAllowed(true)
                            .commit();
                } else {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new SkockoFragment())
                            .setReorderingAllowed(true)
                            .commit();
                }
            }
        }.start();

        activity.getSupportActionBar().hide();

        ShowHideElements.showScoreBoard(activity);

        ShowHideElements.lockDrawerLayout(activity);
    }

    @Override
    public void onStop() {
        super.onStop();

        mainCountDownTimer.cancel();

        ShowHideElements.hideScoreBoard(activity);

        if (Data.userStatus == 2) {
            mqttHandler.WhoKnowsUnsubscribe();
        }

        activity.getSupportActionBar().show();

        ShowHideElements.unlockDrawerLayout(activity);
    }

    public void giveScore(boolean isPlayer1, boolean isCorrect) {
        int score;

        if (isPlayer1) {
            score = Integer.parseInt(player1Score.getText().toString());
        } else {
            score = Integer.parseInt(player2Score.getText().toString());
        }

        if (isCorrect) {
            score += 10;
        } else {
            score -= 5;
        }

        if (isPlayer1) {
            player1Score.setText(score + "");
        } else {
            player2Score.setText(score + "");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                WhoKnowsAnswer answer = new WhoKnowsAnswer("Pera","adf", false);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                mqttHandler.whoKnowsPublish(answer);
//            }
//        });
    }
}