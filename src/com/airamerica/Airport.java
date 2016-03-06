package com.airamerica;

public class Airport {

	private String airportCode;
	private String name;
	private Address address;
	private Coordinate coordinates;
	private double passengerFacilityFee;
	
	
	public Airport(String airportCode, String name, Address address, Coordinate coordinates,
			double passengerFacilityFee) {
		super();
		this.airportCode = airportCode;
		this.name = name;
		this.address = address;
		this.coordinates = coordinates;
		this.passengerFacilityFee = passengerFacilityFee;
	}//public Airport(String airportCode, String name, Address address, Coordinate coordinates,String passengerFacilityFee)


	public String getAirportCode() {
		return airportCode;
	}//String getAirportCode()


	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}//void setAirportCode(String airportCode)


	public String getName() {
		return name;
	}//String getName()


	public void setName(String name) {
		this.name = name;
	}//void setName(String name)


	public Address getAddress() {
		return address;
	}//Address getAddress()

	public void setAddress(Address address) {
		this.address = address;
	}//void setAddress(Address address)


	public Coordinate getCoordinates() {
		return coordinates;
	}//Coordinate getCoordinates()


	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}//void setCoordinates(Coordinate coordinates)


	public double getPassengerFacilityFee() {
		return passengerFacilityFee;
	}//String getPassengerFacilityFee() 


	public void setPassengerFacilityFee(double passengerFacilityFee) {
		this.passengerFacilityFee = passengerFacilityFee;
	}//void setPassengerFacilityFee(String passengerFacilityFee)
	
	
	
	
}//public class Airport
