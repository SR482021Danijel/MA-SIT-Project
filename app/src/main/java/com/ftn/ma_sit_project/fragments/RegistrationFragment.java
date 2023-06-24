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

import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.R;
import com.ftn.ma_sit_project.service.UserService;
import com.google.android.material.navigation.NavigationView;

import java.util.UUID;

public class RegistrationFragment extends Fragment {

    View view;
    AppCompatActivity activity;
    UserService userService;
    private NavigationView navigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (AppCompatActivity) getActivity();

        navigationView = activity.findViewById(R.id.nav_view);

        userService = new UserService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_registration, container, false);

        EditText emailText = view.findViewById(R.id.reg_email);
        EditText usernameText = view.findViewById(R.id.reg_userName);
        EditText passwordText = view.findViewById(R.id.reg_password);
        EditText passwordAgainText = view.findViewById(R.id.reg_password_again);
        Button btnRegister = view.findViewById(R.id.btn_reg);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailText.getText().toString().equals("") &&
                        !usernameText.getText().toString().equals("") &&
                        !passwordText.getText().toString().equals("") &&
                        !passwordAgainText.getText().toString().equals("")) {

                    if (passwordText.getText().toString().equals(passwordAgainText.getText().toString())) {
                        User newUser = new User(UUID.randomUUID().toString(),
                                usernameText.getText().toString(),
                                passwordText.getText().toString(),
                                emailText.getText().toString(), 0, 0, 0, 0, 0, 0, 0, 0, 0 );
                        userService.addUser(newUser);
                        Toast.makeText(activity.getApplicationContext(), "Successful registration", Toast.LENGTH_SHORT).show();

                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment())
                                .setReorderingAllowed(true)
                                .commit();

                        navigationView.setCheckedItem(R.id.nav_item_home);
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Please fill in all information", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}