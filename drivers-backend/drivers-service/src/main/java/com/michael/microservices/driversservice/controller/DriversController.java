package com.michael.microservices.driversservice.controller;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.michael.microservices.driversservice.bean.Address;
import com.michael.microservices.driversservice.bean.Driver;
import com.michael.microservices.driversservice.bean.DriversRepository;
import com.michael.microservices.driversservice.bean.Location;
import com.michael.microservices.driversservice.bean.Status;
import com.michael.microservices.driversservice.configuration.Configuration;
import com.michael.microservices.exception.DriverNotFoundException;

@RestController
public class DriversController {
	
	@Autowired
	private Configuration configuration;
	
	@Autowired
	private DriversRepository repository;
	
	/**
	 * Returns all drivers currently in the repository
	 * @return List<Driver>
	 */
	@GetMapping("/drivers")
	public List<Driver> getDrivers() {
		return repository.findAll();
	}
	
	/**
	 * Returns a single driver by driverId
	 * @param id - driver id
	 * @return Driver
	 */
	@GetMapping("/drivers/{id}")
	public Driver getDriverById(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new DriverNotFoundException(id));
	}
	
	/**
	 * Adds a new driver to the repository (if doesn't exist) or updates the existing record
	 * @param newDriver
	 * @return Driver - the driver object that was added
	 */
	@PostMapping("/drivers")
	public Driver addNewDriver(@RequestBody Driver newDriver) {
		return repository.save(newDriver);
	}
	
	/**
	 * Update driver record by id or create new one if no such id found
	 * @param newDriver - replacement object
	 * @param id - the driver id
	 * @return Driver - the driver object that was updated
	 */
	@PutMapping("/drivers/{id}")
	public Driver replaceDriver(@RequestBody Driver newDriver, @PathVariable Long id) {
		return repository.findById(id)
				.map(driver -> {
					// update existing record with the given id
					driver.setFirstName(newDriver.getFirstName());
					driver.setLastName(newDriver.getLastName());
					driver.setAge(newDriver.getAge());
					driver.setEmail(newDriver.getEmail());
					driver.setPhoneNum(newDriver.getPhoneNum());
					driver.setStart(newDriver.getStart());
					driver.setFinish(newDriver.getFinish());
					driver.setStatus(newDriver.getStatus());
					driver.setAddress(newDriver.getAddress());
					driver.setLatitude(newDriver.getLatitude());
					driver.setLongitude(newDriver.getLongitude());
					return repository.save(driver);
				})
				.orElseGet(() -> {
					// create new driver record with the given id
					newDriver.setId(id);
					return repository.save(newDriver);
				});
	}
	
	/**
	 * Updates current driver location 
	 * @param location
	 * @param id
	 * @return Location - the updated location
	 */
	@PutMapping("/drivers/{id}/location")
	public Location updateLocation(@RequestBody Location location, @PathVariable Long id) {
		Optional<Driver> driverOpt = repository.findById(id);
		if(driverOpt.isPresent()) {
			Driver driver = driverOpt.get();
			driver.setLatitude(location.getLatitude());
			driver.setLongitude(location.getLongitude());
			repository.save(driver);
			return location;
		}
		else throw new DriverNotFoundException(id);
	}
	
	/**
	 * Saves current address to the db
	 * @param address
	 * @param id
	 * @return Address - the updated address
	 */
	@PutMapping("/drivers/{id}/address")
	public Address updateAddress(@RequestBody Address address, @PathVariable Long id) {
		Optional<Driver> driverOpt = repository.findById(id);
		if(driverOpt.isPresent()) {
			Driver driver = driverOpt.get();
			driver.setAddress(address);
			repository.save(driver);
			return address;
		}
		else throw new DriverNotFoundException(id);
	}
	
	/**
	 * Deletes the given driver from the DB
	 * @param id
	 * @return Driver - the driver entity which was deleted
	 */
	@DeleteMapping("/drivers/{id}")
	public Driver deleteDriverById(@PathVariable Long id) {
		Optional<Driver> driverToDelete = repository.findById(id);
		if(driverToDelete.isPresent()) {
			repository.deleteById(id);
			return driverToDelete.get();
		}
		else 
			throw new DriverNotFoundException(id);
	}
		
	/**
	 * Returns drivers which are currently active
	 * @return List<Driver>
	 */
	@GetMapping("/drivers/active")
	public List<Driver> getActiveDrivers(){
		return this.repository.findByStatus(Status.ACTIVE);
	}
	
	@GetMapping("/drivers/start/{startH}/{startM}/finish/{finishH}/{finishM}")
	public List<Driver> getDriversWorkingBetween(
			@PathVariable int startH, @PathVariable int finishH,
			@PathVariable int startM, @PathVariable int finishM) {
		if(startH<0 || finishH>23) {
			throw new IllegalArgumentException("Please provide hours value betwean 0 and 23");
		} else if (startM<0 || finishM>59){
			throw new IllegalArgumentException("Please provide minutes value betwean 0 and 59");
		} else {
			LocalTime start = LocalTime.of(startH, startM).minusMinutes(1);
			LocalTime finish = LocalTime.of(finishH, finishM).plusMinutes(1);
			return repository.findByStartAfterAndFinishBefore(start, finish);
		}
	}
	
	@GetMapping("/drivers/lat/{latMin}/{latMax}/long/{longMin}/{longMax}")
	public List<Driver> getDriversInMapBounds(
			@PathVariable Double latMin, @PathVariable Double latMax,
			@PathVariable Double longMin, @PathVariable Double longMax) {
		if(latMin<-90 || latMax>90) {
			throw new IllegalArgumentException("Please provide latitude value betwean -90 and 90 degrees");
		} else if (longMin<-180 || longMax>180){
			throw new IllegalArgumentException("Please provide longtitude value betwean -180 and 180 degrees");
		} else {
			return repository.findByLatitudeBetweenAndLongitudeBetween(latMin, latMax, longMin, longMax);
		}
	}
		
//	@GetMapping("/limits")
//	public Limits retrieveLimits() {
//		//return new Limits(1,1000);
//		return new Limits(
//				configuration.getMinimum(),
//				configuration.getMaximum()
//		);
//	}
}
