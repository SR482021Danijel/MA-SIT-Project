package com.ftn.ma_sit_project.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.adapters.ProgramAdapter;
import com.ftn.ma_sit_project.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RankListFragment extends Fragment {

    View view;
    ListView listView;
    ProgramAdapter arrayAdapter;

    List<User> users = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank_list, container, false);
        UserRepository.getAll(new UserRepository.FireStoreCallback1() {
            @Override
            public void onCallBack(List<User> user) {
                users.clear();
                users.addAll(user);
                String[] title = new String[users.size()];
                String[] description = new String[users.size()];
                users.sort((u1, u2) -> Integer.compare(u2.getAsocijacije(), u1.getAsocijacije()));
                for (int i = 0; i < users.size(); i++) {
                        title[i] = users.get(i).getUsername();
                        description[i] = users.get(i).getAsocijacije() + "";
                }
                Log.i("list", users+"");
                listView = view.findViewById(R.id.rank_list_view);
                arrayAdapter = new ProgramAdapter((AppCompatActivity) getActivity(), getActivity(), title, description, true);
                listView.setAdapter(arrayAdapter);
            }
        });
        return view;
    }
}