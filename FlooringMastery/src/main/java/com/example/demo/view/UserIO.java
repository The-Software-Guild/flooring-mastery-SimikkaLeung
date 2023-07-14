package com.example.demo.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public interface UserIO {
	public int inputMenuChoice(Scanner keyboard, String prompt);
	public LocalDate inputOrderDate(Scanner keyboard, String prompt);
	public Integer inputOrderNumber(Scanner keyboard, String prompt);
	public String inputCustomerName (Scanner keyboard, String prompt);
	public String inputState(Scanner keyboard, String prompt);
	public String inputProductType (Scanner keyboard, String prompt);
	public BigDecimal inputArea(Scanner keyboard, String prompt);
	public String inputYesNo (Scanner keyboard, String prompt);
}
