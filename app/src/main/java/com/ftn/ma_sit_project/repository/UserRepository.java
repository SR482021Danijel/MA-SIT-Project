package com.ftn.ma_sit_project.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ftn.ma_sit_project.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

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
}
