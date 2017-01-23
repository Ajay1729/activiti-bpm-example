package com.example;

import com.example.jwt.JWTFilter;
import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration(exclude = {
		org.activiti.spring.boot.RestApiAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
@SpringBootApplication
public class UppApplication {

	// ACTIVITI HOW TO:
	// spring guide
	// https://spring.io/blog/2015/03/08/getting-started-with-activiti-and-spring-boot
	// code from guide
	// https://github.com/jbarrez/spring-boot-with-activiti-example
	// activiti official guide
	// https://www.activiti.org/userguide/#springSpringBoot
	// [!] activiti rest api adds basic security - @enableAutoConfig(exclude=...) to disable it



	/* JWT FILTER */
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JWTFilter());
		// TODO add patters as you go
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
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("new_property_1", true);
					//runtimeService.startProcessInstanceByKey("process", variables);
					List<Task> tasks = taskService.createTaskQuery().taskAssignee("admin").list();
					Task task = tasks.get(0);
					System.out.println(formService.getTaskFormData(task.getId()).getFormProperties().get(0).getName());

				}
			};
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





}
