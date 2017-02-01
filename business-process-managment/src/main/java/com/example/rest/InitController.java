package com.example.rest;

import com.example.service.AuthService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.constants.Constants.PROCESS_KEY_01;

/**
 * Created by aloha on 01-Feb-17.
 */
@RestController
@RequestMapping("/api/init")
public class InitController {

    @Autowired
    AuthService authService;

    @Autowired
    IdentityService identityService;

    @Autowired
    RepositoryService repositoryService;


    @RequestMapping(value = "/default",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity defaultInit(final HttpServletRequest request)throws ServletException {
        Optional<User> user1 = authService.getUserFromRequest(request);
        if(user1.isPresent()){


            User user = null;
            Group group = null;


						/*USERS*/
            for(Integer i=0; i<100; i++){
                user = identityService.newUser(i.toString());
                user.setFirstName("First"+i.toString());
                user.setLastName("Last"+i.toString());
                user.setPassword("password");
                identityService.saveUser(user);
            }

						/*GROUPS*/
						/*STUDNTSKA SLUZBA - izvrsavaju neke random taskove */
            group = identityService.newGroup("studentska_sluzba");
            group.setName("Studentska sluzba");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("1", "studentska_sluzba");
            identityService.createMembership("2", "studentska_sluzba");
            identityService.createMembership("3", "studentska_sluzba");
            identityService.createMembership("4", "studentska_sluzba");
            identityService.createMembership("5", "studentska_sluzba");

						/*REFERENTI - mogu da pokrentu proces*/
            group = identityService.newGroup("referent");
            group.setName("Referent");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("6", "referent");
            identityService.createMembership("7", "referent");
            repositoryService.addCandidateStarterGroup(repositoryService.createProcessDefinitionQuery().processDefinitionKey(PROCESS_KEY_01).latestVersion().list().get(0).getId(), "referent");

						/*FAKULTET*/
            group = identityService.newGroup("fakultet");
            group.setName("Fakultet");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("8", "fakultet");
            identityService.createMembership("9", "fakultet");
            identityService.createMembership("10", "fakultet");
            identityService.setUserInfo("8", "fakultet", "dekan");
            identityService.setUserInfo("9", "fakultet", "rukovodilac doktorskih studija");
            identityService.setUserInfo("10", "fakultet","sef veca");

						/*VECE FAKULTETA*/
            group = identityService.newGroup("vece_fakulteta");
            group.setName("Vece fakulteta");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("11", "vece_fakulteta");
            identityService.createMembership("12", "vece_fakulteta");
            identityService.createMembership("13", "vece_fakulteta");
            identityService.createMembership("14", "vece_fakulteta");
            identityService.createMembership("15", "vece_fakulteta");

						/*KATEDRA ZA INFORMATIKU*/
            group = identityService.newGroup("katedra_za_informatiku");
            group.setName("Katedra za informatiku");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("16", "katedra_za_informatiku");
            identityService.createMembership("17", "katedra_za_informatiku");
            identityService.setUserInfo("16", "katedra_za_informatiku", "sef");
            identityService.setUserInfo("17", "katedra_za_informatiku", "sef veca");
						/*VECE KATEDRE ZA INFORMATIKU*/
            group = identityService.newGroup("vece_katedre_za_informatiku");
            group.setName("Vece katedre za informatiku");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("18", "vece_katedre_za_informatiku");
            identityService.createMembership("19", "vece_katedre_za_informatiku");
            identityService.createMembership("20", "vece_katedre_za_informatiku");
            identityService.createMembership("21", "vece_katedre_za_informatiku");
            identityService.createMembership("22", "vece_katedre_za_informatiku");

						/*KATEDRA ZA MEHANIKU*/
            group = identityService.newGroup("katedra_za_mehaniku");
            group.setName("Katedra za mehaniku");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("23", "katedra_za_mehaniku");
            identityService.createMembership("24", "katedra_za_mehaniku");
            identityService.setUserInfo("23", "katedra_za_mehaniku", "sef");
            identityService.setUserInfo("24", "katedra_za_mehaniku", "sef veca");
						/*VECE KATEDRE ZA MEHANIKU*/
            group = identityService.newGroup("vece_katedre_za_mehaniku");
            group.setName("Vece katedre za mehaniku");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("25", "vece_katedre_za_mehaniku");
            identityService.createMembership("26", "vece_katedre_za_mehaniku");
            identityService.createMembership("27", "vece_katedre_za_mehaniku");
            identityService.createMembership("28", "vece_katedre_za_mehaniku");
            identityService.createMembership("29", "vece_katedre_za_mehaniku");


						/*STUD PROGRAM e1*/
            group = identityService.newGroup("stud_program_e1");
            group.setName("Studijski program E1");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("21", "stud_program_e1");
            identityService.createMembership("22", "stud_program_e1");
            identityService.setUserInfo("21", "stud_program_e1", "sef");
            identityService.setUserInfo("22", "stud_program_e1", "sef veca");
						/*VECA STUD PROGRAMA e1*/
            group = identityService.newGroup("vece_stud_program_e1");
            group.setName("Vece studijskog programa E1");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("23", "stud_program_e1");
            identityService.createMembership("24", "stud_program_e1");
            identityService.createMembership("25", "stud_program_e1");
            identityService.createMembership("26", "stud_program_e1");
            identityService.createMembership("27", "stud_program_e1");


						/*STUD PROGRAM e2*/
            group = identityService.newGroup("stud_program_e2");
            group.setName("Studijski program E2");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("28", "stud_program_e2");
            identityService.createMembership("29", "stud_program_e2");
            identityService.setUserInfo("28", "stud_program_e2", "sef");
            identityService.setUserInfo("29", "stud_program_e2", "sef veca");
						/*VECA STUD PROGRAMA e1*/
            group = identityService.newGroup("vece_stud_program_e2");
            group.setName("Vece studijskog programa E2");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("30", "stud_program_e2");
            identityService.createMembership("31", "stud_program_e2");
            identityService.createMembership("32", "stud_program_e2");
            identityService.createMembership("33", "stud_program_e2");
            identityService.createMembership("34", "stud_program_e2");

						/*NASTAVNICI FAKULTETA*/
            group = identityService.newGroup("nastavnici_fakulteta");
            group.setName("Nastavnici fakulteta");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("35", "nastavnici_fakulteta");
            identityService.createMembership("36", "nastavnici_fakulteta");
            identityService.createMembership("37", "nastavnici_fakulteta");
            identityService.createMembership("38", "nastavnici_fakulteta");
            identityService.createMembership("39", "nastavnici_fakulteta");

						/*NASTAVNICI VAN FAKULTETA*/
            group = identityService.newGroup("nastavnici_van_fakulteta");
            group.setName("Nastavnici fakulteta");
            group.setType("assignment");
            identityService.saveGroup(group);
            identityService.createMembership("40", "nastavnici_van_fakulteta");
            identityService.createMembership("41", "nastavnici_van_fakulteta");
            identityService.createMembership("42", "nastavnici_van_fakulteta");
            identityService.createMembership("43", "nastavnici_van_fakulteta");
            identityService.createMembership("44", "nastavnici_van_fakulteta");

            //-------------------------------------------


            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


}
