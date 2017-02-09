package com.example.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.constants.Constants.*;


/**
 * Created by aloha on 23-Jan-17.
 */
@Service
public class RuntimeServiceWrapper {

    @Autowired
    MailService mailService;


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryServiceWrapper repositoryServiceWrapper;

    @Autowired
    FormServiceWrapper formServiceWrapper;

    @Autowired
    IdentityServiceWrapper identityServiceWrapper;


    /**
     * Start process and set process initiator variable
     * */
    public void startProcess(String processId, String userId, Map<String, Object> params){
        params.put(INITIATOR, userId);
        startProcess(processId, params);
    }


    /**
     * Start process without setting process initiator
     * */
    public void startProcess(String id, Map<String, Object> params){
        ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formServiceWrapper.getStartFormData(id);
        boolean canSubmit = formServiceWrapper.checkStartForm(properties, params);
        if(canSubmit){
            ProcessInstance pi = runtimeService.startProcessInstanceById(id, params);
        }
    }


    public List<ProcessInstance> getProcessInstancesStartedByUser(String userId, String processDefId){
        // https://community.alfresco.com/thread/218889-start-process-instance-by-user
        ArrayList<ProcessInstance> retVal = new ArrayList<>();
        ArrayList<ProcessInstance> all = (ArrayList<ProcessInstance>) runtimeService.createProcessInstanceQuery().processDefinitionId(processDefId).list();
        for(ProcessInstance instance:all){
            if(userId.equals(runtimeService.getVariable(instance.getProcessInstanceId(), "initiator"))){
                retVal.add(instance);
            }
        }
        return retVal;
    }


}
