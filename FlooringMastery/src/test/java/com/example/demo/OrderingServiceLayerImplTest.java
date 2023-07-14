package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.junit.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;

import com.example.demo.exception.*;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.State;
import com.example.demo.service.OrderingServiceLayer;
import com.example.demo.service.OrderingServiceLayerImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderingServiceLayerImplTest {
	

	OrderingServiceLayer myService;
	
	
	@BeforeEach
	public void createService()  {
		
		
		try {
			myService = new OrderingServiceLayerImpl();
			myService.loadFromFile();
		} catch (Exception e) {
			
		}
		 
	}
	
	@Test
	void loadFromFileTest() throws IOException {
		
		assertNotNull(myService.loadFromFile());
		
	}

	@Test
	void orderDateIsFutureTest() {
		LocalDate date = LocalDate.of(2023, 05, 27);
		//assertNotNull(date);
		//myService.orderDateIsFuture(date);
		assertFalse(myService.orderDateIsFuture(date));
		date = LocalDate.of(2023, 06, 27);
		assertTrue(myService.orderDateIsFuture(date));
		date = LocalDate.now();
		assertFalse(myService.orderDateIsFuture(date));		
	}
	@Test
	void acceptOrderDateTest() throws IOException, ValueNotFoundException {
		
		assertThrows(ValueNotFoundException.class, ()->myService.acceptOrderDate(LocalDate.of(1999, 06, 27)));
		
		LocalDate date = LocalDate.of(2013, 06, 02);
		assertNotNull(myService.acceptOrderDate(date));	
		assertEquals(myService.acceptOrderDate(date),date);
	}
	
	
	@Test
	void acceptOrderNumberTest() throws NegativeOrZeroException {
		
		assertThrows(NegativeOrZeroException.class, ()->myService.acceptOrderNumber(0));
		assertThrows(NegativeOrZeroException.class, ()->myService.acceptOrderNumber(-1));
		assertEquals(myService.acceptOrderNumber(1),1);
	}
	
	@Test
	void acceptStateTest() throws NonAlphabeticException, ValueNotFoundException {
		assertThrows(NonAlphabeticException.class, ()->myService.acceptState("A##%A"));
		assertThrows(ValueNotFoundException.class, ()->myService.acceptState("HK"));
		assertInstanceOf(State.class, myService.acceptState("ca"));
	}
	
	@Test
	void acceptProductTypeTest() throws NonAlphabeticException, ValueNotFoundException {
		assertThrows(NonAlphabeticException.class, ()->myService.acceptProductType("w@@d"));
		assertThrows(ValueNotFoundException.class, ()->myService.acceptProductType("HK"));
		assertInstanceOf(Product.class, myService.acceptProductType("wood"));
	}
	
	@Test
	void acceptAreaTest() throws NegativeOrZeroException {
		assertThrows(NegativeOrZeroException.class, ()->myService.acceptArea(new BigDecimal(0)));
		assertThrows(NegativeOrZeroException.class, ()->myService.acceptArea(new BigDecimal(-0.1)));
		assertInstanceOf(BigDecimal.class, myService.acceptArea(new BigDecimal(10)));
	}
	
	@Test
	void acceptYesNoTest() throws ValueNotFoundException {
		assertThrows(ValueNotFoundException.class, ()->myService.acceptYesNo("Ok"));
		assertTrue(myService.acceptYesNo("y"));
		assertFalse(myService.acceptYesNo("N"));
	}
	
	@Test
	void findOrderTest() throws IOException {
		LocalDate date = LocalDate.of(2013,06,01);
		assertTrue(myService.findOrder(date,1).isPresent());
		date = LocalDate.of(2000,06,01);
		assertFalse(myService.findOrder(date,1).isPresent());
	}
	
	@Test
	void findOrderListTest() throws IOException {
		LocalDate date = LocalDate.of(2013,06,01);
		assertNotNull(myService.findOrderList(date));
		date = LocalDate.of(2000,06,01);
		assertNull(myService.findOrderList(date));
	}
	
	@Test
	void setAndGetOrderNumberCounterTest(){
		assertTrue(myService.getOrderNumberCounter()>0);
		myService.setOrderNumberCounter(5);
		assertEquals(myService.getOrderNumberCounter(),5);
		assertEquals(myService.incrementOrderNumberCounter(),6);
	}
	

}
