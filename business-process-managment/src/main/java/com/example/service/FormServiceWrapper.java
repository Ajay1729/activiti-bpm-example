package com.example.service;

import com.example.dto.TaskExecutionDTO;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
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

    public List<FormProperty> getTaskFormData(String taskId){
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        return formProperties;
    }


    public List<FormProperty> getStartFormData(String procesDefId){
        //formService.getStartFormData()
        StartFormData startFormData = formService.getStartFormData(procesDefId);
        List<FormProperty> formProperties = startFormData.getFormProperties();
        return formProperties;
    }



    /* execute task */
    public void submitTaskFormData(String taskId, Map<String, String> params){
        formService.submitTaskFormData(taskId, params);
    }


    public boolean checkForm(ArrayList<FormProperty> properties, Map<String, String> params){

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


    public Map<String, String> transformExecutionObject(TaskExecutionDTO taskExecutionDTO){
        Map<String, String> result = new HashMap<>();
        for(Map<String, String> map : taskExecutionDTO.getFormProperties()){
            result.put(map.get("id"), map.get("value"));
        }
        return result;
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
