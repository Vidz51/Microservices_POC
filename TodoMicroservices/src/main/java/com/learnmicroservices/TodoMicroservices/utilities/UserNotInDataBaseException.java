package com.learnmicroservices.TodoMicroservices.utilities;

public class UserNotInDataBaseException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotInDataBaseException(String message){
		super(message);
	}

}
