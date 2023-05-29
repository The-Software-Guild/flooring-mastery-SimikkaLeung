package com.example.demo.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Order;

public class OrderingView {
	//@Autowired
	private UserIO io;
	//@Autowired
	private Scanner keyboard;
	
	public OrderingView(UserIO io) {
		this.io = io;
	};
	
	
	public OrderingView(UserIO io, Scanner keyboard) {
		this.io = io;
		this.keyboard = keyboard;
	};
	
	public void setKeyboard(Scanner keyboard) {
		this.keyboard = keyboard;
	}
	public Scanner getKeyboard() {
		return keyboard;
	}
	public int displayMenu() {
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
		System.out.println("* <<Flooring Program>> ");
		System.out.println("* 1. Display Orders ");
		System.out.println("* 2. Add an Order ");
		System.out.println("* 3. Edit an Order ");
		System.out.println("* 4. Remove an Order ");
		System.out.println("* 5. Export All Data ");
		System.out.println("* 6. Quit ");
		System.out.println("*");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
		
		
		return io.inputMenuChoice(keyboard, "What would you like to do? (1-6)");
	}
	public void displayOrders(List<Order> orderList) {
		
		if (orderList == null) {
			System.out.println("No order is found.");
		} else {
			for (Order order : orderList) {
				System.out.println(order);
			}	
		}		
	}
	public void displayOneOrder(Order order) {
		System.out.println(order);	
	}
	
	
	public void displayGoodbyeMessage() {
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
		System.out.println("* Thank you for visiting our store! We hope to serve you again!");
		System.out.println("*");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
	}
	
	public void displayErrorMessage(String prompt) {
		System.out.println(prompt);

	}
	
	public LocalDate inputOrderDate(Scanner keyboard, String prompt) {
		return io.inputOrderDate(keyboard, prompt);
	}
	public Integer inputOrderNumber(Scanner keyboard, String prompt) {
		return io.inputOrderNumber(keyboard, prompt);
	}
	public String inputCustomerName (Scanner keyboard, String prompt) {
		return io.inputCustomerName(keyboard, prompt);
	}
	public String inputState(Scanner keyboard, String prompt) {
		return io.inputState(keyboard, prompt);
	}
	public String inputProductType (Scanner keyboard, String prompt) {
		return io.inputProductType(keyboard, prompt);
	}
	public BigDecimal inputArea(Scanner keyboard, String prompt) {
		return io.inputArea(keyboard, prompt);
	}
	public String inputYesNo (Scanner keyboard, String prompt) {
		return io.inputYesNo(keyboard, prompt);
	}
	
	public String displayCustomerName(Order order) {
		return order.getCustomerName();
	}
	public String displayState(Order order) {
		return order.getStateAbbreviation();
	}
	public String displayProductType(Order order) {
		return order.getProductType();
	}
	public String displayArea(Order order) {
		return order.getArea().toString();
	}
	
}
