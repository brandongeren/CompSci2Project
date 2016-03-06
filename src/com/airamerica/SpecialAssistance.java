package com.airamerica;

public class SpecialAssistance extends Service{

	private String typeOfService;
	private Person personServed;

	public SpecialAssistance(String productCode, String productType, String typeOfService) {
		super(productCode, productType);
		this.typeOfService = typeOfService;
		this.personServed = null;
	}//SpecialAssistance constructor

	public String getTypeOfService() {
		return typeOfService;
	}//String getTypeOfService()

	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}//void setTypeOfService(String typeOfService)

	public Person getPersonServed() {
		return personServed;
	}//	public Person getPersonServed() {

	public void setPersonServed(Person personServed) {
		this.personServed = personServed;
	}//	public void setPersonServed(Person personServed) 
	
	@Override
	public String displayCostPerUnit() {
		return "";
	}//	public String displayCostPerUnit() {

	@Override
	public double getFees() {
		return 0;
	}//	public double getFees() {

	@Override
	public double getCost() {
		return 0;
	}//	public double getCost() {

	@Override
	public String getCostSummaryLine1() {
		return "Special Aassistance for [" + personServed.getNameLastFirst()
				+ "] (" + typeOfService + ")";
	}//	public String getCostSummaryLine1() {

	@Override
	public String getCostSummaryLine2() {
		return "";
	}//	public String getCostSummaryLine2() {

	
}//public class SpecialAssistance extends Service
