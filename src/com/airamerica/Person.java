package com.airamerica;
/*
/* A partial implementation representing a 
 * Person */
import java.util.ArrayList;
import java.util.List;

public class Person {
	
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private String phoneNumber;
	private List<String> emails;


	public Person(String personCode, String firstName, String lastName, 
			Address address) {
		this.personCode = personCode;
		this.address = address;
		this.emails = new ArrayList<String>();
		this.phoneNumber = "";
		this.firstName = firstName;
		this.lastName = lastName;
	}//Person(String personCode, String firstName, String lastName, Address address)
	
	public Person(String personCode, String firstName, String lastName, 
			Address address, String phoneNumber) {
		this.personCode = personCode;
		this.address = address;
		this.emails = new ArrayList<String>();
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}//public Person(String personCode, String firstName, String lastName, Address address, String phoneNumber)
	
	public Address getAddress() {
		return this.address;
	}//public Address getAddress()

	public void setEmails(List<String> emails)
	{
		this.emails = emails;
	}//public void setEmails(List<String> emails)
	
	public String getPersonCode() {
		return personCode;
	}//	public String getPersonCode()

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}//public void setPersonCode(String personCode

	public String getFirstName() {
		return firstName;
	}//public String getFirstName() {

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}//public void setFirstName(String firstName)

	public String getLastName() {
		return lastName;
	}//public String getLastName() 

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}//public void setLastName(String lastName) 

	public List<String> getEmails() {
		return emails;
	}//public List<String> getEmails()

	public void setAddress(Address address) {
		this.address = address;
	}//public void setAddress(Address address)
	
	public void addEmail(String email) {
		this.emails.add(email);
	}//public void addEmail(String email) 
	
	public String getPhoneNumber() {
		return phoneNumber;
	}//public String getPhoneNumber() {
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}//public void setPhoneNumber(String phoneNumber) {
	
	//returns the first name then the last name delimited by a space
	public String getNameFirstLast() {
		return this.firstName + " " + this.lastName;
	}// String getNameFirstLast()
	
	//returns the last name then the first name delimited by a comma and a space
	public String getNameLastFirst() {
		return this.lastName + ", " + this.firstName;
	}//String getNameLastFirst()
	

}//public class Person 
