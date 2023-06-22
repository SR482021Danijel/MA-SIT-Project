package com.ftn.ma_sit_project.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.commonUtils.TempGetData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserRepository {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addUser(User user) {

        db.collection("Users").document(user.getId())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("db", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("db", "Error writing document", e);
                    }
                });
    }

    public void getUser(String email, String password, FireStoreCallback fireStoreCallback) {
        db.collection("Users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()){
                                    User user = document.toObject(User.class);
                                    fireStoreCallback.onCallBack(user);
                                }else {
                                    Log.i("loggin", "no document");
                                }

                            }
                        }
                    }
                });
    }

    public interface FireStoreCallback {
        void onCallBack(User user);
    }
}
