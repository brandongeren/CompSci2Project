package com.airamerica;

import com.airamerica.utils.StandardUtils;

public class Refreshment extends Service{
	
	private String name; 
	private double cost;
	
	public Refreshment(String productCode, String productType, String name, double cost) {
		super(productCode, productType);
		this.name = name;
		this.cost = cost;
	}//Refreshment constructor
	
	public String getName() {
		return name;
	}//String getName()
	
	public void setName(String name) {
		this.name = name;
	}//void setName(String name)
	
	public double getCost() {
		return cost*super.getNumUnits()*0.95;
	}//double getCost()
	
	public void setCost(double cost) {
		this.cost = cost;
	}//void setCost(double cost)

	public double getRawCost() {
		return this.cost;
	}//	public double getRawCost() {

	
	@Override
	public String displayCostPerUnit() {
		return "(" + super.getNumUnits() + " units @ " + StandardUtils.roundCents(this.getCost()/super.getNumUnits())
				+ "/unit with 5% off)";
	}//	public String displayCostPerUnit() {
	
	@Override
	public double getFees() {
		return 0;
	}//	public double getFees() {

	@Override
	public String getCostSummaryLine1() {
		return name + " " + this.displayCostPerUnit();
	}//	public String getCostSummaryLine1() {

	@Override
	public String getCostSummaryLine2() {
		return "";
	}//	public String getCostSummaryLine2() {
	
}//public class Refreshment extends Service
