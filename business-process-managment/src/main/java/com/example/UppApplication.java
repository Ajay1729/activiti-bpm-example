package com.example;

import com.example.interceptor.JWTFilter;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

	public static final String PROCES_KEY = "process";

	/* JWT FILTER */
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JWTFilter());
		// TODO add patterns as you go
		registrationBean.addUrlPatterns("/api/task/*");
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
						   final IdentityService identityService) {

			return new CommandLineRunner() {

				public void run(String... strings) throws Exception {

					/*add variables*/
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("new_property_2222", "THIS IS FIRST VAR BEFORE START");

					/*get start form*/
					List <ProcessDefinition> ls = repositoryService.createProcessDefinitionQuery().processDefinitionKey(PROCES_KEY).latestVersion().list();
					System.out.println("PROCESSES "+PROCES_KEY+ ": "+runtimeService.createExecutionQuery().count());
					for(ProcessDefinition pd: ls){
						System.out.println(pd.getVersion());
					}
					ProcessDefinition processDef = ls.get(0);
					//ProcessDefinition processDef = repositoryService.getProcessDefinition();
					StartFormData startFormData = formService.getStartFormData(processDef.getId());
					for(FormProperty formProperty: startFormData.getFormProperties()){
						System.out.println("NAME: "+formProperty.getName()+" ID: "+ formProperty.getId()+" TYPE: "+formProperty.getType());
					}

					/*start proces*/
					//runtimeService.startProcessInstanceByKey(PROCES_KEY, variables);
					System.out.println("STARTED PROCESSES COUNT: "+runtimeService.createExecutionQuery().count());

					/*get tasks*/
					List<Task> tasks = taskService.createTaskQuery().taskAssignee("admin").list();
					System.out.println("number of assigned tasks: "+tasks.size());

					/*display task form*/
					ArrayList<FormProperty> properties = (ArrayList<FormProperty>) formService.getTaskFormData(tasks.get(0).getId()).getFormProperties();
					for(FormProperty property: properties){
						//todo field validation - is required, type
						System.out.println("NAME: "+property.getName()+" ID: "+ property.getId()+" TYPE: "+property.getType());
					}

				}
			};
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





}
