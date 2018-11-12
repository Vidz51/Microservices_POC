package com.learnmicroservices.TodoMicroservices;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.learnmicroservices.TodoMicroservices.dao.ToDoDao;
import com.learnmicroservices.TodoMicroservices.dao.UserDao;
import com.learnmicroservices.TodoMicroservices.entities.ToDo;
import com.learnmicroservices.TodoMicroservices.entities.User;
import com.learnmicroservices.TodoMicroservices.utilities.EncryptionUtils;

@Component
public class ToDoHelper {
	private static final Logger logger = LoggerFactory.getLogger(ToDoHelper.class);
	@Autowired
	UserDao userDao;
	
	@Autowired
	ToDoDao toDoDao;
	
	@Autowired
	EncryptionUtils encryptionUtils;
	
	public ToDoHelper(){
		logger.info("ToDoHelper constructir called..");
	}
	public void initializer(){
		String encryptPwd;
		encryptPwd = encryptionUtils.encrypt("Hello");
		userDao.save(new User("vidz51@yahoo.com","Vidushi",encryptPwd));
		
		
		encryptPwd = encryptionUtils.encrypt("Bye");
		userDao.save(new User("abhinav21@gmail.com","Abhinav",encryptPwd));
		encryptPwd = encryptionUtils.encrypt("Hi");
		userDao.save(new User("rinky@gmail.com","Rinky",encryptPwd));
		
		toDoDao.save(new ToDo(null,"learn microservice",new Date(),"High","vidz51@yahoo.com"));
		toDoDao.save(new ToDo(2,"learn Springboot",new Date(),"High","abhinav21@gmail.com"));
		toDoDao.save(new ToDo(null,"Listen songs Springboot",new Date(),"Low","rinky@gmail.com"));
		toDoDao.save(new ToDo(null,"learn Java",new Date(),"High","vidz51@yahoo.com"));
		
	}
}
