package com.ftn.ma_sit_project.fragments;

import static com.google.common.reflect.Reflection.getPackageName;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.TempGetData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HyphensFragment extends Fragment {
    View view;

    TextView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,lastLetButton;
    Map<String, Object> map1 = new HashMap<>();
    List<TextView> leftbtns = new ArrayList<>();
    List<TextView> rightbtns = new ArrayList<>();

    int counter = 0;

    boolean isClickedAgain = false;

    private void setupButtonListeners(List<TextView> leftButtons, List<TextView> rightButtons) {
        String[] lastLeftButtonText = new String[1];
            for (TextView leftButton : leftButtons) {
                leftButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if(isClickedAgain == true){
                            for (TextView b : leftButtons) {
                                b.setEnabled(true);
                            }
                            isClickedAgain = false;
                        }else {
                            lastLeftButtonText[0] = leftButton.getText().toString();
                            lastLetButton = leftButton;
                            for (TextView b : leftButtons) {
                                if (b == lastLetButton) {
                                    isClickedAgain = true;
                                    continue;
                                } else {
                                    b.setEnabled(false);
                                }
                            }
                            for (TextView b : rightButtons) {
                                b.setEnabled(true);
                            }
                        }
                    }
                });
            }

            for (TextView rightButton : rightButtons) {
                rightButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String rightButtonText = rightButton.getText().toString();
                        if (lastLeftButtonText[0].equals(rightButtonText)) {
                            Log.d("LITS", "lavor11");
                            lastLetButton.setBackgroundColor(Color.GREEN);
                            rightButton.setBackgroundColor(Color.GREEN);
                            lastLetButton.setClickable(false);
                            rightButton.setClickable(false);
                        } else {
                            Log.d("LITS", "JOK");
                            lastLetButton.setBackgroundColor(Color.RED);
                            lastLetButton.setClickable(false);
                        }
                        for (TextView b : leftButtons) {
                            b.setEnabled(true);
                        }
                        for (TextView b : rightButtons) {
                            b.setEnabled(false);
                        }
                        counter++;
                        if(counter == 5){
                            counter = 0;

//            List<String> keys = new ArrayList<>(map1.keySet());
//            List<Object> keysValues = new ArrayList<>(map1.values());
//
//            for (Button button : leftbtns) {
//                if (!keys.isEmpty()) {
//                    int randomIndex = new Random().nextInt(keys.size());
//                    String randomKey = keys.get(randomIndex);
//                    button.setText(randomKey);
//                    keys.remove(randomIndex);
//
//                }
//            }
//
//            for (Button button : rightbtns) {
//                if (!keysValues.isEmpty()) {
//                    int randomIndex = new Random().nextInt(keysValues.size());
//                    String randomValue = keysValues.get(randomIndex).toString();
//                    button.setText(randomValue);
//                    keysValues.remove(randomIndex);
//                }
//            }
                            int i = 1;
                            int a = 2;
                            for (Map.Entry<String, Object> entry : map1.entrySet()) {
                                String buttonKey = entry.getKey();
                                String buttonValue = entry.getValue().toString();
                                int resIDKey = getResources().getIdentifier("btn" + i, "id", getActivity().getPackageName());
                                TextView buttonKey1 = getActivity().findViewById(resIDKey);
                                buttonKey1.setText(buttonKey);
                                int resIDValue = getResources().getIdentifier("btn" + a, "id", getActivity().getPackageName());
                                TextView buttonValue1 = getActivity().findViewById(resIDValue);
                                buttonValue1.setText(buttonValue);
                                i += 2;
                                a += 2;
                                ColorDrawable viewColor = (ColorDrawable) buttonKey1.getBackground();
                                if(viewColor.getColor() == Color.GREEN){
                                    buttonKey1.setBackgroundColor(Color.GREEN);
                                    buttonValue1.setBackgroundColor(Color.GREEN);
                                }else {
                                    buttonKey1.setBackgroundColor(Color.RED);
                                    buttonValue1.setBackgroundColor(Color.RED);
                                }
                            }
                        }
                    }
                });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hyphens, container, false);

        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
        btn3 = view.findViewById(R.id.btn3);
        btn4 = view.findViewById(R.id.btn4);
        btn5 = view.findViewById(R.id.btn5);
        btn6 = view.findViewById(R.id.btn6);
        btn7 = view.findViewById(R.id.btn7);
        btn8 = view.findViewById(R.id.btn8);
        btn9 = view.findViewById(R.id.btn9);
        btn10 = view.findViewById(R.id.btn10);

        Button btnNext = view.findViewById(R.id.hyphens);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AssociationsFragment())
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        TempGetData.getDataAsMap(new TempGetData.FireStoreCallback1() {
            @Override
            public void onCallBack(Map<String, Object> map) {
                map1.putAll(map);

                List<String> keys = new ArrayList<>(map1.keySet());
                List<Object> keysValues = new ArrayList<>(map1.values());

                leftbtns.add(btn1);
                leftbtns.add(btn3);
                leftbtns.add(btn5);
                leftbtns.add(btn7);
                leftbtns.add(btn9);
                rightbtns.add(btn2);
                rightbtns.add(btn4);
                rightbtns.add(btn6);
                rightbtns.add(btn8);
                rightbtns.add(btn10);


                for (TextView button : leftbtns) {
                    if (!keys.isEmpty()) {
                        int randomIndex = new Random().nextInt(keys.size());
                        String randomKey = keys.get(randomIndex);
                        button.setText(randomKey);
                        keys.remove(randomIndex);

                    }
                }

                for (TextView button : rightbtns) {
                    if (!keysValues.isEmpty()) {
                        int randomIndex = new Random().nextInt(keysValues.size());
                        String randomValue = keysValues.get(randomIndex).toString();
                        button.setText(randomValue);
                        keysValues.remove(randomIndex);
                    }
                }
                Log.d("LITS", map1.toString());
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupButtonListeners(leftbtns, rightbtns);
            }
        });

        setupButtonListeners(leftbtns, rightbtns);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        ((AppCompatActivity) getActivity()).findViewById(R.id.score_board).setVisibility(View.VISIBLE);

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        getActivity().findViewById(R.id.score_board).setVisibility(View.GONE);

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}