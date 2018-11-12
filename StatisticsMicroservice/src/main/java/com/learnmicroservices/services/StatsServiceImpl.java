package com.learnmicroservices.services;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.learnmicroservices.dao.StatisticsDao;
import com.learnmicroservices.entity.Statistics;
import com.learnmicroservices.utilities.JsonResponseBody;

@Service
public class StatsServiceImpl implements StatisticsService {
@Autowired
StatisticsDao statsDao;

	public List<Statistics> getStats(String jwt, String email) {
		//1. call ToDoMicroservice to get List<ToDos> (we need to result logged passing a valid JWT in the HEADER of the request)
        //   we get the valid jwt from the client (PostMan or Browser with HTML interface) which has done the login (first call)
			List<LinkedHashMap> todos = getNewDataFromToDoMicroservice(jwt);
			
		//2. calculate the statistics on the List of ToDos received
		String statsDesc = "No statistics available";
		if(todos != null && todos.size()>0){
			int highPriorityTodos = 0;
			int lowPriorityTodos = 0;
			
			for(int i=0;i<todos.size();i++){
				LinkedHashMap todo = todos.get(i);
				String priority = (String) todo.get("priority");
				if("low".equals(priority)) lowPriorityTodos++;
				if("high".equals(priority)) highPriorityTodos++;
			}
			statsDesc = "You have <b>" + lowPriorityTodos + " low priority Todos  </br> and <b>" + highPriorityTodos + " High priority todos </br>.";
		}
		//3. save the new statistic into statisticdb if a day has passed from the last update
		List<Statistics> stats = statsDao.getLatestStats(email);
			Date now = new Date();
			long diff = now.getTime() - stats.get(0).getDate().getTime();
			long days = diff/(24*60*60*1000);
			if(days>1){
				stats.add(statsDao.save(new Statistics(null,statsDesc,new Date(), email)));
				
			}
		//4. return the List of statistics
		return stats;
	}
	
	  private List<LinkedHashMap> getNewDataFromToDoMicroservice(String jwt){
		
		//prepare the header of request with jwt
		  MultiValueMap<String,String> header = new LinkedMultiValueMap<String, String>();
		  header.add("jwt",jwt);
		  //attach it to http request
		  HttpEntity<?> request = new HttpEntity(String.class,header);
		  
		  
		//call ToDoMicroservice getting ResponseEntity<JsonResponseBody> as HTTP response
		  RestTemplate restTemplate = new RestTemplate();
		  ResponseEntity<JsonResponseBody> responseEntity = restTemplate.exchange("http://localhost:8181/showToDos", HttpMethod.POST, request, JsonResponseBody.class);
		  
				  
		  //extract from JsonResponseBody (recognized as java Object from JacksonLibrary) the "response" attribute.
	        //This response attribute for "/showToDos" is a List<ToDos>. But in this microservice we haven't defined ToDo.java
	        //then JacksonLibrary cannot interpret it as a List<ToDos> but it will interpret it as a List<LinkedHashMap>
		  List<LinkedHashMap> toDoList = (List) responseEntity.getBody().getResponse();
		  
		  return  toDoList;
	  }

}
