package com.learnmicroservices.TodoMicroservices.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;


//Validation JSR303->Hibernate validator data binding (name,email,password) -> spring inteprets as 
//new User(name,email,password) a user is valid when it has these three @NotNull @NotBlank @NotEmpty
@Entity
@Table(name="users")
public class User extends BaseModel {

	@Column(name="email")
	@NotNull @NotBlank @NotEmpty
	private String email;
	
	@NotNull @NotBlank @NotEmpty
	private String name;
	
	@NotNull @NotBlank @NotEmpty
	private String password;

	public User(String email, String name, String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public User() {
	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
