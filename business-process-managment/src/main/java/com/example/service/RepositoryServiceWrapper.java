package com.example.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aloha on 23-Jan-17.
 */
@Service
public class RepositoryServiceWrapper {


    @Autowired
    RepositoryService repositoryService;


    public ProcessDefinition getProcessDefinition(String processKey){
        List<ProcessDefinition> ls = repositoryService.createProcessDefinitionQuery().processDefinitionKeyLike(processKey).latestVersion().list();
        return ls.get(0);
    }


}
