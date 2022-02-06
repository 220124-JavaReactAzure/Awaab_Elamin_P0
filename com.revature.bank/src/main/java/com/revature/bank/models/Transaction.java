package com.revature.bank.models;

import java.io.Serializable;

public class Transaction implements Serializable {
	private int transactionId;
	private int accountId;
	private String transactionDate;
	private double transactionValue;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(int transactionId, int accountId, String transactionDate, double transactionValue) {
		super();
		this.transactionId = transactionId;
		this.accountId = accountId;
		this.transactionDate = transactionDate;
		this.transactionValue = transactionValue;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(double transactionValue) {
		this.transactionValue = transactionValue;
	}

	@Override
	public String toString() {
		return " Date= " + transactionDate + ", Value= " + transactionValue;
	}

}
