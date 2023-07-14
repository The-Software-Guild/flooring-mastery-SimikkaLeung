package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.OrderDaoImpl;
import com.example.demo.dao.ProductDao;
import com.example.demo.dao.ProductDaoImpl;
import com.example.demo.dao.StateDao;
import com.example.demo.dao.StateDaoImpl;
import com.example.demo.exception.*;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.State;


public class OrderingServiceLayerImpl implements OrderingServiceLayer {
	//@Autowired
	private OrderDao orderDao;
	//@Autowired
	private ProductDao productDao;
	//@Autowired
	private StateDao stateDao;
//	private Map<LocalDate, List<Order>> orderMap;
//	private Map<String, Product> productMap;
//	private Map<String, State> stateMap;
	
	public OrderingServiceLayerImpl() throws FileNotFoundException, IOException {
		orderDao = new OrderDaoImpl();
		
		productDao = new ProductDaoImpl();
		
		stateDao = new StateDaoImpl();
//		orderDao.loadFromFile();
//		productDao.loadFromFile();
//		stateDao.loadFromFile();
		
		//System.out.println("getOrderNumberCounter()" +getOrderNumberCounter() );
	}
	
	
	@Override
	public Map<LocalDate, List<Order>> addOrder(Order order) throws IOException {
		// TODO Auto-generated method stub
		
		Map<LocalDate, List<Order>> orderMap = orderDao.getOrderMap();
		List<Order> orderList = orderMap.get(order.getOrderDate());
		if (orderList == null) {
			orderList = new ArrayList<Order>();
			orderList.add(order);
		} else {
			orderList.add(order);
		}
		orderMap.put(order.getOrderDate(), orderList);
		orderDao.setOrderMap(orderMap);
		orderDao.loadFromFile();
		return orderMap;
	}
	// Constructor
	public OrderingServiceLayerImpl(OrderDao orderDao, ProductDao productDao, StateDao stateDao) {
	super();
	this.orderDao = orderDao;
	this.productDao = productDao;
	this.stateDao = stateDao;
	}
	@Override
	public Map<LocalDate, List<Order>> deleteOrder(Order order) throws IOException {
		// TODO Auto-generated method stub
		
		Map<LocalDate, List<Order>> orderMap = orderDao.getOrderMap();
		List<Order> orderList = orderMap.get(order.getOrderDate());
		int indexOfOrder = -1;
		for (int i = 0; i < orderList.size() ; i++) {
			if (orderList.get(i).equals(order)) {
				indexOfOrder = i;
			}
		}
		
		orderMap.get(order.getOrderDate()).remove(indexOfOrder);
		orderDao.setOrderMap(orderMap);
		orderDao.loadFromFile();
		return orderMap;
	}
	@Override
	public Map<LocalDate, List<Order>> editOrder(Order oldOrder, Order newOrder)  throws IOException {
		// TODO Auto-generated method stub
		
		Map<LocalDate, List<Order>> orderMap = orderDao.getOrderMap();
		List<Order> orderList = orderMap.get(oldOrder.getOrderDate());
		
		int indexOfOldOrder = -1;
		for (int i = 0; i<orderList.size(); i++) {
			if (orderList.get(i).equals(oldOrder)) {
				indexOfOldOrder = i;
			}
		}
		orderMap.get(oldOrder.getOrderDate()).set(indexOfOldOrder, newOrder);
		
		orderDao.setOrderMap(orderMap);
		orderDao.loadFromFile();
		return orderMap;
	}
	@Override
	public void exportAllData() throws IOException {
		// TODO Auto-generated method stub
		// Optional feature
		orderDao.exportAllData();
		
	}
	@Override
	public Map<LocalDate, List<Order>> loadFromFile() throws IOException {
		
		productDao.loadFromFile();
		stateDao.loadFromFile();		
		return orderDao.loadFromFile();
	}
	@Override
	public void saveToFile() throws IOException {
		// TODO Auto-generated method stub
		orderDao.saveToFile();
	}
	@Override
	public boolean orderDateIsFuture(LocalDate orderDate) {
		return orderDate.isAfter(LocalDate.now());
	}
	@Override
	public LocalDate acceptOrderDate(LocalDate orderDate) throws ValueNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		if (orderDao.getOrderMap().containsKey(orderDate) == false) {
			throw new ValueNotFoundException();
		}
//		try {
////			orderDao.getOrderMap().containsKey(orderDate);
//			
//			
//		} catch (NullPointerException ex) {
//			throw new ValueNotFoundException();
//		}
		
		return orderDate;
	}
	
	@Override
	public Integer acceptOrderNumber(Integer orderNumber) throws NegativeOrZeroException {
		// TODO Auto-generated method stub
		
		if (orderNumber <= 0) {
			throw new NegativeOrZeroException();
		}
				
		return orderNumber;
	}
	
	@Override
	public String acceptCustomerName(String customerName) throws NonAlphabeticException {
		// TODO Auto-generated method stub
		
		String customerNameCleaned = customerName.replaceAll("[^A-Za-z]"," ");
		
		System.out.println("customerNameCleaned: " + customerNameCleaned);
		if (!customerNameCleaned.equals(customerName)) {
			throw new NonAlphabeticException();
		}
		return customerNameCleaned;
	}
	@Override
	public State acceptState(String state) throws NonAlphabeticException, ValueNotFoundException {
		// TODO Auto-generated method stub
		
		String stateCleaned = state.replaceAll("[^A-Za-z]"," ");

		if (!stateCleaned.equals(state)) {
			throw new NonAlphabeticException();
		}
		stateCleaned = stateCleaned.toUpperCase();
		if (!stateDao.getStateMap().containsKey(stateCleaned)) {

			throw new ValueNotFoundException();
		}
		
		return stateDao.getStateMap().get(stateCleaned);

	}
	@Override
	public Product acceptProductType(String productType) throws NonAlphabeticException, ValueNotFoundException {
		// TODO Auto-generated method stub
		String productTypeCleaned = productType.replaceAll("[^A-Za-z]"," ");
		
		if (!productTypeCleaned.equals(productType)) {
			throw new NonAlphabeticException();
		}
		productTypeCleaned = productTypeCleaned.toUpperCase();
		if (!productDao.getProductMap().containsKey(productTypeCleaned)) {
			throw new ValueNotFoundException();
		}
		
		return productDao.getProductMap().get(productTypeCleaned);
	}
	@Override
	public BigDecimal acceptArea(BigDecimal area) throws NegativeOrZeroException {
		// TODO Auto-generated method stub
		if (area.doubleValue() <= 0.0) {
			throw new NegativeOrZeroException();
		}
				
		return area;
	}
	@Override
	public boolean acceptYesNo(String response) throws ValueNotFoundException {
		// TODO Auto-generated method stub
	
		
		if (!response.equalsIgnoreCase("Y") && !response.equalsIgnoreCase("N") ) {
			throw new ValueNotFoundException();
		}
		
		return response.toUpperCase().equals("Y");
	}
	@Override
	public Optional<Order> findOrder(LocalDate orderDate, Integer orderNumber) throws IOException {
		List<Order> orderList = orderDao.getOrderMap().get(orderDate);
		
		
//		if (orderList != null) {		// If there is at least one order on that date
//
//				for (Order order: orderList) {
//					if (order.getOrderNumber() == orderNumber) {		// If the order number is found
//						return order;
//					}
//				}
//		}
		//As required in the marking scheme: Lambdas and Streams: The application uses lambdas and streams in at least part of the DAO.
		Optional<Order> order = Optional.empty();
		if (orderList != null){
			order = orderList.stream().filter( o->o.getOrderNumber()==orderNumber).findFirst();
		}
		return order;
		
		//return orderList.stream().filter( o->o.getOrderNumber()==orderNumber).findFirst().get();
	}
	@Override
	public List<Order> findOrderList(LocalDate orderDate) throws IOException {
		return orderDao.getOrderMap().get(orderDate);
	}
	@Override
	public Integer getOrderNumberCounter() {
		return orderDao.getOrderNumberCounter();		
	}
	@Override
	public void setOrderNumberCounter(Integer orderNumberCounter) {
		orderDao.setOrderNumberCounter(orderNumberCounter);	
	}
	@Override
	public Integer incrementOrderNumberCounter() {
		return orderDao.incrementOrderNumberCounter();
	}

	

	
	
}
