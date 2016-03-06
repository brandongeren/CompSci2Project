package com.airamerica;

import java.sql.Time;

import com.airamerica.utils.StandardUtils;

public class AwardTickets extends Tickets{

	private int pointsPerMile;


	public AwardTickets(String productCode, String productType, String depAirportCode, String arrAirportCode,
			String flightNo, String flightClass, String aircraftType, String depTime, String arrTime,
			int pointsPerMile) {
		super(productCode, productType, depAirportCode, arrAirportCode, flightNo, flightClass, aircraftType, depTime,
				arrTime);
		this.pointsPerMile = pointsPerMile;
	}//AwardTickets constructor

	public int getPointsPerMile() {
		return pointsPerMile;
	}//String getPointsPerMile()

	public void setPointsPerMile(int pointsPerMile) {
		this.pointsPerMile = pointsPerMile;
	}//void setPointsPerMile(String pointsPerMile)

	@Override
	public String displayCostPerUnit() {
		return "(" + super.getNumPassengers()+" units @ " + this.getRewardMiles() + 
				" rewardMiles/unit with $30.00 RedemptionFee)";
	}//	public String displayCostPerUnit() {

	public double getRewardMiles() {
		return Math.round(22*super.getMiles());
	}//	public double getCostPerUnit() {
	
	public double getCost() {
		return 30;
	}//	public double getCost() {

	@Override
	public String getCostSummaryLine1() {
		return "AwardTicket(" + super.getFlightClass() + ") " + super.getDepAirportCode()
		+ " to " + super.getArrAirportCode() + " (" + StandardUtils.roundCents(super.getMiles()) + " miles)";
	}//	public String getCostSummaryLine1() {

	@Override
	public String getCostSummaryLine2() {
		return displayCostPerUnit();
	}//	public String getCostSummaryLine2() {

}//public class AwardTickets extends Tickets
