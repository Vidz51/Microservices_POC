package com.learnmicroservices.entity;

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
@Table(name="latest_statistics")

public class Statistics {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;
	
	@Column(name="DESCRIPTION")
	@NotNull @NotEmpty @NotBlank
	private String description;
	
	@Column(name="DATE")
	private Date date;
	
	@Column(name="EMAIL")
	@NotNull @NotEmpty @NotBlank
	private String  email;
	
	public Statistics(Integer id, String description, Date date, String email) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
		this.email = email;
	}

	public Statistics(){
		
	}
	
	@PrePersist
	private void getTimeOperation(){
		this.date= new Date();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
