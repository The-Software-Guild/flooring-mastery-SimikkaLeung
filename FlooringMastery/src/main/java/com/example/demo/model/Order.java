package com.example.demo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Order {
	private LocalDate orderDate;
	private Integer orderNumber;
	private String customerName;
	private State state;
	private String stateAbbreviation;	// get it from the State objec
	private String stateName;	// get it from the State object
	private BigDecimal taxRate;		// get it from the State object
	private Product product;
	private String productType;		// get it from the Product object
	private BigDecimal area;
	private BigDecimal costPerSquareFoot;	//	get it from the Product object
	private BigDecimal laborCostPerSquareFoot;				// get it from the Product object
	private BigDecimal materialCost;		// Calculate the cost in this class
	private BigDecimal laborCost;		// Calculate the cost in this class
	private BigDecimal tax;		// Calculate the cost in this class
	private BigDecimal total;		// Calculate the cost in this class
	
	
	public Order() {
		
	}
	
	public Order(LocalDate orderDate, Integer orderNumber, String customerName, State state,
			Product product, BigDecimal area) {	
		super();
		this.orderDate = orderDate;
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.state = state;		
		this.stateAbbreviation = state.getStateAbbreviation();
		this.stateName = state.getStateName();
		this.taxRate = state.getTaxRate();
		this.product = product;
		this.productType = product.getProductType();
		this.area = area;
		this.costPerSquareFoot = product.getCostPerSquareFoot();
		this.laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
		this.materialCost = this.costPerSquareFoot.multiply(this.area);
		this.laborCost = this.laborCostPerSquareFoot.multiply(this.area);
		this.tax = this.materialCost.add(this.laborCost).multiply(this.taxRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		this.total = this.materialCost.add(this.laborCost).add(this.tax);
		
	}

	// Create a copy constructor for a deep copy so that the user can edit the copy 
	//before replacing the original order.
	public Order copy() {
		Order order = new Order();
		order.orderDate = this.orderDate;
		order.orderNumber = this.orderNumber;
		order.customerName = this.customerName;
		order.state = this.state;
		order.stateAbbreviation = this.stateAbbreviation;
		order.stateName = this.stateName;
		order.taxRate = this.taxRate;
		order.product = this.product;
		order.productType = this.productType;
		order.area = this.area;
		order.costPerSquareFoot = this.costPerSquareFoot;
		order.laborCostPerSquareFoot = this.laborCostPerSquareFoot;
		order.materialCost = this.materialCost;
		order.laborCost = this.laborCost;
		order.tax = this.tax;
		order.total = this.total;
		
		return order;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		this.stateAbbreviation = state.getStateAbbreviation();
		this.stateName = state.getStateName();
		this.taxRate = state.getTaxRate();
		this.tax = this.materialCost.add(this.laborCost).multiply(this.taxRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		this.total = this.materialCost.add(this.laborCost).add(this.tax);
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
		this.productType = product.getProductType();
		this.costPerSquareFoot = product.getCostPerSquareFoot();
		this.laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
		this.materialCost = this.costPerSquareFoot.multiply(this.area);
		this.laborCost = this.laborCostPerSquareFoot.multiply(this.area);
		this.tax = this.materialCost.add(this.laborCost).multiply(this.taxRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		this.total = this.materialCost.add(this.laborCost).add(this.tax);
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
		this.materialCost = this.costPerSquareFoot.multiply(this.area);
		this.laborCost = this.laborCostPerSquareFoot.multiply(this.area);
		this.tax = this.materialCost.add(this.laborCost).multiply(this.taxRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
		this.total = this.materialCost.add(this.laborCost).add(this.tax);
	}

	public BigDecimal getCostPerSquareFoot() {
		return costPerSquareFoot;
	}

	public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
		this.costPerSquareFoot = costPerSquareFoot;
	}

	public BigDecimal getLaborCostPerSquareFoot() {
		return laborCostPerSquareFoot;
	}

	public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
		this.laborCostPerSquareFoot = laborCostPerSquareFoot;
	}

	public BigDecimal getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(BigDecimal materialCost) {
		this.materialCost = materialCost;
	}

	public BigDecimal getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean equals(Order order) {		
		if(order.orderDate.equals(this.orderDate)  && order.orderNumber.equals(this.orderNumber) 
				&& order.customerName.equals(this.customerName) && order.stateAbbreviation.equals(this.stateAbbreviation)
				&& order.productType.equals(this.productType) && order.area.equals(this.area) ) {
			return true;
		}
		return false;
	}
	
	
	public String toOrderTxt() {
		return orderNumber + "," + customerName
				+ "," + stateAbbreviation + "," + taxRate + "," + productType 
				+ "," + area + "," + costPerSquareFoot
				+ "," + laborCostPerSquareFoot + "," + materialCost
				+ "," + laborCost + "," + tax + "," + total;
	}

	@Override
	public String toString() {
		return "Order [orderDate=" + orderDate + ", orderNumber=" + orderNumber + ", customerName=" + customerName
				+ ", stateAbbreviation=" + stateAbbreviation + ", taxRate=" + taxRate + ", productType=" + productType
				+ ", area=" + area + ", costPerSquareFoot=" + costPerSquareFoot + ", laborCostPerSquareFoot="
				+ laborCostPerSquareFoot + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", tax="
				+ tax + ", total=" + total + "]";
	}

	
}
