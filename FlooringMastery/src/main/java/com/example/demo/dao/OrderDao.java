package com.example.demo.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Order;

public interface OrderDao {
	Map<LocalDate, List<Order>> loadFromFile() throws FileNotFoundException, IOException;
	void saveToFile() throws IOException;
	Map<LocalDate, List<Order>> getOrderMap() throws IOException;
	public void setOrderMap(Map<LocalDate, List<Order>> orderMap) throws IOException;
	//void exportAllData(Map<LocalDate, List<Order>> orderMap);
	public Integer getOrderNumberCounter();
	public void setOrderNumberCounter(Integer orderNumberCounter);
	public Integer incrementOrderNumberCounter();
	
	void exportAllData() throws IOException;
}
