package com.ftn.ma_sit_project.service;

import com.ftn.ma_sit_project.Model.User;
import com.ftn.ma_sit_project.repository.UserRepository;

public class UserService {

    UserRepository userRepository = new UserRepository();

    public void addUser(User user){
        userRepository.addUser(user);
    }
}
