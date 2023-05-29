package com.example.demo.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.example.demo.model.Product;
import com.example.demo.model.State;

public class ProductDaoImpl implements ProductDao{

	private Map<String, Product> productMap;
	
	public ProductDaoImpl() {
		productMap = new TreeMap<String, Product>();
	}
	
	@Override
	public Map<String, Product> loadFromFile() throws IOException {
//		BufferedReader br = null;
//		FileReader fr = null;
//		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		try (BufferedReader br = new BufferedReader(new FileReader("./src/Data/Products.txt"))){
						
			String productWholeLine = br.readLine(); // The first line of the file is the header. Do not write it to Order.
			
			while ( (productWholeLine = br.readLine()) != null ) {
				Product product = new Product();
				String[] productElements = productWholeLine.split(",");
				
				
				product.setProductType(productElements[0]);
				product.setCostPerSquareFoot(new BigDecimal(productElements[1]));
				product.setLaborCostPerSquareFoot(new BigDecimal(productElements[2]));
				productMap.put(product.getProductType().toUpperCase(), product);
			}
			
//			for (Entry<String,Product>entry: productMap.entrySet()) {
//				System.out.println(entry);
//			}
		} catch (FileNotFoundException fnf) {
			// This program will not handle this error because without the product file it is not possible to place orders.
			System.err.println("The Products.txt does not exist! The application cannot proceed.");
			throw fnf;
		} 

		return productMap;
	}

	@Override
	public Map<String, Product> getProductMap() {
		return productMap;
	}

}
