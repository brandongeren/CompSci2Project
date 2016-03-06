package com.airamerica;

public class General extends Customer{
	public General(String customerCode, Person primaryContact, String name, int airlineMiles) {
		super(customerCode, primaryContact, name, airlineMiles);
	}//General constructor

	// General customer pay full price for all the products along with fees and taxes
	
	
	@Override
	public double getDiscount() {
		return 0;
	}//	public double getDiscount() {

	@Override
	public double taxMultiplier() {
		return 1;
	}//	public double taxMultiplier() {

	@Override
	public double getComplianceFee() {
		return 0;
	}//	public double getComplianceFee() {

	@Override
	public String getType() {
		return "[General]";
	}//	public String getType() {


}//public class General extends Customer
