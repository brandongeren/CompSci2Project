package com.airamerica;

public class Address {
	
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;

	public Address(String street, String city, String state, String zip, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		}//public Address(String street, String city, String state, String zip, String country) {

	public String getStreet() {
		return street;
	}//String getStreet()

	public String getState() {
		return state;
	}//String getState()

	public String getZip() {
		return zip;
	}//public String getZip()

	public String getCountry() {
		return country;
	}//public String getCountry() 

	public void setState(String state) {
		this.state = state;
	}//public void setState(String state) 

	public void setZip(String zip) {
		this.zip = zip;
	}//public void setZip(String zip)

	public void setCountry(String country) {
		this.country = country;
	}//public void setCountry(String country) 

	public void setStreet(String street) {
		this.street = street;
	}//setStreet(String street)

	public String getCity() {
		return city;
	}//public String getCity()

	public void setCity(String city) {
		this.city = city;
	}//public void setCity(String city) 
	
	public String toString() {
		return street + "\n" + city + " " + state + " " + zip + " " + country;
	}//	public String toString() {

	public String getCityState() {
		return city + "," + state;
	}//	public String getCityState() {
	
}//public class Address {
