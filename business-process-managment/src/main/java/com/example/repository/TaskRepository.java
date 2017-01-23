package com.example.repository;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by aloha on 23-Jan-17.
 */
@Component
public class TaskRepository {


    @Autowired
    TaskService taskService;


    public ArrayList<Task> getTasksAssignedToUser(String userId){
        return (ArrayList<Task>) taskService.createTaskQuery().taskAssignee(userId).list();
    }

    public ArrayList<Task> getTasksThatCanBeClaimed(String userId){
        return (ArrayList<Task>) taskService.createTaskQuery().taskCandidateUser(userId).list();
    }

}
