package com.michael.microservices.driversservice.bean;

import java.util.Objects;

import javax.persistence.Embeddable;

// Location is stored in driver directly as latitude and longitude (without Location class) 
// This is done to avoid creating redundant multiple objects with 2 fields only 
// and to simplify querying by method name.
// This class still remains and is used to get/return the location object from client with single param
// It may also be used to create a list of resent locations etc...
@Embeddable
public class Location {
	private Double latitude;   
    private Double longitude;
   
    public Location() {}

	// create and initialize a point with given name and
    // (latitude, longitude) specified in degrees
    public Location(Double latitude, Double longitude) {
        this.latitude  = latitude;
        this.longitude = longitude;
    }

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}

	@Override
	public String toString() {
		return "Location [longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}
}
