package com.example.rest;

import com.example.dto.UserDto;
import com.example.dto.UserLoginDto;
import com.example.service.AuthService;
import com.example.service.IdentityServiceWrapper;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by aloha on 22-Jan-17.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    IdentityServiceWrapper identityServiceWrapper;

    @Autowired
    AuthService authService;


    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto){

        Optional<User> user = identityServiceWrapper.getUserById(userLoginDto.getId());

        if(user.isPresent()){
            if(user.get().getPassword().equals(userLoginDto.getPassword())){
                HashMap<String, String> responseObj = new HashMap<>();
                responseObj.put("token", "Bearer "+authService.buildToken(user.get()));
                return new ResponseEntity<>(responseObj, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }



    @RequestMapping(value = "/me",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity me(final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            HashMap<String, UserDto> res = new HashMap<>();
            res.put("user", new UserDto(user.get()));
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/group/{id}")
    public ResponseEntity usersByGroup(@PathVariable String id, final HttpServletRequest request) throws ServletException{
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            ArrayList<UserDto> responseList = new ArrayList<>();
            ArrayList<User> users = identityServiceWrapper.getMembersOfGroup(id);
            for(User user1:users){
                responseList.add(new UserDto(user1));
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }



    @RequestMapping(value = "/user/{id}")
    public ResponseEntity userById(@PathVariable String id, final HttpServletRequest request) throws ServletException{
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            Optional<User> user1 = identityServiceWrapper.getUserById(id);
            return new ResponseEntity<>(user1.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }



}
