package com.example.demo.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.example.demo.model.Product;

public interface ProductDao {
	public Map<String, Product> loadFromFile() throws FileNotFoundException, IOException;
	public Map<String, Product> getProductMap();
	
	
}
