package com.example.rest;

import com.example.dto.ExecutionDTO;
import com.example.service.AuthService;
import com.example.service.FormServiceWrapper;
import com.example.service.RepositoryServiceWrapper;
import com.example.service.RuntimeServiceWrapper;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


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


    /**
     * Returns process definitions that user can start
     * */
    @RequestMapping(value = "/my",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity myprocesses(final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){

            ArrayList<ProcessDefinition> startable = (ArrayList<ProcessDefinition>) repositoryServiceWrapper.getProcessDefStartableByUser(user.get().getId());
            ArrayList<Map<String, String>> customStartable = new ArrayList<>();
            for(ProcessDefinition processDefinition: startable) {
                Map<String, String> map = new HashMap<>();
                map.put("key", processDefinition.getKey());
                map.put("name", processDefinition.getName());
                map.put("description", processDefinition.getDescription());
                customStartable.add(map);
            }
            return new ResponseEntity<>(customStartable, HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    /**
     * Returns start form
     * params: process def key
     * */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity startForm(@PathVariable String id, final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formServiceWrapper.getStartFormData(id);
            return new ResponseEntity<>(properties, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    /**
     * Start process definition
     * params: process def key & start form params
     * */
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



    /**
     * Returns process instances
     * params: process def key
     * */
    @RequestMapping(value = "/instances/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity processInstances(@PathVariable String id, final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){

            List<ProcessInstance> instances = runtimeServiceWrapper.getProcessInstancesStartedByUser(user.get().getId(), id);
            List<Map<String, Object>> custom = new ArrayList<>();
            for(ProcessInstance processInstance:instances){
                Map<String, Object> map = new HashMap<>();
                map.put("id", processInstance.getId());
                map.put("name", processInstance.getName());
                custom.add(map);
            }
            return new ResponseEntity<>(custom, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }




}
