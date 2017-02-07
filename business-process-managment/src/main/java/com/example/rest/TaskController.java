package com.example.rest;

import com.example.dto.ExecutionDto;
import com.example.service.AuthService;
import com.example.service.FormServiceWrapper;
import com.example.service.TaskServiceWrapper;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by aloha on 24-Jan-17.
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {


    @Autowired
    AuthService authService;

    @Autowired
    TaskServiceWrapper taskServiceWrapper;

    @Autowired
    FormServiceWrapper formServiceWrapper;



    @RequestMapping(value = "/mytasks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity myTasks(final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            ArrayList<Task> list = taskServiceWrapper.getTasksAssignedToUser(user.get().getId());
            List<Map<String, Object>> customTaskList = new ArrayList<>();
            for (Task task : list) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", task.getId());
                //map.put("taskDefinitionKey", task.getTaskDefinitionKey());
                map.put("name", task.getName());
                map.put("description", task.getDescription());
                //task.getProcessDefinitionId() //todo add more task info, like name of process that task belongs to
                customTaskList.add(map);
            }
            return new ResponseEntity<>(customTaskList, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }



    @RequestMapping(value = "/involved",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity involved(final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            ArrayList<Task> list = taskServiceWrapper.getTasksThatCanBeClaimed(user.get().getId());
            List<Map<String, Object>> customTaskList = new ArrayList<>();
            for (Task task : list) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("id", task.getId());
                //map.put("taskDefinitionKey", task.getTaskDefinitionKey());
                map.put("name", task.getName());
                map.put("description", task.getDescription());
                //task.getProcessDefinitionId() //todo add more task info, like name of process that task belongs to
                customTaskList.add(map);
            }
            return new ResponseEntity<>(customTaskList, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }




    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity taskForm(@PathVariable String id, final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formServiceWrapper.getTaskFormData(id);
            return new ResponseEntity<>(properties, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/execute",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity executeTask(@RequestBody ExecutionDto data, final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            Map<String, String> params = formServiceWrapper.makeTaskFormParams(data);
            taskServiceWrapper.executeTask(data.getId(), user.get().getId(), params);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/claim/{taskId}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity claimTask(@PathVariable String taskId, final HttpServletRequest request)throws ServletException {
        Optional<User> user = authService.getUserFromRequest(request);
        if(user.isPresent()){
            taskServiceWrapper.claimTask(taskId, user.get().getId());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }




}
