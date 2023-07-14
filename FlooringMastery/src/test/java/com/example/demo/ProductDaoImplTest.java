package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.example.demo.dao.ProductDao;
import com.example.demo.dao.ProductDaoImpl;

class ProductDaoImplTest {
	
	ProductDao productDao = new ProductDaoImpl();

	@Test
	public void loadFromFileTest() throws FileNotFoundException, IOException {
		assertNotNull(productDao.loadFromFile());
		
	}

		
	public void getProductMapTest() throws IOException {
		assertNotNull(productDao.getProductMap());
		
	}
	
	
}
