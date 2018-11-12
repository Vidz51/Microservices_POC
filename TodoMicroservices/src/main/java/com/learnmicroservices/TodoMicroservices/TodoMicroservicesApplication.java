package com.learnmicroservices.TodoMicroservices;

import lombok.extern.java.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log
@SpringBootApplication
public class TodoMicroservicesApplication implements CommandLineRunner {
	
	@Autowired
	ToDoHelper toDoHelper;

	private static final Logger logger = LoggerFactory.getLogger(TodoMicroservicesApplication.class);

	public static void main(String[] args) {
		logger.info("Main app starting..");
		SpringApplication.run(TodoMicroservicesApplication.class, args);
	}

	// to implement something before application starts up
	@Override
	public void run(String... args) {
		logger.info("Lets fill h2 database");
		
		//Everything here is implemented before our Microservice will  be available for Http requests
		toDoHelper.initializer();
		
		
		
		
		
		logger.info("Finished filling h2 db");
	}
}
