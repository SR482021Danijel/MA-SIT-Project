package com.ftn.ma_sit_project.fragments;


import android.graphics.Color;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.Skocko;
import com.ftn.ma_sit_project.Model.SkockoDTO;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.Draggable;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;
import com.ftn.ma_sit_project.commonUtils.TempGetData;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SkockoFragment extends Fragment {

    View view;
    CountDownTimer countDownTimer;
    TextView player1Score, player2UserName, p1UserName, roundText;
    AppCompatActivity activity;
    GridLayout gridLayout;
    LinearLayout linearLayout;
    ArrayList<ImageView> activeSlots = new ArrayList<>();
    ArrayList<String> answers = new ArrayList<>();
    ArrayList<String> guesses = new ArrayList<>();
    ArrayList<ImageView> circleAnswers = new ArrayList<>();
    int score = 0, j = 0, attempt = 0;
    MqttHandler mqttHandler;
    boolean isMyTurn;
    Button btnNext;
    ImageView skocko, rectangle, circle, heart, triangle, star;

    public static SkockoFragment newInstance(String round, boolean isMyTurn) {

        Bundle args = new Bundle();
        args.putString("ROUND", round);
        args.putBoolean("TURN", !isMyTurn);

        SkockoFragment fragment = new SkockoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_skocko, container, false);

        gridLayout = view.findViewById(R.id.targets);
        linearLayout = view.findViewById(R.id.skocko_options);

        skocko = view.findViewById(R.id.option_skocko);
        rectangle = view.findViewById(R.id.option_rectangle);
        circle = view.findViewById(R.id.option_circle);
        heart = view.findViewById(R.id.option_heart);
        triangle = view.findViewById(R.id.option_triangle);
        star = view.findViewById(R.id.option_star);

        roundText = view.findViewById(R.id.round_text);

        if (getArguments() != null) {
            roundText.setText(getArguments().getString("ROUND"));
            isMyTurn = getArguments().getBoolean("TURN");
        }

        btnNext = view.findViewById(R.id.btn_skocko);
        btnNext.setVisibility(View.GONE);

//        Data.loggedInUser != null && !player2UserName.getText().toString().equals("Guest")

        if (Data.userStatus == 2) {
            colorAllTiles(gridLayout, isMyTurn);
            colorAllTiles(linearLayout, isMyTurn);
            setCheckMultiButton();
            if (isMyTurn) {
                setOptions();
                btnNext.setVisibility(View.VISIBLE);
            }
        } else {
            setOptions();
            btnNext.setVisibility(View.VISIBLE);
            setCheckSoloButton();
        }

        j = setNewTargets(gridLayout, activeSlots);

        if (Data.loggedInUser != null) {
            p1UserName.setText(Data.loggedInUser.getUsername());
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        TempGetData.getSkocko(new TempGetData.FireStoreCallback() {
            @Override
            public void onCallBack(ArrayList<String> list) {
                answers.addAll(list);
            }
        });

        TextView scoreTimer = activity.findViewById(R.id.score_timer);

        p1UserName = activity.findViewById(R.id.player_1_user_name);
        player1Score = activity.findViewById(R.id.player_1_score);
        player2UserName = activity.findViewById(R.id.player_2_user_name);

        ShowHideElements.showScoreBoard(activity);

        if (Data.userStatus == 2) {
            mqttHandler = new MqttHandler();

            if (getArguments() == null)
                isMyTurn = mqttHandler.getTurnPlayer();

            mqttHandler.skockoSubscribe(new MqttHandler.SkockoCallback() {
                @Override
                public void onCallback(ArrayList<String> dataList) {
//                    if (!isMyTurn) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 4; i++) {
                                int imageId = Skocko.getImage(dataList.get(i));
                                activeSlots.get(i).setImageResource(imageId);
                                activeSlots.get(i).setTag(dataList.get(i));
                                activeSlots.get(i).invalidate();
                            }
                            btnNext.performClick();
                        }
                    });
//                    }
                }
            });
        }


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

        mqttHandler.skockoUnsubscribe();

        ShowHideElements.hideScoreBoard(activity);

        activity.getSupportActionBar().show();

        ShowHideElements.unlockDrawerLayout(activity);
    }

    public void setCheckMultiButton() {
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
                                Toast.makeText(activity.getApplicationContext(), "Correct! Points: +" + 20, Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                            case 4:
                                score += 15;
                                Toast.makeText(activity.getApplicationContext(), "Correct! Points: +" + 15, Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                            case 6:
                                score += 10;
                                Toast.makeText(activity.getApplicationContext(), "Correct! Points: +" + 10, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        player1Score.setText(score + "");

                        displayAnswer();
                        if (isMyTurn) {
                            SkockoDTO skockoDTO = new SkockoDTO("Pera", guesses.get(0), guesses.get(1), guesses.get(2), guesses.get(3));
                            mqttHandler.skockoPublish(skockoDTO);
                        }

                        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                            @Override
                            public void onTick(long l) {
                            }

                            @Override
                            public void onFinish() {
                                if (roundText.getText().equals("Round: 1")) {
                                    Log.i("mqtt", "switch now");
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container, SkockoFragment.newInstance("Round: 2", isMyTurn))
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else {
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container, new MyNumberFragment())
                                            .setReorderingAllowed(true)
                                            .commit();
                                }

                            }
                        }.start();

                    } else if (attempt == 6) {
                        Log.i("mqtt", "switch now");

                        setNextTurn();

                        if (isMyTurn) {
                            skocko.setOnLongClickListener(null);
                            rectangle.setOnLongClickListener(null);
                            circle.setOnLongClickListener(null);
                            heart.setOnLongClickListener(null);
                            triangle.setOnLongClickListener(null);
                            star.setOnLongClickListener(null);
                            btnNext.setVisibility(View.GONE);
                        } else {
                            setOptions();
                            btnNext.setVisibility(View.VISIBLE);
                        }
                    } else if (attempt == 7) {
                        displayAnswer();
                    } else {
                        setNextTurn();
                    }
                }
            }
        });
    }

    public void setCheckSoloButton() {

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
                                Toast.makeText(activity.getApplicationContext(), "Correct! Points: +" + 20, Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                            case 4:
                                score += 15;
                                Toast.makeText(activity.getApplicationContext(), "Correct! Points: +" + 15, Toast.LENGTH_SHORT).show();
                                break;
                            case 5:
                            case 6:
                                score += 10;
                                Toast.makeText(activity.getApplicationContext(), "Correct! Points: +" + 10, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        player1Score.setText(score + "");

                        displayAnswer();
                        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long l) {
                            }

                            @Override
                            public void onFinish() {
                                if (roundText.getText().equals("Round: 1")) {
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container, SkockoFragment.newInstance("Round: 2", isMyTurn))
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else {
                                    getParentFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container, new MyNumberFragment())
                                            .setReorderingAllowed(true)
                                            .commit();
                                }

                            }
                        }.start();

                    } else {
                        setNextTurn();
                    }
                }
            }
        });
    }

    public void setNextTurn() {
        displayAnswer();
        guesses.clear();
        activeSlots.clear();
        circleAnswers.clear();
        j = setNewTargets(gridLayout, activeSlots);
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
        Log.i("mqtt", attempt + ". attempt");
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

    public void setOptions() {
        Draggable.makeDraggable(skocko, "1");
        Draggable.makeDraggable(rectangle, "2");
        Draggable.makeDraggable(circle, "3");
        Draggable.makeDraggable(heart, "4");
        Draggable.makeDraggable(triangle, "5");
        Draggable.makeDraggable(star, "6");
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Data.userStatus == 2) {
            int n;
            SkockoDTO skockoDTO = new SkockoDTO((String) player2UserName.getText(), "1", "2", "3", "4");
            SkockoDTO skockoDTOCorrect = new SkockoDTO((String) player2UserName.getText(), "3", "2", "4", "1");

            if (!isMyTurn) {
//                n = 6;
//                SkockoDTO skockoDTO = new SkockoDTO((String) player2UserName.getText(), "1", "2", "3", "4");
//                for (int i = 0; i < n; i++) {
//                    mqttHandler.skockoPublish(skockoDTO);
//                }


                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        mqttHandler.skockoPublish(skockoDTO);

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        mqttHandler.skockoPublish(skockoDTOCorrect);
                    }
                });


//                mqttHandler.skockoPublish(skockoDTO);
//                mqttHandler.skockoPublish(skockoDTO);
//                mqttHandler.skockoPublish(skockoDTO);
//                mqttHandler.skockoPublish(skockoDTO);
            } else {
//                n = 5;
//                for (int i = 0; i < n; i++) {
//                    mqttHandler.skockoPublish(skockoDTO);
//                }
            }
        }
    }
}