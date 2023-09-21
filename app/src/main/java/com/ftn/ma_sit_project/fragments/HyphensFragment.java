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

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.Hyphens;
import com.ftn.ma_sit_project.Model.UserDTO;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;
import com.ftn.ma_sit_project.commonUtils.TempGetData;
import com.ftn.ma_sit_project.repository.UserRepository;

import org.checkerframework.checker.index.qual.PolyUpperBound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class HyphensFragment extends Fragment {
    View view;

    TextView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,lastLetButton, player2UserName, p1UserName, hyphens1, hyphens2, player1Score, player2Score;
    Map<String, Object> map1 = new HashMap<>();
    StepByStepFragment stepByStepFragment = new StepByStepFragment();
    List<TextView> leftbtns = new ArrayList<>();
    List<TextView> rightbtns = new ArrayList<>();
    List<TextView> leftReds = new ArrayList<>();

    TextView leftTextView11;
    MqttHandler mqttHandler = new MqttHandler();

    int counter = 0;
    int counter1 = 0;



    boolean isClickedAgain = false;

    boolean isMyTurn;

    boolean isMyTurnOver;

    CountDownTimer countDownTimer;

    boolean azaz = true;

    AppCompatActivity activity;

    int score = 0;
    int score1 = 0;

//    boolean isMyTurnOver = false;

    boolean isLeftTextViewClickedBoolean;

    int buttonClickedCount = 0;
    int counterIfAllLeftTextViewAreGreen = 0;

    boolean isOnline;

    UserRepository userRepository = new UserRepository();



//    private void setupButtonListeners(List<TextView> leftButtons, List<TextView> rightButtons) {  // koristi se ako je tvoj red tj. ako igras prvi
//        String[] lastLeftButtonText = new String[1];
////        if(mqttHandler.getP2Boolean() == true){
//            for (TextView leftButton : leftButtons) {
//                leftButton.setOnClickListener(new View.OnClickListener() { // ako se klikne levo dugme onda se postavlja vrednost na enabled = false i onda se predje na desno dugme
//                    public void onClick(View v) {
//                        if(isClickedAgain == true){
//                            for (TextView b : leftButtons) {
//                                b.setEnabled(true);
//                            }
//                            isClickedAgain = false;
//                        }else {
//                            lastLeftButtonText[0] = leftButton.getText().toString();
//                            lastLetButton = leftButton;
//                            for (TextView b : leftButtons) {
//                                if (b == lastLetButton) {
//                                    isClickedAgain = true;
//                                    continue;
//                                } else {
//                                    b.setEnabled(false);
//                                }
//                            }
//                            for (TextView b : rightButtons) {
//                                b.setEnabled(true);
//                            }
//                        }
//                    }
//                });
//            }
//
//            for (TextView rightButton : rightButtons) { // logika kada se klikne na desno dugme onda se logicki postavljaju boje leve i desne strane
//                rightButton.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        String rightButtonText = rightButton.getText().toString();
//                        if (lastLeftButtonText[0].equals(rightButtonText)) {
//                            Log.d("LITS", "lavor11");
//                            lastLetButton.setBackgroundColor(Color.GREEN);
//                            rightButton.setBackgroundColor(Color.GREEN);
//                            lastLetButton.setClickable(false);
//                            rightButton.setClickable(false);
//                            lastLetButton.isEnabled();
//                            counter++;
//                            mqttHandler.textViewSharePublish(lastLetButton, false, false, counter);
//                            mqttHandler.textViewSharePublish(rightButton, false, false, 0);
//                            score = Integer.parseInt((String) player1Score.getText());
//                            score += 2;
//                            player1Score.setText(score + "");
//                            mqttHandler.pointPublish(2);
//                            Log.d("TextView", Integer.toString(lastLetButton.getId()));
//                        } else {
//                            Log.d("LITS", "JOK");
//                            lastLetButton.setBackgroundColor(Color.RED);
//                            lastLetButton.setClickable(false);
//                            counter++;
//                            mqttHandler.textViewSharePublish(lastLetButton, false, false, counter);
//                        }
//                        for (TextView b : leftButtons) {
//                            b.setEnabled(true);
//                        }
//                        for (TextView b : rightButtons) {
//                            b.setEnabled(false);
//                        }
//                    }
//                });
//            }
//
//    }
//
//    private void setup(List<TextView> leftButtons, List<TextView> rightButtons) { // koristi se ako si ako drugi igra
//        String[] lastLeftButtonText = new String[1];
//        for (TextView leftButton : leftButtons) { // proveravamo da li je svako levo drugme crveno koje jeste u listu za crvena drugmad
//            ColorDrawable viewColor = (ColorDrawable) leftButton.getBackground();
//            if (viewColor.getColor() == Color.RED) {
//                leftReds.add(leftButton);
//            }
//        }
//        for (TextView leftButton : leftReds){ // prolazimo kroz crvena dugmad
//            leftButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    if (isClickedAgain == true) { // ako je ponovo kliknuto
//                        for (TextView b : leftButtons) { // prodlazimo kroz leve crvene dugmice
//                            b.setEnabled(true); // postavljamo ih na moguce kliknuti
//                        }
//                        isClickedAgain = false; // i ako je kliknuto ponovo postavljamo da nije
//                    } else { // u frugom slucaju ako njie kliknuto
//                        lastLeftButtonText[0] = leftButton.getText().toString(); // uzimamo text posledneg kliknutog dugmeta
//                        lastLetButton = leftButton;
//                        for (TextView b : leftReds) {
//                            if (b == lastLetButton) {
//                                isClickedAgain = true;
//                                continue;
//                            } else {
//                                b.setEnabled(false);
//                            }
//                        }
//                        for (TextView b : rightButtons) {
//                            b.setEnabled(true);
//                        }
//                    }
//                }
//            });
//        }
//
//        for (TextView rightButton : rightButtons) {
//            rightButton.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    String rightButtonText = rightButton.getText().toString();
//                    if (lastLeftButtonText[0].equals(rightButtonText)) {
//                        Log.d("LITS", "lavor11");
//                        lastLetButton.setBackgroundColor(Color.GREEN);
//                        rightButton.setBackgroundColor(Color.GREEN);
//                        lastLetButton.setClickable(false);
//                        rightButton.setClickable(false);
//                        lastLetButton.isEnabled();
//                        mqttHandler.textViewSharePublish(lastLetButton, false, false, 0);
//                        mqttHandler.textViewSharePublish(rightButton, false, false, 0);
//                        score = Integer.parseInt((String) player1Score.getText());
//                        score += 2;
//                        player1Score.setText(score + "");
//                        mqttHandler.pointPublish(2);
//                        Log.d("TextView", Integer.toString(lastLetButton.getId()));
//                    } else {
//                        Log.d("LITS", "JOK");
//                        lastLetButton.setBackgroundColor(Color.RED);
//                        lastLetButton.setClickable(false);
//                        mqttHandler.textViewSharePublish(lastLetButton, false, false, 0 );
//                    }
//                    for (TextView b : leftButtons) {
//                        b.setEnabled(true);
//                    }
//                    for (TextView b : rightButtons) {
//                        b.setEnabled(false);
//                    }
//                }
//            });
//        }
//
//    }

    public void isLeftTextViewClicked(TextView leftTextView){ // ako se klikne levo dugme postavlja se da je kliknuto desno dugme i postavljaju se desna i postavljaju se ostala desna dugmad na isCLicable false
        leftTextView11 = leftTextView;
        isLeftTextViewClickedBoolean = true;
        setClicableForLeftTextViews(leftTextView);
        chechRightTextViews();
    }

    public void chechRightTextViews(){ // postavljanje desnie strane za ako je levo dugme kliknuto
        for (TextView textView: rightbtns){
            ColorDrawable viewColor = (ColorDrawable) textView.getBackground();
            if(viewColor.getColor() == Color.GREEN){
                textView.setClickable(false);
            }else{
                textView.setEnabled(true);
            }
        }
    }

    public void setClicableForLeftTextViews(TextView textVie){ // postavljanje eve strane ako je levo dugme kliknuto
        for (TextView textView: leftbtns){
            if(textView.getId() == textVie.getId()){
                continue;
            }else{
                textView.setEnabled(false);
            }
        }
    }

    public void setClicableForRightTextViews(){
        for (TextView textView: rightbtns){
            textView.setEnabled(false);
        }
    }

    public void setClicableForLeftTextViews(){
        for (TextView textView: leftbtns){
            ColorDrawable viewColor = (ColorDrawable) textView.getBackground();
            if(viewColor.getColor() == Color.GREEN || viewColor.getColor() == Color.RED){
                if(!isMyTurn && isOnline && viewColor.getColor() == Color.RED){ // u slucaju online igre
                    textView.setEnabled(true);
                }else{
                    textView.setEnabled(false);
                }
            }else {
                textView.setEnabled(true);
            }
        }
    }

    public void setPoints(){
        score1 = Integer.parseInt((String) player1Score.getText());
        score1+=2;
        player1Score.setText(score1 + "");
        Data.loggedInUser.setSpojnice(Data.loggedInUser.getSpojnice()+score1);
        if(isOnline){
            userRepository.updateSpojnice(Data.loggedInUser, Data.loggedInUser.getSpojnice()+score1);
            mqttHandler.pointPublish(score1);
        }
    }
    public void isRightTextViewClicked(TextView textView){ // ne bi trebalo da se moze kliknuti dok se ne klikne levo dugme al vidi
        if(textView.getText().equals(leftTextView11.getText())){
            leftTextView11.setBackgroundColor(Color.GREEN);
            textView.setBackgroundColor(Color.GREEN);
            setClicableForRightTextViews();
            setClicableForLeftTextViews();
            setPoints();
            counter++;
            if(isOnline){
//                counterIfAllLeftTextViewAreGreen++;
//                if(counterIfAllLeftTextViewAreGreen == 5){
//                    if(getArguments() == null){
//                        getParentFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.fragment_container, HyphensFragment.newInstance(true, true))
//                                .setReorderingAllowed(true)
//                                .commit();
//                    }else{
//                        getParentFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.fragment_container, new StepByStepFragment())
//                                .setReorderingAllowed(true)
//                                .commit();
//                    }
//                }
                mqttHandler.textViewSharePublish(leftTextView11, false, false, counter);
                mqttHandler.textViewSharePublish(textView, false, false, 0);
            }
        }else{
            leftTextView11.setBackgroundColor(Color.RED); //
            setClicableForRightTextViews(); // postavlja da su desni textView-ovi
            setClicableForLeftTextViews(); // postavlja da su levi textView-ovi moguci da se pritisnu
            counter++;
            if(isOnline){
                mqttHandler.textViewSharePublish(leftTextView11, false, false, counter);
            }
        }
    }

    public static HyphensFragment newInstance(boolean round, boolean isOnline) {

        Bundle args = new Bundle();
        args.putBoolean("isFirstRound", round);
        args.putBoolean("isOnline", isOnline);

        HyphensFragment fragment = new HyphensFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setIsMyTurn(){
        if(isMyTurn == true){
            isMyTurn = false;
        }else if(isMyTurn == false){
            isMyTurn = true;
        }
    }

//    public void setNewFragment(){
//        if(counterIfAllLeftTextViewAreGreen == 5){
//            if(getArguments() == null){
//                getParentFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, HyphensFragment.newInstance(true, true))
//                        .setReorderingAllowed(true)
//                        .commit();
//            }else{
//                getParentFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, new StepByStepFragment())
//                        .setReorderingAllowed(true)
//                        .commit();
//            }
//        }
//    }
    public void setIsOnline(boolean turn){
        isOnline = turn;
    }
    public void TempGetDataMethod(String runda){
        TempGetData.getDataAsMap(new TempGetData.FireStoreCallback1() {
            @Override
            public void onCallBack(Map<String, Object> map) {
                if(getArguments() != null){
                    isOnline = getArguments().getBoolean("isOnline", false);
                }
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


                Log.i("mqtt","isOnline: "+ isOnline);
                for (TextView button : leftbtns) {
                    if (!keys.isEmpty()) {
                        int randomIndex = new Random().nextInt(keys.size());
                        String randomKey = keys.get(randomIndex);
                        button.setText(randomKey);
                        if(isOnline){
                          mqttHandler.textViewSharePublish(button, false, true, 0);
                        }
                        keys.remove(randomIndex);
                    }
                }

                for (TextView button : rightbtns) {
                    if (!keysValues.isEmpty()) {
                        int randomIndex = new Random().nextInt(keysValues.size());
                        String randomValue = keysValues.get(randomIndex).toString();
                        button.setText(randomValue);
                        if (isOnline){
                            mqttHandler.textViewSharePublish(button, false, true, 0);
                        }
                        keysValues.remove(randomIndex);
                        button.setEnabled(false);
                    }
                }
                Log.d("LITS", map1.toString());

                if(isOnline){
                    isMyTurn = mqttHandler.getTurnPlayer();
                    if(getArguments() != null){
                        setIsMyTurn();
                    }
                }

                if(!isMyTurnOver && !isMyTurn && isOnline){
                    for(TextView textView : leftbtns){
                        textView.setEnabled(false);
                    }
                    for(TextView textView : rightbtns){
                        textView.setEnabled(false);
                    }
                }
            }
        },runda);
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
        hyphens1 = view.findViewById(R.id.hyphens1);
        hyphens2 = view.findViewById(R.id.hyphens2);
        player1Score = activity.findViewById(R.id.player_1_score);
        player2Score = activity.findViewById(R.id.player_2_score);


//        Button btnNext = view.findViewById(R.id.hyphens);
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getParentFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, new AssociationsFragment())
//                        .setReorderingAllowed(true)
//                        .commit();
//            }
//        });

        if(getArguments() == null){
            TempGetDataMethod("Spojnice");
        }else{
            TempGetDataMethod("Spojnice2");
        }


//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
////                if(isMyTurn == true){
////                    Log.d("mqtt", "isMyTurn");
////                    setup(leftbtns, rightbtns);
////                }else{
////                    setupButtonListeners(leftbtns, rightbtns);
////                }
//                Log.i("mqtt","btn1");
//                isLeftTextViewClicked(btn1);
//            }
//        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("mqtt","btn1");
                buttonClickedCount++;
                if(buttonClickedCount == 2){
                    setClicableForRightTextViews();
                    setClicableForLeftTextViews();
                    buttonClickedCount = 0;
                }else{
                    isLeftTextViewClicked(btn1);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                isRightTextViewClicked(btn2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                buttonClickedCount++;
                if(buttonClickedCount == 2){
                    setClicableForRightTextViews();
                    setClicableForLeftTextViews();
                    buttonClickedCount = 0;
                }else{
                    isLeftTextViewClicked(btn3);
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                isRightTextViewClicked(btn4);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                buttonClickedCount++;
                if(buttonClickedCount == 2){
                    setClicableForRightTextViews();
                    setClicableForLeftTextViews();
                    buttonClickedCount = 0;
                }else{
                    isLeftTextViewClicked(btn5);
                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                isRightTextViewClicked(btn6);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                buttonClickedCount++;
                if(buttonClickedCount == 2){
                    setClicableForRightTextViews();
                    setClicableForLeftTextViews();
                    buttonClickedCount = 0;
                }else{
                    isLeftTextViewClicked(btn7);
                }
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                isRightTextViewClicked(btn8);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                buttonClickedCount++;
                if(buttonClickedCount == 2){
                    setClicableForRightTextViews();
                    setClicableForLeftTextViews();
                    buttonClickedCount = 0;
                }else{
                    isLeftTextViewClicked(btn9);
                }
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("mqtt", "isMyTurn: "+ isMyTurn);
//                if(isMyTurn == true){
//                    setup(leftbtns, rightbtns);
//                }else{
//                    setupButtonListeners(leftbtns, rightbtns);
//                }
                isRightTextViewClicked(btn10);
            }
        });


//        setupButtonListeners(leftbtns, rightbtns);
//        if(isMyTurn == true){
//            setup(leftbtns, rightbtns);
//        }else{
//            setupButtonListeners(leftbtns, rightbtns);
//        }
        if (Data.loggedInUser != null) {
            p1UserName.setText(Data.loggedInUser.getUsername());
        }
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        p1UserName = activity.findViewById(R.id.player_1_user_name);
        player2UserName = activity.findViewById(R.id.player_2_user_name);

        if(Data.loggedInUser != null && !player2UserName.getText().toString().equals("Guest")){
            mqttHandler.pointSubscribe(new MqttHandler.PointCallback() {
                @Override
                public void onCallback(UserDTO userDTO) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            score1 = userDTO.getPoints();
                            player2Score.setText(userDTO.getPoints()+"");
                        }
                    });
                }
            });
            mqttHandler.textViewShareSubscribe(new MqttHandler.TextViewStoreCallback() {
                @Override
                public void onCallBack(Hyphens hyphens) {
                    if(hyphens != null){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(hyphens.getCounterIsMyTurnOver() != 0){
                                    counter1++;
                                }
                                if(counter1 == 5){
                                    counter1 = 0;
                                    isMyTurnOver = true;
                                    for(TextView textView: leftbtns){
                                        ColorDrawable viewColor = (ColorDrawable) textView.getBackground();
                                        if(viewColor.getColor() == Color.RED){
                                            textView.setEnabled(true);
                                        }else {
                                            counterIfAllLeftTextViewAreGreen++;
                                        }
                                    }
                                }
//                                if(counterIfAllLeftTextViewAreGreen == 5){
//                                    if(getArguments() == null){
//                                        getParentFragmentManager()
//                                                .beginTransaction()
//                                                .replace(R.id.fragment_container, HyphensFragment.newInstance(true, true))
//                                                .setReorderingAllowed(true)
//                                                .commit();
//                                    }else{
//                                        getParentFragmentManager()
//                                                .beginTransaction()
//                                                .replace(R.id.fragment_container, new StepByStepFragment())
//                                                .setReorderingAllowed(true)
//                                                .commit();
//                                    }
//                                }
                                if(hyphens.isStart()){ // moramo ovo da stavimo da bi mogli da imamo iste rezultate kada se pokerne igra tj. da imamo iste podatke na istim mestima
                                    for (TextView leftButton: leftbtns){ // ima gore salje se u startovanju igre isStarted = true i onda se postavljaju vrednosti na ista mesta
                                        if(leftButton.getId() == hyphens.getId()){
                                            leftButton.setText(hyphens.getText());
                                        }
                                    }
                                    for(TextView rightButton: rightbtns){
                                        if(rightButton.getId() == hyphens.getId()){
                                            rightButton.setText(hyphens.getText());
                                        }
                                    }
                                }else {
                                    for(TextView textView : leftbtns){
                                        if(textView.getId() == hyphens.getId()){
                                            textView.setBackgroundColor(hyphens.getColor());
                                            textView.invalidate();
                                            Log.i("mqtt", hyphens + "");
                                            Log.i("mqtt", textView + "");
    //                                        textView.setClickable(false);
                                        }
                                    }
                                    for(TextView textView : rightbtns){
                                        if(textView.getId() == hyphens.getId()){
                                            textView.setBackgroundColor(hyphens.getColor());
                                            textView.invalidate();
                                            Log.i("mqtt", hyphens + "");
    //                                        textView.setClickable(false);
                                        }
                                    }
                                }
//                                if(hyphens.getId() == hyphens1.getId()){
//                                    hyphens2.setBackgroundColor(hyphens.getColor());
//                                    hyphens2.invalidate();
//                                }
                            }
                        });
                    }
                }
            });
        }


        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        ((AppCompatActivity) getActivity()).findViewById(R.id.score_board).setVisibility(View.VISIBLE);

        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        TextView scoreTimer = activity.findViewById(R.id.score_timer);

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                Long min = ((l / 1000) % 3600) / 60;
                Long sec = (l / 1000) % 60;
                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                scoreTimer.setText(format);
            }

            @Override
            public void onFinish() {
//                if(isMyTurn == true){
//                    isMyTurn = true;
                    score = 0;
                    for(TextView textView : leftbtns){
                        ColorDrawable viewColor = (ColorDrawable) textView.getBackground();
                        if(viewColor.getColor() == Color.RED){
                            textView.setClickable(true);
                        }
                    }
                    countDownTimer = new CountDownTimer(30000, 1000) {
                        @Override
                        public void onTick(long l) {
                            Long min = ((l / 1000) % 3600) / 60;
                            Long sec = (l / 1000) % 60;
                            String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                            scoreTimer.setText(format);
                        }

                        @Override
                        public void onFinish() {
//                            isMyTurn = false;
                            scoreTimer.setText("00:00");
                            // jedan if ako je igrac usao u mod jednog igraca
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
                                if (viewColor.getColor() == Color.GREEN) {
                                    buttonKey1.setBackgroundColor(Color.GREEN);
                                    buttonValue1.setBackgroundColor(Color.GREEN);
                                } else {
                                    buttonKey1.setBackgroundColor(Color.RED);
                                    buttonValue1.setBackgroundColor(Color.RED);
                                }
                            }
                            if(getArguments() == null){
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, HyphensFragment.newInstance(true, true))
                                        .setReorderingAllowed(true)
                                        .commit();
                            }else{
                                stepByStepFragment.setIsOnline(true);
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, stepByStepFragment)
                                        .setReorderingAllowed(true)
                                        .commit();
                            }
                        }
                    }.start();

                }
//                else{
//                    scoreTimer.setText("00:00");
//                    isMyTurn = true;
//                    getParentFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.fragment_container, new HomeFragment())
//                            .setReorderingAllowed(true)
//                            .commit();
//                }
//            }
        }.start();

    }

    @Override
    public void onStop() {
        super.onStop();
        score = 0;

        countDownTimer.cancel();

        ShowHideElements.hideScoreBoard(activity);

        activity.getSupportActionBar().show();

        ShowHideElements.unlockDrawerLayout(activity);
    }
}