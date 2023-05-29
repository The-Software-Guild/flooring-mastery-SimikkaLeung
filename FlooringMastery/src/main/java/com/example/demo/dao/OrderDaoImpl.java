package com.example.demo.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import com.example.demo.model.Order;


public class OrderDaoImpl implements OrderDao{
	
	private Map<LocalDate, List<Order>> orderMap;
	private Integer orderNumberCounter;
	
	public OrderDaoImpl() {
		orderMap = new TreeMap<LocalDate, List<Order>>();
		orderNumberCounter = 0;
	}

	
	@Override
	public Map<LocalDate, List<Order>> loadFromFile() throws IOException {
		BufferedReader br = null;
		// Create a File object for the directory
		File directoryPath = new File("./src/Orders");
		//Create a list of all files
		File[] fileList = directoryPath.listFiles();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
		
		if (fileList.length > 1) {
			for (int i = 0; i < fileList.length; i++) {
				br = new BufferedReader(new FileReader(fileList[i]));
				List<Order> orderList = new ArrayList<Order>();
				String orderWholeLine = null;
				orderWholeLine = br.readLine();		// The first line of the file is the header. Do not write it to Order.
				String filename = fileList[i].toString();
				String dateStr = fileList[i].toString().substring(filename.length()-12, filename.length()-4);	
				LocalDate date = LocalDate.parse(dateStr,formatter);
				while ((orderWholeLine = br.readLine()) != null ) {
					String[] orderElements = orderWholeLine.split(",");
					Order order = new Order();

					order.setOrderDate(date);
					order.setOrderNumber(Integer.parseInt(orderElements[0]));
					if (order.getOrderNumber()>orderNumberCounter) {		// Find the max order number
						orderNumberCounter = order.getOrderNumber();
					}
					order.setCustomerName(orderElements[1]);				
					order.setStateAbbreviation(orderElements[2]);
					order.setTaxRate(new BigDecimal(orderElements[3]));
					order.setProductType(orderElements[4]);					
					order.setCostPerSquareFoot(new BigDecimal(orderElements[6]));
					order.setLaborCostPerSquareFoot(new BigDecimal(orderElements[7]));
					order.setArea(new BigDecimal(orderElements[5]));
					order.setMaterialCost(new BigDecimal(orderElements[8]));
					order.setLaborCost(new BigDecimal(orderElements[9]));					
					order.setTax(new BigDecimal(orderElements[10]));
					order.setTotal(new BigDecimal(orderElements[11]));
					
					orderList.add(order);
				}

				orderMap.put(date,orderList);
			}
			br.close();
		}
		return orderMap;
	}

	
	
	@Override
	public void saveToFile() throws IOException {
		FileWriter fw = null;

		Set<Entry<LocalDate, List<Order>>> dateEntrySet = this.orderMap.entrySet();
	
		
		for (Entry<LocalDate, List<Order>> dateEntry : dateEntrySet) {
			LocalDate date = dateEntry.getKey();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
			
			String filename = "./src/Orders/Orders_" + formatter.format(date)+".txt";
			fw = new FileWriter(filename);
			fw.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n");
			for (Order order : dateEntry.getValue()) {
				fw.write(order.toOrderTxt());
				fw.write("\n");

			}
			fw.close();		// It will flush the content before closing it
			
		}

		
	}

	@Override
	public Map<LocalDate, List<Order>> getOrderMap() throws IOException {
		this.orderMap = loadFromFile();
		return orderMap;
	}
	
	@Override
	public void setOrderMap(Map<LocalDate, List<Order>> orderMap) throws IOException {
		this.orderMap = orderMap;
		saveToFile();
	}
	
	public Integer getOrderNumberCounter() {
		return orderNumberCounter;
	}
	
	public void setOrderNumberCounter(Integer orderNumberCounter) {
		this.orderNumberCounter = orderNumberCounter;
	}

	@Override
	public Integer incrementOrderNumberCounter() {
		return ++orderNumberCounter;
	}


	@Override
	public void exportAllData() throws IOException {
		// TODO Auto-generated method stub
		FileWriter fw = null;

		Set<Entry<LocalDate, List<Order>>> dateEntrySet = this.orderMap.entrySet();
		String filename = "./src/Backup/DataExport.txt";
		
		
		fw = new FileWriter(filename);
		fw.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate\n");
		
		for (Entry<LocalDate, List<Order>> dateEntry : dateEntrySet) {
			LocalDate date = dateEntry.getKey();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

			for (Order order : dateEntry.getValue()) {
				fw.write(order.toOrderTxt()+","+formatter.format(date));

				fw.write("\n");
			}
			
			
		}
		fw.close();		// It will flush the content before closing it
	}
	
}
