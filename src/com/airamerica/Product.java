package com.airamerica;

public abstract class Product {

	private String productCode;
	private String productType;
	private int numUnits;
	
	public Product(String productCode, String productType) {
		this.productCode = productCode;
		this.productType = productType;
		this.numUnits = 0;
	}//Product(String productCode, String productType)

	public String getProductCode() {
		return productCode;
	}//String getProductCode()

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}//void setProductCode(String productCode)

	public String getProductType() {
		return productType;
	}//String getProductType()

	public void setProductType(String productType) {
		if(productType.equals("TS")) {
				this.productType = productType;
		}//if(productType.equals("TS"))
		
		else if(productType.equals("TA")) {
			this.productType = productType;
		}//else if(productType.equals("TA")) 
		
		else if(productType.equals("TO")) {
			this.productType = productType;
		}//else if(productType.equals("TO"))
		
		else if(productType.equals("SC")) {
			this.productType = productType;
		}//else if(productType.equals("SC"))
		
		else if(productType.equals("SI")) {
			this.productType = productType;
		}//else if(productType.equals("SI"))
	
		else if(productType.equals("SR")) {
			this.productType = productType;
		}//else if(productType.equals("SR"))
		
		else if(productType.equals("SS")) {
			this.productType = productType;
		}//else if(productType.equals("SS"))
	}//void setProductType(String productType)
	
	public int getNumUnits() {
		return numUnits;
	}//	public int getNumUnits() {

	public void incrementNumUnits() {
		this.numUnits++;
	}//	public void incrementNumUnits() {
	
	public void incrementNumUnits(int i) {
		this.numUnits+=i;
	}//	public void incrementNumUnits() {

	//this creates a string to be used in the invoiceReport
	public abstract String displayCostPerUnit();
	
	//this returns the tax rate of a product as a decimal
	public abstract double getTaxRate();
	
	public abstract double getFees();
	
	public abstract double getCost();
	
	//string to be used in the invoice report
	public abstract String getCostSummaryLine1();
	
	//string to be used in the invoice report
	public abstract String getCostSummaryLine2();
	
	public double getTaxes() {
		return this.getCost() * this.getTaxRate();
	}//	public double getTaxes() {
		
}//public abstract class Product
