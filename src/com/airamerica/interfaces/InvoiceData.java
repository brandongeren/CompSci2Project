package com.airamerica.interfaces;
/* Assignment 5 - (Phase IV) */
/* NOTE: Donot change the package name or any of the method signatures.
 *  
 * There are 23 methods in total, all of which need to be completed as a 
 * bare minimum as part of the assignment.You can add additional methods 
 * for testing if you feel.
 * 
 * It is also recommended that you write a separate program to read
 * from the .dat files and test these methods to insert data into your 
 * database.
 * 
 * Donot forget to change your reports generation classes to read from 
 * your database instead of the .dat files.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import com.airamerica.utils.ConnectionFactory;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() { 
		//Delete from StateCountries
		//Delete from AddressStates
		//Delete from Countries
		//Delete from States
		//Delete from Addresses
		//Delete from Coordinates
		//Delete from Tickets
		//Delete from Airports
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
				
		String setAirportAddressID = "Update Airports set AddressID = ?";
		String setPersonAddress = "Update Persons set AddressID = ?";
		String setSalesPersonID = "Update Invoices set SalesPersonID = ?";
		String setPrimaryContactID = "Update Customers set PersonID = ?";
		String setAssociatedPersonID = "Update InvoiceProducts set AssociatedPersonID = ?";

		
		String deleteStateCountries = "Delete from StateCountries";
		String deleteAddressStates = "Delete from AddressStates";
		String deleteCountries = "Delete from Countries";
		String deleteStates = "Delete from States";
		String deleteAddresses = "Delete from Addresses";
		String deleteEmails = "Delete from Emails";
		String deleteTicketPassengers = "Delete from TicketPassengers";

		
		String deletePersons = "Delete from Persons";
		
		
		try {
			
			ps = conn.prepareStatement(setAirportAddressID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setAssociatedPersonID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteTicketPassengers);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setSalesPersonID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setPrimaryContactID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setPersonAddress);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteStateCountries);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteAddressStates);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteCountries);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteStates);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteAddresses);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteEmails);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deletePersons);
			ps.executeUpdate();
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch

	}//public static void removeAllPersons()

	/**
	 * Method to add an address record to the database with the provided data
	 */
	public static void addAddress(String street, String city, String state, String zip, String country) {
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectCountry = "Select * from Countries where CountryName like ?;";
		try {
		
			ps = conn.prepareStatement(selectCountry);
			ps.setString(1, country);
			rs = ps.executeQuery();
			int countryID = -1;
			
			
			if(rs.next()) {
				countryID = rs.getInt("CountryID");
				rs.close();
				ps.close();
			} else {
				String insertCountry = "Insert into Countries (CountryName) values (?);";
				PreparedStatement ps2;
				ResultSet rs2;

				ps2 = conn.prepareStatement(insertCountry);
				ps2.setString(1, country);
				ps2.executeUpdate();
				
				ps2.close();
				ps2 = conn.prepareStatement(selectCountry);
				ps2.setString(1, country);
				rs2 = ps.executeQuery();
				
				if(rs2.next()) {
					countryID = rs2.getInt("CountryID");
				} else {
					throw new RuntimeException("If this happens, check out addAddress for an error.");
				}// if(rs2.next()) {
				
				rs2.close();
				ps2.close();
				rs.close();
				ps.close();
			}//	if(rs.next()) {

			String selectState = "Select * from States where StateName like ?";
			
			ps = conn.prepareStatement(selectState);
			ps.setString(1, state);
			rs = ps.executeQuery();
			int stateID = -1;
				
			if(rs.next()) {
				stateID = rs.getInt("StateID");
				rs.close();
				ps.close();
			} else {
				String insertState = "Insert into States (StateName) values (?);";
				PreparedStatement ps2;
				ResultSet rs2;

				ps2 = conn.prepareStatement(insertState);
				ps2.setString(1, state);
				ps2.executeUpdate();
				
				ps2.close();
				ps2 = conn.prepareStatement(selectState);
				ps2.setString(1, state);
				rs2 = ps.executeQuery();
				
				if(rs2.next()) {
					stateID = rs2.getInt("StateID");
				} else {
					throw new RuntimeException("If this happens, check out addAddress for an error.");
				}// if(rs2.next()) {
				
				rs2.close();
				ps2.close();
				rs.close();
				ps.close();
			}//	if(rs.next()) {
			
			String selectStateCountry = "Select * from StateCountries where StateID like ?;";
			ps = conn.prepareStatement(selectStateCountry);
			ps.setInt(1, stateID);
			rs = ps.executeQuery();
				
			if(rs.next()) {
				rs.close();
				ps.close();
			} else {
				String insertStateCountry = "insert into StateCountries (StateID, CountryID) values (?,?);";
				
				PreparedStatement ps2;
				
				ps2 = conn.prepareStatement(insertStateCountry);
				ps2.setInt(1, stateID);
				ps2.setInt(2, countryID);
				ps2.executeUpdate();
				
				ps2.close();
				rs.close();
				ps.close();
			}//	if(rs.next()) {
			
			
			String insertAddress = "insert into Addresses (Street, City, Zip) values (?,?,?);";
			ps = conn.prepareStatement(insertAddress);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			ps.executeUpdate();
			ps.close();
			
			String selectAddress = "Select * from Addresses where Street like ?;";
			ps = conn.prepareStatement(selectAddress);
			ps.setString(1, street);
			rs = ps.executeQuery();

			int addressID = 0;
			
			if(rs.next()) {
				addressID = rs.getInt("AddressID");
			} else {
				throw new RuntimeException("This shouldn't happen. Check addAddress for errors");
			}//	if(rs.next()) {

			rs.close();
			ps.close();
			
			String selectAddressState = "Select * from AddressStates where AddressID like ?;";
			ps = conn.prepareStatement(selectAddressState);
			ps.setInt(1, addressID);
			rs = ps.executeQuery();
				
			if(rs.next()) {
				rs.close();
				ps.close();
			} else {
				String insertAddressState = "insert into AddressStates (StateID, AddressID) values (?,?);";
				
				PreparedStatement ps2;
				
				ps2 = conn.prepareStatement(insertAddressState);
				ps2.setInt(1, stateID);
				ps2.setInt(2, addressID);
				ps2.executeUpdate();
				
				ps2.close();
				rs.close();
				ps.close();
			}//	if(rs.next()) {
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addAddress(String street, String city, String state, String zip, String country) {
	
	/**
	 * Method to add a person record to the database with the provided data. 
	 */
	public static void addPerson(String personCode, String firstName, String lastName, 
			String phoneNo, String street, String city, String state, 
			String zip, String country) {
		
		addAddress(street, city, state, zip, country);
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectPersonQuery = "Select * from Persons where PersonCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectPersonQuery);
			ps.setString(1, personCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				throw new RuntimeException("Person with given personCode was already found in the database.");
			}//	if(rs.next()) {
		
			rs.close();
			ps.close();
			
			String selectAddress = "Select * from Addresses where Street like ?;";
			ps = conn.prepareStatement(selectAddress);
			ps.setString(1, street);
			rs = ps.executeQuery();

			int addressID = 0;
			
			if(rs.next()) {
				addressID = rs.getInt("AddressID");
			} else {
				throw new RuntimeException("This shouldn't happen. Check addPerson for errors");
			}//	if(rs.next()) {

			rs.close();
			ps.close();

			
			String insertPersonQuery = "Insert into Persons (AddressID, PersonCode, FirstName, LastName, PhoneNo) values (?,?,?,?,?);";

			ps = conn.prepareStatement(insertPersonQuery);
			ps.setInt(1, addressID);
			ps.setString(2, personCode);
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			ps.setString(5, phoneNo);
			ps.executeUpdate();

			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//public static void addPerson

	/**
	 * Method that removes every airport record from the database
	 */
	public static void removeAllAirports() {
		//Delete from StateCountries
		//Delete from AddressStates
		//Delete from Countries
		//Delete from States
		//Delete from Addresses
		//Delete from Coordinates
		//Delete from Tickets
		//Delete from Airports
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
				
		String setAirportAddressID = "Update Airports set AddressID = ?";
		String setAirportCoordinatesID = "Update Airports set CoordinateID = ?";
		String setDepAirportID = "Update Tickets set DepAirportID = ?";
		String setArrAirportID = "Update Tickets set ArrAirportID = ?";
		String setPersonAddress = "Update Persons set AddressID = ?";
		
		String deleteStateCountries = "Delete from StateCountries";
		String deleteAddressStates = "Delete from AddressStates";
		String deleteCountries = "Delete from Countries";
		String deleteStates = "Delete from States";
		String deleteAddresses = "Delete from Addresses";
		String deleteCoordinates = "Delete from Coordinates";
		String deleteAirports = "Delete from Airports";
		
		
		try {
			
			ps = conn.prepareStatement(setAirportAddressID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setAirportCoordinatesID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setDepAirportID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setArrAirportID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(setPersonAddress);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteStateCountries);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteAddressStates);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteCountries);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteStates);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteAddresses);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteCoordinates);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteAirports);
			ps.executeUpdate();
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//public static void removeAllAirports
	
	/**
	 * Method to add a airport record to the database with the provided data. 
	 */
	public static void addAirport(String airportCode, String name, String street, 
			String city, String state, String zip, String country, 
			int latdegs, int latmins, int londegs, int lonmins, 
			double passengerFacilityFee) { 
		addAddress(street, city, state, zip, country);
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectAirportQuery = "Select * from Airports where AirportCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectAirportQuery);
			ps.setString(1, airportCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				throw new RuntimeException("Airport with given airportCode was already found in the database.");
			}//	if(rs.next()) {
		
			rs.close();
			ps.close();
			
			String selectAddress = "Select * from Addresses where Street like ?;";
			ps = conn.prepareStatement(selectAddress);
			ps.setString(1, street);
			rs = ps.executeQuery();

			int addressID = 0;
			
			if(rs.next()) {
				addressID = rs.getInt("AddressID");
			} else {
				throw new RuntimeException("This shouldn't happen. Check addPerson for errors");
			}//	if(rs.next()) {

			rs.close();
			ps.close();

			int coordinateID = 0;
			
			String insertCoordinate = "Insert into Coordinates (LatDegs, LatMins, LonDegs, LonMins) values (?,?,?,?);";

			ps = conn.prepareStatement(insertCoordinate);
			ps.setInt(1, latdegs);
			ps.setInt(2, latmins);
			ps.setInt(3, londegs);
			ps.setInt(4, lonmins);

			ps.executeUpdate();
			
			ps.close();
			
			
			String selectCoordinates = "Select * from Coordinates where LatDegs like ? and LatMins like ? and LonDegs like ? and LonMins like ?;";
			
			ps = conn.prepareStatement(selectCoordinates);
			ps.setInt(1, latdegs);
			ps.setInt(2, latmins);
			ps.setInt(3, londegs);
			ps.setInt(4, lonmins);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				coordinateID = rs.getInt("CoordinateID");
			} else {
				throw new RuntimeException("If this happens, check out addAirport for an error.");
			}// if(rs2.next()) {
			
			rs.close();
			ps.close();

			
			
			String insertAirportQuery = "Insert into Airports (AddressID, CoordinateID, AirportCode, PassFacFee, AirportName) values (?,?,?,?,?);";

			ps = conn.prepareStatement(insertAirportQuery);
			ps.setInt(1, addressID);
			ps.setInt(2, coordinateID);
			ps.setString(3, airportCode);
			ps.setDouble(4, passengerFacilityFee);
			ps.setString(5, name);
			ps.executeUpdate();

			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addAirport(String airportCode, String name, String street, 
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 */
	public static void addEmail(String personCode, String email) { 
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String personIDquery = "Select PersonID from Persons where PersonCode like ?;";
		try {
			ps = conn.prepareStatement(personIDquery);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			int personID;
			
			
			if(rs.next()) {
				personID = rs.getInt("PersonID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Person associated with email " + email + " not found in database");
			}//	if(rs.next()) {

			String insertEmailQuery = "insert into Emails (PersonID, Email) values (?,?)";
			ps = conn.prepareStatement(insertEmailQuery);
			ps.setInt(1, personID);
			ps.setString(2, email);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addEmail(String personCode, String email) { 
	
	/**
	 * Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() { 
		//set CustomerID in Invoices to all null
		//"Delete from Customers"
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		
		String setCustomerID = "Update Invoices set CustomerID = ?";
		String deleteCustomers = "Delete from Customers";
		
		try {
			
			ps = conn.prepareStatement(setCustomerID);
			ps.setString(1, null);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteCustomers);
			ps.executeUpdate();
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//	public static void removeAllCustomers() { 

	/**
	 * Method to add a customer record to the database with the provided data. 
	 */
	public static void addCustomer(String customerCode, String customerType, 
			String primaryContactPersonCode, String name, 
			int airlineMiles) {	
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String personIDquery = "Select PersonID from Persons where PersonCode like ?;";
		try {
			ps = conn.prepareStatement(personIDquery);
			ps.setString(1, primaryContactPersonCode);
			rs = ps.executeQuery();
			Integer personID = null;
			
			
			if(rs.next()) {
				personID = rs.getInt("PersonID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Person associated with personCode " + primaryContactPersonCode + " not found in database. Check what's going on in addCustomer.");
			}//	if(rs.next()) {

			String insertCustomerQuery = "insert into Customers (PersonID, CustomerCode, CompanyName, AirlineMiles, CustomerType) values (?,?,?,?,?)";
			ps = conn.prepareStatement(insertCustomerQuery);
			ps.setInt(1, personID);
			ps.setString(2, customerCode);
			ps.setString(3, name);
			ps.setInt(4, airlineMiles);
			ps.setString(5, customerType);
			ps.executeUpdate();
			
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//	public static void addCustomer(String customerCode, String customerType, 

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() { 
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		
		String deleteTicketPassengers = "Delete from TicketPassengers";
		String deleteOffSeasonTickets = "Delete from OffSeasonTickets";
		String deleteAwardTickets = "Delete from AwardTickets";
		String deleteTickets = "Delete from Tickets";
		String deleteCheckedBaggage = "Delete from CheckedBaggage";
		String deleteSpecialAssistance = "Delete from SpecialAssistances";
		String deleteRefreshments = "Delete from Refreshments";
		String deleteInsurances = "Delete from Insurances";
		String deleteInvoiceProducts = "Delete from InvoiceProducts";
		String deleteProducts = "Delete from Products";
		
		try {
			
			ps = conn.prepareStatement(deleteTicketPassengers);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteOffSeasonTickets);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteAwardTickets);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteTickets);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteCheckedBaggage);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteSpecialAssistance);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteRefreshments);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteInsurances);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteInvoiceProducts);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteProducts);
			ps.executeUpdate();
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void removeAllProducts() { 

	/**
	 * Adds an standardTicket record to the database with the
	 * provided data.  
	 */
	public static void addStandardTicket(String productCode,String depAirportCode, 
			String arrAirportCode, String depTime, String arrTime, 
			String flightNo, String flightClass, String aircraftType) { 
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductQuery = "Select * from Products where ProductCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				throw new RuntimeException("Product with given productCode was already found in the database.");
			}//	if(rs.next()) {
		
			rs.close();
			ps.close();
			
			
			String insertProductQuery = "Insert into Products (ProductCode, ProductType) values (?,?);";
		
			ps = conn.prepareStatement(insertProductQuery);
			ps.setString(1, productCode);
			ps.setString(2, "TS");
			ps.executeUpdate();

			ps.close();
						
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			
			int productID = 0;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("If this happens, there is an error in addStandardTickets. This should not happen");
			}//	if(rs.next()) {
			
			String selectAirportQuery = "Select * from Airports where AirportCode like ?;";
			
			ps = conn.prepareStatement(selectAirportQuery);
			ps.setString(1, depAirportCode);
			rs = ps.executeQuery();
			
			int depAirportID = 0;
			
			if(rs.next()) {
				depAirportID = rs.getInt("AirportID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Airport with given depAirportCode " + depAirportCode + " not found.");
			}//	if(rs.next()) {
						
			ps = conn.prepareStatement(selectAirportQuery);
			ps.setString(1, arrAirportCode);
			rs = ps.executeQuery();
			
			int arrAirportID = 0;
			
			if(rs.next()) {
				arrAirportID = rs.getInt("AirportID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Airport with given arrAirportCode " + arrAirportCode + " not found.");
			}//	if(rs.next()) {
			
			String insertInsuranceQuery = "insert into Tickets (ProductID, DepAirportID, ArrAirportID, FlightNo,"
					+" FlightClass, AircraftType, DepTime, ArrTime) values (?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(insertInsuranceQuery);
			ps.setInt(1, productID);
			ps.setInt(2, depAirportID);
			ps.setInt(3, arrAirportID);
			ps.setString(4, flightNo);
			ps.setString(5, flightClass);
			ps.setString(6, aircraftType);
			ps.setString(7, depTime);
			ps.setString(8, arrTime);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//	public static void addStandardTicket(String productCode,String depAirportCode, 
		
	 /** 
	 * Adds an offSeasonTicket record to the database with the
	 * provided data.  
	 */
	public static void addOffSeasonTicket(String productCode, String seasonStartDate, 
			String seasonEndDate, String depAirportCode, String arrAirportCode, 
			String depTime, String arrTime,	String flightNo, String flightClass, 
			String aircraftType, double rebate) { 
		
		addStandardTicket(productCode, depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType);
	
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductQuery = "Select * from Products where ProductCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);

			rs = ps.executeQuery();
			
			int productID = 0;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("If this happens, there is an error in addOffSeasonTickets. This should not happen");
			}//	if(rs.next()) {
			
			String insertTicketQuery = "insert into OffSeasonTickets (ProductID, SeasonStartDate, SeasonEndDate, Rebate) values (?,?,?,?)";
			ps = conn.prepareStatement(insertTicketQuery);
			ps.setInt(1, productID);
			ps.setString(2, seasonStartDate);
			ps.setString(3, seasonEndDate);
			ps.setDouble(4, rebate);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//	public static void addOffSeasonTicket(String productCode, String seasonStartDate, 
	 
	 /** Adds an awardsTicket record to the database with the
	 * provided data.  
	 */
	public static void addAwardsTicket(String productCode,String depAirportCode, 
			String arrAirportCode, String depTime, String arrTime, 
			String flightNo, String flightClass, 
			String aircraftType, double pointsPerMile) { 
		
		addStandardTicket(productCode, depAirportCode, arrAirportCode, depTime, arrTime, flightNo, flightClass, aircraftType);
	
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductQuery = "Select * from Products where ProductCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);

			rs = ps.executeQuery();
			
			int productID = 0;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("If this happens, there is an error in addAwardTickets. This should not happen");
			}//	if(rs.next()) {
			
			String insertTicketQuery = "insert into AwardTickets (ProductID, PointsPerMile) values (?,?)";
			ps = conn.prepareStatement(insertTicketQuery);
			ps.setInt(1, productID);
			ps.setDouble(2, pointsPerMile);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	} //	public static void addAwardsTicket(String productCode,String depAirportCode, 
	
	/**
	 * Adds a CheckedBaggage record to the database with the
	 * provided data.  
	 */
	public static void addCheckedBaggage(String productCode, String ticketCode) { 
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductQuery = "Select * from Products where ProductCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				throw new RuntimeException("Product with given productCode was already found in the database.");
			}//	if(rs.next()) {
		
			rs.close();
			ps.close();
		
		
			String insertProductQuery = "Insert into Products (ProductCode, ProductType) values (?,?);";
			ps = conn.prepareStatement(insertProductQuery);
			ps.setString(1, productCode);
			ps.setString(2, "SC");
			ps.executeUpdate();

			ps.close();
						
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			
			int productID = 0;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("If this happens, there is an error in addCheckedBaggage. This should not happen");
			}//	if(rs.next()) {
			
			String selectTicketQuery = "Select * from Products where ProductCode like ?;";
			
			ps = conn.prepareStatement(selectTicketQuery);
			ps.setString(1, ticketCode);
			rs = ps.executeQuery();
			
			int ticketID = 0;
			
			if(rs.next()) {
				ticketID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
			}//	if(rs.next()) {
			
			String insertBaggageQuery = "insert into CheckedBaggage (ProductID, TicketID) values (?,?)";
			ps = conn.prepareStatement(insertBaggageQuery);
			ps.setInt(1, productID);
			ps.setInt(2, ticketID);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addCheckedBaggage(String productCode, String ticketCode) { 

	/**
	 * Adds a Insurance record to the database with the
	 * provided data.  
	 */
	public static void addInsurance(String productCode, String productName, 
			String ageClass, double costPerMile) {	
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductQuery = "Select * from Products where ProductCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				throw new RuntimeException("Product with given productCode was already found in the database.");
			}//	if(rs.next()) {
		
			rs.close();
			ps.close();
		
		String insertProductQuery = "Insert into Products (ProductCode, ProductType) values (?,?);";

			ps = conn.prepareStatement(insertProductQuery);
			ps.setString(1, productCode);
			ps.setString(2, "SI");
			ps.executeUpdate();

			ps.close();
						
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			
			int productID = 0;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("If this happens, there is an error in addInsurance. This should not happen");
			}//	if(rs.next()) {
			
			String insertInsuranceQuery = "insert into Insurances (ProductID, InsuranceName, InsuranceCost, InsuranceAge) values (?,?,?,?)";
			ps = conn.prepareStatement(insertInsuranceQuery);
			ps.setInt(1, productID);
			ps.setString(2, productName);
			ps.setDouble(3, costPerMile);
			ps.setString(4, ageClass);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//public static void addInsurance(String productCode, String productName
	
	/**
	 * Adds a SpecialAssistance record to the database with the
	 * provided data.  
	 */
	public static void addSpecialAssistance(String productCode, String assistanceType) { 
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductQuery = "Select * from Products where ProductCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				throw new RuntimeException("Product with given productCode was already found in the database.");
			}//	if(rs.next()) {
		
			rs.close();
			ps.close();
			
			
			String insertProductQuery = "Insert into Products (ProductCode, ProductType) values (?,?);";
		
			ps = conn.prepareStatement(insertProductQuery);
			ps.setString(1, productCode);
			ps.setString(2, "SA");
			ps.executeUpdate();

			ps.close();
						
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			
			int productID = 0;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("If this happens, there is an error in addSpecialAssistance. This should not happen");
			}//	if(rs.next()) {
			
			String insertInsuranceQuery = "insert into SpecialAssistances (ProductID, SpecialAssistanceType) values (?,?)";
			ps = conn.prepareStatement(insertInsuranceQuery);
			ps.setInt(1, productID);
			ps.setString(2, assistanceType);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//public static void addSpecialAssistance(String productCode, String assistanceType) { 

	/**
	 * Adds a refreshment record to the database with the
	 * provided data.  
	 */
	public static void addRefreshment(String productCode, String name, double cost) { 
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductQuery = "Select * from Products where ProductCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				throw new RuntimeException("Product with given productCode was already found in the database.");
			}//	if(rs.next()) {

			rs.close();
			ps.close();
			
			
			String insertProductQuery = "Insert into Products (ProductCode, ProductType) values (?,?);";
			
			ps = conn.prepareStatement(insertProductQuery);
			ps.setString(1, productCode);
			ps.setString(2, "SR");
			ps.executeUpdate();

			ps.close();
			
			ps = conn.prepareStatement(selectProductQuery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			
			int productID = 0;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("If this happens, there is an error in addRefreshment. This should not happen");
			}//	if(rs.next()) {
			
			String insertRefreshmentQuery = "insert into Refreshments (ProductID, RefreshmentName, RefreshmentCost) values (?,?,?)";
			ps = conn.prepareStatement(insertRefreshmentQuery);
			ps.setInt(1, productID);
			ps.setString(2, name);
			ps.setDouble(3, cost);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addRefreshment(String productCode, String name, double cost) { 
	
	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() { 
		
		//Delete from InvoiceProducts
		//Delete from TicketPassengers
		//Delete from Invoices
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		
		String deleteInvoiceProducts = "Delete from InvoiceProducts";
		String deleteTicketPassengers = "Delete from TicketPassengers";
		String deleteInvoices = "Delete from Invoices";
		
		try {	
			ps = conn.prepareStatement(deleteInvoiceProducts);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteTicketPassengers);
			ps.executeUpdate();
			ps.close();
			
			ps = conn.prepareStatement(deleteInvoices);
			ps.executeUpdate();
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void removeAllInvoices() { 
	
	/**
	 * Adds an invoice record to the database with the given data.  
	 */
	public static void addInvoice(String invoiceCode, String customerCode, 
			String salesPersonCode, String invoiceDate) {
		// when adding Invoices, note that SalesPersonCode of NULL means SalesPerson is online
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectCustomerQuery = "Select * from Customers where CustomerCode like ?;";
		
		try {
			ps = conn.prepareStatement(selectCustomerQuery);
			ps.setString(1, customerCode);
			
			rs = ps.executeQuery();
			
			int customerID = 0;
			
			if(rs.next()) {
				customerID = rs.getInt("CustomerID");
			}//	if(rs.next()) {

			rs.close();
			ps.close();
			
			
			Integer salesPersonID = 0;
			
			/*
			 * if(salesPersonCode.equalsIgnoreCase("online")) {
			 
				salesPersonID = null;
			} else {
				String selectPerson = "Select * from Persons where PersonCode like ?;";
				
				ps = conn.prepareStatement(selectPerson);
				ps.setString(1, salesPersonCode);
				
				rs = ps.executeQuery();
				
				
				if(rs.next()) {
					salesPersonID = rs.getInt("PersonID");
				}//	if(rs.next()) {

				rs.close();
				ps.close();
			}//	if(salesPersonCode.equals("online")) {
			 	*/
			
			if(!salesPersonCode.trim().equalsIgnoreCase("online")) {
				
				String selectPerson = "Select * from Persons where PersonCode like ?;";
				
				ps = conn.prepareStatement(selectPerson);
				ps.setString(1, salesPersonCode);
				
				rs = ps.executeQuery();
				
				
				if(rs.next()) {
					salesPersonID = rs.getInt("PersonID");
				}//	if(rs.next()) {

				rs.close();
				ps.close();
			}//	if(salesPersonCode.equals("online")) {
	
			String insertInvoiceQuery = "insert into Invoices (CustomerID, InvoiceCode, InvoiceDate, SalesPersonID) values (?,?,?,?)";
			ps = conn.prepareStatement(insertInvoiceQuery);
			ps.setInt(1, customerID);
			ps.setString(2, invoiceCode);
			ps.setString(3, invoiceDate);

			
			if(salesPersonCode.equalsIgnoreCase("online")) {
				ps.setNull(4, Types.INTEGER);
			} else {
				ps.setInt(4, salesPersonID);
			}//	if(salesPersonID == null) {
			
			ps.executeUpdate();
			
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
		
		
		
	}//	public static void addInvoice(String invoiceCode, String customerCode, 
	
	/**
	 * Adds a particular Ticket (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * additional details
	 */
	public static void addTicketToInvoice(String invoiceCode, String productCode, 
			String travelDate, String ticketNote) { 
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String productIDquery = "Select ProductID from Products where ProductCode like ?;";
		String invoiceIDquery = "Select InvoiceID from Invoices where InvoiceCode like ?;";
		
		try {
			ps = conn.prepareStatement(productIDquery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			int productID;
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Product associated with productCode " + productCode + " not found in database");
			}//	if(rs.next()) {

			
			ps = conn.prepareStatement(invoiceIDquery);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			int invoiceID;
			
			
			if(rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Invoice associated with invoiceCode " + invoiceCode + " not found in database");
			}//	if(rs.next()) {
		
			
			String insertInvoiceProductsQuery = "insert into InvoiceProducts (ProductID, InvoiceID, TicketNote, TravelDate) values (?,?,?,?)";
			ps = conn.prepareStatement(insertInvoiceProductsQuery);
			ps.setInt(1, productID);
			ps.setInt(2, invoiceID);
			ps.setString(3, ticketNote);
			ps.setString(4, travelDate);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addTicketToInvoice(String invoiceCode, String productCode, 
	
	/**
	 * Adds a Passenger information to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> 
	 */
	public static void addPassengerInformation(String invoiceCode, String productCode, 
			String personCode, 
			String identity, int age, String nationality, String seat){ 
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String productIDquery = "Select ProductID from Products where ProductCode like ?;";
		String invoiceIDquery = "Select InvoiceID from Invoices where InvoiceCode like ?;";
		
		try {
			ps = conn.prepareStatement(productIDquery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			int productID;
			
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Product associated with productCode " + productCode + " not found in database");
			}//	if(rs.next()) {

			
			ps = conn.prepareStatement(invoiceIDquery);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			int invoiceID;
			
			
			if(rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Invoice associated with invoiceCode " + invoiceCode + " not found in database");
			}//	if(rs.next()) {
		
			String personIDquery = "Select * from Persons where PersonCode like ?;";
			
			ps = conn.prepareStatement(personIDquery);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			int personID;
			
			
			if(rs.next()) {
				personID = rs.getInt("PersonID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Person associated with personCode " + personCode + " not found in database");
			}//	if(rs.next()) {
			
			String insertTicketPassengersQuery = "insert into TicketPassengers (TicketID, PersonID, SeatNo, "
					+ "PersonAge, InvoiceID, Nationality, IdentificationNo) values (?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(insertTicketPassengersQuery);
			ps.setInt(1, productID);
			ps.setInt(2, personID);
			ps.setString(3, seat);
			ps.setInt(4, age);
			ps.setInt(5, invoiceID);
			ps.setString(6, nationality);
			ps.setString(7, identity);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//	public static void addPassengerInformation(String invoiceCode, String productCode, 
	
	/**
	 * Adds an Insurance Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity and associated ticket information
	 */
	public static void addInsuranceToInvoice(String invoiceCode, String productCode, 
			int quantity, String ticketCode) { 
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String productIDquery = "Select ProductID from Products where ProductCode like ?;";
		String invoiceIDquery = "Select InvoiceID from Invoices where InvoiceCode like ?;";
		
		try {
			ps = conn.prepareStatement(productIDquery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			int productID;
			
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Product associated with productCode " + productCode + " not found in database");
			}//	if(rs.next()) {

			
			ps = conn.prepareStatement(invoiceIDquery);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			int invoiceID;
			
			
			if(rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Invoice associated with invoiceCode " + invoiceCode + " not found in database");
			}//	if(rs.next()) {
		
			ps = conn.prepareStatement(productIDquery);
			ps.setString(1, ticketCode);
			rs = ps.executeQuery();
			int ticketID;
			
			
			if(rs.next()) {
				ticketID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Ticket associated with productCode " + ticketCode + " not found in database");
			}//	if(rs.next()) {
			
			String insertInvoiceProductsQuery = "insert into InvoiceProducts (ProductID, InvoiceID, NumUnits, AssociatedTicketID) values (?,?,?,?)";
			ps = conn.prepareStatement(insertInvoiceProductsQuery);
			ps.setInt(1, productID);
			ps.setInt(2, invoiceID);
			ps.setInt(3, quantity);
			ps.setInt(4, ticketID);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addInsuranceToInvoice(String invoiceCode, String productCode, 

	/**
	 * Adds a CheckedBaggage Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addCheckedBaggageToInvoice(String invoiceCode, String productCode, 
			int quantity) { 		
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String productIDquery = "Select ProductID from Products where ProductCode like ?;";
		String invoiceIDquery = "Select InvoiceID from Invoices where InvoiceCode like ?;";

		try {
			ps = conn.prepareStatement(productIDquery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			int productID;
			
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Product associated with productCode " + productCode + " not found in database");
			}//	if(rs.next()) {

			
			ps = conn.prepareStatement(invoiceIDquery);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			int invoiceID;
			
			
			if(rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Invoice associated with invoiceCode " + invoiceCode + " not found in database");
			}//	if(rs.next()) {

			
			
			String insertInvoiceProductsQuery = "insert into InvoiceProducts (ProductID, InvoiceID, NumUnits) values (?,?,?)";
			ps = conn.prepareStatement(insertInvoiceProductsQuery);
			ps.setInt(1, productID);
			ps.setInt(2, invoiceID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//public static void addCheckedBaggageToInvoice
		
	/**
	 * Adds a SpecialAssistance Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addSpecialAssistanceToInvoice(String invoiceCode, String productCode, 
			String personCode) { 
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String productIDquery = "Select ProductID from Products where ProductCode like ?;";
		String invoiceIDquery = "Select InvoiceID from Invoices where InvoiceCode like ?;";
		String personIDquery = "Select PersonID from Persons where PersonCode like ?;";

		
		try {
			ps = conn.prepareStatement(productIDquery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			int productID;
			
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Product associated with productCode " + productCode + " not found in database");
			}//	if(rs.next()) {

			
			ps = conn.prepareStatement(invoiceIDquery);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			int invoiceID;
			
			
			if(rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Invoice associated with invoiceCode " + invoiceCode + " not found in database");
			}//	if(rs.next()) {
		
			ps = conn.prepareStatement(personIDquery);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			int personID;
			
			
			if(rs.next()) {
				personID = rs.getInt("PersonID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Person associated with personCode " + personCode + " not found in database");
			}//	if(rs.next()) {
			
			String insertInvoiceProductsQuery = "insert into InvoiceProducts (ProductID, InvoiceID, AssociatedPersonID) values (?,?,?)";
			ps = conn.prepareStatement(insertInvoiceProductsQuery);
			ps.setInt(1, productID);
			ps.setInt(2, invoiceID);
			ps.setInt(3, personID);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
	}//	public static void addSpecialAssistanceToInvoice(String invoiceCode, String productCode, 
	
	/**
	 * Adds a Refreshment service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addRefreshmentToInvoice(String invoiceCode, 
			String productCode, int quantity) { 
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String productIDquery = "Select ProductID from Products where ProductCode like ?;";
		String invoiceIDquery = "Select InvoiceID from Invoices where InvoiceCode like ?;";

		try {
			ps = conn.prepareStatement(productIDquery);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			int productID;
			
			
			if(rs.next()) {
				productID = rs.getInt("ProductID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Product associated with productCode " + productCode + " not found in database");
			}//	if(rs.next()) {

			
			ps = conn.prepareStatement(invoiceIDquery);
			ps.setString(1, invoiceCode);
			rs = ps.executeQuery();
			int invoiceID;
			
			
			if(rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
				rs.close();
				ps.close();
			} else {
				throw new RuntimeException("Invoice associated with invoiceCode " + invoiceCode + " not found in database");
			}//	if(rs.next()) {
		
			String insertInvoiceProductsQuery = "insert into InvoiceProducts (ProductID, InvoiceID, NumUnits) values (?,?,?)";
			ps = conn.prepareStatement(insertInvoiceProductsQuery);
			ps.setInt(1, productID);
			ps.setInt(2, invoiceID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
		
	}//	public static void addRefreshmentToInvoice(String invoiceCode, 
}//public class InvoiceData {
