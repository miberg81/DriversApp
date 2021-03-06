package com.michael.microservices.driversservice.bean;

import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// A driver model to store personal information,
// driver status, working hours and current location.
@Entity
@Table(name = "DRIVERS")
public class Driver {
	
	// ISSENTIAL DRIVER INFO
	@Id @GeneratedValue
	private Long id; // positive, unique integer driver identifier, can't be changed after initialization
	
	private String firstName;
	
	private String lastName;
	
	private LocalTime start;
	
	private LocalTime finish;
	
	private Status status = Status.INACTIVE; // inactive unless reported otherwise
	
    private Double latitude;
    
    private Double longitude;
	
	// ADDITIONAL DRIVER INFO
    private Integer age;
	
    private String email;
	
    private String phoneNum;
	
	@Embedded
	Address address;
	
	public Driver() {}

	// constructor with minimum information.
	// needed for registration and working day setup.
	// location and address may be added later when the driver reports it via app.
	public Driver(String firstName, String lastName, Status status, 
				  int startH, int startM, int finishH, int finishM,
			      Integer age, String email, String phoneNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.start = LocalTime.of(startH, 0);
		this.finish = LocalTime.of(finishH, 0);
		
		this.age = age;
		this.email = email;
		this.phoneNum = phoneNum;
	}
	
	// constructor with address included (without location)
	public Driver(String firstName, String lastName, Status status, 
			     int startH, int startM, int finishH, int finishM,
				 Integer age, String email, String phoneNum , Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.start = LocalTime.of(startH, 0);
		this.finish = LocalTime.of(finishH, 0);
		
		this.age = age;
		this.email = email;
		this.phoneNum = phoneNum;
		this.address = address;
	}
	
	// constructor with everything (address + location)
	public Driver(String firstName, String lastName, Status status, 
				  int startH, int startM, int finishH, int finishM, Double latitude, Double longtitude,
				  Integer age, String email, String phoneNum , Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.start = LocalTime.of(startH, 0);
		this.finish = LocalTime.of(finishH, 0);
		this.latitude = latitude;
		this.longitude = longtitude;
		
		this.age = age;
		this.email = email;
		this.phoneNum = phoneNum;	
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getFinish() {
		return finish;
	}

	public void setFinish(LocalTime finish) {
		this.finish = finish;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Driver [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", start=" + start
				+ ", finish=" + finish + ", status=" + status + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", age=" + age + ", email=" + email + ", phoneNum=" + phoneNum + ", address=" + address + "]";
	}
}


