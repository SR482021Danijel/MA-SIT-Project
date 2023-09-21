package com.ftn.ma_sit_project.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Asocijacije;
import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.KorakPoKorak;
import com.ftn.ma_sit_project.Model.UserDTO;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.commonUtils.MqttHandler;
import com.ftn.ma_sit_project.commonUtils.ShowHideElements;
import com.ftn.ma_sit_project.commonUtils.TempGetData;
import com.ftn.ma_sit_project.repository.UserRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StepByStepFragment extends Fragment {

    private MyViewModel viewModel;

    View view;

    Dialog dialog;
    CountDownTimer countDownTimer;
    AppCompatActivity activity;

    MqttHandler mqttHandler = new MqttHandler();

    TempGetData tempGetData = new TempGetData();
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView_answer, points_right, player1Score, player2Score,  player2UserName;
    Map<Integer, TextView> textViwMap = new HashMap<>();
    Map<String, Object> runda1 = new HashMap<>();
    ArrayList<String> arrayList = new ArrayList<>();
    int count = 2;

    int score = 0;
    int counter1 = 0;

    boolean isOkButtonClicked = false;

//    final String response = new String();

//    private String getResponse() {
//        return textViwMap.get(8).getText().toString();
//    }

    boolean isMyTurn;
    boolean isAnswerCorrect = false;
    boolean isFirstRound;

    private AsyncTask myTask;

    private static final String KEY_STATE = "state";

    UserRepository userRepository = new UserRepository();
    boolean isOnline;

    public StepByStepFragment() {
    }

    AssociationsFragment associationsFragment = new AssociationsFragment();
    public void setIsMyTurn(){
        if(isMyTurn){
            isMyTurn = false;
        }else{
            isMyTurn = true;
        }
    }

    public static StepByStepFragment newInstance(boolean round, boolean bool) {

        Bundle args = new Bundle();
        args.putBoolean("isFirstRound", round);
        args.putBoolean("isOnline", bool);

        StepByStepFragment fragment = new StepByStepFragment();
        fragment.setArguments(args);
        return fragment;
    }



    public void TempGetDataMethod(String runda){
        TempGetData.getKorakPoKorak(new TempGetData.FireStoreCallback() {
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

                if(getArguments() != null){
                    isOnline = getArguments().getBoolean("isOnline", false);
                }


                if(isOnline){
                    isMyTurn = mqttHandler.getTurnPlayer();
                    if (getArguments() != null) {
                        setIsMyTurn();
                    }
                }
            }
        }, runda);
    }

    public void setIsOnline(boolean bool){
        isOnline = true;
    }
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
        player2Score = activity.findViewById(R.id.player_2_score);
        points_right = view.findViewById(R.id.step_by_stepTextView_points_right);

        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pop_up_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button ok = dialog.findViewById(R.id.ok_dialog);
        Button cancel = dialog.findViewById(R.id.cancel_dialog);
        EditText editText = dialog.findViewById(R.id.pop_up);
        //neki if sa savedInstanceState koji ce da kaze koja je runda
        if (getArguments() == null) {
            TempGetDataMethod("runda1");
        }else {
            TempGetDataMethod("runda2");
        }

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        textView_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("mqtt", "MyTurn: " + isMyTurn);
                if(isMyTurn){
                    dialog.show();
                }
                if(!isOnline){
                    dialog.show();
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
//                isTrue(editText.toString());
                String editText1 = editText.getText().toString();
                String response = textViwMap.get(8).getText().toString();
                if (textView_answer.toString() != "") {
                    if (editText1.equals(response)) {
                        isOkButtonClicked = true;
                        countDownTimer.cancel();
                        isCorect();
                        Log.i("mqtt", "Pre nego sto se proveri da li je saveInstanceState == null, "+ "saveInstanceState: " + savedInstanceState);
//                        if (viewModel.getIsFirstRoundLiveData() != null) {
//                            viewModel.getIsFirstRoundLiveData().observe(getViewLifecycleOwner(), isFirstRound -> {
//                                if (isFirstRound == true && isOkButtonClicked) {
                        if(isOnline){
                            if(getArguments() == null) {
    //                                    isOkButtonClicked = false;
                                Log.i("mqtt", "IsFirstRound = true");
                                mqttHandler.korakPoKorakPublish(textView_answer, true, false);
                                isFragment(true);
    //                                    viewModel.setIsFirstRound(false);
    //                                } else if(isFirstRound == false && isOkButtonClicked) {
                            }else{
    //                            isOkButtonClicked = false;
                                Log.i("mqtt", "IsFirstRound = false");
                                mqttHandler.korakPoKorakPublish(textView_answer, false, false);
                                isFragment(false);
    //                            viewModel.setIsFirstRound(true);
    //                                }
    //                            });
                            }
                        }
//                        Log.i("mqtt","counter1: "+ counter1);!!!!!!!!!!!!!!!!
//                        viewModel.getIsFirstRoundLiveData().observe(getViewLifecycleOwner(), isFirstRound -> {
//                            Log.i("mqtt","isFirstRound in MyViewModel: " + isFirstRound);
//                            if (isFirstRound != null) {
//                                if(isFirstRound){
//                                    viewModel.setIsFirstRound(false);
//                                    Log.i("mqtt", "Posle nego sto se proveri da li je saveInstanceState == null i ako JESTE posalje se publish koji kaze da je isFirstRund = true");
////                                    mqttHandler.korakPoKorakPublish(textView_answer,true, false );
//                                    isFragment(true);
//                                }else{
//                                    viewModel.setIsFirstRound(true);
//                                    Log.i("mqtt", "Posle nego sto se proveri da li je saveInstanceState == null i ako NIJE posalje se publish koji kaze da je isFirstRund = false");
////                                    mqttHandler.korakPoKorakPublish(textView_answer, false, false);
//                                    isFragment(false);
//                                }
//                            }
//                        });!!!!!!!!!!
//                        if(savedInstanceState == null){
//                            isFirstRound = savedInstanceState.getBoolean("isFirstRound", true);
//                            Log.i("mqtt", "Posle nego sto se proveri da li je saveInstanceState == null i ako JESTE posalje se publish koji kaze da je isFirstRund = true");
//                            mqttHandler.korakPoKorakPublish(textView_answer,true, false );
//                            isFragment(true);
////                            savedInstanceState.remove("isFirstRound");
//                        }else{
//                            isFirstRound = savedInstanceState.getBoolean("isFirstRound", false);
//                            Log.i("mqtt", "Posle nego sto se proveri da li je saveInstanceState == null i ako NIJE posalje se publish koji kaze da je isFirstRund = false");
//                            mqttHandler.korakPoKorakPublish(textView_answer, false, false);
//                            isFragment(false);
////                            savedInstanceState.remove("isFirstRound");
//                        }

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
        isAnswerCorrect = true;
        player1Score.setText(score + "");
        Data.loggedInUser.setKorakPoKorak(Data.loggedInUser.getKorakPoKorak()+score);
        if(isOnline){
            userRepository.updateKorakPoKorak(Data.loggedInUser, Data.loggedInUser.getKorakPoKorak()+score);
            mqttHandler.pointPublish(score);
        }
        points_right.setTextColor(Color.RED);
        if(!isOnline){
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AssociationsFragment())
                    .setReorderingAllowed(true)
                    .commit();
        }
    }

    public void isFragment(boolean bool) {
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
                if (isAdded()) {
                    if(bool){
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, StepByStepFragment.newInstance(true, true))
                                .setReorderingAllowed(true)
                                .commit();
                    }else{
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new AssociationsFragment())
                                .setReorderingAllowed(true)
                                .commit();
                    }
                }

//            }
//        });
    }


    //    public void isFragment(boolean bool){
//        if(bool){
//            getParentFragmentManager()
//                    .beginTransaction()
////                    .detach(StepByStepFragment.this)
////                    .attach(new StepByStepFragment())
//                    .replace(R.id.fragment_container, StepByStepFragment.class, null)
//                    .setReorderingAllowed(true)
//                    .commit();}
//        else{
//            getParentFragmentManager()
//                    .beginTransaction()
////                    .detach(StepByStepFragment.this)
////                    .attach(new SkockoFragment())
//                    .replace(R.id.fragment_container, SkockoFragment.class, null)
//                    .setReorderingAllowed(true)
//                    .commit();
//        }
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        activity = (AppCompatActivity) getActivity();

        TextView scoreTimer = activity.findViewById(R.id.score_timer);

        player2UserName = activity.findViewById(R.id.player_2_user_name);

        if(Data.loggedInUser != null && !player2UserName.getText().toString().equals("Guest")){
            mqttHandler.korakPoKorakSubscribe(new MqttHandler.KorakPoKorakCallback() {
                @Override
                public void onCallBack(KorakPoKorak korakPoKorak) {
                    if(korakPoKorak != null){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("mqtt", "Loged User: "+Data.loggedInUser.getUsername());
//                                Log.i("mqtt", "IsFirstRound u subcribu: "+korakPoKorak.isFirstRound().toString());
                                    if(korakPoKorak.isFirstRound() && !korakPoKorak.isSecondPlayerTurn()){
//                                    if(isAdded()){
                                        Log.i("mqtt", "Ako je poslat publis u tacnom odg i isFristRound je true");
                                        isFragment(true);
                                        Log.i("mqtt", "isFirstRound = true i zavrsio se FragmentMenager");
//                                    }
                                    }else if(!korakPoKorak.isFirstRound() && !korakPoKorak.isSecondPlayerTurn()){
//                                    if(isAdded()){
                                        Log.i("mqtt", "Ako je poslat publis u tacnom odg i isFristRound je false");
                                        isFragment(false);
                                        Log.i("mqtt", "isFirstRound = false i zavrsio se FragmentMenager");
//                                    }
                                }
                                if(korakPoKorak.isSecondPlayerTurn()){
                                    Log.i("mqtt", "isMyTurn u subskrajbu pre nego sto je pomenjeno: " + isMyTurn);
                                    setIsMyTurn();
                                    Log.i("mqtt", "isMyTurn u subskrajbu posle nego sto je pomenjeno: " + isMyTurn);
                                        countDownTimer = new CountDownTimer(60000, 1000) {
                                            @Override
                                            public void onTick(long l) {
                                                Log.e("AA", "rad3" + l);
                                                Long min = ((l / 1000) % 3600) / 60;
                                                Long sec = (l / 1000) % 60;
                                                String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                                                scoreTimer.setText(format);
                                            }

                                            @Override
                                            public void onFinish() {
                                                scoreTimer.setText("00:00");
                                                if(savedInstanceState == null){
                                                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new StepByStepFragment().newInstance(true, true)).commit();
                                                    isFirstRound = false;
                                                }else{
                                                    associationsFragment.setIsOnline(true);
                                                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, associationsFragment).commit();
                                                }
                                            }
                                        }.start();
                                    }
                                }
//                            }
                        });
                    }
                }
            });

            mqttHandler.pointSubscribe(new MqttHandler.PointCallback() {
                @Override
                public void onCallback(UserDTO userDTO) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            player2Score.setText(userDTO.getPoints()+"");
                        }
                    });
                }
            });
        }

        ShowHideElements.showScoreBoard(activity);

        countDownTimer = new CountDownTimer(70000, 1000) {
            @Override
            public void onTick(long l) {
                if (Math.abs(l % 10000) < 1200) {
                    Log.e("AA","radi1");
                    if(count < 9){
                        Log.e("AA","rad2");
                        //treba da se stavi if ako igra jedna ili dva igraca ako igra jedan igrac onda treba da se pokazuje resenje na kraju ako ne onda treba da bude prazno resenje
                        if(isOnline){
                            if(count == 8){

                            }
                            else{
                                TextView textView = (TextView)textViwMap.get(count);
                                textView.setTextColor(Color.RED);
                            }
                        }else{
                            TextView textView = (TextView)textViwMap.get(count);
                            textView.setTextColor(Color.RED);
                        }
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
                if(!isOnline){
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new AssociationsFragment())
                            .setReorderingAllowed(true)
                            .commit();
                }
                if(isOnline){
//                if(!isAnswerCorrect){ //ako je prva ili drugs igra i igrac nije pogodio resenje jos jedan timer za 1 min
                    Log.i("mqtt", "User sending: "+Data.loggedInUser.getUsername());
                    mqttHandler.korakPoKorakPublish(textView_answer, true, true);
                    //moramo ovde poslati publish
//                    countDownTimer = new CountDownTimer(60000, 1000) {
//                        @Override
//                        public void onTick(long l) {
//                            Log.e("AA","rad3" + l);
//                            Long min = ((l / 1000) % 3600) / 60;
//                            Long sec = (l / 1000) % 60;
//                            String format = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
//                            scoreTimer.setText(format);
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            scoreTimer.setText("00:00");
//                        }
//                    }.start();
                }
                }
//                if(!isFirstRound){//ovaj if ne treba tu da stoji stavio si ga jer imas gore if koji kaze da ako je kraj i odgovor je netacan ond drugi igrac ima pravo da igra pa samo da znas
//                    getParentFragmentManager()
//                            .beginTransaction()
//                            .replace(R.id.fragment_container, new MyNumberFragment())
//                            .setReorderingAllowed(true)
//                            .commit();
//                }
//            }
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

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("myData", isFirstRound);
        counter1 = 1;
        editor.apply();
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean("isFirsRound", isFirstRound);
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isFirstRound", isFirstRound);
        super.onSaveInstanceState(outState);
    }
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean(KEY_STATE, isFirstRound);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//        if (myTask != null) {
//            myTask.cancel(true);
//        }
//    }

}