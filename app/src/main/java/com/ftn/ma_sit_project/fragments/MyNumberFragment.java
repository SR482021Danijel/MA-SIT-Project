package com.ftn.ma_sit_project.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class MyNumberFragment extends Fragment {

    View view;

    CountDownTimer countDownTimer;

    AppCompatActivity activity;

    TextView targetNumber;
    TextView display;
    int deleteStart = 0;
    boolean isTargetSet = false;
    boolean isAllStopped = false;
    Button btnDelete;
    Button number1;
    Button number2;
    Button number3;
    Button number4;
    Button number5;
    Button number6;
    Button symbolBracketLeft;
    Button symbolBracketRight;
    Button symbolAdd;
    Button symbolSub;
    Button symbolMulti;
    Button symbolDivide;
    double result;
    TextView displayResult;
    Button btnStop;
    TextView player1Score;

    ArrayList<String> expressionList = new ArrayList<>();

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

        StringBuffer stringBuffer = new StringBuffer();

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

                if (result != 0.0) {
                    displayResult.setText(result + "");
                    if (result == Double.parseDouble((String) targetNumber.getText())) {
                        int score = Integer.parseInt((String) player1Score.getText());
                        score += 20;
                        player1Score.setText(score + "");
                        Toast.makeText(activity.getApplicationContext(), "Correct! Points: +20", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                    }
                    CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                        @Override
                        public void onTick(long l) {
                            Long min = ((l / 1000) % 3600) / 60;
                            Long sec = (l / 1000);
                        }

                        @Override
                        public void onFinish() {
                            getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, new HomeFragment())
                                    .setReorderingAllowed(true)
                                    .commit();
                        }
                    }.start();
                    btnCheck.setOnClickListener(null);
                }

//
            }
        });

        btnStop = view.findViewById(R.id.numbers_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isTargetSet) {
                    int randomTarget = ThreadLocalRandom.current().nextInt(100, 999 + 1);
                    targetNumber.setText(randomTarget + "");
                    isTargetSet = true;
                } else {
                    int randomNum1 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    number1.setText(randomNum1 + "");
                    int randomNum2 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    number2.setText(randomNum2 + "");
                    int randomNum3 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    number3.setText(randomNum3 + "");
                    int randomNum4 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    number4.setText(randomNum4 + "");
                    int randomNum5 = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                    int randomNum6 = ThreadLocalRandom.current().nextInt(1, 4 + 1);
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
                }
            }
        });

        number1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) number1.getText());
                    display();
                }
            }
        });

        number2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) number2.getText());
                    display();
                }
            }
        });

        number3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) number3.getText());
                    display();
                }
            }
        });

        number4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) number4.getText());
                    display();
                }
            }
        });

        number5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
                    expressionList.add((String) number5.getText());
                    display();
                }
            }
        });

        number6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllStopped) {
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
                    expressionList.remove(expressionList.size() - 1);
                    display();
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

        ShowHideElements.showScoreBoard(activity);

        countDownTimer = new CountDownTimer(60000, 1000) {
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

    public void display() {
        display.setText(TextUtils.join(" ", expressionList));
    }
}