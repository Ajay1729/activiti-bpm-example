package com.example.service;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by aloha on 22-Jan-17.
 */
@Service
public class IdentityServiceWrapper {

    @Autowired
    IdentityService identityService;

    public ArrayList<User> getAll(){

        return (ArrayList<User>) identityService.createUserQuery().list();

    }

    //TODO [!] get users from certain group



    public List<Group> getUsersGroups(String userId){
        return identityService.createGroupQuery().groupMember(userId).list();
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
