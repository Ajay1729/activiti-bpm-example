package com.example.service;

import com.example.dto.ExecutionDto;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aloha on 23-Jan-17.
 */
@Service
public class FormServiceWrapper {


    @Autowired
    FormService formService;

    @Autowired
    RepositoryServiceWrapper repositoryServiceWrapper;


    public List<FormProperty> getTaskFormData(String taskId){
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        return formProperties;
    }


    public List<FormProperty> getStartFormData(String processId){
        ProcessDefinition processDef = repositoryServiceWrapper.getProcessDefById(processId);//repositoryServiceWrapper.getProcessDefByProcessDefKey(processKey);
        StartFormData startFormData = formService.getStartFormData(processDef.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        return formProperties;
    }

    /**
     * Execute/finish task
     * */
    public void submitTaskFormData(String taskId, Map<String, String> params){
        formService.submitTaskFormData(taskId, params);
    }


    public Map<String, String> makeTaskFormParams(ExecutionDto executionDto){
        Map<String, String> result = new HashMap<>();
        for(Map<String, String> map : executionDto.getFormProperties()){
            result.put(map.get("id"), map.get("value"));
        }
        return result;
    }

    public Map<String, Object> makeStartFormParams(ExecutionDto executionDto){
        Map<String, Object> result = new HashMap<>();
        for(Map<String, String> map : executionDto.getFormProperties()){
            result.put(map.get("id"), map.get("value"));
        }
        return result;
    }


    public boolean checkTaskForm(ArrayList<FormProperty> properties, Map<String, String> params){

        boolean canSubmit = true;

        for(FormProperty property: properties){
            //todo field validation

            /*is required*/
            if(property.isRequired()) {
                if(params.containsKey(property.getId())) {
                    canSubmit = !(params.get(property.getId()).equals(""));
                }else {
                    canSubmit = false;
                    break;
                }
            }

            /*type check*/


        }

        return canSubmit;
    }


    public boolean checkStartForm(ArrayList<FormProperty> properties, Map<String, Object> params){

        boolean canSubmit = true;

        for(FormProperty property: properties){
            //todo field validation

            /*is required*/
            if(property.isRequired()) {
                if(params.containsKey(property.getId())) {
                    canSubmit = !(params.get(property.getId()).toString().equals("")); //toString
                }else {
                    canSubmit = false;
                    break;
                }
            }

            /*type check*/


        }

        return canSubmit;
    }



}
