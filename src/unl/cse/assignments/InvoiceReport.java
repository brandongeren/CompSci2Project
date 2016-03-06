package unl.cse.assignments;

import java.util.ArrayList;
import java.util.Comparator;

import com.airamerica.Customer;
import com.airamerica.General;
import com.airamerica.Government;
import com.airamerica.Invoice;
import com.airamerica.Person;
import com.airamerica.Product;
import com.airamerica.SortedList;
import com.airamerica.Tickets;
import com.airamerica.utils.StandardUtils;

/* Assignment 3,5 and 6 (Project Phase-II,IV and V) */

public class InvoiceReport {
		
	public static DataConverter dataConverter = new DataConverter();
	
	private String generateSummaryReport(SortedList<Invoice> sortedList) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Executive Summary Report\n");
		sb.append("=========================\n");
		sb.append(String.format("%-10s%-50s%-30s%11s%12s%12s%12s%12s\n", "Invoice", 
				"Customer", "Salesperson", "Subtotal", "Fees", "Taxes", "Discount", "Total"));
		double totalSubTotal = 0.0;
		double feesTotal = 0.0;
		double taxTotal = 0.0;
		double discountTotal = 0.0;
		double totalGrandTotal = 0.0;
		
		for(Invoice invoice: sortedList) {
			sb.append(String.format("%-10s%-50s%-30s$%10.2f $%10.2f $%10.2f $%10.2f $%10.2f\n",
					invoice.getInvoiceCode(), invoice.getCustomer().getName() + " " + invoice.getCustomer().getType(), 
					invoice.getSalespersonName(), invoice.getTotalCost(), invoice.getCustomer().getComplianceFee(), 
					invoice.getTaxes(), invoice.getDiscount()*(-1), invoice.getGrandTotal()
					));
			totalSubTotal+=invoice.getTotalCost();
			feesTotal+=invoice.getTotalFees();
			taxTotal+=invoice.getTaxes();
			discountTotal+=invoice.getDiscount();
			totalGrandTotal+=invoice.getGrandTotal();
		}//	for(Invoice invoice: dataConverter.getInvoices()) {

		sb.append("=========================================================================================================================================\n");
		
		sb.append(String.format("%-90s$%10.2f $%10.2f $%10.2f $%10.2f $%10.2f\n", "TOTALS", 
				totalSubTotal, feesTotal, taxTotal, discountTotal, totalGrandTotal));
		return sb.toString();
		//formula: subtotal + fees + taxes - discount = total
		
	}//private String generateSummaryReport()
                     
	private String getTravelSummary(Invoice invoice) {
		StringBuilder sb = new StringBuilder();
		sb.append("FLIGHT INFORMATION\n");
		sb.append("==================================================\n");

		//Add code for generating Travel Information of an Invoice
		
		sb.append(String.format("%-15s%-10s%-8s%-25s%-25s%-15s\n", "Day,Date", "Flight",
				"Class", "DepartureCity and Time", "ArrivalCity and Time", "Aircraft"));
		
		for(Product product: invoice.getProductList()) {
			if(product instanceof Tickets) {
				
				Tickets ticket = (Tickets) product;
				
				sb.append(String.format("%-15s%-10s%-8s%-25s%-25s%-15s\n", ticket.getTravelDate(), 
						ticket.getFlightNo(), ticket.getFlightClass(), 
						ticket.getDepAirport().getAddress().getCityState(), 
						ticket.getArrAirport().getAddress().getCityState(),
						ticket.getAircraftType()));
				sb.append(String.format("%33s%-25s%-25s\n", "",
						"(" + ticket.getDepAirportCode() + ") " + ticket.getDepTime(),
						"(" + ticket.getArrAirportCode() + ") " + ticket.getArrTime()));
				sb.append(String.format("%20s%-30s%-10s%-20s\n", "", "Traveller", 
										"Age", "SeatNo"));
				for(String seat: ticket.getSeats()) {
					sb.append(String.format("%20s%-30s%-10s%-20s\n", "", 
							ticket.getPassengers().get(ticket.getSeats().indexOf(seat)).getNameLastFirst(), 
							ticket.getAges().get(ticket.getSeats().indexOf(seat)), seat));
				}//	for(String seat: ticket.getSeats()) {

				// if there is a ticket note, output it 
				if(!ticket.getTicketNote().equals("")) {
					sb.append("\n\t *" + ticket.getTicketNote() + "\n");
				}//	if(!ticket.getTicketNote().equals("")) {

			}//	if(product instanceof Tickets) {

		}//	for(Product product: invoice.productList()) {

		
		sb.append("--------------------------------------------------------------------------------------------------------\n");
		
		//airport city is obtained from airport.getAddress().getCity()
		return sb.toString();
		
	}//private String getTravelSummary()
	
	private String getCostSummary(Invoice invoice) {
		StringBuilder sb = new StringBuilder();
		sb.append("FARES AND SERVICES\n");
		sb.append("==================================================\n");
				
		sb.append(String.format("%-10s%-70s%11s%12s%12s\n", "Code", 
				"Item", "Subtotal", "Taxes", "Total"));
		double totalSubTotal = 0.0;
		double taxTotal = 0.0;
		double totalGrandTotal = 0.0;

		//"Code", "Item",  "Subtotal", "Taxes", "Total"
		
		//output the product code, cost summary, subtotal, tax, and total for each product
		//make sure it is formatted nicely
		for(Product product: invoice.getProductList()) {
			sb.append(String.format("%-10s%-70s$%10.2f $%10.2f $%10.2f\n", 
					product.getProductCode(), product.getCostSummaryLine1(),
					StandardUtils.roundCents(product.getCost()), 
					StandardUtils.roundCents(product.getTaxRate() * product.getCost()),
					StandardUtils.roundCents(product.getCost()+product.getTaxRate()*product.getCost())));
					
			totalSubTotal += product.getCost();
			taxTotal += product.getTaxes();
			totalGrandTotal += product.getCost()+product.getTaxes();
			
			//add the second line of the cost summary if the second line is not an empty string
			if(!product.getCostSummaryLine2().equals("")) {
				sb.append(String.format("%-10s%-70s\n", "", product.getCostSummaryLine2()));
			}//			if(!product.getCostSummaryLine2().equals("")) {

		}//	for(Product product: invoice.getProductList()) {
		
		sb.append(String.format("%115s\n", "===================================="));
		
		sb.append(String.format("%-80s$%10.2f $%10.2f $%10.2f\n", 
				"SUB-TOTALS", totalSubTotal, taxTotal, totalGrandTotal));
		
		String discount = "";
		
		//if the customer is general, then there is no discount
		if(invoice.getCustomer() instanceof General) {
			discount = "None";
		} //if(invoice.getCustomer() instanceof General) {

		
		//if the customer is government, the discount is that there is no tax
		else if(invoice.getCustomer() instanceof Government) {
			discount = "NO TAX";
		}//	else if(invoice.getCustomer() instanceof Government) {	

		// if the customer is Corporate, then calculate the discount
		else {
			discount = StandardUtils.roundCents(invoice.getDiscountRate()*100) + "% of SUBTOTAL";
		}//if the discount is from a customer
				
		double totalDiscount = invoice.getDiscount()*(-1);
		
		sb.append(String.format("%-103s $%10.2f\n", 
				"DISCOUNT ( " + discount + " )", totalDiscount));
		
		sb.append(String.format("%-103s $%10.2f\n", 
				"ADDITIONAL FEE", 
				invoice.getCustomer().getComplianceFee()));
		
		sb.append(String.format("%-103s $%10.2f\n", 
				"TOTAL", 
				totalGrandTotal+totalDiscount+invoice.getCustomer().getComplianceFee()));
		
		sb.append("--------------------------------------------------------------------------------------------------------\n");
		
		return sb.toString();
		
	}//private String getCostSummary()

	public String generateDetailReport(SortedList<Invoice> sortedList) {
		StringBuilder sb = new StringBuilder();		
		sb.append("Individual Invoice Detail Reports\n");
		sb.append("==================================================\n"); 
		
	
	
		for(Invoice invoice: sortedList) {
			//invoice number
			sb.append("Invoice " + invoice.getInvoiceCode() +"\n");
			sb.append("--------------------------------------------------------------------------------------------------------\n");
			
			//invoice date, pnr, etc
			sb.append(String.format("%-90s%s\n", "AIR AMERICA", "PNR"));
			sb.append(String.format("%-90s%s\n", "IssueDate: " + invoice.getDate(), invoice.getInvoicePNR()));
			sb.append("--------------------------------------------------------------------------------------------------------\n");

			
			//tack on the travel summary
			sb.append(getTravelSummary(invoice) + "\n");
			
			//add in customer information
			sb.append("CUSTOMER INFORMATION:\n");
			sb.append("\t" + invoice.getCustomer().getName() + " (" + 
					  invoice.getCustomer().getCustomerCode() + ")\n");
			sb.append("\t" + invoice.getCustomer().getType() + "\n");
			sb.append("\t" + invoice.getCustomer().getPrimaryContact().getNameLastFirst() + "\n");
			sb.append("\t" + invoice.getCustomer().getPrimaryContact().getAddress().toString() + "\n");
			sb.append("Salesperson: " + invoice.getSalespersonName() + "\n");
			sb.append("--------------------------------------------------------------------------------------------------------\n");

			
			//toss on a cost summary
			sb.append(getCostSummary(invoice) + "\n");
			
		}//	for(Invoice invoice: invoices) {

		return sb.toString();
	}//public String generateDetailReport()
	
	public static void main(String args[]) {
		
		//grab products from DataConverter.getAllProducts(), customers from DataConverter.getAllCustomers(), etc
		
		//create a method in DataConverter for getAllInvoices()
		
		//fix getAllProducts/productConverter in DataConverter
		
		//create methods for all the taxes and fees
		
		//use the formula :
			//formula: subtotal + fees + taxes - discount = total
			//discount = subtotal * discountRate
			//fees include Product.getFees() and Customer.getComplianceFee()
			//taxes = Customer.getTaxMultiplier * taxes
				
		//if there is a ticket and a refreshment together, the refreshment is discounted 5%
		
		//loop through products and check each one's type
			//use this to keep track of the number of units
				//keep track of units by using aProduct.incrementNumUnits();
			//remember to multiply costPerUnit by number of units
				
				
		System.out.println("Invoice Report sorted by Customer Name:");
		printInvoiceReport(byCustomerName);
		System.out.println();
		System.out.println();
		System.out.println("Invoice Report sorted by Total Cost:");
		printInvoiceReport(byTotal);
		System.out.println();
		System.out.println();
		System.out.println("Invoice Report sorted by Customer Type, then by Salesperson Name:");
		printInvoiceReport(byCustomerType);
		
		
	}//public static void main(String args[])
	
	public static void printInvoiceReport(Comparator<Invoice> comp) {
		InvoiceReport ir = new InvoiceReport();
		String summary = ir.generateSummaryReport(dataConverter.getInvoices().changeComparator(comp));
		String details = ir.generateDetailReport(dataConverter.getInvoices().changeComparator(comp));
		System.out.println(summary);
		System.out.println("\n\n");
		System.out.println(details);
		
		System.out.println("======================================================================================================================");
		System.out.println("\n\n");
	}//	public static void printInvoiceReport(SortedList<Invoice> invoices) {
	
	static Comparator<Invoice> byCustomerName = new Comparator<Invoice>() {
		@Override
		public int compare(Invoice a, Invoice b) {
			return a.getCustomer().getPrimaryContact().getNameLastFirst().compareToIgnoreCase(b.getCustomer().getPrimaryContact().getNameLastFirst());
		}//public int compare(Invoice a, Invoice b) {
	};//Comparator<Invoice> bySalesPersonName = new Comparator<Invoice>() {

	static Comparator<Invoice> byTotal = new Comparator<Invoice>() {
		@Override
		public int compare(Invoice a, Invoice b) {
			Double aTotalCost = new Double(a.getGrandTotal());
			Double bTotalCost = new Double(b.getGrandTotal());
			return bTotalCost.compareTo(aTotalCost);
		}//public int compare(Invoice a, Invoice b) {
	};//Comparator<Invoice> byTotalCost = new Comparator<Invoice>() {
	
	static Comparator<Invoice> byCustomerType = new Comparator<Invoice>() {
		@Override
		public int compare(Invoice a, Invoice b) {
			if(a.getCustomer().getType().equals(b.getCustomer().getType())) {
				return a.getSalespersonName().compareToIgnoreCase(b.getSalespersonName());
			}//if(a.getCustomer().getType().equals(b.getCustomer().getType())) {
			else {
				return a.getCustomer().getType().compareToIgnoreCase(b.getCustomer().getType());
			}//NOT if(a.getCustomer().getType().equals(b.getCustomer().getType())) {
		}//public int compare(Invoice a, Invoice b) {
	};//Comparator<Invoice> byCustomerType = new Comparator<Invoice>() {
	
}//public class InvoiceReport {

