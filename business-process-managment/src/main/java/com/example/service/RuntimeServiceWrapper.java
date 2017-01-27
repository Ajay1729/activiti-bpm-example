package com.example.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processKey, params);
            //todo set name - use some global var - name of student
            runtimeService.setProcessInstanceName(processInstance.getId(), "my process name :)");
        }
    }

    public void getVariable(String  variableName){
        //todo get global vars
        //runtimeService.setProcessInstanceName();

    }

    public List<ProcessInstance> getProcessInstancesByProcessDefinitionKey(String processDefKey){
        return runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefKey).list();
    }

}
