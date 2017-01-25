package com.example.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by aloha on 23-Jan-17.
 */
@Service
public class RuntimeServiceWrapper {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryServiceWrapper repositoryServiceWrapper;

    @Autowired
    FormServiceWrapper formServiceWrapper;


    /**
     * Start process
     * */
    public void startProcess(String processKey, String userId, Map<String, Object> params){
        params.put("initiator", userId);
        ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formServiceWrapper.getStartFormData(processKey);
        boolean canSubmit = formServiceWrapper.checkStartForm(properties, params);
        if(canSubmit){
            runtimeService.startProcessInstanceByKey(processKey, params);
        }
    }




}
