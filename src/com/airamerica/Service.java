package com.airamerica;

public abstract class Service extends Product{

	public Service(String productCode, String productType) {
		super(productCode, productType);
	}//Service(String productCode, String productType)
	
	//the tax rate for all Services is 4%
	public double getTaxRate() {
		return 0.04;
	}//	public double getTaxRate() {


}//public abstract class Service extends Product
