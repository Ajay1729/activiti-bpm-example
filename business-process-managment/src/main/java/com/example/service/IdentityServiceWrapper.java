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


    public ArrayList<User> getMembersOfGroup(String groupId){
        return (ArrayList<User>) identityService.createUserQuery().memberOfGroup(groupId).list();
    }

    public String getUserInfo(String userId, String key){
        return identityService.getUserInfo(userId, key);
    }

    public List<Group> getUsersGroups(String userId){
        return identityService.createGroupQuery().groupMember(userId).list();
    }

    public Optional<User> getUserById(String userId){
        ArrayList<User> users = (ArrayList<User>) identityService.createUserQuery().userId(userId).list();
        if(users.size()!=0){
            return Optional.of(users.get(0));
        }else {
            return Optional.empty();
        }
    }


}
