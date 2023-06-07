package com.ftn.ma_sit_project.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;
import com.ftn.ma_sit_project.commonUtils.TempGetData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StepByStepFragment extends Fragment {
    View view;

    Dialog dialog;
    CountDownTimer countDownTimer;
    AppCompatActivity activity;
    TempGetData tempGetData = new TempGetData();
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView_answer, points_right, player1Score;
    Map<Integer, TextView> textViwMap = new HashMap<>();
    Map<String, Object> runda1 = new HashMap<>();
    ArrayList<String> arrayList = new ArrayList<>();
    int count = 2;

    int score = 0;

//    final String response = new String();

//    private String getResponse() {
//        return textViwMap.get(8).getText().toString();
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_step_by_step, container, false);
        TextView next = view.findViewById(R.id.step_by_stepTextView_points_right);
        textView1 = view.findViewById(R.id.step_by_stepTextView1);
        textView1.setTextColor(Color.RED);
        textView2 = view.findViewById(R.id.step_by_stepTextView2);
        textView3 = view.findViewById(R.id.step_by_stepTextView3);
        textView4 = view.findViewById(R.id.step_by_stepTextView4);
        textView5 = view.findViewById(R.id.step_by_stepTextView5);
        textView6 = view.findViewById(R.id.step_by_stepTextView6);
        textView7 = view.findViewById(R.id.step_by_stepTextView7);
        textView_answer = view.findViewById(R.id.step_by_stepTextView_answer);
        player1Score = activity.findViewById(R.id.player_1_score);
        points_right = view.findViewById(R.id.step_by_stepTextView_points_right);

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pop_up_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button ok = dialog.findViewById(R.id.ok_dialog);
        Button cancel = dialog.findViewById(R.id.cancel_dialog);
        EditText editText = dialog.findViewById(R.id.pop_up);

        tempGetData.getKorakPoKorak(new TempGetData.FireStoreCallback() {
            @Override
            public void onCallBack(ArrayList<String> list) {
                arrayList.addAll(list);
                textView1.setText(arrayList.get(0));
                textView2.setText(arrayList.get(1));
                textView3.setText(arrayList.get(2));
                textView4.setText(arrayList.get(3));
                textView5.setText(arrayList.get(4));
                textView6.setText(arrayList.get(5));
                textView7.setText(arrayList.get(6));
                textView_answer.setText(arrayList.get(7));

                textViwMap.put(1, textView1);
                textViwMap.put(2, textView2);
                textViwMap.put(3, textView3);
                textViwMap.put(4, textView4);
                textViwMap.put(5, textView5);
                textViwMap.put(6, textView6);
                textViwMap.put(7, textView7);
                textViwMap.put(8, textView_answer);

                Log.d("LITS", arrayList.toString());
            }
        });

        textView_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isTrue(editText.toString());
                String editText1 = editText.getText().toString();
                String response = textViwMap.get(8).getText().toString();
                if (textView_answer.toString() != "") {
                    if (editText1.equals(response)) {
                        countDownTimer.cancel();
                        isCorect();
                        for (Map.Entry<Integer, TextView> entry : textViwMap.entrySet()) {
                            TextView textView = entry.getValue();
                            textView.setTextColor(Color.RED);
                        }
                    }else {
                        Toast.makeText(getActivity(),"Pokusaj ponovo", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });


//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getParentFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new MyNumberFragment())
//                        .setReorderingAllowed(true)
//                        .commit();
//            }
//        });
        return view;
    }

    int x = 0;
    private void isCorect() {
        for (Map.Entry<Integer, TextView> entry : textViwMap.entrySet()) {
            if(entry.getKey().equals(8)){
                break;
            }
            if(entry.getValue().getCurrentTextColor() != Color.RED){
                x++;
            }
        }
        score = Integer.parseInt((String) player1Score.getText());
        if(x == 1){
            points_right.setText("10");
            score += 10;
        }else if(x == 2){
            points_right.setText("12");
            score += 12;
        }else if(x == 3){
            points_right.setText("14");
            score += 14;
        }else if(x == 4){
            points_right.setText("16");
            score += 16;
        }else if(x == 5){
            points_right.setText("18");
            score += 18;
        }else if(x == 6){
            points_right.setText("20");
            score += 20;
        }
        player1Score.setText(score + "");
        points_right.setTextColor(Color.RED);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MyNumberFragment())
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        TextView scoreTimer = activity.findViewById(R.id.score_timer);

        ShowHideElements.showScoreBoard(activity);

        countDownTimer = new CountDownTimer(70000, 1000) {
            @Override
            public void onTick(long l) {
                if (Math.abs(l % 10000) < 1200) {
                    Log.e("AA","radi1");
                    if(count < 9){
                        Log.e("AA","rad2");
                        TextView textView = (TextView)textViwMap.get(count);
                        textView.setTextColor(Color.RED);
                        count++;
                    }
                }
                Log.e("AA","rad3" + l);
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000) % 60;
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                scoreTimer.setText(format);
            }

            @Override
            public void onFinish() {
                scoreTimer.setText("00:00");
                count = 1;
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MyNumberFragment())
                        .setReorderingAllowed(true)
                        .commit();
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