package com.revature.bank.models;

import java.io.Serializable;

public class Account implements Serializable {
	private int accountId;
	private int customerId;
	private double balance;
	public Account() {
		super();
	}
	public Account(int accountId, int customerId, double balance) {
		super();
		this.accountId = accountId;
		this.customerId = customerId;
		this.balance = balance;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "accountId= " + accountId + ",balance= " + balance;
	}
	
	
}
