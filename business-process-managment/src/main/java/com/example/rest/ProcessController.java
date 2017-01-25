package com.example.rest;

import com.example.dto.ExecutionDTO;
import com.example.service.AuthService;
import com.example.service.FormServiceWrapper;
import com.example.service.RepositoryServiceWrapper;
import com.example.service.RuntimeServiceWrapper;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.UppApplication.PROCES_KEY;

/**
 * Created by aloha on 25-Jan-17.
 */
@RestController
@RequestMapping("/api/process")
public class ProcessController {

    @Autowired
    RepositoryServiceWrapper repositoryServiceWrapper;

    @Autowired
    AuthService authService;

    @Autowired
    FormServiceWrapper formServiceWrapper;

    @Autowired
    RuntimeServiceWrapper runtimeServiceWrapper;

    @RequestMapping(value = "/my",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity myprocesses(final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){

            //-------//
            // todo - get processes that belong to current user
            ProcessDefinition processDefinition = repositoryServiceWrapper.getProcessDefinition(PROCES_KEY);
            Map<String, String > map = new HashMap<>();
            map.put("key", processDefinition.getKey());
            map.put("name", processDefinition.getName());
            map.put("description", processDefinition.getDescription());
            //--------


            ArrayList<Map<String, String>> procesiiiii = new ArrayList<>();
            procesiiiii.add(map);
            return new ResponseEntity<>(procesiiiii, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity startForm(@PathVariable String id, final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            //todo maybe process + form, not just form
            ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formServiceWrapper.getStartFormData(id);
            return new ResponseEntity<>(properties, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/start",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity execute(@RequestBody ExecutionDTO data, final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            Map<String, Object> params = formServiceWrapper.makeStartFormParams(data);
            runtimeServiceWrapper.startProcess(data.getId(), user.get().getId(), params);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }






}
