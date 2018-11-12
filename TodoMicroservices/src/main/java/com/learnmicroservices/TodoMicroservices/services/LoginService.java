package com.learnmicroservices.TodoMicroservices.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.learnmicroservices.TodoMicroservices.entities.User;
import com.learnmicroservices.TodoMicroservices.utilities.UserNotInDataBaseException;
import com.learnmicroservices.TodoMicroservices.utilities.UserNotLoggedInException;

public interface LoginService {

		Optional<User> getUserFromDb(String email,String pwd) throws UserNotInDataBaseException;
		
		String createJwt(String email,String name,Date date) throws UnsupportedEncodingException;
		
		Map<String,Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException,UserNotLoggedInException;
}
