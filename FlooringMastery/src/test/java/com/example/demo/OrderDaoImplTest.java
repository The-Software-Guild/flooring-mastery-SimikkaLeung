package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.demo.dao.OrderDao;
import com.example.demo.dao.OrderDaoImpl;
import com.example.demo.model.Order;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderDaoImplTest {
	
	OrderDao orderDao = new OrderDaoImpl();

	
	@Test
	public void loadFromFileTest() throws FileNotFoundException, IOException {
		assertNotNull(orderDao.loadFromFile());
		
	}
	@Test
	public void getOrderMapTest() throws IOException {
		assertNotNull(orderDao.getOrderMap());
		
	}

	@Test
	public void setAndGetOrderNumberCounterTest() {
		orderDao.setOrderNumberCounter(5);
		assertEquals(orderDao.getOrderNumberCounter(),5);
		assertNotEquals(orderDao.getOrderNumberCounter(),4);
	}
	@Test
	public void incrementOrderNumberCounterTest() {
		orderDao.setOrderNumberCounter(5);
		assertEquals(orderDao.getOrderNumberCounter(),5);
		orderDao.incrementOrderNumberCounter();
		assertNotEquals(orderDao.getOrderNumberCounter(),5);
		assertEquals(orderDao.getOrderNumberCounter(),6);
	}
	
}
