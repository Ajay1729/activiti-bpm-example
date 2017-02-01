package com.example.delegate;

import com.example.service.IdentityServiceWrapper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static com.example.constants.Constants.*;


/**
 * Created by aloha on 01-Feb-17.
 */
public class InitProcessVariables implements JavaDelegate {

//    @Autowired
//    IdentityServiceWrapper identityServiceWrapper;
//
//    @Autowired
//    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("DELEGATE EXECUTION WORKS");

        RuntimeService runtimeService = delegateExecution.getEngineServices().getRuntimeService();
        IdentityService identityService = delegateExecution.getEngineServices().getIdentityService();
        runtimeService.setProcessInstanceName(delegateExecution.getProcessInstanceId(), delegateExecution.getVariable("doktorant_id").toString());

        ArrayList<User> users = (ArrayList<User>) identityService.createUserQuery().list();
        for(User user:users){
            /*sef veca fakulteta*/
            try {
                if (identityService.getUserInfo(user.getId(), "fakultet").equals("sef veca")) {
                    delegateExecution.setVariable(SEF_VECA_FAKULTETA, user.getId());
                }
            }
            catch (Exception e){

            }
            try {
            /*sef veca stud programa*/
                if (identityService.getUserInfo(user.getId(), delegateExecution.getVariable(STUD_PROG_PICK).toString()).equals("sef veca")) {
                    delegateExecution.setVariable(SEF_VECA_STUD_PROGRAMA, user.getId());
                }
            }catch (Exception e){

            }
            try {
            /*sef veca katedre*/
                if (identityService.getUserInfo(user.getId(), delegateExecution.getVariable(KATEDRA_PICK).toString()).equals("sef veca")) {
                    delegateExecution.setVariable(SEF_VECA_KATEDRE, user.getId());
                }
            }catch (Exception e){

            }
            try {
            /*sef katedre*/
                if (identityService.getUserInfo(user.getId(), delegateExecution.getVariable(KATEDRA_PICK).toString()).equals("sef")) {
                    delegateExecution.setVariable(SEF_KATEDRE, user.getId());
                }
            }catch (Exception e){

            }
            try {
            /*rukovodical doktorskih studija*/
                if (identityService.getUserInfo(user.getId(), "fakultet").equals("rukovodilac doktorskih studija")) {
                    delegateExecution.setVariable(RUKOVODILAC_DOK_STUDIJA, user.getId());
                }
            }catch (Exception e){

            }
            try {
            /*dekan fakulteta*/
                if (identityService.getUserInfo(user.getId(), "fakultet").equals("dekan")) {
                    delegateExecution.setVariable(DEKAN, user.getId());
                }
            }catch (Exception e){

            }
        }



    }


}
