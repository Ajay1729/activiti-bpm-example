package com.example.rest;

import com.example.dto.UserDTO;
import com.example.dto.UserLogin;
import com.example.service.AuthService;
import com.example.service.IdentityServiceWrapper;
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
    public ResponseEntity login(@RequestBody UserLogin userLogin){

        Optional<User> user = identityServiceWrapper.getById(userLogin.getId());

        if(user.isPresent()){
            if(user.get().getPassword().equals(userLogin.getPassword())){
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
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity me(final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            HashMap<String, UserDTO> res = new HashMap<>();
            res.put("user", new UserDTO(user.get()));
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }




}
