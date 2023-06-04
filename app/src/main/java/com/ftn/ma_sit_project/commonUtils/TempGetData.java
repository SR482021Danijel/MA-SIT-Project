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

    public interface FireStoreCallback{
        void onCallBack(ArrayList<String> list);
    }

}
