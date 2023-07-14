package com.example.demo.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.example.demo.exception.EmptyInputException;

public class UserIOConsoleImpl implements UserIO {
	
	/*
	 * This class only checks if the data type is valid. 
	 * More data validation will be done in the service class.
	 */
	
	@Override
	public int inputMenuChoice(Scanner keyboard, String prompt) {
		// TODO Auto-generated method stub
		System.out.println(prompt);
		int choice = 0;
		boolean toContinue = true;
		
		do {
			
			try {
				String strChoice = keyboard.nextLine();
				choice = Integer.parseInt(strChoice);
				toContinue = false;
			} catch (NumberFormatException ex) {
				System.out.println("Please provide a valid response.");
				toContinue = true;
			}
			
		} while (toContinue);

		return choice;
	}
	
	@Override
	public LocalDate inputOrderDate(Scanner keyboard, String prompt) {
		System.out.println(prompt);
		LocalDate orderDate = null;
		boolean toContinue = true;
		
		do {
			try {
				orderDate = LocalDate.parse(keyboard.nextLine());
				toContinue = false;
			} catch (DateTimeParseException ex) {
				System.out.println("Please provide a valid response.");
				toContinue = true;
			}
			
		} while (toContinue);
		
		return orderDate;
	}
	
	
	@Override
	public Integer inputOrderNumber(Scanner keyboard, String prompt) {
		// TODO Auto-generated method stub
		System.out.println(prompt);
		String strOrderNumber = keyboard.nextLine();
		int orderNumber = 0;
		boolean toContinue = true;
		
		do {

			try {
				if (strOrderNumber == null || strOrderNumber.length() == 0 ) {
					throw new EmptyInputException();
				}
				orderNumber = Integer.parseInt(strOrderNumber);
				toContinue = false;
			} catch (ArithmeticException | EmptyInputException ex1) {
				System.out.println("Please provide a valid response.");
				toContinue = true;
			}
			
		} while (toContinue);

		return orderNumber;
	}

	@Override
	public String inputCustomerName(Scanner keyboard, String prompt) {
		// TODO Auto-generated method stub
		System.out.println(prompt);
		return keyboard.nextLine();
	}

	@Override
	public String inputState(Scanner keyboard, String prompt) {
		// TODO Auto-generated method stub
		System.out.println(prompt);
		return keyboard.nextLine();
	}

	@Override
	public String inputProductType(Scanner keyboard, String prompt) {
		// TODO Auto-generated method stub
		System.out.println(prompt);
		return keyboard.nextLine();
	}

	@Override
	public BigDecimal inputArea(Scanner keyboard, String prompt) {
		// TODO Auto-generated method stub
		System.out.println(prompt);
		
		BigDecimal area = null;
		boolean toContinue = true;
		String strArea = null;
		do {
			strArea = keyboard.nextLine();
			if (strArea.length()==0 || strArea == null) {
				return null;
			}
			try {

				area = new BigDecimal(strArea);
				toContinue = false;
			} catch (NumberFormatException ex) {
				System.out.println("Please provide a valid response.");
				toContinue = true;
			}
			
		} while (toContinue);

		return area;
	}

	@Override
	public String inputYesNo(Scanner keyboard, String prompt) {
		// TODO Auto-generated method stub
		System.out.println(prompt);
//		boolean toContinue = true;
//		String response;
//		do {
//			response = keyboard.nextLine();
//			if (response.equalsIgnoreCase("Y") || response.equalsIgnoreCase("N") ) {
//				toContinue = false;
//			} else {
//				System.out.println("Invalid Response: Only 'Y' and 'N' are allowed.");
//			}
//		} while (toContinue);
//		
		
		return keyboard.nextLine();
	}

}
