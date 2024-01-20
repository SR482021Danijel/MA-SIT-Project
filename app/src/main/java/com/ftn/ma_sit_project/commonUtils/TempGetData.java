package com.ftn.ma_sit_project.commonUtils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ftn.ma_sit_project.Model.WhoKnows;
import com.ftn.ma_sit_project.Model.WhoKnowsAnswer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TempGetData {

    static String TAG = "games";
    static ArrayList<String> list = new ArrayList<>();
//    static List<Integer> roundNumbersList = Arrays.asList(1,2);

    public static void saveAsocijacije(int broj) {

    }

//    public static void shuffleRounds() {
//        Collections.shuffle(roundNumbersList);
//    }

    public static void getSkocko(String currentRound, FireStoreCallback fireStoreCallback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int roundNum;
        if (currentRound.equals("Round: 1")) {
            roundNum = 1;
        } else {
            roundNum = 2;
        }

        DocumentReference docRef = db.collection("Games").document("Skocko");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        list = (ArrayList<String>) document.get("answer" + roundNum);
                        fireStoreCallback.onCallBack(list);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public static void getWhoKnows(Integer currentRound, WhoFireStoreCallback fireStoreCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection("WhoKnowsGame").document("Round" + currentRound);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        WhoKnows whoKnows = document.toObject(WhoKnows.class);
                        fireStoreCallback.onCallback(whoKnows);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public static void setWhoKnows() {
        WhoKnows whoKnows = new WhoKnows("What is the atomic number of Hydrogen?", Arrays.asList(
                new WhoKnowsAnswer("1", true),
                new WhoKnowsAnswer("2", false),
                new WhoKnowsAnswer("3", false),
                new WhoKnowsAnswer("4", false))
        );

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("WhoKnowsGame").document("Round5").set(whoKnows);
    }

    public static void getKorakPoKorak(FireStoreCallback firestoreCallback, String runda) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> list = new ArrayList<String>();
        db.collection("Games").document("KorakPoKorak")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("selectTestById", "Task successful");
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("selectTestById", "Document exists");
                                List<String> runda1 = (List<String>) document.get(runda);
                                if (runda1 != null) {
                                    list.addAll(runda1);
                                }
                                firestoreCallback.onCallBack(list);
                            } else {
                                Log.e("GRESKA", "No such document");
                            }
                        } else {
                            Log.e("GRESKA", "LAVOR");
                        }
                    }
                });

    }

    public static void getAsocijacije(FireStoreCallback firestoreCallback, String runda) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> list = new ArrayList<String>();
        db.collection("Games").document("Asocijacije")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("selectTestById", "Task successful");
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("selectTestById", "Document exists");
                                List<String> runda1 = (List<String>) document.get(runda);
                                if (runda1 != null) {
                                    list.addAll(runda1);
                                }
                                firestoreCallback.onCallBack(list);
                            } else {
                                Log.e("GRESKA", "No such document");
                            }
                        } else {
                            Log.e("GRESKA", "LAVOR");
                        }
                    }
                });

    }

    public interface FireStoreCallback {
        void onCallBack(ArrayList<String> list);
    }

    public interface WhoFireStoreCallback {
        void onCallback(WhoKnows whoKnows);
    }

    public interface FireStoreCallback1 {
        void onCallBack(Map<String, Object> map);
    }

//    public static void getSpojnice(FireStoreCallback firestoreCallback) {
//        ArrayList<String> list = new ArrayList<String>();
//        db.collection("Games").document("Spojnice")
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("selectTestById", "Task successful");
//                            DocumentSnapshot document = task.getResult();
//                            if (document.exists()) {
//                                Log.d("selectTestById", "Document exists");
//                                List<String> runda1 = (List<String>) document.get("runda1");
//                                if (runda1 != null) {
//                                    list.addAll(runda1);
//                                }
//                                firestoreCallback.onCallBack(list);
//                            } else {
//                                Log.e("GRESKA", "No such document");
//                            }
//                        } else {
//                            Log.e("GRESKA", "LAVOR");
//                        }
//                    }
//                });
//
//    }

    public static void getDataAsMap(FireStoreCallback1 firestoreCallback, String document) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Games").document(document)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String, Object> data = document.getData();
                                firestoreCallback.onCallBack(data);
                            } else {
                                Log.e("ERROR", "No such document");
                            }
                        } else {
                            Log.e("ERROR", "Task failed");
                        }
                    }
                });
    }

}
