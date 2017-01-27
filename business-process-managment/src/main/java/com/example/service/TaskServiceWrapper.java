package com.example.service;

import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.Group;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by aloha on 23-Jan-17.
 */
@Service
public class TaskServiceWrapper {

    @Autowired
    TaskService taskService;

    @Autowired
    FormServiceWrapper formServiceWrapper;

    @Autowired
    IdentityServiceWrapper identityServiceWrapper;


    public ArrayList<Task> getTasksAssignedToUser(String userId){
        return (ArrayList<Task>) taskService.createTaskQuery().taskAssignee(userId).list();
    }

    public ArrayList<Task> getTasksThatCanBeClaimed(String userId){
        ArrayList<Task> tasks = new ArrayList<>();

        /*1 - user is candidate*/
        for(Task task : taskService.createTaskQuery().taskCandidateUser(userId).list()){
            tasks.add(task);
        }

        /*2 - group that user belongs is candidate*/
        ArrayList<Group> groups = (ArrayList<Group>) identityServiceWrapper.getUsersGroups(userId);
        for(Group group:groups){
            for(Task task : taskService.createTaskQuery().taskCandidateGroup(group.getId()).list()){
                tasks.add(task);
            }
        }

        return tasks;
    }


    public void claimTask(String taskId, String userId){
        if(canClaim(taskId, userId)) {
            taskService.claim(taskId, userId);
        }
    }


    public void executeTask(String taskId, String userId, Map<String, String> params){
        if(canExecute(taskId, userId)){
            ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formServiceWrapper.getTaskFormData(taskId);
            boolean canSubmit = formServiceWrapper.checkForm(properties, params);
            if(canSubmit){
                formServiceWrapper.submitTaskFormData(taskId, params);
            }
        }
    }


    public boolean canClaim(String taskId, String userId){
        for (Task t : getTasksThatCanBeClaimed(userId))
            if (t.getId().equals(taskId))
                return true;
        return false;
    }

    public boolean canExecute(String taskId, String userId){
        for (Task t : getTasksAssignedToUser(userId))
            if (t.getId().equals(taskId))
                return true;
        return false;
    }



}
