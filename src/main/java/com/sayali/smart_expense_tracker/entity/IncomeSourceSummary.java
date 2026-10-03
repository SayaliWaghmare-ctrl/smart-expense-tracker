package com.sayali.smart_expense_tracker.entity;

import java.math.BigDecimal;

public class IncomeSourceSummary {

	private String source;
	private BigDecimal totalAmount;
	
	//counstuctor
	
	public IncomeSourceSummary(String source, BigDecimal totalAmount) {
	
		this.source = source;
		this.totalAmount = totalAmount;
	}
	
	
	//getter and setter

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
