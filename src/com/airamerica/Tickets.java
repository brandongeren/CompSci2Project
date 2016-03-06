package com.airamerica;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Locale;

import com.airamerica.utils.Haversine;

import unl.cse.assignments.DataConverter;

public abstract class Tickets extends Product{

	private String depAirportCode;
	private String arrAirportCode;
	private String flightNo;
	private String flightClass;
	private String aircraftType;
	private String depTime;
	private String arrTime;
	private int numPassengers;
	private ArrayList<Person> passengers;
	private Airport depAirport;
	private Airport arrAirport;
	private ArrayList<String> seats;
	private ArrayList<Integer> ages;
	private String ticketNote;
	private String travelDate;
	
	public Tickets(String productCode, String productType, String depAirportCode, String arrAirportCode,
			String flightNo, String flightClass, String aircraftType, String depTime, String arrTime) {
		super(productCode, productType);
		this.depAirportCode = depAirportCode;
		this.arrAirportCode = arrAirportCode;
		this.flightNo = flightNo;
		this.flightClass = flightClass;
		this.aircraftType = aircraftType;
		
		//format the depTime
		String[] depTimeHoursMins = depTime.split(":");
		this.depTime = depTimeHoursMins[0] + ":" + depTimeHoursMins[1];
		
		//format the arrTime
		String[] arrTimeHoursMins = arrTime.split(":");
		this.arrTime = arrTimeHoursMins[0] + ":" + arrTimeHoursMins[1];
		
		
		this.passengers = new ArrayList<Person>();
		
		this.depAirport = null;
		this.arrAirport = null;
		
		//look into Airports.dat for the airport referenced
		for(Airport airport: DataConverter.getAllAirports()) {
			//set the depAirport in this class
			if(this.depAirportCode.equals(airport.getAirportCode())) {
				this.depAirport = airport;
			}
			
			//set the arrAirport in this class
			if(this.arrAirportCode.equals(airport.getAirportCode())) {
				this.arrAirport = airport;
			}
		}//	for(Airport airport: DataConverter.getAllAirports("data/Airports.dat")) {
		seats = new ArrayList<String>();
		ages = new ArrayList<Integer>();
		this.ticketNote = "";
		this.travelDate = "0001-01-01";
	}//Tickets(String productCode, String productType, String depAirportCode, String arrAirportCode, String flightNo, String flightClass, String aircraftType)

	public String getDepAirportCode() {
		return depAirportCode;
	}//String getDepAirportCode()

	public void setDepAirportCode(String depAirportCode) {
		this.depAirportCode = depAirportCode;
	}//void setDepAirportCode(String depAirportCode)

	public String getArrAirportCode() {
		return arrAirportCode;
	}//String getArrAirportCode()

	public void setArrAirportCode(String arrAirportCode) {
		this.arrAirportCode = arrAirportCode;
	}//void setArrAirportCode(String arrAirportCode)

	public String getFlightNo() {
		return flightNo;
	}//String getFlightNo() 

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}//void setFlightNo(String flightNo)

	public String getFlightClass() {
		return flightClass;
	}//String getFlightClass()

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}//void setFlightClass(String flightClass)

	public String getAircraftType() {
		return aircraftType;
	}//String getAircraftType()

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}//void setAircraftType(String aircraftType)c

	public String getDepTime() {
		return depTime;
	}//Time getDepTime()

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}//void setDepTime(Time depTime)

	public String getArrTime() {
		return arrTime;
	}//Time getArrTime()

	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}//void setArrTime(Time arrTime) 
	
	public Airport getDepAirport() {
		return depAirport;
	}//	public Airport getDepAirport() {

	public void setDepAirport(Airport depAirport) {
		this.depAirport = depAirport;
	}//	public void setDepAirport(Airport depAirport) {

	public Airport getArrAirport() {
		return arrAirport;
	}//	public Airport getArrAirport() {

	public void setArrAirport(Airport arrAirport) {
		this.arrAirport = arrAirport;
	}//	public void setArrAirport(Airport arrAirport) {

	public ArrayList<String> getSeats() {
		return seats;
	}//	public ArrayList<String> getSeats() {

	public void addSeat(String seat) {
		seats.add(seat);
	}//	public void addSeat(String seat) {

	public ArrayList<Integer> getAges() {
		return ages;
	}//	public ArrayList<Integer> getAges() {

	public void addAge(int age) {
		ages.add(age);
	}//	public void setAges(ArrayList<Integer> ages) {

	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}//	public void setPassengers(int passengers) {

	public int getNumPassengers() {
		return this.numPassengers;
	}//	public int getNumPassengers() {
	
	public ArrayList<Person> getPassengers() {
		return this.passengers;
	}//	public ArrayList<Person> getPassengers() {
	
	public void addPassenger(Person passenger) {
		passengers.add(passenger);
	}//	public void setPassengers(ArrayList<Person> passengers) {
	
	public String getTicketNote() {
		return ticketNote;
	}//	public String getTicketNote() {

	public void setTicketNote(String ticketNote) {
		this.ticketNote = ticketNote;
	}//	public void setTicketNote(String ticketNote) {
	
	public String getRawTravelDate() {
		return this.travelDate;
	}//	public String getRawTravelDate() {
	
	public String getTravelDate() {
		
		int year = Integer.parseInt(travelDate.substring(0,4));
		int month = Integer.parseInt(travelDate.substring(5,7));
		int dayOfMonth = Integer.parseInt(travelDate.substring(8));
				
		LocalDate date = LocalDate.of(year, month, dayOfMonth);
		DayOfWeek dow = date.getDayOfWeek();
		Locale locale = Locale.getDefault();
		Month monthOfYear = date.getMonth();
		//dow.getDisplayName(TextStyle.SHORT, locale)
		return dow.getDisplayName(TextStyle.SHORT, locale) + "," + 
		date.getDayOfMonth() + monthOfYear.getDisplayName(TextStyle.SHORT, locale)
		+Integer.toString(date.getYear()).substring(2);
	}//	public String getTravelDate() {
	
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}//	public void setTravelDate(String travelDate) {

	public double getFarePerMile() {
		if(this.flightClass.equals("EC")) {
			return 0.15;
		} else if(this.flightClass.equals("BC")) {
			return 0.5;
		} else if(this.flightClass.equals("EP")) {
			return 0.2;
		} else {
			return 0;
		}//	if(this.flightClass.equals("EC")) {

	}//	public double getRatePerMile() {
	
	public double getMiles() {
		if(arrAirport == null) {
			System.out.println("arrAirport is null" + depAirport.getAirportCode());
		}
		return Haversine.getMiles(depAirport.getCoordinates().getLatitude(), 
				depAirport.getCoordinates().getLongitude(), 
				arrAirport.getCoordinates().getLatitude(), 
				arrAirport.getCoordinates().getLongitude());
	}//	public double getMiles() {


	//get the fees to be added upon computation of total costs
	public double getTaxRate() {
		return 0.075;
	}//	public double getTaxRate() 
	
	public double getTaxes() {
		return this.getTaxRate()*this.getCost()+this.getFees();
	}//	public double getTaxes() {
	
	@Override
	public double getFees() {
		if(this.getArrAirport() == null) {
			System.out.println(this.getDepAirportCode());
		}
		return 4*this.getNumPassengers()+5.6*this.getNumPassengers()
		+this.getArrAirport().getPassengerFacilityFee()*this.numPassengers;
	}//	public double getFees() {
}//public abstract class Tickets extends Product
