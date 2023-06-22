package com.ftn.ma_sit_project.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ftn.ma_sit_project.Model.Data;
import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.repository.UserRepository;
import com.ftn.ma_sit_project.service.UserService;
import com.google.android.material.navigation.NavigationView;

public class LoginFragment extends Fragment {

    View view;
    EditText emailText, passwordText;
    UserService userService;
    AppCompatActivity activity;
    private NavigationView navigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userService = new UserService();

        activity = (AppCompatActivity) getActivity();

        navigationView = activity.findViewById(R.id.nav_view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);

        emailText = view.findViewById(R.id.log_email);
        passwordText = view.findViewById(R.id.log_password);
        Button btnLogin = view.findViewById(R.id.btn_log);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailText.getText().toString().equals("")
                        && !passwordText.getText().toString().equals("")) {

                    userService.getUser(emailText.getText().toString(),
                            passwordText.getText().toString(),
                            new UserRepository.FireStoreCallback() {
                                @Override
                                public void onCallBack(User user) {
                                    if (user != null) {
                                        Data.loggedInUser = user;

                                        getParentFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_container, new HomeFragment())
                                                .setReorderingAllowed(true)
                                                .commit();

                                        navigationView.setCheckedItem(R.id.nav_item_home);

                                        Log.i("loggin", Data.loggedInUser.getUsername() + " " + Data.loggedInUser.getEmail());
                                        Toast.makeText(activity.getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Please fill in all information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}