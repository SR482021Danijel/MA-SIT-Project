package com.ftn.ma_sit_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ftn.ma_sit_project.R;

public class HomeFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        Button btnPlay = view.findViewById(R.id.play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                List<StepByStep> stepBySteps = stepByStepDAO.selectAll();
                         getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AssociationsFragment())
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        return view;
    }
}