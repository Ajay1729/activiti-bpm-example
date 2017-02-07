package com.example.dto;

import org.activiti.engine.identity.User;

/**
 * Created by aloha on 22-Jan-17.
 */
public class UserDto {

    String id;
    String role;
    String firstName;
    String lastName;


    public UserDto(){}

    public UserDto(String id, String role, String firstName, String lastName) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



}
