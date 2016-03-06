package com.airamerica;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.airamerica.utils.StandardUtils;

public class Invoice {
	private String invoicePNR;
	private String invoiceCode;
	private Customer customer;
	private String salespersonName;
	private String date;
	private ArrayList<Product> productList;
	
	public Invoice(String invoiceCode, Customer customer,
			String salespersonName, String date, ArrayList<Product> productList) {
		this.invoicePNR = StandardUtils.generatePNR();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salespersonName = salespersonName;
		this.date = date;
		this.productList = productList;
	}//Invoice constructor
	
	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public void setSalespersonName(String salespersonName) {
		this.salespersonName = salespersonName;
	}

	public String getInvoicePNR() {
		return invoicePNR;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public String getSalespersonName() {
		return salespersonName;
	}
	
	public String getDate() {
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int dayOfMonth = Integer.parseInt(date.substring(8));
				
		LocalDate date = LocalDate.of(year, month, dayOfMonth);
		Locale locale = Locale.getDefault();
		Month monthOfYear = date.getMonth();
		//dow.getDisplayName(TextStyle.SHORT, locale)
		return monthOfYear.getDisplayName(TextStyle.SHORT, locale) + " " +
		date.getDayOfMonth() + "," + date.getYear();
	}
	
	public ArrayList<Product> getProductList() {
		return productList;
	}

	public void setInvoicePNR(String invoicePNR) {
		this.invoicePNR = invoicePNR;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setSalesperson(String salespersonName) {
		this.salespersonName = salespersonName;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}
	
	public void addProduct(Product product) {
		productList.add(product);
	}
	
	public double getTotalCost() {
		double totalCost = 0.0;
		for(Product product: productList) {
			totalCost+=product.getCost();
		}//for(Product product: productList) {

		return totalCost;
		
	}
	
	public double getTotalFees() {
		double totalFees = 0.0;
		
		for(Product product: productList) {
			totalFees+=product.getFees();
		}
		
		
		return totalFees;
	}
	
	public double getTaxes() {
		double totalTaxes = 0.0;
		
		for(Product product: productList) {
			totalTaxes+=product.getTaxes();
		}
		
		return totalTaxes;
	}

	public double getDiscount() {
		if(customer instanceof Government) {
			return this.getTaxes();
		}
		if(customer instanceof Corporate) {
			return this.getTotalCost()*customer.getDiscount();
		} else {
			return 0;
		}
	}

	public double getGrandTotal() {
		return this.getTotalCost()+this.getCustomer().getComplianceFee()-this.getDiscount()+this.getTaxes();
	}
	
	//invoice.getTotalCost(), invoice.getCustomer().getComplianceFee(), 
	//invoice.getTaxes(), invoice.getDiscount()*(-1), invoice.getGrandTotal()
	
	public double getDiscountRate() {
		if(customer instanceof Corporate) {
			return customer.getDiscount();
		} else {
			return 0;
		}
	}
	
	
}//public class Invoice {

