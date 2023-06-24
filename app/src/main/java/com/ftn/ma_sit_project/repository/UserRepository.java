package com.ftn.ma_sit_project.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.commonUtils.TempGetData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void updateUser(User user) {
            DocumentReference docRef = db.collection("User").document(user.getId());
            docRef.update("koZnaZna", user.getKoZnaZna(), "spojnice", user.getSpojnice(), "asocijacije", user.getAsocijacije(), "skocko", user.getSkocko(), "korakPoKorak", user.getKorakPoKorak(), "mojBroj", user.getMojBroj(), "partije", user.getPartije(),"pobede", user.getPobede(), "porazi", user.getPorazi())
                    .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "User successfully changed"))
                    .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));

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

    public static void getAll(FireStoreCallback1 fireStoreCallback1){
        List<User> users = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                users.add(user);
                                fireStoreCallback1.onCallBack(users);
                                Log.d("REZ_DB", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public interface FireStoreCallback {
        void onCallBack(User user);
    }

    public interface FireStoreCallback1 {
        void onCallBack(List<User> user);
    }
}
