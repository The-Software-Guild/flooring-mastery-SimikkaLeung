/*
 * Author: Simikka Leung
 * This application allows users to place, edit, delete and view orders. The data is stored in files.
 * The program adopts the MVC design pattern with a service layer.
 */

package com.example.demo.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.exception.*;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.State;
import com.example.demo.service.OrderingServiceLayer;
import com.example.demo.view.OrderingView;


public class OrderingController {

	
	//@Autowired
	private OrderingServiceLayer myService;
	//@Autowired
	private OrderingView myView;
	//@Autowired
	private Scanner keyboard;
	
	public OrderingController(OrderingServiceLayer myService, OrderingView myView, Scanner keyboard) {
		this.myService = myService;
		this.myView = myView;
		this.keyboard = keyboard;
	}
	public OrderingController(OrderingServiceLayer myService, OrderingView myView) {
		this.myService = myService;
		this.myView = myView;
	}
	
	public void setKeyboard(Scanner keyboard) {
		this.keyboard = keyboard;
		myView.setKeyboard(keyboard);
	}
	public Scanner getKeyboard() {
		return keyboard;
	}
	// The core method of the controller. It will determine the flow of the application, 
	// and when to call methods in the view and the service components.
	public void run() throws IOException {
		
		myService.loadFromFile();
		
		// The user can choose one of the six tasks to perform
		int choice = 0;
		
		do {
			choice = myView.displayMenu();
			//boolean toContinue = true;
			switch (choice) {
				case 1:		// Display Orders
					displayOrders();
					break;
				case 2:		// Add an Order
					addOrder();					
					break;
					
				case 3:	// Edit an Order
					Optional<Order> oldOrder=findOrder();
					if (!oldOrder.isPresent()) {
						myView.displayErrorMessage("The order is not found in the system.");
					} else {
						editOrder(oldOrder.get());
					}
					break;
				case 4: // Remove an Order
					Optional<Order> order4=findOrder();
					if (!order4.isPresent()) {
						myView.displayErrorMessage("The order is not found in the system.");
					} else {
						// Display the info of the new order and ask for a confirmation.
						myView.displayOneOrder(order4.get());
						if (checkYesOrNo("Do you want to delete this order? (Y/N)")) {
							myService.deleteOrder(order4.get());
						}					
						
					}
					break;
				case 5:		// Export All Data: It is an optional feature so I skip it for now.
					//myView.displayErrorMessage("This service is coming soon...");
					myService.exportAllData();
					break;
				case 6:		// Quit
					myView.displayGoodbyeMessage();
					break;
					
			}	// end of the switch statement
			
		} while (choice != 6);

		
		
	}
	
	public void displayOrders() throws IOException {
		boolean toContinue = true;
		LocalDate orderDate1 = null;
		do {
			orderDate1 =  myView.inputOrderDate(keyboard,"Please enter an order date (YYYY-MM-DD):");
			try {
				myService.acceptOrderDate(orderDate1);
				toContinue = false;
			} catch (ValueNotFoundException ex) {
				myView.displayErrorMessage("Invalid Date: The order date is not found in the system.");
				toContinue = true;
			}
		} while (toContinue);
		myView.displayOrders(myService.findOrderList(orderDate1));
	}
	
	public void addOrder() throws IOException {
		// Prompt the user for mandatory info of an order.
		
		boolean toContinue = true;
		LocalDate orderDate2 = null;
		do {
			orderDate2 =  myView.inputOrderDate(keyboard,"Please enter an order date (YYYY-MM-DD):");
			if (orderDate2 == null) {
				myView.displayErrorMessage("Invalid Input: It cannot be empty.");
				toContinue = true;
			} else if (!myService.orderDateIsFuture(orderDate2)) {
				myView.displayErrorMessage("Invalid Date: The order date must be in the future.");
				toContinue = true;
			} else {
				toContinue = false;
			}
		} while (toContinue);
		
		Integer orderNumber2 = myService.getOrderNumberCounter()+1;	// We do not want to update the actual counter until the customer confirm it.
		
		String customerName2 = null;
		do {
			customerName2 = myView.inputCustomerName(keyboard, "Please enter a customer name:");
			if (customerName2 == null || customerName2.length()==0) {
				myView.displayErrorMessage("Invalid Input: It cannot be empty.");
				toContinue = true;
			} else {
				try {
					myService.acceptCustomerName(customerName2);
					toContinue = false;
				} catch (NonAlphabeticException ex) {
					myView.displayErrorMessage("Invalid Name: The name can only contain alphabetical letters and whitespace.");
					toContinue = true;
				}
			}
		} while (toContinue);
		
		
		String stateAbbreviation2 = null;
		State state2 = null;
		do {
			stateAbbreviation2 = myView.inputState(keyboard, "Please enter a state abbreviation:");
			if (stateAbbreviation2 == null || stateAbbreviation2.length()==0) {
				myView.displayErrorMessage("Invalid Input: It cannot be empty.");
				toContinue = true;
			} else {
				
				try {
					state2 = myService.acceptState(stateAbbreviation2);
					toContinue = false;
				} catch (ValueNotFoundException ex1) {
					myView.displayErrorMessage("Invalid State: Our service is not offered in this state yet.");
					toContinue = true;
				} catch (NonAlphabeticException ex2) {
					myView.displayErrorMessage("Invalid State: The name can only contain alphabetical letters.");
				}
			}
				
		} while (toContinue);
		
		String productType2 = null;
		Product product2 = null;
		do {
			productType2 = myView.inputProductType(keyboard, "Please enter a product type:");
			if (productType2 == null || productType2.length()==0) {
				myView.displayErrorMessage("Invalid Input: It cannot be empty.");
				toContinue = true;
			} else {
				try {
					product2 = myService.acceptProductType(productType2);
					toContinue = false;
				} catch (ValueNotFoundException ex1) {
					myView.displayErrorMessage("Invalid Product Type: The product type is not found in the system.");
					toContinue = true;
				} catch (NonAlphabeticException ex2) {
					myView.displayErrorMessage("Invalid Product Type: The name can only contain alphabetical letters and whitespace.");
					toContinue = true;
				}
				
			}

		} while (toContinue);
		
		//BigDecimal acceptArea
		BigDecimal area2 = null;
		do {
			area2 = myView.inputArea(keyboard, "Please enter an area:");
			if (area2 == null) {
				myView.displayErrorMessage("Invalid Input: It cannot be empty.");
				toContinue = true;
			} else {
				try {
					myService.acceptArea(area2);
					toContinue = false;
				} catch (NegativeOrZeroException ex) {
					myView.displayErrorMessage("Invalid Area: It must be a positive number.");
					toContinue = true;
				}
			}

		} while (toContinue);				

		
		// Display the info of the new order and ask for a confirmation.
		Order order = new Order(orderDate2,orderNumber2,customerName2,state2,product2,area2);
		myView.displayOneOrder(order);
		if (checkYesOrNo("Do you want to place this order? (Y/N)")) {
			myService.addOrder(order);
			//myService.incrementOrderNumberCounter();
		}
		
	}
	
	public boolean checkYesOrNo(String prompt) {
		String yesNo;
		boolean toContinue;
		boolean isYes = false;;
		do {
			yesNo = myView.inputYesNo(keyboard, prompt);
			
			try {
				isYes = myService.acceptYesNo(yesNo);
				toContinue = false;			
			} catch (ValueNotFoundException ex) {
				System.out.println("Invalid Response: Only 'Y' and 'N' are allowed.");
				toContinue = true;
			}
			
		} while (toContinue);
		return isYes;
	}
	
	// Both editing and deleting an order requires the application to identify the target order.
	public Optional<Order> findOrder() throws IOException {
		LocalDate orderDate3 = null;
		
		boolean toContinue = true;
		do {
			orderDate3 =  myView.inputOrderDate(keyboard,"Please enter an order date (YYYY-MM-DD):");
			try {
				myService.acceptOrderDate(orderDate3);
				toContinue = false;
			} catch (ValueNotFoundException ex) {
				myView.displayErrorMessage("Invalid Date: The order date is not found in the system.");
				toContinue = true;
			}
		} while (toContinue);
		Integer orderNumber3 = null;
		do {
			orderNumber3 =  myView.inputOrderNumber(keyboard,"Please enter an order number:");
			try {
				myService.acceptOrderNumber(orderNumber3);
				toContinue = false;
			} catch (NegativeOrZeroException e) {
				myView.displayErrorMessage("Invalid Order Number: The order number must be a positive integer.");
				toContinue = true;
			}
		} while (toContinue);

		return myService.findOrder(orderDate3, orderNumber3);		
	}
	
	public void editOrder(Order oldOrder) throws IOException {
		boolean toContinue = true;
		//LocalDate orderDate3 = null;
		Order newOrder = oldOrder.copy();	// Create a deep copy

		String customerName3 = null;
		do {
			customerName3 = myView.inputCustomerName(keyboard, "Enter customer name (" + myView.displayCustomerName(newOrder) + "):");
			if (customerName3.length()!=0 && customerName3 != null) {
				try {
					myService.acceptCustomerName(customerName3);
					newOrder.setCustomerName(customerName3);
					toContinue = false;
				} catch (NonAlphabeticException ex) {
					myView.displayErrorMessage("Invalid Name: The name can only contain alphabetical letters and whitespace.");
					toContinue = true;
				}
			} else {
				toContinue = false;		// This field remains unchanged.
			}
			
		} while (toContinue);
		
		
		String stateAbbreviation3 = null;
		State state3 = null;
		do {
			stateAbbreviation3 = myView.inputState(keyboard, "Enter stete (" + myView.displayState(newOrder) + "):");
			if (stateAbbreviation3.length()!=0 && stateAbbreviation3 != null ) {
				try {

					state3 = myService.acceptState(stateAbbreviation3);
					newOrder.setState(state3);
					toContinue = false;
				} catch (ValueNotFoundException ex1) {
					myView.displayErrorMessage("Invalid State: Our service is not offered in this state yet.");
					toContinue = true;
				} catch (NonAlphabeticException ex2) {
					myView.displayErrorMessage("Invalid State: The name can only contain alphabetical letters.");
				}
			} else {
				toContinue = false;		// This field remains unchanged.
			}
		} while (toContinue);
		
		String productType3 = null;
		Product product3 = null;
		do {
			productType3 = myView.inputProductType(keyboard, "Enter product type (" + myView.displayProductType(newOrder) + "):");
			
			if (productType3.length()!=0 && productType3 != null) {
				try {
					product3 = myService.acceptProductType(productType3);
					newOrder.setProduct(product3);
					toContinue = false;
				} catch (ValueNotFoundException ex1) {
					myView.displayErrorMessage("Invalid Product Type: The product type is not found in the system.");
					toContinue = true;
				} catch (NonAlphabeticException ex2) {
					myView.displayErrorMessage("Invalid Product Type: The name can only contain alphabetical letters and whitespace.");
				}
			} else {
				toContinue = false;		// This field remains unchanged.
			}
			
		} while (toContinue);
		
		//BigDecimal acceptArea
		BigDecimal area3 = null;
		do {
			area3 = myView.inputArea(keyboard, "Enter area (" + myView.displayArea(newOrder) + "):");
			if (area3 != null) {
				try {
					myService.acceptArea(area3);	
					newOrder.setArea(area3);
					toContinue = false;
				} catch (NegativeOrZeroException ex) {
					myView.displayErrorMessage("Invalid Area: It must be a positive number.");
					toContinue = true;
				}
			} else {
				toContinue = false;		// This field remains unchanged.
			}

		} while (toContinue);				
		
		// Display the info of the new order and ask for a confirmation.
		myView.displayOneOrder(newOrder);
		if (checkYesOrNo("Do you want to save this update? (Y/N)")) {
			myService.editOrder(oldOrder, newOrder);
		}
	
	}
	
	
}
