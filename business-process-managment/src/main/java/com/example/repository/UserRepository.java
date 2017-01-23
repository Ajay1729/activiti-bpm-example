package com.example.repository;

import org.activiti.engine.*;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by aloha on 22-Jan-17.
 */
@Component
public class UserRepository {

    //usually i'll just extends JPA repo

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    IdentityService identityService;


    public ArrayList<User> getAll(){
        return (ArrayList<User>) identityService.createUserQuery().list();
    }


    public Optional<User> getById(String id){
        ArrayList<User> users =  getAll();
        for(User user : users){
            if(user.getId().equals(id))
                return Optional.of(user);
        }
        return Optional.empty();
    }



}
