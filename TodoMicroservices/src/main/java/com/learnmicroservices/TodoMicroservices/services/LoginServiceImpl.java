package com.learnmicroservices.TodoMicroservices.services;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnmicroservices.TodoMicroservices.dao.UserDao;
import com.learnmicroservices.TodoMicroservices.entities.User;
import com.learnmicroservices.TodoMicroservices.utilities.EncryptionUtils;
import com.learnmicroservices.TodoMicroservices.utilities.JwtUtils;
import com.learnmicroservices.TodoMicroservices.utilities.UserNotInDataBaseException;
import com.learnmicroservices.TodoMicroservices.utilities.UserNotLoggedInException;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	EncryptionUtils encryptionUtils;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Override
	public Optional<User> getUserFromDb(String email, String pwd)	throws UserNotInDataBaseException {
		
		Optional<User> userr = userDao.findUserByEmail(email);
		
		if(userr.isPresent()){
			User user = userr.get();
			if(! encryptionUtils.decrypt(user.getPassword()).equals(pwd)){
				throw new UserNotInDataBaseException("Wrong password");
			}
		}else 
			throw new UserNotInDataBaseException("Wrong Email");
		
		return userr;
	}

	@Override
	public String createJwt(String email, String name, Date date)	throws UnsupportedEncodingException {
		date.setTime(date.getTime() + (300*1000));
		return jwtUtils.generateJwt(email, name, date);
	}

	@Override
	public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request)	throws UnsupportedEncodingException, UserNotLoggedInException {
		String jwt = jwtUtils.getJwtFromHttpRequest(request);
		if(jwt==null){
			throw new UserNotLoggedInException("User not logged, log in first");
		}
		
		return jwtUtils.jwt2Map(jwt);
	}

}
