package com.learnmicroservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnmicroservices.services.StatisticsService;
import com.learnmicroservices.utilities.JsonResponseBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	private static final Logger logger = LoggerFactory.getLogger(RestController.class);
	
	@Autowired
	StatisticsService statService;
	
	public RestController(){
		logger.info("Constructor called");
		System.out.println("Constructor called");
	}
	@GetMapping("/test")
	public String test(){
		logger.info("RestController method");
		return "StatisticsMicroservice works fine!!";
	}
	@RequestMapping("/")
	public String sayHello(){
		return "hello";
	}	
	
	@CrossOrigin
	@RequestMapping("/getStats")
	public ResponseEntity<JsonResponseBody> getStats(@RequestParam("jwt") String jwt, @RequestParam("email") String email){
		
		return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), statService.getStats(jwt, email)));
		
	}
}
