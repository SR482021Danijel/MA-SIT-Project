package com.ftn.ma_sit_project.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.R;

public class ProfileFragment extends Fragment {
    View view;
    TextView userName, email, koZnaZna, spojnice, asocijacije, skocko, korakPoKorak, mojBroj, partije, pobede, porazi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.userName);
        email = view.findViewById(R.id.email);
        koZnaZna = view.findViewById(R.id.textViewKoZnaZna);
        spojnice = view.findViewById(R.id.textViewSpojnice);
        asocijacije = view.findViewById(R.id.textViewAsocijacije);
        skocko = view.findViewById(R.id.textViewSkocko);
        korakPoKorak = view.findViewById(R.id.textViewKorakPoKorak);
        mojBroj = view.findViewById(R.id.textViewMojBroj);
        partije = view.findViewById(R.id.textViewPartije);
        pobede = view.findViewById(R.id.textViewPobedjene);
        porazi = view.findViewById(R.id.textViewIzgubljene);
        if(Data.loggedInUser != null){
            userName.setText(Data.loggedInUser.getUsername());
            email.setText(Data.loggedInUser.getEmail());
            koZnaZna.setText(Data.loggedInUser.getKoZnaZna()+"");
            spojnice.setText(Data.loggedInUser.getSpojnice()+"");
            asocijacije.setText(Data.loggedInUser.getAsocijacije()+"");
            skocko.setText(Data.loggedInUser.getSkocko()+"");
            korakPoKorak.setText(Data.loggedInUser.getKorakPoKorak()+"");
            mojBroj.setText(Data.loggedInUser.getMojBroj()+"");
            partije.setText(Data.loggedInUser.getPartije()+"");
            pobede.setText(Data.loggedInUser.getPobede()+"");
            porazi.setText(Data.loggedInUser.getPorazi()+"");
        }
        return view;
    }
}