package com.example.rest;

import com.example.dto.UserDTO;
import com.example.dto.UserLogin;
import com.example.service.AuthService;
import com.example.service.UserServiceWrapper;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by aloha on 22-Jan-17.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    UserServiceWrapper userServiceWrapper;

    @Autowired
    AuthService authService;


    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserLogin userLogin){

        Optional<User> user = userServiceWrapper.getUserById(userLogin.getId());

        if(user.isPresent()){
            if(user.get().getPassword().equals(userLogin.getPassword())){
                return new ResponseEntity<>(authService.buildToken(user.get()), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }



    @RequestMapping(value = "/me",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity me(final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            return new ResponseEntity<>(new UserDTO(user.get()), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }




}
