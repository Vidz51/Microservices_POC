package com.learnmicroservices.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learnmicroservices.entity.Statistics;

public interface StatisticsDao extends JpaRepository<Statistics,Integer>{
	
	@Query(value="SELECT * FROM latest_Statistics WHERE EMAIL=:email ORDER BY Date desc LIMIT 10", nativeQuery=true)
	public List<Statistics> getLatestStats(@Param("email") String email);
	

}
