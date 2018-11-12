package com.learnmicroservices.TodoMicroservices.controllers;

import io.jsonwebtoken.ExpiredJwtException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.learnmicroservices.TodoMicroservices.entities.ToDo;
import com.learnmicroservices.TodoMicroservices.entities.User;
import com.learnmicroservices.TodoMicroservices.services.LoginService;
import com.learnmicroservices.TodoMicroservices.services.ToDoService;
import com.learnmicroservices.TodoMicroservices.utilities.JsonResponseBody;
import com.learnmicroservices.TodoMicroservices.utilities.ToDoValidator;
import com.learnmicroservices.TodoMicroservices.utilities.UserNotInDataBaseException;
import com.learnmicroservices.TodoMicroservices.utilities.UserNotLoggedInException;


@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@Autowired
    LoginService loginService;

    @Autowired
    ToDoService toDoService;
	
	@RequestMapping("/Hello")
	public String sayHello(){
		return "hello";
	}	
	
	@RequestMapping("/userInOutput")
	public User giveUser(){
		return new User("vidz51@yahoo.com","Vidushi", "Vidushi14!");
	}
	
	@RequestMapping("/todoInput1")
	public String todoInput1(ToDo todo) {
		return "Todo Description: " + todo.getDescription()
				+ " Todo Priority: " + todo.getPriority();

	}

	@RequestMapping("/todoInput2")
	public String todoInput2(@Valid ToDo todo) { //@Valid validates JSR 303 validation in ToDo entity @NotBlank, @NotNull,@Not empty
		return "Todo Description: " + todo.getDescription()
				+ " Todo Priority: " + todo.getPriority();
	}
	/**To manage the errors that @Valid gives we use BindingResult
	 * 
	 * BindingResult->Spring object which collects the validation error
	 */
	@RequestMapping("/todoInput3")
	public String todoInput3(@Valid ToDo todo, BindingResult result) {
		if(result.hasErrors()){
			return "Errors are: " + result.toString();
		}
		return "Todo Description: " + todo.getDescription()
				+ " Todo Priority: " + todo.getPriority();

	}
	
	//Custom validation
	@RequestMapping("/todoInput4")
	public String todoInput4( ToDo todo, BindingResult result) {
		ToDoValidator todoValidator = new ToDoValidator();
		todoValidator.validate(todo, result);
		if(result.hasErrors()){
			return "Errors are: " + result.toString();
		}
		return "Todo Description: " + todo.getDescription()
				+ " Todo Priority: " + todo.getPriority();

	}
	
	@RequestMapping("/todoInput5")
	public String todoInput5(@Valid ToDo todo, BindingResult result) {
		ToDoValidator todoValidator = new ToDoValidator();
		todoValidator.validate(todo, result);
		if(result.hasErrors()){
			return "Errors are: " + result.toString();
		}
		return "Todo Description: " + todo.getDescription()
				+ " Todo Priority: " + todo.getPriority();

	}
	
	/*@RequestMapping("/exampleUrl")
	public ResponseEntity<JsonResponseBody> returnStandardResponse(){
		return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(new JsonResponseBody());
	}*/
	

    //   Controller layer   -   (  we want it to manage all Exceptions!  )
    //         |
    //         |
    //   Service Layer (throw all the present and "lower-layer" exceptions)
    //      |       |
    //      |       |
    //  Utilities  Daos
    //              |
    //              |
    //             Database

	
	@PostMapping(value="/login")
	 public ResponseEntity<JsonResponseBody> login(@RequestParam(value="email") String email, @RequestParam(value="password") String pwd){
		//1) success: return a String with the login message + JWT in the HEADER of the HTTP RESPONSE
        //2) return the error message
		try {
			 Optional<User> userr = loginService.getUserFromDb(email, pwd);
			 User user = userr.get();
			 String jwt = loginService.createJwt(email, user.getName(), new Date());
			 
			//the only case in which the server sends the JWT to the client in the HEADER of the RESPONSE
			 return ResponseEntity.status(HttpStatus.OK).header("jwt", jwt).body(new JsonResponseBody(HttpStatus.OK.value(), "Success! User logged in!"));
			 
		}catch(UserNotInDataBaseException e1){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e1.toString()));
        }catch(UnsupportedEncodingException e2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e2.toString()));
        }
		
	}
	
	 @RequestMapping("/showToDos")
	 public ResponseEntity<JsonResponseBody> showTodos(HttpServletRequest  request){
		 
		//1) success: arraylist of ToDos in the "response" attribute of the JsonResponseBody
	    //2) fail: error message
		 
		 try {
			Map<String,Object> userData = loginService.verifyJwtAndGetData(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(),toDoService.getToDos((String) userData.get("email"))));
		} catch(UnsupportedEncodingException e1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e1.toString()));
        }catch(UserNotLoggedInException e2){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e2.toString()));
        }catch(ExpiredJwtException e3){
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
        }
	 }
	 
	 @PostMapping(value="/newToDo")
	 public ResponseEntity<JsonResponseBody> newTodos(HttpServletRequest  request, @Valid ToDo todo,BindingResult result){
		//1) success: todoInserted into the response attribute of the JsonResponseBody
	        //2) fail: error message
		 
		 ToDoValidator validator = new ToDoValidator();
		 validator.validate(todo, result);
		 if(result.hasErrors()){
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + result.toString()));
		 }
		 try {
			loginService.verifyJwtAndGetData(request);
			
			return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(),toDoService.addToDo(todo)));
		} catch (UnsupportedEncodingException e) {
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e.toString()));
		} catch (UserNotLoggedInException e) {
			 return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e.toString()));
		 }catch(ExpiredJwtException e3){
	            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
	     }
	 }
	 
	 @RequestMapping("/deleteToDo/{id}")
	    public ResponseEntity<JsonResponseBody> deleteToDo(HttpServletRequest request, @PathVariable(name="id") Integer toDoId){
		// 1) success> message of success
	        // 2) fail: error message
		 try {
			loginService.verifyJwtAndGetData(request);
			toDoService.deleteToDo(toDoId);
			return ResponseEntity.status(HttpStatus.OK).body(new JsonResponseBody(HttpStatus.OK.value(), "ToDo correctly delete"));
		} catch(UnsupportedEncodingException e1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new JsonResponseBody(HttpStatus.BAD_REQUEST.value(), "Bad Request: " + e1.toString()));
        }catch(UserNotLoggedInException e2){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new JsonResponseBody(HttpStatus.FORBIDDEN.value(), "Forbidden: " + e2.toString()));
        }catch(ExpiredJwtException e3){
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new JsonResponseBody(HttpStatus.GATEWAY_TIMEOUT.value(), "Session Expired: " + e3.toString()));
        }
		 

	        
	 }
	 
}
