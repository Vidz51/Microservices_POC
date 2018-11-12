package com.learnmicroservices.TodoMicroservices.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learnmicroservices.TodoMicroservices.entities.User;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
	//name stategy
	Optional<User> findUserByEmail(String email);
	
	//we can also do query annotation
	@Query(value="SELECT * FROM users where id=:id", nativeQuery= true)
	Optional<User> findUserById(@Param("id")Integer id);
	
	//native method
	User findOne(Integer id);
}
