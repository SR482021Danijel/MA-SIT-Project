package com.ftn.ma_sit_project.DAO;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ftn.ma_sit_project.MainActivity;
import com.ftn.ma_sit_project.Model.StepByStep;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepByStepDAO {

    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    StepByStep stepByStep = new StepByStep();
    MainActivity mainActivity;

    public void insert() {
        StepByStep stepByStep = new StepByStep("IMA VEZE SA DUNAVOM I SAVOM", "SVIH DESET SLOVA OVE RECI SU RALICITA", "IMA VEZE SA ZDRAVLJEM", "MOZE SE ODNOSITI NA ZIVOT", "IMA SVOG AGENTA I SVOJU PREMIJU", "ZA VOZILO JE OBAVEZNO", "POSTOJI I KASKO VARIJANTA", "OSIGURANJE");
        Map<String, String> stepByStepMap = new HashMap<>();
        stepByStepMap.put("first", stepByStep.getGuestion1());
        stepByStepMap.put("second", stepByStep.getGuestion2());
        stepByStepMap.put("third", stepByStep.getGuestion3());
        stepByStepMap.put("fourth", stepByStep.getGuestion4());
        stepByStepMap.put("fifth", stepByStep.getGuestion5());
        stepByStepMap.put("sixth", stepByStep.getGuestion6());
        stepByStepMap.put("seventh", stepByStep.getGuestion7());
        stepByStepMap.put("response", stepByStep.getResponse());
        fb.collection("stepByStep")
                .add(stepByStepMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("REZ_DB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REZ_DB", "Error adding document", e);
                    }
                });
    }

    public List<StepByStep> selectAll() {
        List<StepByStep> listOfSteps = new ArrayList<>();
        fb.collection("stepByStep")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot qds : task.getResult()) {
                                Map<String, Object> map = qds.getData();
                                StepByStep stepByStep1 = new StepByStep();
                                stepByStep1.setQuestion1(map.get("first").toString());
                                stepByStep1.setQuestion2(map.get("second").toString());
                                stepByStep1.setQuestion3(map.get("third").toString());
                                stepByStep1.setQuestion4(map.get("forth").toString());
                                stepByStep1.setQuestion5(map.get("fifth").toString());
                                stepByStep1.setQuestion6(map.get("sixth").toString());
                                stepByStep1.setQuestion7(map.get("seventh").toString());
                                stepByStep1.setResponse(map.get("response").toString());
                                listOfSteps.add(stepByStep1);
                                Log.d("REZ_DB", "question1:"+ qds.getData());
                            }
                        } else {
                            Log.w("REZ_DB", "Error getting documents.", task.getException());
                        }
                    }
                });
        return listOfSteps;
    }

    public void update(StepByStep stepByStep) {
        Map<String, Object> stepByStepMap = new HashMap<>();
        stepByStepMap.put("question1", stepByStep.getGuestion1());
        stepByStepMap.put("question2", stepByStep.getGuestion2());
        stepByStepMap.put("question3", stepByStep.getGuestion3());
        stepByStepMap.put("question4", stepByStep.getGuestion4());
        stepByStepMap.put("question5", stepByStep.getGuestion5());
        stepByStepMap.put("question6", stepByStep.getGuestion6());
        stepByStepMap.put("question7", stepByStep.getGuestion7());
        stepByStepMap.put("response", stepByStep.getResponse());
        update(stepByStep);
        fb.collection("stepByStep")
                .document("")
                .update(stepByStepMap)
                .addOnSuccessListener(aVoid -> Log.d("REZ_DB", "Steps successfully changed"))
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));
    }

    public void delete(String id) {
        fb.collection("stepByStep")
                .document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("REZ_DB", "Steps successfully deleted");})
                .addOnFailureListener(e -> Log.w("REZ_DB", "Error getting documents.", e));
    }

    public StepByStep selectById() {
        DocumentReference docRef = fb.collection("stepByStep").document("pAPBDVqspKgwgdQdh8Jh");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> map = documentSnapshot.getData();
                StepByStep stepByStep1 = new StepByStep();
                stepByStep1.setQuestion1(map.get("first").toString());
                stepByStep1.setQuestion2(map.get("second").toString());
                stepByStep1.setQuestion3(map.get("third").toString());
                stepByStep1.setQuestion4(map.get("forth").toString());
                stepByStep1.setQuestion5(map.get("fifth").toString());
                stepByStep1.setQuestion6(map.get("sixth").toString());
                stepByStep1.setQuestion7(map.get("seventh").toString());
                stepByStep1.setResponse(map.get("response").toString());
                stepByStep = stepByStep1;
                Log.d("REZ_DB", documentSnapshot.getId() + " => " + documentSnapshot.getData());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                stepByStep = null;
                Log.w("REZ_DB", "Error getting documents.", e);
            }
        });
        return stepByStep;
    }

}
