package com.learnmicroservices.TodoMicroservices.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnmicroservices.TodoMicroservices.entities.ToDo;

public interface ToDoDao extends JpaRepository<ToDo, Integer>{
	
	//name stategy
	List<ToDo> findByFkUser(String email);

}
