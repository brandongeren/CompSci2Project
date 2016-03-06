package com.airamerica;

public class Government extends Customer{
	public Government(String customerCode, Person primaryContact, String name, int airlineMiles) {
		super(customerCode, primaryContact, name, airlineMiles);
	}//Government constructor

	//Government customers pay the full price for all the products along with fees, but are exempt from all kinds of taxes
	
	@Override
	public double getDiscount() {
		return 0;
	}//	public double getDiscount() {

	@Override
	public double taxMultiplier() {
		return 0;
	}//	public double taxMultiplier() {

	@Override
	public double getComplianceFee() {
		return 0;
	}//	public double getComplianceFee() {

	@Override
	public String getType() {
		return "[Government]";
	}//	public String getType() {


}//public class Government extends Customer
