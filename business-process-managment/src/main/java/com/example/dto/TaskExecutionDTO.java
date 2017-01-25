package com.example.dto;

import org.activiti.engine.form.FormProperty;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by aloha on 24-Jan-17.
 */
public class TaskExecutionDTO {

    private String taskId;
    private ArrayList<Map<String, String>> formProperties;

    public TaskExecutionDTO(){}

    public TaskExecutionDTO(String taskId, ArrayList<Map<String, String>> formProperties) {
        this.taskId = taskId;
        this.formProperties = formProperties;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ArrayList<Map<String, String>> getFormProperties() {
        return formProperties;
    }

    public void setFormProperties(ArrayList<Map<String, String>> formProperties) {
        this.formProperties = formProperties;
    }
}
