package com.ftn.ma_sit_project.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.UserDTO;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyNumberFragment extends Fragment implements SensorEventListener {

    //region Vars
    View view;
    CountDownTimer countDownTimer;
    AppCompatActivity activity;
    TextView targetNumber, display, displayResult, player1Score, player2Score, roundText, scoreTimer;
    int potentialScore = 0;
    boolean isAllStopped = false, isMyTurn, isTargetSet = false, isOpponentCorrect = false, isMyCorrect;
    Button btnDelete, number1, number2, number3, number4, number5, number6, symbolBracketLeft, symbolBracketRight;
    Button symbolAdd, symbolSub, symbolMulti, symbolDivide, btnStop;
    double result, opponentNumber = 0;
    ArrayList<String> expressionList = new ArrayList<>();
    ArrayList<String> usedNumberList = new ArrayList<>();
    Random random;
    MqttHandler mqttHandler;
    SensorManager sensorManager;
    private Sensor sensor;

    //endregion

    public static MyNumberFragment newInstance(String round, boolean isMyTurn) {

        Bundle args = new Bundle();
        args.putString("ROUND", round);
        args.putBoolean("TURN", !isMyTurn);

        MyNumberFragment fragment = new MyNumberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_number, container, false);

        targetNumber = view.findViewById(R.id.number_target);
        btnDelete = view.findViewById(R.id.delete);
        display = view.findViewById(R.id.number_display);
        displayResult = view.findViewById(R.id.number_result);
        number1 = view.findViewById(R.id.number_1);
        number2 = view.findViewById(R.id.number_2);
        number3 = view.findViewById(R.id.number_3);
        number4 = view.findViewById(R.id.number_4);
        number5 = view.findViewById(R.id.number_5);
        number6 = view.findViewById(R.id.number_6);
        symbolBracketLeft = view.findViewById(R.id.symbol_bracket_left);
        symbolBracketRight = view.findViewById(R.id.symbol_bracket_right);
        symbolAdd = view.findViewById(R.id.symbol_add);
        symbolSub = view.findViewById(R.id.symbol_sub);
        symbolMulti = view.findViewById(R.id.symbol_multi);
        symbolDivide = view.findViewById(R.id.symbol_divide);
        roundText = view.findViewById(R.id.round_text_mn);

        if (getArguments() != null) {
            roundText.setText(getArguments().getString("ROUND"));
            isMyTurn = getArguments().getBoolean("TURN");
        }

        Button btnCheck = view.findViewById(R.id.check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Expression expression = new ExpressionBuilder(TextUtils.join("", expressionList)).build();
                    result = expression.evaluate();
                } catch (Exception e) {
                    Toast.makeText(activity.getApplicationContext(), "Incorrect expression", Toast.LENGTH_SHORT).show();
                }
                int score = Integer.parseInt((String) player1Score.getText());
                int oppoScore = Integer.parseInt((String) player2Score.getText());
                if (result != 0.0) {
                    displayResult.setText(result + "");
                    isMyCorrect = result == Double.parseDouble((String) targetNumber.getText());
                    if (isMyCorrect) {
                        if (isMyTurn) {
                            score += 20;
                            player1Score.setText(score + "");
                            UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), score, true, result);
                            mqttHandler.myNumberPublish(userDTO);
                            Toast.makeText(activity.getApplicationContext(), "Correct! Points: +20", Toast.LENGTH_SHORT).show();
                            endGame();
                        } else {
                            if (opponentNumber != 0) {
                                score += 20;
                                player1Score.setText(score + "");
                                UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), score, true, result);
                                mqttHandler.myNumberPublish(userDTO);
                                Toast.makeText(activity.getApplicationContext(), "Correct! Points: +20", Toast.LENGTH_SHORT).show();
                                endGame();
                            } else {
                                potentialScore += 20;
                                UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), score, true, result);
                                mqttHandler.myNumberPublish(userDTO);
                                Toast.makeText(activity.getApplicationContext(), "Sending to opponent", Toast.LENGTH_SHORT).show();

                            }
                        }

                    } else {
                        UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), score, false, result);
                        mqttHandler.myNumberPublish(userDTO);
//                        Toast.makeText(activity.getApplicationContext(), "Incorrect! Points: +0", Toast.LENGTH_SHORT).show();
                        if (isOpponentCorrect) {
                            oppoScore += 20;
                            player2Score.setText(oppoScore + "");
                        } else if (opponentNumber != 0) {
                            double target = Double.parseDouble((String) targetNumber.getText());
                            double myRange;
                            double theirRange;
                            if (target < result) {
                                myRange = result - target;
                            } else {
                                myRange = target - result;
                            }
                            if (target < opponentNumber) {
                                theirRange = opponentNumber - target;
                            } else {
                                theirRange = target - opponentNumber;
                            }

                            if (myRange < theirRange) {
                                score += 5;
                                player1Score.setText(score + "");
                            } else {
                                oppoScore += 5;
                                player2Score.setText(oppoScore + "");
                            }
                        }
                        if (opponentNumber != 0) {
                            endGame();
                        }
                    }
//                    CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
//                        @Override
//                        public void onTick(long l) {
//                            Long min = ((l / 1000) % 3600) / 60;
//                            Long sec = (l / 1000);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            getParentFragmentManager()
//                                    .beginTransaction()
//                                    .replace(R.id.fragment_container, new HomeFragment())
//                                    .setReorderingAllowed(true)
//                                    .commit();
//                        }
//                    }.start();
                    btnCheck.setOnClickListener(null);
                }
            }
        });

        btnStop = view.findViewById(R.id.numbers_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isTargetSet) {
//                    random.nextInt(max - min + 1) + min
                    int randomTarget = random.nextInt(1000 - 100 + 1) + 100;
//                    int randomTarget = ThreadLocalRandom.current().nextInt(100, 999 + 1);
                    targetNumber.setText(randomTarget + "");
                    isTargetSet = true;
                } else {
//                    int randomNum1 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    int randomNum1 = random.nextInt(10 - 1 + 1) + 1;
                    number1.setText(randomNum1 + "");
//                    int randomNum2 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    int randomNum2 = random.nextInt(10 - 1 + 1) + 1;
                    number2.setText(randomNum2 + "");
//                    int randomNum3 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    int randomNum3 = random.nextInt(10 - 1 + 1) + 1;
                    number3.setText(randomNum3 + "");
//                    int randomNum4 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    int randomNum4 = random.nextInt(10 - 1 + 1) + 1;
                    number4.setText(randomNum4 + "");
                    int randomNum5 = random.nextInt(3 - 1 + 1) + 1;
                    int randomNum6 = random.nextInt(4 - 1 + 1) + 1;
//                    int randomNum5 = ThreadLocalRandom.current().nextInt(1, 3 + 1);
//                    int randomNum6 = ThreadLocalRandom.current().nextInt(1, 4 + 1);
                    switch (randomNum5) {
                        case 1:
                            number5.setText(10 + "");
                            break;
                        case 2:
                            number5.setText(15 + "");
                            break;
                        case 3:
                            number5.setText(20 + "");
                            break;
                    }
                    switch (randomNum6) {
                        case 1:
                            number6.setText(25 + "");
                            break;
                        case 2:
                            number6.setText(50 + "");
                            break;
                        case 3:
                            number6.setText(75 + "");
                            break;
                        case 4:
                            number6.setText(100 + "");
                            break;
                    }
                    isAllStopped = true;
                }
                if (isAllStopped) {
                    btnStop.setOnClickListener(null);
                    expressionList.add("(");
                    expressionList.add("100");
                    expressionList.add("*");
                    expressionList.add("2");
                    expressionList.add(")");
                    expressionList.add("+");
                    expressionList.add("(");
                    expressionList.add("7");
                    expressionList.add("-");
                    expressionList.add("(");
                    expressionList.add("15");
                    expressionList.add("-");
                    expressionList.add("9");
                    expressionList.add(")");
                    expressionList.add(")");
                    expressionList.add("+");
                    expressionList.add("3");
                    display();
                }
            }
        });

        //#region btns

        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped && !findUsedNumber((String) number1.getText())) {
                    usedNumberList.add((String) number1.getText());
                    expressionList.add((String) number1.getText());
                    display();
                }
            }
        });

        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped && !findUsedNumber((String) number2.getText())) {
                    usedNumberList.add((String) number2.getText());
                    expressionList.add((String) number2.getText());
                    display();
                }
            }
        });

        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped && !findUsedNumber((String) number3.getText())) {
                    usedNumberList.add((String) number3.getText());
                    expressionList.add((String) number3.getText());
                    display();
                }
            }
        });

        number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped && !findUsedNumber((String) number4.getText())) {
                    usedNumberList.add((String) number4.getText());
                    expressionList.add((String) number4.getText());
                    display();
                }
            }
        });

        number5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped && !findUsedNumber((String) number5.getText())) {
                    usedNumberList.add((String) number5.getText());
                    expressionList.add((String) number5.getText());
                    display();
                }
            }
        });

        number6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped && !findUsedNumber((String) number6.getText())) {
                    usedNumberList.add((String) number6.getText());
                    expressionList.add((String) number6.getText());
                    display();
                }
            }
        });

        symbolBracketLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) symbolBracketLeft.getText());
                    display();
                }
            }
        });

        symbolBracketRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) symbolBracketRight.getText());
                    display();
                }
            }
        });

        symbolAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) symbolAdd.getText());
                    display();
                }
            }
        });

        symbolSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) symbolSub.getText());
                    display();
                }
            }
        });

        symbolMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) symbolMulti.getText());
                    display();
                }
            }
        });

        symbolDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) symbolDivide.getText());
                    display();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped && expressionList.size() > 0) {
                    if (findUsedNumber(expressionList.get(expressionList.size() - 1))) {
                        usedNumberList.remove(usedNumberList.size() - 1);
                    }
                    expressionList.remove(expressionList.size() - 1);
                    display();
                }
            }
        });
//#endregion

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        scoreTimer = activity.findViewById(R.id.score_timer);

        player1Score = activity.findViewById(R.id.player_1_score);
        player2Score = activity.findViewById(R.id.player_2_score);

        ShowHideElements.showScoreBoard(activity);

        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        if (getArguments() != null) {
            random = new Random(111);
        } else {
            random = new Random(222);
        }

        if (Data.userStatus == 2) {
            mqttHandler = new MqttHandler();

            if (getArguments() == null)
                isMyTurn = mqttHandler.getTurnPlayer();

            mqttHandler.myNumberSubscribe(new MqttHandler.PointCallback() {
                @Override
                public void onCallback(UserDTO userDTO) {
                    isOpponentCorrect = userDTO.isOpponentCorrect();
                    opponentNumber = userDTO.getOpponentNumber();

                    if (isOpponentCorrect) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity.getApplicationContext(), "Opponent won round", Toast.LENGTH_SHORT).show();
                                player2Score.setText(userDTO.getPoints() + "");
                                if (result != 0.0 || !isMyTurn)
                                    endGame();
                            }
                        });
                    } else {
                        if (opponentNumber != 0) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity.getApplicationContext(), "Opponent lost round", Toast.LENGTH_SHORT).show();
                                    if (result != 0.0) {
                                        if (!isMyTurn && isMyCorrect) {
                                            int score = Integer.parseInt((String) player1Score.getText());
                                            score += 20;
                                            player1Score.setText(score + "");
                                        }
                                        if (!isMyCorrect) {
                                            int score = Integer.parseInt((String) player1Score.getText());
                                            int oppoScore = Integer.parseInt((String) player2Score.getText());
                                            double target = Double.parseDouble((String) targetNumber.getText());
                                            double myRange;
                                            double theirRange;
                                            if (target < result) {
                                                myRange = result - target;
                                            } else {
                                                myRange = target - result;
                                            }
                                            if (target < opponentNumber) {
                                                theirRange = opponentNumber - target;
                                            } else {
                                                theirRange = target - opponentNumber;
                                            }

                                            if (myRange < theirRange) {
                                                score += 5;
                                                player1Score.setText(score + "");
                                            } else {
                                                oppoScore += 5;
                                                player2Score.setText(oppoScore + "");
                                            }
                                        }
                                        endGame();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }

        countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000) % 60;
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                scoreTimer.setText(format);
                if (scoreTimer.getText().equals("00:55")) {
                    btnStop.performClick();
                    btnStop.performClick();
                }
            }

            @Override
            public void onFinish() {
                scoreTimer.setText("00:00");
                if (result == 0.0){
                    int score = Integer.parseInt((String) player1Score.getText());
                    UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), score, false, 1);
                    mqttHandler.myNumberPublish(userDTO);
                }
                Toast.makeText(activity.getApplicationContext(), "Times up!", Toast.LENGTH_SHORT).show();
                endGame();
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

        if (Data.userStatus == 2)
            mqttHandler.myNumberUnsubscribe();

        activity.getSupportActionBar().show();

        sensorManager.unregisterListener(this);

        ShowHideElements.unlockDrawerLayout(activity);
    }

    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void display() {
        display.setText(TextUtils.join(" ", expressionList));
    }

    public boolean findUsedNumber(String number) {
        for (String symbol : usedNumberList) {
            if (symbol.equals(number))
                return true;
        }
        return false;
    }

    public void endGame() {
        countDownTimer.cancel();
        CountDownTimer endCountDownTimer = new CountDownTimer(7000, 1000) {
            @Override
            public void onTick(long l) {
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000) % 60;
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                scoreTimer.setText(format);
            }

            @Override
            public void onFinish() {

                if (roundText.getText().equals("Round: 2")) {
                    int score = Integer.parseInt((String) player1Score.getText());
                    int oppoScore = Integer.parseInt((String) player2Score.getText());

                    if (score > oppoScore){
                        Toast.makeText(activity.getApplicationContext(), "You Win", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "You Lose", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!isMyTurn && isMyCorrect) {
                        int score = Integer.parseInt((String) player1Score.getText());
                        UserDTO userDTO = new UserDTO(Data.loggedInUser.getUsername(), score, true, result);
                        mqttHandler.myNumberPublish(userDTO);
                    }
                }

                if (roundText.getText().equals("Round: 1")) {
                    Log.i("mqtt", "switch round now");
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, MyNumberFragment.newInstance("Round: 2", isMyTurn))
                            .setReorderingAllowed(true)
                            .commit();
                } else {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .setReorderingAllowed(true)
                            .commit();
                }
            }
        }.start();
    }


    private static final int SHAKE_THRESHOLD = 800;
    private long lastUpdate;
    private float last_x;
    private float last_y;
    private float last_z;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > SHAKE_THRESHOLD) {
//                Log.d("REZ", "shake detected w/ speed: " + speed);
                btnStop.performClick();
            }
            last_x = x;
            last_y = y;
            last_z = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}