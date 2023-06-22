package com.ftn.ma_sit_project.commonUtils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TempGetData {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    static String TAG = "skocko";

    static ArrayList<String> list = new ArrayList<>();

    public static void getSkocko(FireStoreCallback fireStoreCallback) {

        DocumentReference docRef = db.collection("Games").document("Skocko");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        list = (ArrayList<String>) document.get("answer1");
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

    public static void getKorakPoKorak(FireStoreCallback firestoreCallback) {
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
                                List<String> runda1 = (List<String>) document.get("runda1");
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

    public static void getAsocijacije(FireStoreCallback firestoreCallback) {
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
                                List<String> runda1 = (List<String>) document.get("runda1");
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

    public interface FireStoreCallback{
        void onCallBack(ArrayList<String> list);
    }

    public interface FireStoreCallback1{
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

    public static void getDataAsMap(FireStoreCallback1 firestoreCallback) {
        db.collection("Games").document("Spojnice")
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
