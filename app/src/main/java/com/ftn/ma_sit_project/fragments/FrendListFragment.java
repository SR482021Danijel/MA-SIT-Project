package com.ftn.ma_sit_project.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.adapters.ProgramAdapter;

public class FrendListFragment extends Fragment {

    View view;
    ListView listView;
    SearchView searchView;
//    ArrayAdapter<String> arrayAdapter;
    ProgramAdapter arrayAdapter;
    String[] title = {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa"};
    String[] description = {"b","bb","bbb","bbbb","bbbbb","bbbbbb","bbbbbbb","bbbbbbbb","bbbbbbbbb"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frend_list, container, false);
        listView = view.findViewById(R.id.listView);
        arrayAdapter = new ProgramAdapter(getActivity(), title, description);
        listView.setAdapter(arrayAdapter);
        return view;
    }
}