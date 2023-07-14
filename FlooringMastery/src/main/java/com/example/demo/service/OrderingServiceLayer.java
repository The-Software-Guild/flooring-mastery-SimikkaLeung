package com.example.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.ProductDao;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.State;
import com.example.demo.exception.*;

public interface OrderingServiceLayer {
	
	public Map<LocalDate, List<Order>> addOrder(Order order) throws IOException; 
	public Map<LocalDate, List<Order>> deleteOrder(Order order) throws IOException;
	public Map<LocalDate, List<Order>> editOrder(Order oldOrder, Order newOrder) throws IOException;
	public void exportAllData () throws IOException;
	public void saveToFile() throws IOException;
	public Map<LocalDate, List<Order>> loadFromFile() throws IOException;
	
	public boolean orderDateIsFuture(LocalDate orderDate);
	public LocalDate acceptOrderDate(LocalDate orderDate) throws ValueNotFoundException, IOException;;
	public Integer acceptOrderNumber(Integer orderNumber) throws NegativeOrZeroException;
	public String acceptCustomerName (String customerName) throws NonAlphabeticException;
	public State acceptState(String state) throws NonAlphabeticException, ValueNotFoundException;
	public Product acceptProductType (String productType) throws NonAlphabeticException, ValueNotFoundException;
	public BigDecimal acceptArea(BigDecimal area) throws NegativeOrZeroException;
	public boolean acceptYesNo (String response) throws ValueNotFoundException;
	
	public Optional<Order> findOrder(LocalDate orderDate, Integer orderNumber) throws IOException;
	public List<Order> findOrderList(LocalDate orderDate) throws IOException;
	
	public Integer getOrderNumberCounter();
	public void setOrderNumberCounter(Integer orderNumberCounter);
	public Integer incrementOrderNumberCounter();
}
