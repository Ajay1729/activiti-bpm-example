package com.example.service;

import com.example.repository.UserRepository;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by aloha on 22-Jan-17.
 */
@Service
public class UserServiceWrapper {

    @Autowired
    UserRepository userRepository;


    public Optional<User> getUserById(String id){
        System.out.println(">>> GETTING USER BY ID");
        return userRepository.getById(id);
    }

    public void getUserRole(){
        // TODO - get user role
    }

}
