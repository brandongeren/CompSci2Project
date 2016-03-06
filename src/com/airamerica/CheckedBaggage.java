package com.airamerica;

public class CheckedBaggage extends Service{

	private String ticketCode;

	public CheckedBaggage(String productCode, String productType, String ticketCode) {
		super(productCode, productType);
		this.ticketCode = ticketCode;
	}//CheckedBaggage constructor

	public String getTicketCode() {
		return ticketCode;
	}//String getTicketCode()

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}//void setTicketCode(String ticketCode)

	@Override
	public String displayCostPerUnit() {
		return "(" + super.getNumUnits() + " units @ $25.00 for 1st and $35.00 onwards)"; 
	}//	public String displayCostPerUnit() {


	@Override
	public double getFees() {
		return 0;
	}//	public double getFees() {

	@Override
	public double getCost() {
		if(super.getNumUnits()==1) {
			return 25;
		} else {
			return 25+ (super.getNumUnits()-1)*35;
		}//	if(super.getNumUnits()==1) {


	}//	public double getCost() {

	@Override
	public String getCostSummaryLine1() {
		return "Baggage" + " " + this.displayCostPerUnit();
	}//	public String getCostSummaryLine1() {

	@Override
	public String getCostSummaryLine2() {
		return "";
	}//	public String getCostSummaryLine2() {
	
}//public class CheckedBaggage extends Service
