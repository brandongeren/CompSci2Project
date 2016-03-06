package unl.cse.assignments;

/* Phase-I */
import com.airamerica.*;
import com.airamerica.utils.ConnectionFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

// Include imports for XML/JSON libraries if needed
import com.thoughtworks.xstream.XStream;

public class DataConverter {
	
	ArrayList<Person> persons;
	ArrayList<Airport> airports;
	ArrayList<Customer> customers;
	ArrayList<Product> products;
	SortedList<Invoice> invoices;
	
	public DataConverter() {
		this.persons = getAllPersons();
		this.airports = getAllAirports();
		this.customers = getAllCustomers();
		this.products = getAllProducts();
		this.invoices = getAllInvoices();
	}//DataConverter constructor

	public ArrayList<Person> getPersons() {
		return persons;
	}//	public ArrayList<Person> getPersons() {

	public ArrayList<Airport> getAirports() {
		return airports;
	}//	public ArrayList<Airport> getAirports() {

	public ArrayList<Customer> getCustomers() {
		return customers;
	}//	public ArrayList<Customer> getCustomers() {

	public ArrayList<Product> getProducts() {
		return products;
	}//	public ArrayList<Product> getProducts() {

	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}//	public void setPersons(ArrayList<Person> persons) {

	public void setAirports(ArrayList<Airport> airports) {
		this.airports = airports;
	}//	public void setAirports(ArrayList<Airport> airports) {

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}//	public void setCustomers(ArrayList<Customer> customers) {

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}//	public void setProducts(ArrayList<Product> products) {

	public SortedList<Invoice> getInvoices() {
		return invoices;
	}//	public SortedList<Invoice> getInvoices() {

	public void setInvoices(SortedList<Invoice> invoices) {
		this.invoices = invoices;
	}//	public void setInvoices(SortedList<Invoice> invoices) {
	
	public static void main(String args[]) {

		personsConverter();
		airportConverter();
		customerConverter();
		productsConverter();
		
		
	}//public static void main(String args[]) 	
	
	public static ArrayList<Person> getAllPersons() {
		//this ArrayList holds all the persons
		ArrayList<Person> persons = new ArrayList<Person>();
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectPersonQuery = "select * from Persons join Addresses on Addresses.AddressID = Persons.AddressID "
				+ "join AddressStates on Addresses.AddressID = AddressStates.AddressID "
				+ "join States on  States.StateID = AddressStates.StateID "
				+ "join StateCountries on States.StateID = StateCountries.StateID "
				+ "join Countries on Countries.CountryID = StateCountries.CountryID;";
		
		try {
		
			ps = conn.prepareStatement(selectPersonQuery);
			rs = ps.executeQuery();
			
			//initialize a bunch of variables
			/*
			 * String airportCode = null;
			 * String name = null;
			Address address = null;
			Coordinate coordinate = null;
			double passengerFacilityFee = 0.0;
			 */
			
			String personCode = "";
			String firstName = "";
			String lastName = "";
			Address address = null;
			String phoneNumber = "";
			
			
			
			while(rs.next()) {
				firstName = rs.getString("FirstName");
				lastName = rs.getString("LastName");
				personCode = rs.getString("PersonCode");
				phoneNumber = rs.getString("PhoneNo");
				if(phoneNumber == null) {
					phoneNumber = "";
				}//if(phoneNumber == null) {
				
				//get all the data to make an address
				String street = rs.getString("Street");
				String city = rs.getString("City");
				String state = rs.getString("StateName");
				String zip = rs.getString("Zip");
				String country = rs.getString("CountryName");
				
				
				address = new Address(street, city, state, zip, country);

				Person person = new Person(personCode, firstName, lastName, address, phoneNumber);
				
				PreparedStatement ps2;
				ResultSet rs2;
				
				String selectEmails = "Select * from Emails where PersonID like ?;";
				ps2 = conn.prepareStatement(selectEmails);
				ps2.setInt(1, rs.getInt("PersonID"));
				rs2 = ps2.executeQuery();
				
				while(rs2.next()) {
					person.addEmail(rs2.getString("Email"));
				}//	while(rs2.next()) {
				
				rs2.close();
				ps2.close();
			
			
				
				persons.add(person);
			}//	while(rs.next()) {
	
			rs.close();
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch
			
		return persons;
				
	}//public ArrayList<Person> getAllPersons() {
	
	public static void personsConverter() {
		

		//create an xstream object and a printwriter object
				//make a foreach loop to iterate over every person in the arraylist
					//feed the xstream elements of the arraylist of persons
					//use the printwriter to output the data to an xml file
					//the loop will repeat this for every object
		
				
		ArrayList<Person> persons = getAllPersons();
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("data/Persons.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}//try+catch
		
		XStream xstream = new XStream();
		xstream.alias("person", Person.class);
		
		pw.print("<persons>\n");
		for(Person currentPerson: persons) {
			pw.print(xstream.toXML(currentPerson) + "\n");
		}//for(Person currentPerson: persons)
		pw.print("</persons>" + "\n");
		pw.close();
		
		System.out.println("XML file generated at data/Persons.xml");
	
	}//public static void personsConverter()
	
	public static ArrayList<Airport> getAllAirports() {
		//this ArrayList holds all the airports from Airports.dat
		ArrayList<Airport> airports = new ArrayList<Airport>();
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectAirportQuery = "select * from Airports join Addresses on Addresses.AddressID = Airports.AddressID "
				+ "join Coordinates on Coordinates.CoordinateID = Airports.CoordinateID "
				+ "join AddressStates on Addresses.AddressID = AddressStates.AddressID "
				+ "join States on  States.StateID = AddressStates.StateID "
				+ "join StateCountries on States.StateID = StateCountries.StateID "
				+ "join Countries on Countries.CountryID = StateCountries.CountryID";
		
		try {
		
			ps = conn.prepareStatement(selectAirportQuery);
			rs = ps.executeQuery();
			
			//initialize a bunch of variables
			String airportCode = "";
			String name = "";
			Address address = new Address("","","","","");
			Coordinate coordinate = new Coordinate(0,0,0,0);
			double passengerFacilityFee = 0.0;
			
			while(rs.next()) {
				name = rs.getString("AirportName");
				airportCode = rs.getString("AirportCode");
				
				
				
				//get all the data to make an address
				String street = rs.getString("Street");
				String city = rs.getString("City");
				String state = rs.getString("StateName");
				String zip = rs.getString("Zip");
				String country = rs.getString("CountryName");
				
				
				address = new Address(street, city, state, zip, country);
				
				//get all the data to make a Coordinate class
				int latDegs = rs.getInt("LatDegs");
				int latMins = rs.getInt("LatMins");
				int lonDegs = rs.getInt("LonDegs");
				int lonMins = rs.getInt("LonMins");
				
				coordinate = new Coordinate(latDegs, latMins, lonDegs, lonMins);
				
				airports.add(new Airport(airportCode, name, address, coordinate, passengerFacilityFee));
			}//	while(rs.next()) {
	
			rs.close();
			ps.close();
			
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}//trycatch

		return airports;
	}//	public static ArrayList<Airport> getAllAirports(String filename) {
	
	public static void airportConverter() {
							
			ArrayList<Airport> airports = getAllAirports();
		
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(new File("data/Airports.xml"));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}//try+catch
				
				XStream xstream = new XStream();
				xstream.alias("airport", Airport.class);
				
				pw.print("<airports>\n");
				for(Airport currentAirport: airports) {
					pw.print(xstream.toXML(currentAirport) + "\n");
				}//for(Airport currentAirport: airports)
				pw.print("</airports>" + "\n");
				pw.close();
				
				System.out.println("XML file generated at data/Airports.xml");
	}//public static void airportConverter()
	
	public static ArrayList<Product> getAllProducts() {

	    ArrayList<Product> products = new ArrayList<Product>();
	    
	    Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectProductsQuery = "Select * from Products;";
		
		try {
		
			ps = conn.prepareStatement(selectProductsQuery);
			rs = ps.executeQuery();
			
			//initialize a bunch of variables
			String productType = "";
			String productCode = "";
			
			
			while(rs.next()) {
				productType = rs.getString("ProductType");
				productCode = rs.getString("ProductCode");
				
				Product currentProduct = null;

		    	//this if statement is true if the product is a ticket
		    	if (productType.substring(0,1).equals("T")){
		    		String selectTicketQuery = "select * from Tickets join Products on Tickets.ProductID = Products.ProductID where Products.ProductCode like ?";
		    		PreparedStatement ps2;
		    		ResultSet rs2;
		    		
		    		ps2 = conn.prepareStatement(selectTicketQuery);
		    		ps2.setString(1, productCode);
		    		rs2 = ps2.executeQuery();
		    		
		    		String arrTime = "";
		    		String depTime = "";
		    		String flightNo = "";
		    		String flightClass = "";
		    		String aircraftType = "";
		    		
		    		if(rs2.next()) {
		    			arrTime = rs2.getString("ArrTime");
		    			depTime = rs2.getString("DepTime");
		    			flightNo = rs2.getString("FlightNo");
		    			flightClass = rs2.getString("FlightClass");
		    			aircraftType = rs2.getString("AircraftType");
		    			
		    		}//if(rs2.next()) {

		    		String depAirportCode = "";
		    		String arrAirportCode = "";
		    		
		    		String selectDepAirportCodeQuery = "Select * from Airports join Tickets on Tickets.DepAirportID = Airports.AirportID "
		    				+ "join Products on Products.ProductID = Tickets.ProductID where Products.ProductCode like ?;";
		    		
		    		ps2 = conn.prepareStatement(selectDepAirportCodeQuery);
		    		ps2.setString(1, productCode);
		    		rs2 = ps2.executeQuery();
		    		
		    		if(rs2.next()) {
		    			depAirportCode = rs2.getString("AirportCode");
		    		}//if(rs2.next()) {
		   
		    		rs2.close();
		    		ps2.close();
		    		
		    		String selectArrAirportCodeQuery = "Select * from Airports join Tickets on Tickets.ArrAirportID = Airports.AirportID "
		    				+ "join Products on Products.ProductID = Tickets.ProductID where Products.ProductCode like ?;";
		    		
		    		ps2 = conn.prepareStatement(selectArrAirportCodeQuery);
		    		ps2.setString(1, productCode);
		    		rs2 = ps2.executeQuery();
		    		
		    		if(rs2.next()) {
		    			arrAirportCode = rs2.getString("AirportCode");
		    		}//if(rs2.next()) {
		   
		    		rs2.close();
		    		ps2.close();
		    		
		    		//Standard Ticket generator
		    		if (productType.substring(1).equals("S")){
		    			
		    				    		
		    			//Create the standard ticket
		    			currentProduct = new StandardTickets(productCode, productType, depAirportCode, arrAirportCode, flightNo,
		    								flightClass, aircraftType, depTime, arrTime);
		    		}//Standard Ticket case
		    		
		    		//Award Ticket generator
		    		else if(productType.substring(1).equals("A")){
		    		
		    			String selectAwardTickets = "Select * from AwardTickets join Products on Products.ProductID = AwardTickets.ProductID "
		    					+ "where Products.ProductCode like ?;";
		    			
		    			ps2 = conn.prepareStatement(selectAwardTickets);
		    			ps2.setString(1, productCode);
		    			rs2 = ps2.executeQuery();
		    			
		    			int pointsPerMile = -1;
		    			
		    			if(rs2.next()) {
		    				pointsPerMile = rs2.getInt("PointsPerMile");
		    			 }//if(rs2.next()) {

		    			rs2.close();
		    			ps2.close();
		    			
		    			//create the award ticket
		    			currentProduct = new AwardTickets(productCode, productType, depAirportCode, arrAirportCode, flightNo,
		    							flightClass, aircraftType, depTime, arrTime, pointsPerMile);
		    		}//Award Ticket Case
		    		
		    		//OffSeason Ticket generator (assume that all tickets which are not Award or Standard are OffSeason)
		    		else {
		    			
		    			
		    			String selectOSTickets = "Select * from OffSeasonTickets join Products on Products.ProductID = OffSeasonTickets.ProductID "
		    					+ "where Products.ProductCode like ?;";
		    			
		    			ps2 = conn.prepareStatement(selectOSTickets);
		    			ps2.setString(1, productCode);
		    			rs2 = ps2.executeQuery();
		    			
		    			String seasonStartDate = "";
		    			String seasonEndDate = "";
		    			double rebate = -0.01;
		    			
		    			if(rs2.next()) {
		    				seasonStartDate = rs2.getString("SeasonStartDate");
			    			seasonEndDate = rs2.getString("SeasonEndDate");
			    			rebate = rs2.getDouble("Rebate");
			    		}//if(rs2.next()) {
		    			
		    			rs2.close();
		    			ps2.close();
		    			
		    			//create the OffSeason ticket
		    			currentProduct = new OffSeasonTickets(productCode, "TO", depAirportCode, arrAirportCode, flightNo, 
		    					flightClass, aircraftType, depTime, arrTime, seasonStartDate, seasonEndDate, rebate);
		    		}//OffSeason Ticket case
		    		
		    	}//Tickets case
		    	
		    	//Services generators (assume that all Products which are not tickets are services)
		    	else{
		    		PreparedStatement ps2;
		    		ResultSet rs2;
		    		
		    		//Checked Baggage generator
		    		if (productType.substring(1).equals("C")){

			    		String selectCB = "Select * from CheckedBaggage join Products on Products.ProductID = CheckedBaggage.ProductID "
		    					+ "where Products.ProductCode like ?;";
		    			
		    			ps2 = conn.prepareStatement(selectCB);
		    			ps2.setString(1, productCode);
		    			rs2 = ps2.executeQuery();
		    			
		    			int ticketID = -1;
		    			
		    			if(rs2.next()) {
		    				ticketID = rs2.getInt("TicketID");
			    		}//if(rs2.next()) {
		    			
		    			rs2.close();
		    			ps2.close();
		    			
		    			String selectTicketCode = "Select * from Products where ProductID like ?";
		    			ps2 = conn.prepareStatement(selectTicketCode);
		    			ps2.setInt(1, ticketID);
			    		rs2 = ps2.executeQuery();
			    		
			    		String ticketCode = "";
			    		
			    		if(rs2.next()) {
			    			ticketCode = rs2.getString("ProductCode");
			    		}
		    					    			
		    			//create the checked baggage
		    			currentProduct = new CheckedBaggage(productCode, productType, ticketCode);
		    		}//Checked Baggage case
		    		
		    		//Insurance generator
		    		else if (productType.substring(1).equals("I")){
		    			String selectInsurance = "Select * from Insurances join Products on Products.ProductID = Insurances.ProductID "
		    					+ "where Products.ProductCode like ?;";
		    			
		    			ps2 = conn.prepareStatement(selectInsurance);
		    			ps2.setString(1, productCode);
		    			rs2 = ps2.executeQuery();
		    			
		    			String name = "";
		    			String ageClass = "";
		    			double costPerMile = -0.01;
		    			
		    			if(rs2.next()) {
		    				name = rs2.getString("InsuranceName");
		    				ageClass = rs2.getString("InsuranceAge");
		    				costPerMile = rs2.getDouble("InsuranceCost");
			    		}//if(rs2.next()) {
		    			
		    			//create the insurance
		    			currentProduct = new Insurance(productCode, productType, name, ageClass, costPerMile);

		    		}//Insurance case
		    		
		    		//Special Assistance generator
		    		else if (productType.substring(1).equals("S")){
		    			String selectSpecialAssistance = "Select * from SpecialAssistances "
		    					+ "join Products on Products.ProductID = SpecialAssistances.ProductID "
		    					+ "where Products.ProductCode like ?;";
		    			
		    			ps2 = conn.prepareStatement(selectSpecialAssistance);
		    			ps2.setString(1, productCode);
		    			rs2 = ps2.executeQuery();
		    			
		    			String typeOfService = "";
		    			
		    			
		    			if(rs2.next()) {
		    				typeOfService = rs2.getString("SpecialAssistanceType");
			    		}//if(rs2.next()) {		  
		    			
		    			
		    			//create the special assistance
		    			currentProduct = new SpecialAssistance(productCode, productType, typeOfService);
		    		}//Special Assistance case
		    		
		    		//Refreshment generator (Assume that all services which aren't SpecialAssistance, CheckedBaggage, or Insurance are Refreshment)
		    		else{
		    			String selectRefreshment = "Select * from Refreshments join Products on Products.ProductID = Refreshments.ProductID "
		    					+ "where Products.ProductCode like ?;";
		    			
		    			ps2 = conn.prepareStatement(selectRefreshment);
		    			ps2.setString(1, productCode);
		    			rs2 = ps2.executeQuery();
		    			
		    			String name = "";
		    			double cost = -0.01;
		    			
		    			if(rs2.next()) {
		    				name = rs2.getString("RefreshmentName");
		    				cost = rs2.getDouble("RefreshmentCost");
			    		}//if(rs2.next()) {
		    			
		    			//create the refreshment
		    			currentProduct = new Refreshment(productCode, productType, name, cost);
		    		}//Refreshment Case
		    	}//services case
		    	
		    	//add whatever product was created to the ArrayList of products
		    	products.add(currentProduct);
			}//	while(rs.next()) {

			rs.close();
			ps.close();
			
			conn.close();
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}//trycatch
		
		
	    return products;

		
	}//	public static ArrayList<Product> getAllProducts(String filename) {

	public static void productsConverter() {
				
		ArrayList<Product> products = getAllProducts();
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("data/Products.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}//try+catch
		
		XStream xstream = new XStream();
		xstream.alias("product", Product.class);
		
		pw.print("<products>\n");
		for(Product currentProduct: products) {
			
			//pw.print(xstream.toXML(currentProduct) + "\n");
		}//for(Product currentProduct: products)
		pw.print("</products>" + "\n");
		pw.close();
		
		System.out.println("XML file generated at data/Proudcts.xml");
		
	}//public static void productsConverter()

	public static ArrayList<Customer> getAllCustomers() {
		//This ArrayList holds all the customers
		ArrayList<Customer> customers = new ArrayList<Customer>();
			
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectCustomerQuery = "Select * from Customers;";
		
		try {
		
			
			
			
			
			
			
			ps = conn.prepareStatement(selectCustomerQuery);
			rs = ps.executeQuery();
			
			//initialize a bunch of variables
			String type = null;
			String primaryContactCode = null;
			Person primaryContact = null;
			String customerCode = null;
			String name = null;
			int airlineMiles = -1;
			
			while(rs.next()) {
				type = rs.getString("CustomerType");
				customerCode = rs.getString("CustomerCode");
				name = rs.getString("CompanyName");
				airlineMiles = rs.getInt("AirlineMiles");
				
				String personQuery = "select * from Customers join Persons on Customers.PersonID = Persons.PersonID where Customers.CustomerCode = ?";
				PreparedStatement ps2;
				ResultSet rs2;
				
				ps2 = conn.prepareStatement(personQuery);
				ps2.setString(1, customerCode);
				rs2 = ps2.executeQuery();
				
				if(rs2.next()) {
					primaryContactCode = rs2.getString("PersonCode");					
				}//	if(rs.next()) {
				
				rs2.close();
				ps2.close();
				
				//check all of the persons for their personCodes. if it's the right one, that's this customer's primary contact
				for(Person person: getAllPersons()) {
					if(person.getPersonCode().equals(primaryContactCode)) {
						primaryContact = person;
					}//if(person.getPersonCode().equals(primaryContactCode))
				}//	for(Person person: getAllPersons("data/Persons.dat"))
				
				if(type.equals("G")) {
					Customer customer = new General(customerCode, primaryContact, name, airlineMiles);
					customers.add(customer);
				}
				
				if(type.equals("V")) {
					Customer customer = new Government(customerCode, primaryContact, name, airlineMiles);
					customers.add(customer);
				}
				
				if(type.equals("C")) {
					Customer customer = new Corporate(customerCode, primaryContact, name, airlineMiles);
					customers.add(customer);
				}
			}//	while(rs.next()) {

			rs.close();
			ps.close();
			
			conn.close();
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}//trycatch
		
		return customers;
	}//	public static ArrayList<Customer> getAllCustomers(String filename) {

	public static void customerConverter() {
		
		ArrayList<Customer> customers = getAllCustomers();
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("data/Customers.xml"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}//try+catch
		
		XStream xstream = new XStream();
		xstream.alias("customer", Customer.class);
		
		pw.print("<customers>\n");
		for(Customer currentCustomer: customers) {
			pw.print(xstream.toXML(currentCustomer) + "\n");
		}//Customer currentCustomer: customers)
		pw.print("</customers>" + "\n");
		pw.close();
		
		System.out.println("XML file generated at data/Customers.xml");
		
	}//public static void customerConverter()

	public SortedList<Invoice> getAllInvoices() {

		//default comparator.use the changeComparator method to change away from this comparator
		Comparator<Invoice> byCustomerName = new Comparator<Invoice>() {
			@Override
			public int compare(Invoice a, Invoice b) {
				return 0;
			}//public int compare(Invoice a, Invoice b) {
		};//Comparator<Invoice> bySalesPersonName = new Comparator<Invoice>() {
		
		SortedList<Invoice> invoices = new SortedList<Invoice>(byCustomerName);
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		
		String selectInvoiceQuery = "Select * from Invoices";
		
		try {
		
			ps = conn.prepareStatement(selectInvoiceQuery);
			rs = ps.executeQuery();
		
			while(rs.next()) {
		

				PreparedStatement ps2;
				ResultSet rs2;
				
				String salesPersonCode = "";
				String invoiceCode = "";
				String customerCode = "";
				String invoiceDate = "";
				
				if(rs.getString("SalesPersonID") == null) {
					String selectInvoiceDataQuery = "Select * from Invoices join Customers on Customers.CustomerID = Invoices.CustomerID;";
					
					ps2 = conn.prepareStatement(selectInvoiceDataQuery);
					rs2 = ps2.executeQuery();
					if(rs2.next()) {
						salesPersonCode = "online";
						invoiceCode = rs2.getString("InvoiceCode");
						customerCode = rs2.getString("CustomerCode");
						invoiceDate = rs2.getString("InvoiceDate");	
					}//	if(rs.next()) {


				} else {
					String selectInvoiceDataQuery = "Select * from Invoices join Customers on Customers.CustomerID = Invoices.CustomerID "
							+ "join Persons on Persons.PersonID = Invoices.SalesPersonID;";
					ps2 = conn.prepareStatement(selectInvoiceDataQuery);
					rs2 = ps2.executeQuery();
					if(rs2.next()) {

						invoiceCode = rs2.getString("InvoiceCode");
						customerCode = rs2.getString("CustomerCode");
						salesPersonCode = rs2.getString("PersonCode");
						invoiceDate = rs2.getString("InvoiceDate");	
					}// if(rs.next())
				}//	if(rs.getString("SalesPersonID") == null) {

				rs2.close();
				ps2.close();
					
			
				String selectInvoiceProductForModification = "Select * from InvoiceProducts join Products on Products.ProductID = "
						+ "InvoiceProducts.ProductID where InvoiceProducts.InvoiceID = (select Invoices.InvoiceID "
						+ "from Invoices where InvoiceCode like ?)";
				
				PreparedStatement ps3;
				ResultSet rs3;
				
				ps3 = conn.prepareStatement(selectInvoiceProductForModification);
				ps3.setString(1, rs.getString("InvoiceCode"));
				rs3 = ps3.executeQuery();
				
				ArrayList<Product> productList = new ArrayList<Product>();
				
				if(rs3.next()) {
			
					for(Product product: products) {
						if(product.getProductCode().equals(rs3.getString("ProductCode"))) {
		
						Product modifiedProduct = null;
						
						//remember to use addSeat, addPerson, addAge
						//remember to setTicketNote() OPTIONALLY
						//check the size of the arrayList!
						if(product instanceof Tickets) {
							
							String selectInvoiceProduct = "Select * from InvoiceProducts "
									+ "join Products on InvoiceProducts.ProductID = Products.ProductID "
									+ "where Products.ProductCode like ?;"; 
							
							ps2 = conn.prepareStatement(selectInvoiceProduct);
							ps2.setString(1, product.getProductCode());
							rs2 = ps2.executeQuery();
							
							String travelDate = "";
							String ticketNote = "";
							
							if(rs2.next()) {
								travelDate = rs2.getString("TravelDate");
								ticketNote = rs2.getString("TicketNote");
							}
							
							rs2.close();
							ps2.close();
												
							if(product instanceof StandardTickets) {
								StandardTickets referenceCopy = (StandardTickets) product;
								/*
								 * String productCode, String productType, String depAirportCode, String arrAirportCode,
			String flightNo, String flightClass, String aircraftType, String depTime, String arrTime
								 */
								modifiedProduct = new StandardTickets(referenceCopy.getProductCode(), referenceCopy.getProductType(), 
										referenceCopy.getDepAirportCode(), referenceCopy.getArrAirportCode(), 
										referenceCopy.getFlightNo(), referenceCopy.getFlightClass(), referenceCopy.getAircraftType(),
										referenceCopy.getDepTime(), referenceCopy.getArrTime());
							}//	if(product instanceof StandardTickets) {
		
							
							if(product instanceof OffSeasonTickets) {
								OffSeasonTickets referenceCopy = (OffSeasonTickets) product;
								/*
								 * (String productCode, String productType, String depAirportCode, String arrAirportCode,
			String flightNo, String flightClass, String aircraftType, String depTime, String arrTime, String seasonStartDate,
			String seasonEndDate, double rebate)
								 */
								
								modifiedProduct = new OffSeasonTickets(referenceCopy.getProductCode(), referenceCopy.getProductType(), 
										referenceCopy.getDepAirportCode(), referenceCopy.getArrAirportCode(), 
										referenceCopy.getFlightNo(), referenceCopy.getFlightClass(), referenceCopy.getAircraftType(),
										referenceCopy.getDepTime(), referenceCopy.getArrTime(), referenceCopy.getSeasonStartDate(),
										referenceCopy.getSeasonEndDate(), referenceCopy.getRebate());
							}//	if(product instanceof StandardTickets) {
		
							
							if(product instanceof AwardTickets) {
								AwardTickets referenceCopy = (AwardTickets) product;
								
								modifiedProduct = new AwardTickets(referenceCopy.getProductCode(), referenceCopy.getProductType(), 
										referenceCopy.getDepAirportCode(), referenceCopy.getArrAirportCode(), 
										referenceCopy.getFlightNo(), referenceCopy.getFlightClass(), referenceCopy.getAircraftType(),
										referenceCopy.getDepTime(), referenceCopy.getArrTime(), referenceCopy.getPointsPerMile());
							}//if(product instanceof AwardTickets) 
							
							Tickets modifiedTickets = (Tickets) modifiedProduct;
							
							String seat = "";
							String personCode = "";
							int age = 0;
									
							//grab the data for each passenger

							String selectTicketPassengers = "Select * from TicketPassengers "
									+ "join Persons on TicketPassengers.PersonID = Persons.PersonID "
									+ "where TicketPassengers.InvoiceID = (select Invoices.InvoiceID from Invoices where InvoiceCode like ?) "
									+ "and TicketID = (Select Products.ProductID from Products where ProductCode like ?)";
							
							ps2 = conn.prepareStatement(selectTicketPassengers);
							ps2.setString(1, rs.getString("InvoiceCode"));
							ps2.setString(2, product.getProductCode());
							rs2 = ps2.executeQuery();
					
							while(rs2.next()) {
								seat = rs2.getString("SeatNo");
								personCode = rs2.getString("PersonCode");
								age = rs2.getInt("PersonAge");
							}
														
							for(Person person: persons) {
								if(person.getPersonCode().equals(personCode)) {
									modifiedTickets.addPassenger(person);
									modifiedTickets.addSeat(seat);
									modifiedTickets.addAge(age);
								}
							}
							
							modifiedTickets.setTravelDate(travelDate);
							modifiedTickets.setNumPassengers(modifiedTickets.getSeats().size());
							
							
							//add in the ticket note, if it exists
							if(ticketNote != null) {
								modifiedTickets.setTicketNote(ticketNote);
							}//	if(ticketNote != null) {
		
							//modifiedTickets.addSeat
							//modifiedTickets.addPerson
							//modifiedTickets.addAge
							//modifiedTickets.setTicketNote()						
							
							Product newProduct = modifiedTickets;
							newProduct.incrementNumUnits(modifiedTickets.getNumPassengers());
							modifiedProduct = newProduct;
							
						}//	if(product instanceof Tickets) {
						
						else if(product instanceof CheckedBaggage) {
							CheckedBaggage referenceCopy = (CheckedBaggage) product;
							
							/*
							 * CheckedBaggage(String productCode, String productType, String ticketCode)
							 */
							
							modifiedProduct = new CheckedBaggage(referenceCopy.getProductCode(), referenceCopy.getProductType(),
															referenceCopy.getTicketCode());
							
							String selectInvoiceProduct = "Select * from InvoiceProducts "
									+ "join Products on InvoiceProducts.ProductID = Products.ProductID "
									+ "where Products.ProductCode like ?;"; 
							
							ps2 = conn.prepareStatement(selectInvoiceProduct);
							ps2.setString(1, product.getProductCode());
							rs2 = ps2.executeQuery();
							
							int quantity = 0;
							
							if(rs.next()) {
								quantity = rs2.getInt("NumUnits");
							}
							
							rs2.close();
							ps2.close();

							
							modifiedProduct.incrementNumUnits(quantity);
						}//	else if(product instanceof CheckedBaggage) {
						
						else if(product instanceof Insurance) {
							Insurance referenceCopy = (Insurance) product;
							
							/*
							 * (String productCode, String productType, String name, String ageClass, double costPerMile)
							 */
							
							modifiedProduct = new Insurance(referenceCopy.getProductCode(), referenceCopy.getProductType(),
															referenceCopy.getName(), referenceCopy.getAgeClass(),
															referenceCopy.getCostPerMile());
							
							String selectInvoiceProduct = "Select * from InvoiceProducts "
									+ "join Products on InvoiceProducts.ProductID = Products.ProductID "
									+ "where Products.ProductCode like ?;"; 
							
							ps2 = conn.prepareStatement(selectInvoiceProduct);
							ps2.setString(1, product.getProductCode());
							rs2 = ps2.executeQuery();
							
							int quantity = 0;
							
							if(rs.next()) {
								quantity = rs2.getInt("NumUnits");
							}
							
							rs2.close();
							ps2.close();

							
							modifiedProduct.incrementNumUnits(quantity);
							
							Insurance modifiedInsurance = (Insurance) modifiedProduct;
							
							String ticketCode = "";
							
							String selectTicketCode = "Select * from Products join InvoiceProducts on Products.ProductID = InvoiceProducts.AssociatedTicketID "
									+ "where InvoiceProducts.ProductID = (select Products.ProductID from Products where Products.ProductCode like ?)";
							
							ps2 = conn.prepareStatement(selectTicketCode);
							ps2.setString(1, product.getProductCode());
							rs2 = ps2.executeQuery();
													
							if(rs.next()) {
								ticketCode = rs2.getString("ProductCode");
							}
							
							rs2.close();
							ps2.close();

							
							for(Product ticket: products) {
								if(ticket.getProductCode().equals(ticketCode)) {
									modifiedInsurance.setAssociatedTicket((Tickets) ticket);
									break;
								}// if(ticket.getProductCode().equals(currentSingleProductData.get(2))) {
		
							}//	for(Product ticket: products) {
							
							Product newProduct = modifiedInsurance;
							modifiedProduct = newProduct;
						}//	else if(product instanceof Insurance) {
						
						else if(product instanceof Refreshment) {
							Refreshment referenceCopy = (Refreshment) product;
							/*
							 * (String productCode, String productType, String name, double cost) 
							 */
							modifiedProduct = new Refreshment(referenceCopy.getProductCode(), referenceCopy.getProductType(),
															referenceCopy.getName(), referenceCopy.getRawCost());
							
							String selectInvoiceProduct = "Select * from InvoiceProducts "
									+ "join Products on InvoiceProducts.ProductID = Products.ProductID "
									+ "where Products.ProductCode like ?;"; 
							
							ps2 = conn.prepareStatement(selectInvoiceProduct);
							ps2.setString(1, product.getProductCode());
							rs2 = ps2.executeQuery();
							
							int quantity = 0;
							
							if(rs.next()) {
								quantity = rs2.getInt("NumUnits");
							}
							
							rs2.close();
							ps2.close();

							
							modifiedProduct.incrementNumUnits(quantity);
						}//	else if(product instanceof Refreshments) {
						
						else {
							for(Person person: persons) {
								SpecialAssistance referenceCopy = (SpecialAssistance) product;
								
								/*
								 * (String productCode, String productType, String typeOfService)
								 */
								
								modifiedProduct = new SpecialAssistance(referenceCopy.getProductCode(), referenceCopy.getProductType(),
																		referenceCopy.getTypeOfService());
								
								
								String selectInvoiceProduct = "Select * from InvoiceProducts "
										+ "join Products on InvoiceProducts.ProductID = Products.ProductID "
										+ "where Products.ProductCode like ?;"; 
								
								ps2 = conn.prepareStatement(selectInvoiceProduct);
								ps2.setString(1, product.getProductCode());
								rs2 = ps2.executeQuery();
								
								int quantity = 0;
								
								if(rs.next()) {
									quantity = rs2.getInt("NumUnits");
								}
								
								rs2.close();
								ps2.close();

								
								modifiedProduct.incrementNumUnits(quantity);
																
								String personCode = "";
								
								String selectPersonCode = "Select * from Persons join InvoiceProducts on Persons.PersonID = InvoiceProducts.AssociatedPersonID "
										+ "where InvoiceProducts.ProductID = (select Products.ProductID from Products where Products.ProductCode like ?)";
								
								ps2 = conn.prepareStatement(selectPersonCode);
								ps2.setString(1, product.getProductCode());
								rs2 = ps2.executeQuery();
														
								if(rs.next()) {
									personCode = rs2.getString("PersonCode");
								}
								
								SpecialAssistance modifiedSpecialAssistance = (SpecialAssistance) modifiedProduct;
								
								rs2.close();
								ps2.close();
								if(person.getPersonCode().equals(personCode)) {
									modifiedSpecialAssistance.setPersonServed((Person) person);
									break;
								}// if(ticket.getProductCode().equals(currentSingleProductData.get(2))) {
		
							}//	for(Product ticket: products) {
						}// if it's a special assistance
						productList.add(modifiedProduct);
					}//	if(currentSingleProductData.get(0).equals(product.getProductCode())) {
		
					}//	for(Product product: products) {

				//remove duplicates from productList
				Collection<Object> productListWithoutDuplicates = productList.stream().distinct().collect(Collectors.toList());
				productList = new ArrayList<Product>();
				for(Object element: productListWithoutDuplicates) {
					Product product = null;
					product = (Product) element;
					productList.add(product);
				}//	for(Object element: productListWithoutDuplicates) 
				
			}//	for(ArrayList<String> currentSingleProductData: allProductsData) {
			
			Customer customer = null;
			
			for(Customer currentCustomer: customers) {
				if(currentCustomer.getCustomerCode().equals(customerCode)) {
					customer = currentCustomer;
					break;
				}//	if(currentCustomer.getCustomerCode().equals(customerCode)) {

			}//	for(Customer currentCustomer: customers) {

			if(customer == null) {
				throw new RuntimeException("Customer is null. why? " + invoiceCode + invoiceDate);
			}//	if(customer == null) {

			
			String salespersonName = "";
			
			if(salesPersonCode.equalsIgnoreCase("ONLINE")) {
				salespersonName = "ONLINE, null";
			}//	if(salesPersonCode.equals("ONLINE")) {

			else {
				for(Person person: persons) {
					if(person.getPersonCode().equals(salesPersonCode)) {
						salespersonName = person.getNameLastFirst();
						break;
					}// if(person.getPersonCode().equals(salesPersonCode)) {
				}//for(Person person: persons)
			}//if(salesPersonCode.equalsIgnoreCase("ONLINE")) {
						
			Invoice invoice = new Invoice(invoiceCode, customer, salespersonName, invoiceDate, productList);
			invoices.add(invoice);
			
		}//while(rs.next())
				
				conn.close();
			} catch (SQLException e) {
				System.out.println("SQLException: ");
				e.printStackTrace();
				throw new RuntimeException(e);
			}//trycatch
		return invoices;
		
		//remember to add the person for SpecialAssistance
	}//public static ArrayList<Invoice> getAllInvoices() {

	
}//class DataConverter 
