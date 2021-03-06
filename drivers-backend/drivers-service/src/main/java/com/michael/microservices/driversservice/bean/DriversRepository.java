package com.michael.microservices.driversservice.bean;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DriversRepository extends JpaRepository<Driver, Long>{
	
	/**
	 * Finds drivers by status: active or inactive
	 * @param status
	 * @return List&lt;Driver&gt;
	 */
	public List<Driver> findByStatus(Status status);
	
	/**
	 * Finds drivers thats work between start and finish time
	 * @param start - time when the driver starts work
	 * @param finish time when the driver finishes work
	 * @return List&lt;Driver&gt;
	 */
	public List<Driver> findByStartAfterAndFinishBefore(LocalTime start, LocalTime finish);
	
	/**
	 * Finds drivers who are currently within map bounds 
	 * defined by latitude(min and max) and longtitude(min and max)
	 * @param latitudeMin
	 * @param latitudeMax
	 * @param longitudeMin
	 * @param longitudeMax
	 * @return List&lt;Driver&gt;
	 */
	public List<Driver> findByLatitudeBetweenAndLongitudeBetween(
			Double latitudeMin, Double latitudeMax,
			Double longitudeMin, Double longitudeMax);
}
