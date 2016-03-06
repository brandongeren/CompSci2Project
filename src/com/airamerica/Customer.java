package com.airamerica;

public abstract class Customer {

	private String customerCode;
	private Person primaryContact;
	private String name;
	private int airlineMiles;
		
	public Customer(String customerCode, Person primaryContact, 
			String name, int airlineMiles) {
		this.customerCode = customerCode;
		this.primaryContact = primaryContact;
		this.name = name;
		this.airlineMiles = airlineMiles;
	}//Customer(String customerCode, String type, Person primaryContact, String firstName, String lastName, int airlineMiles) {
	
	public Person getPrimaryContact() {
		return primaryContact;
	}//public Person getPrimaryContact() 

	public void setPrimaryContact(Person primaryContact) {
		this.primaryContact = primaryContact;
	}//	public void setPrimaryContact(Person primaryContact) {

	public String getCustomerCode() {
		return customerCode;
	}//public String getCustomerCode() 
	
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}//	public void setCustomerCode(String customerCode) {
	
	public String getName() {
		return this.name;
	}//String getFirstName()
	
	public void setName(String name) {
		this.name = name;
	}//void setFirstName(String firstName) 
	
	public int getAirlineMiles() {
		return this.airlineMiles;
	}//int getAirlineMiles()
	
	public void setAirlineMiles(int airlineMiles) {
		this.airlineMiles = airlineMiles;
	}//void setAirlineMiles(int airlineMiles)
	
	//this discount does not take into account taxes
	public abstract double getDiscount();

	//some kinds of Customer have a taxMultiplier other than 1. For Government, it is 0
	public abstract double taxMultiplier();
	
	//some kinds of Customer have a compliance fee
	public abstract double getComplianceFee();
	
	//return what type of customer this customer is
	public abstract String getType();
	

}//public class Customer
