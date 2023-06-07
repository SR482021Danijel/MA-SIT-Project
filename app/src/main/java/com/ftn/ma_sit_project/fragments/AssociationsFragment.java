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
import android.widget.EditText;
import android.widget.TextView;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;
import com.ftn.ma_sit_project.commonUtils.TempGetData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AssociationsFragment extends Fragment {
    View view;
    TextView a1,a2,a3,a4,b1,b2,b3,b4,c1,c2,c3,c4,d1,d2,d3,d4,e,player1Score,a_button,b_button,c_button,d_button;
    Dialog dialog;
    CountDownTimer countDownTimer;
    ArrayList<String> arrayList = new ArrayList<>();
//    Map<Integer, TextView> textViwMap = new HashMap<>();
    ArrayList<String> aFields = new ArrayList<>();
    ArrayList<String> bFields = new ArrayList<>();
    ArrayList<String> cFields = new ArrayList<>();
    ArrayList<String> dFields = new ArrayList<>();

    ArrayList<TextView> textViewsa = new ArrayList<>();
    ArrayList<TextView> textViewsb = new ArrayList<>();
    ArrayList<TextView> textViewsc = new ArrayList<>();
    ArrayList<TextView> textViewsd = new ArrayList<>();
    boolean field_a = false;
    boolean field_b = false;
    boolean field_c = false;
    boolean field_d = false;
    boolean field_e = false;
    boolean isTruea = false;
    boolean isTrueb = false;
    boolean isTruec = false;
    boolean isTrued = false;
    AppCompatActivity activity;

    int poinst = 0;
    int score = 0;
//    private void Score(int poinst){
//        score = Integer.parseInt((String) player1Score.getText());
//        score += poinst;
//        player1Score.setText(score);
//    }
    TempGetData tempGetData = new TempGetData();

    public void setAFields(){
        a1.setText(aFields.get(0));
        a2.setText(aFields.get(1));
        a3.setText(aFields.get(2));
        a4.setText(aFields.get(3));
        a_button.setText(aFields.get(4));
    }
    public void setBFields(){
        b1.setText(bFields.get(0));
        b2.setText(bFields.get(1));
        b3.setText(bFields.get(2));
        b4.setText(bFields.get(3));
        b_button.setText(bFields.get(4));
    }
    public void setCFields(){
        c1.setText(cFields.get(0));
        c2.setText(cFields.get(1));
        c3.setText(cFields.get(2));
        c4.setText(cFields.get(3));
        c_button.setText(cFields.get(4));
    }
    public void setDFields(){
        d1.setText(dFields.get(0));
        d2.setText(dFields.get(1));
        d3.setText(dFields.get(2));
        d4.setText(dFields.get(3));
        d_button.setText(dFields.get(4));
    }

    public void setPoints(int poinst){
        score = Integer.parseInt((String) player1Score.getText());
        score+=poinst;
        player1Score.setText(score + "");
    }

    public void setEFields(){
        e.setText(arrayList.get(20));
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SkockoFragment())
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_associations, container, false);
        a1 = view.findViewById(R.id.a1_field);
        a2 = view.findViewById(R.id.a2_field);
        a3 = view.findViewById(R.id.a3_field);
        a4 = view.findViewById(R.id.a4_field);
        b1 = view.findViewById(R.id.b1_field);
        b2 = view.findViewById(R.id.b2_field);
        b3 = view.findViewById(R.id.b3_field);
        b4 = view.findViewById(R.id.b4_field);
        c1 = view.findViewById(R.id.c1_field);
        c2 = view.findViewById(R.id.c2_field);
        c3 = view.findViewById(R.id.c3_field);
        c4 = view.findViewById(R.id.c4_field);
        d1 = view.findViewById(R.id.d1_field);
        d2 = view.findViewById(R.id.d2_field);
        d3 = view.findViewById(R.id.d3_field);
        d4 = view.findViewById(R.id.d4_field);
        e = view.findViewById(R.id.final_field);
        a_button = view.findViewById(R.id.a_field);
        b_button = view.findViewById(R.id.b_field);
        c_button = view.findViewById(R.id.c_field);
        d_button = view.findViewById(R.id.d_field);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pop_up_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button ok = dialog.findViewById(R.id.ok_dialog);
        Button cancel = dialog.findViewById(R.id.cancel_dialog);
        EditText editText = dialog.findViewById(R.id.pop_up);
        tempGetData.getAsocijacije(new TempGetData.FireStoreCallback() {
            @Override
            public void onCallBack(ArrayList<String> list) {
                arrayList.addAll(list);
//                a1.setText(arrayList.get(0));
//                a2.setText(arrayList.get(1));
//                a3.setText(arrayList.get(2));
//                a4.setText(arrayList.get(3));
//                a_button.setText(arrayList.get(4));
//                b1.setText(arrayList.get(5));
//                b2.setText(arrayList.get(6));
//                b3.setText(arrayList.get(7));
//                b4.setText(arrayList.get(8));
//                b_button.setText(arrayList.get(9));
//                c1.setText(arrayList.get(10));
//                c2.setText(arrayList.get(11));
//                c3.setText(arrayList.get(12));
//                c4.setText(arrayList.get(13));
//                c_button.setText(arrayList.get(14));
//                d1.setText(arrayList.get(15));
//                d2.setText(arrayList.get(16));
//                d3.setText(arrayList.get(17));
//                d4.setText(arrayList.get(18));
//                d_button.setText(arrayList.get(19));
//                e.setText(arrayList.get(20));

                textViewsa.add(a1);
                textViewsa.add(a2);
                textViewsa.add(a2);
                textViewsa.add(a3);
                textViewsa.add(a_button);
                textViewsb.add(b1);
                textViewsb.add(b2);
                textViewsb.add(b3);
                textViewsb.add(b4);
                textViewsb.add(b_button);
                textViewsc.add(c1);
                textViewsc.add(c2);
                textViewsc.add(c3);
                textViewsc.add(c4);
                textViewsc.add(c_button);
                textViewsd.add(d1);
                textViewsd.add(d2);
                textViewsd.add(d3);
                textViewsd.add(d4);
                textViewsd.add(d_button);
                for(int i = 0; i< arrayList.size(); i++){
                    if(i >= 0 && i <=4){
                        aFields.add(arrayList.get(i));
                    }else if(i>4 && i<=9){
                        bFields.add(arrayList.get(i));
                    }else if(i>9 && i<=14){
                        cFields.add(arrayList.get(i));
                    }else if(i>14 && i<=19){
                        dFields.add(arrayList.get(i));
                    }
                }
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
                String editText1 = editText.getText().toString();
//                String editText1 = "DIM";
//                String editText2 = "GVOZDJE";
//                String editText3 = "KADA";
//                String editText4 = "SPUSTANJE";
//                String editText5 = "ZAVESA";
                String a = aFields.get(4);
                String b = bFields.get(4);
                String c = cFields.get(4);
                String d = dFields.get(4);
                String e = arrayList.get(20);
                if(field_a != false){
                    if(editText1 != ""){
                        if(editText1.equals(a)){
                            int counter_a = 0;
                            for(TextView textView : textViewsa){
                                if(textView.getText() == ""){
                                    if(counter_a == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_a++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setAFields();
                            isTruea = true;
                        }
                    }
                } else if (field_b != false) {
                    if(editText1 != ""){
                        if(editText1.equals(b)){
                            int counter_b = 0;
                            for(TextView textView : textViewsb){
                                if(textView.getText() == ""){
                                    if(counter_b == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_b++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setBFields();
                            isTrueb = true;
                        }
                    }
                }else if (field_c != false) {
                    if(editText1 != ""){
                        if(editText1.equals(c)){
                            int counter_c = 0;
                            for(TextView textView : textViewsc){
                                if(textView.getText() == ""){
                                    if(counter_c == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_c++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setCFields();
                            isTruec = true;
                        }
                    }
                }else if (field_d != false) {
                    if(editText1 != ""){
                        if(editText1.equals(d)){
                            int counter_d = 0;
                            for(TextView textView : textViewsd){
                                if(textView.getText() == ""){
                                    if(counter_d == 4){
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_d++;
                            }
                            poinst += 2;
                            setPoints(poinst);
                            setDFields();
                            isTrued = true;
                        }
                    }
                }else if(field_e != false){
                    if(editText1 != ""){
                        if(editText1.equals(e)){
                            int counter_a = 0;
                            for(TextView textView : textViewsa){
                                if(textView.getText() == ""){
                                    if(counter_a == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_a++;
                            }
                            int counter_b = 0;
                            for(TextView textView : textViewsb){
                                if(textView.getText() == ""){
                                    if(counter_b == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_b++;
                            }
                            int counter_c = 0;
                            for(TextView textView : textViewsc){
                                if(textView.getText() == ""){
                                    if(counter_c == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_c++;
                            }
                            int counter_d = 0;
                            for(TextView textView : textViewsd){
                                if(textView.getText() == ""){
                                    if(counter_d == 4){
                                        if(textView.getText() == ""){
                                            poinst += 2;
                                        }
                                        break;
                                    }else {
                                        poinst++;
                                    }
                                }
                                counter_d++;
                            }
                            poinst += 7;
                            setPoints(poinst);
                            setAFields();
                            setBFields();
                            setCFields();
                            setDFields();
                            setEFields();
                            isTrued = true;
                            countDownTimer.cancel();
                        }
                    }
                }
                field_a = false;
                field_b = false;
                field_c = false;
                field_d = false;
                field_e = false;
                editText.setText("");
                dialog.dismiss();
            }
        });


        a_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_a = true;
                int y = 0;
                for(TextView textView : textViewsa){
                    if(textView.getText() == ""){
                        y++;
                    }
                }
                if(isTruea != true && y != 5){
                    dialog.show();
                    y=0;
                }
            }
        });

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a1.setText(aFields.get(0));
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a2.setText(aFields.get(1));
            }
        });

        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a3.setText(aFields.get(2));
            }
        });

        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a4.setText(aFields.get(3));
            }
        });
        b_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_b = true;
                if(!isTrueb){
                    dialog.show();
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setText(bFields.get(0));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setText(bFields.get(1));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3.setText(bFields.get(2));
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b4.setText(bFields.get(3));
            }
        });

        c_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_c = true;
                if(!isTruec){
                    dialog.show();
                }
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1.setText(cFields.get(0));
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c2.setText(cFields.get(1));
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c3.setText(cFields.get(2));
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c4.setText(cFields.get(3));
            }
        });

        d_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_d = true;
                if(!isTrued){
                    dialog.show();
                }
            }
        });

        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d1.setText(dFields.get(0));
            }
        });

        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d2.setText(dFields.get(1));
            }
        });

        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d3.setText(dFields.get(2));
            }
        });

        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d4.setText(dFields.get(3));
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int y = 0;
                for(TextView textView : textViewsa){
                    if(textView.getText() == ""){
                        y++;
                    }
                }
                for(TextView textView : textViewsb){
                    if(textView.getText() == ""){
                        y++;
                    }
                }
                for(TextView textView : textViewsc){
                    if(textView.getText() == ""){
                        y++;
                    }
                }
                for(TextView textView : textViewsd){
                    if(textView.getText() == ""){
                        y++;
                    }
                }

                field_e = true;
                if(y != 20){
                    dialog.show();
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

        countDownTimer = new CountDownTimer(120000, 1000) {
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
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SkockoFragment())
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