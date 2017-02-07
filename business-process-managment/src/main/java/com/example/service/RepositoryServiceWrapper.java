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


    public ProcessDefinition getProcessDefByProcessDefKey(String processKey){
        List<ProcessDefinition> ls = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion().list();
        return ls.get(0);
    }

    public ProcessDefinition getProcessDefById(String id){
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(id).list().get(0);
    }

    public List<ProcessDefinition> getProcessDefStartableByUser(String userId){
        //--------------------------------------------------------------------
        //proces def key
        //getProcessDefByProcessDefKey(PROCESS_01).getId();
        //repositoryService.addCandidateStarterGroup("process:1:4", "admins");
        //--------------------------------------------------------------------
        return repositoryService.createProcessDefinitionQuery().startableByUser(userId).list();
    }

    public void getProcessDefStartableByGroup(){
        //startable by user check for groups user belongs to
    }

    public void setProcessDefStartableGroup(String processDefId, String groupId){
        repositoryService.addCandidateStarterGroup(processDefId, groupId);
    }





}
