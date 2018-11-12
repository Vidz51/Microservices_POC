package com.learnmicroservices.TodoMicroservices.services;

import java.util.List;

import com.learnmicroservices.TodoMicroservices.entities.ToDo;

public interface ToDoService {

		List<ToDo> getToDos(String email);
		
		ToDo addToDo(ToDo todo);
		
		void deleteToDo(Integer id);
}
