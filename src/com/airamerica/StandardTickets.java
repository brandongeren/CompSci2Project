package com.airamerica;

import java.sql.Time;

import com.airamerica.utils.StandardUtils;

public class StandardTickets extends Tickets{

	public StandardTickets(String productCode, String productType, String depAirportCode, String arrAirportCode,
			String flightNo, String flightClass, String aircraftType, String depTime, String arrTime) {
		super(productCode, productType, depAirportCode, arrAirportCode, flightNo, flightClass, aircraftType, depTime, arrTime);
	}//StandardTickets constructor

	@Override
	public String displayCostPerUnit() {
		return "(" + super.getNumUnits() + " units @ $" + 
				StandardUtils.roundCents(this.getCost()/super.getNumPassengers())
				+ "/unit)";
	}//	public String displayCostPerUnit() {

	@Override
	public double getCost() {
		return super.getMiles()*super.getFarePerMile()*super.getNumPassengers();
	}//	public double getCost() {

	@Override
	public String getCostSummaryLine1() {
		return "StandardTicket(" + super.getFlightClass() + ") " + super.getDepAirportCode()
		+ " to " + super.getArrAirportCode() + " (" + StandardUtils.roundCents(super.getMiles()) + " miles)";
	}//	public String getCostSummaryLine1() {


	@Override
	public String getCostSummaryLine2() {
		return displayCostPerUnit();
	}//	public String getCostSummaryLine2() {

}//public class StandardTickets extends Tickets
