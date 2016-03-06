package com.airamerica;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

import com.airamerica.utils.StandardUtils;

public class OffSeasonTickets extends Tickets{
	
	private String seasonStartDate;
	private String seasonEndDate;
	private double rebate;
	public boolean isSeason;
	
	public OffSeasonTickets(String productCode, String productType, String depAirportCode, String arrAirportCode,
			String flightNo, String flightClass, String aircraftType, String depTime, String arrTime, String seasonStartDate,
			String seasonEndDate, double rebate) {
		super(productCode, productType, depAirportCode, arrAirportCode, flightNo, flightClass, aircraftType, depTime,
				arrTime);
		this.seasonStartDate = seasonStartDate;
		this.seasonEndDate = seasonEndDate;
		this.rebate = rebate;
		
		this.isSeason = false;
		 
	}//OffSeasonTickets constructor
		
	
	public String getSeasonStartDate() {
		return seasonStartDate;
	}//Date getSeasonStartDate()

	public void setSeasonStartDate(String seasonStartDate) {
		this.seasonStartDate = seasonStartDate;
	}//	public void setSeasonStartDate(String seasonStartDate) {

	public String getSeasonEndDate() {
		return seasonEndDate;
	}//	public String getSeasonEndDate() {

	public void setSeasonEndDate(String seasonEndDate) {
		this.seasonEndDate = seasonEndDate;
	}//	public void setSeasonEndDate(String seasonEndDate) {

	public double getRebate() {
		if(isSeason) {
			return rebate;
		} //if the ticketDate is in the season
		
		else{
			return 0.0;
		}//if the ticketDate is not in the season there won't be a rebate
	}//	public double getRebate() {

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}//	public void setRebate(double rebate) {

	@Override
	public String displayCostPerUnit() {
		return "(" + super.getNumPassengers() + " units @ $" + 
				StandardUtils.roundCents((this.getCost()-20)/super.getNumPassengers())
				+ "/unit with $20.00 fee) " ;
	}//	public String displayCostPerUnit() {

	@Override
	public double getCost() {
		if(isSeason) {
			return super.getMiles()*super.getFarePerMile()*(1-rebate)*super.getNumPassengers()+20;
		} //if the ticketDate is in the season
		
		else {
			return super.getMiles()*super.getFarePerMile()*super.getNumPassengers()+20;
		}//if the ticketDate is not in the season
		
		//need a different return statement for when the travelDate is in between the season Dates
		
	}//	public double getCost() {

	@Override
	public String getCostSummaryLine1() {
		return "OffSeasonTicket(" + super.getFlightClass() + ") " + super.getDepAirportCode()
		+ " to " + super.getArrAirportCode() + " (" + StandardUtils.roundCents(super.getMiles()) + " miles) "
		+ StandardUtils.roundCents(this.getRebate()*100) +"% off";
	}//	public String getCostSummaryLine1() {

	@Override
	public String getCostSummaryLine2() {
		return this.displayCostPerUnit();
	}//	public String getCostSummaryLine2() {

	public void setTravelDate(String travelDate) {
		
		super.setTravelDate(travelDate);
		
		
		int flightYear = Integer.parseInt(travelDate.substring(0,4));
		int flightMonth = Integer.parseInt(travelDate.substring(5,7));
		int flightDayOfMonth = Integer.parseInt(travelDate.substring(8));
				
		LocalDate flightDate = LocalDate.of(flightYear, flightMonth, flightDayOfMonth);	
			
		int seasonStartYear = Integer.parseInt(seasonStartDate.substring(0,4));
		int seasonStartMonth = Integer.parseInt(seasonStartDate.substring(5,7));
		int seasonStartDayOfMonth = Integer.parseInt(seasonStartDate.substring(8));
			
		LocalDate seasonStart = LocalDate.of(seasonStartYear, seasonStartMonth, seasonStartDayOfMonth);

		int seasonEndYear = Integer.parseInt(seasonEndDate.substring(0,4));
		int seasonEndMonth = Integer.parseInt(seasonEndDate.substring(5,7));
		int seasonEndDayOfMonth = Integer.parseInt(seasonEndDate.substring(8));
			
		LocalDate seasonEnd = LocalDate.of(seasonEndYear, seasonEndMonth, seasonEndDayOfMonth);

		isSeason = flightDate.isAfter(seasonStart) && flightDate.isBefore(seasonEnd);
	}
	
}//public class OffSeasonTickets extends Tickets
