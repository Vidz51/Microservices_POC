package com.learnmicroservices.TodoMicroservices.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="todos")
public class ToDo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@NotNull @NotBlank @NotEmpty
	private String description;
	private Date date;
	@NotNull @NotBlank @NotEmpty
	private String priority;
	
	@Column(name="FK_USERID")
	private String fkUser;
	
	@PrePersist
	void getTimeOperation(){
		this.date=new Date();
	}
	
	public ToDo(Integer id, String description, Date date, String priority,
			String fkUser) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
		this.priority = priority;
		this.fkUser = fkUser;
	}

	public ToDo() {
		super();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getfkUser() {
		return fkUser;
	}

	public void setfkUser(String fkUser) {
		this.fkUser = fkUser;
	}

	

	}
