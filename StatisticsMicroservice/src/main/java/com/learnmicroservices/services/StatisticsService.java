package com.learnmicroservices.services;

import java.util.List;

import com.learnmicroservices.entity.Statistics;

public interface StatisticsService {
	
	List<Statistics> getStats(String jwt,String email);
}
