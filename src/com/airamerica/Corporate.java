package com.airamerica;

public class Corporate extends Customer{
	public Corporate(String customerCode, Person primaryContact, String name, int airlineMiles) {
		super(customerCode, primaryContact, name, airlineMiles);
	}//Corporate constructor

	//Corporate customers enjoy a 12% discount on all products (excluding taxes) and pay a flat compliance fee of $40.00 per invoice. 
	
	@Override
	public double getDiscount() {
		return 0.12;
	}//	public double getDiscount() {

	@Override
	public double taxMultiplier() {
		return 1;
	}//	public double taxMultiplier() {

	@Override
	public double getComplianceFee() {
		return 40.0;
	}//	public double getComplianceFee() {

	@Override
	public String getType() {
		return "[Corporate]";
	}//	public String getType() {


}//public class Corporate extends Customer
