package com.airamerica;

import com.airamerica.utils.Haversine;
import com.airamerica.utils.StandardUtils;

public class Insurance extends Service{

	private String name;
	private String ageClass;
	private double costPerMile;
	private Tickets associatedTicket;
	
	
	public Insurance(String productCode, String productType, String name, String ageClass, double costPerMile) {
		super(productCode, productType);
		this.name = name;
		this.ageClass = ageClass;
		this.costPerMile = costPerMile;
		this.associatedTicket = null;
	}//Insurance constructor


	public String getName() {
		return name;
	}//String getName()

	public void setName(String name) {
		this.name = name;
	}//void setName(String name)

	public String getAgeClass() {
		return ageClass;
	}//String getAgeClass()

	public void setAgeClass(String ageClass) {
		this.ageClass = ageClass;
	}//void setAgeClass(String ageClass)

	public Tickets getAssociatedTicket() {
		return associatedTicket;
	}//	public Tickets getAssociatedTicket() {

	public void setAssociatedTicket(Tickets associatedTicket) {
		this.associatedTicket = associatedTicket;
	}//	public void setAssociatedTicket(Tickets associatedTicket) {

	public double getCostPerMile() {
		return costPerMile;
	}// double getCostPerMile() 

	public void setCostPerMile(double costPerMile) {
		this.costPerMile = costPerMile;
	}//void setCostPerMile(double costPerMile)

	@Override
	public String displayCostPerUnit() {
		return "(" + super.getNumUnits() + " units @ $" + StandardUtils.roundCents(costPerMile)
		+ " perMile x " + StandardUtils.roundCents(associatedTicket.getMiles()) + " miles)";
	}//	public String displayCostPerUnit() {

	@Override
	public double getFees() {
		return 0;
	}//	public double getFees() {

	@Override
	public double getCost() {
		return associatedTicket.getMiles()*costPerMile*super.getNumUnits();
	}//	public double getCost() {

	@Override
	public String getCostSummaryLine1() {
		return "Insurance " + name + "(" + ageClass + ")";
	}//	public String getCostSummaryLine1() {

	@Override
	public String getCostSummaryLine2() {
		return displayCostPerUnit();
	}//	public String getCostSummaryLine2() {
	
}//public class Insurance extends Service
