package com.example.service;

import com.example.repository.TaskRepository;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by aloha on 23-Jan-17.
 */
@Service
public class TaskServiceWrapper {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;


    public ArrayList<Task> getTasksAssignedToUser(String userId){
        System.out.println(">>> GETTING TASKS ASSIGNED TO USER");
        return taskRepository.getTasksAssignedToUser(userId);
    }

    public ArrayList<Task> getTasksThatCanBeClaimed(String userId){
        System.out.println(">>> GETTING TASKS THAT CAN BE CLAIMED");
        return taskRepository.getTasksThatCanBeClaimed(userId);
    }


    public boolean claimTask(String taskId, String userId){
        if (!canClaim(taskId, userId))
            return false;
        else{
            taskService.claim(taskId, userId);
            return true;
        }
    }


    public void getTaskForm(String taskId, String userId){


    }


    /* utility methods */
    private boolean canClaim(String taskId, String userId){
        for (Task t : taskRepository.getTasksThatCanBeClaimed(userId))
            if (t.getId().equals(taskId))
                return true;
        return false;
    }

    private boolean canExecute(String taskId, String userId){
        for (Task t : taskRepository.getTasksAssignedToUser(userId))
            if (t.getId().equals(taskId))
                return true;
        return false;
    }

}
