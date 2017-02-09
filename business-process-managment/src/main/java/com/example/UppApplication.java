package com.example;

import com.example.interceptor.JWTFilter;
import com.example.service.MailService;
import org.activiti.engine.*;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.delegate.DelegateInvocation;
import org.activiti.engine.impl.interceptor.DelegateInterceptor;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.*;

import static com.example.constants.Constants.PROCESS_KEY_01;


/***
 *
 // ACTIVITI HOW TO:
 // spring guide
 // https://spring.io/blog/2015/03/08/getting-started-with-activiti-and-spring-boot
 // code from guide
 // https://github.com/jbarrez/spring-boot-with-activiti-example
 // activiti official guide
 // https://www.activiti.org/userguide/#springSpringBoot
 // [!] activiti rest api adds basic security - @enableAutoConfig(exclude=...) to disable it
 *
 * */
@EnableAutoConfiguration(exclude = {
		org.activiti.spring.boot.RestApiAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
@SpringBootApplication
public class UppApplication {


	/* JWT FILTER */
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JWTFilter());
		registrationBean.addUrlPatterns("/api/task/*");
		registrationBean.addUrlPatterns("/api/process/*");
		return registrationBean;
	}


	/* APP */
	public static void main(String[] args) {
		SpringApplication.run(UppApplication.class, args);
		System.out.println("APPLICATION STARTED http://localhost:8090/");
	}



	/* FOR TESTING */
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Bean
	CommandLineRunner init(final RepositoryService repositoryService,
						   final RuntimeService runtimeService,
						   final TaskService taskService,
						   final FormService formService,
						   final IdentityService identityService,
						   final MailService mailService,
						   final ProcessEngine processEngine,
						   final ProcessEngineConfigurationImpl processEngineConfiguration) {

			return new CommandLineRunner() {

				public void run(String... strings) throws Exception {


					boolean test = false;
					if(test) {


						/*add start form variables*/
							Map<String, Object> variables = new HashMap<String, Object>();
							variables.put("new_property_2222", "THIS IS FIRST VAR BEFORE START");
							variables.put("initiator", "admin");

						/*get start form*/
							List<ProcessDefinition> ls = repositoryService.createProcessDefinitionQuery().processDefinitionKey(PROCESS_KEY_01).latestVersion().list();
							System.out.println("PROCESSES " + PROCESS_KEY_01 + ": " + runtimeService.createExecutionQuery().count());
							for (ProcessDefinition pd : ls) {
								System.out.println(pd.getVersion());
							}
							ProcessDefinition processDef = ls.get(0);
							//ProcessDefinition processDef = repositoryService.getProcessDefByProcessDefKey();
							StartFormData startFormData = formService.getStartFormData(processDef.getId());
							for (FormProperty formProperty : startFormData.getFormProperties()) {
								System.out.println("NAME: " + formProperty.getName() + " ID: " + formProperty.getId() + " TYPE: " + formProperty.getType());
							}

						/*start proces*/
							//runtimeService.startProcessInstanceByKey(PROCES_KEY, variables);
							System.out.println("STARTED PROCESSES COUNT: " + runtimeService.createExecutionQuery().count());

						/*get tasks*/
							List<Task> tasks = taskService.createTaskQuery().taskAssignee("admin").list();
							System.out.println("number of assigned tasks: " + tasks.size());

						/*display task form*/
							ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formService.getTaskFormData(tasks.get(0).getId()).getFormProperties();
							for (FormProperty property : properties) {
								System.out.println("NAME: " + property.getName() + " ID: " + property.getId() + " TYPE: " + property.getType());
							}


						/*getting process instances*/
							List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery().processDefinitionKey("process").list();
							for (ProcessInstance pi : instances) {
								//System.out.println(pi.getId());
							}


							// process startable groups
							//******************************************************************//
							//******************************************************************//
							//getprocessdefbykey - then set that procesdef (latest) to user group - everytime process:1:4, process1:5....
							repositoryService.addCandidateStarterGroup("process:1:4", "admins");
							//******************************************************************//
							//******************************************************************//
							System.out.println("added startable group for proces def");
							ArrayList<ProcessDefinition> def = (ArrayList<ProcessDefinition>) repositoryService.createProcessDefinitionQuery().startableByUser("admin").list();
							System.out.println("getting process def startable by user");
							for (ProcessDefinition pd : def) {
								System.out.println("Id procesa " + pd.getId());
								System.out.println("Key procesa " + pd.getKey());
							}


							ArrayList<User> users = (ArrayList<User>) identityService.createUserQuery().userId("admin").list();
							if (users.size() != 0) {
								System.out.print("IMA ADMINA" + Optional.of(users.get(0).getId()));
							} else {
								//return Optional.empty();
							}

					}


				}
			};
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
