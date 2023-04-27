package com.ftn.ma_sit_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.adapters.ProgramAdapter;

public class RankListFragment extends Fragment {

    View view;
    ListView listView;
    ProgramAdapter arrayAdapter;
    String[] title = {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa"};
    String[] description = {"b","bb","bbb","bbbb","bbbbb","bbbbbb","bbbbbbb","bbbbbbbb","bbbbbbbbb"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank_list, container, false);
        listView = view.findViewById(R.id.rank_list_view);
        arrayAdapter = new ProgramAdapter(getActivity(), title, description);
        listView.setAdapter(arrayAdapter);
        return view;
    }
}