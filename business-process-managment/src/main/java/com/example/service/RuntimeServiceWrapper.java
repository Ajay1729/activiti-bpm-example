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


    public void startProcess(String procesKey, String userId, Map<String, Object> params){
        ProcessDefinition processDefinition = repositoryServiceWrapper.getProcessDefinition(procesKey);
        ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formServiceWrapper.getStartFormData(procesKey);
        boolean canSubmit = formServiceWrapper.checkStartForm(properties, params);
        if(canSubmit){
            runtimeService.startProcessInstanceByKey(procesKey, params);
        }
    }

}
