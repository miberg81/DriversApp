package com.michael.microservices.driversservice.bean;
import javax.persistence.Embeddable;


// Drivers address
// We make this class embeddable(into Driver) to avoid creating a separate table for address,
// which would complicate and slow down queries, reducing performance and maintainability
@Embeddable
public class Address {
	String country;
	String region;
	String city;
	String street;
	Integer houseNum;
	Integer floor;
	Integer apartmentNum;
	String zip;
	String comment = null; // special optional comment for user with non standard adress
	
	public Address() {}
	
	public Address(String country, String region, String city, String street, Integer houseNum, Integer floor,
			Integer apartmentNum, String zip, String comment) {
		super();
		this.country = country;
		this.region = region;
		this.city = city;
		this.street = street;
		this.houseNum = houseNum;
		this.floor = floor;
		this.apartmentNum = apartmentNum;
		this.zip = zip;
		this.comment = comment;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getHouseNum() {
		return houseNum;
	}
	public void setHouseNum(Integer houseNum) {
		this.houseNum = houseNum;
	}
	public Integer getApartmentNum() {
		return apartmentNum;
	}
	public void setApartmentNum(Integer apartmentNum) {
		this.apartmentNum = apartmentNum;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Address [country=" + country + ", city=" + city + ", street=" + street
				+ ", houseNum=" + houseNum + ", floor=" + floor + ", apartmentNum=" + apartmentNum + "]";
	}
}
