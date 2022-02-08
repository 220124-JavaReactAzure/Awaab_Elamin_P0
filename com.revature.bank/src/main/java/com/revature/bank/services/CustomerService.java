package com.revature.bank.services;

import java.time.LocalDate;

import com.revature.bank.dao.BankDAO;
import com.revature.bank.exceptions.AuthenticationException;
import com.revature.bank.exceptions.InvalidRequestException;
import com.revature.bank.models.Account;
import com.revature.bank.models.Customer;
import com.revature.bank.models.Transaction;
import com.revature.bank.util.ArrayList;

public class CustomerService {
	private BankDAO bankdao = new BankDAO();
	private Customer sessionCustomer;

	public CustomerService() {
		super();
		this.sessionCustomer = null;
	}

	public Customer getSessionCustomer() {
		return sessionCustomer;
	}

	public boolean registerNewCustomer(Customer customer) {
		if (!isCustomerValid(customer)) {
			throw new InvalidRequestException("Invalid data provided");
		}

		// need to verify the new customer information is not duplicate in the system
		bankdao.create(customer);
		customer = bankdao.findCustomerByEmail(customer.getEmail());
		int row = bankdao.createAccount(customer.getCustomerId());
		return true;
	}

	public boolean authenticateCustomer(String username, String password) {
		if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
			throw new InvalidRequestException(
					"Either username or password is an invalid entry. Please try logging in again");
		}

		Customer authenticateCustomer = bankdao.findByUsernameAndPassword(username, password);

		if (authenticateCustomer == null) {
//			throw new AuthenticationException("Unauthenticated user, information provided was not found in our database.");
			return false;
		}
		sessionCustomer = authenticateCustomer;
		return true;
	}

	public boolean isCustomerValid(Customer customer) {
		if (customer == null)
			return false;
		if (customer.getFirstName() == null || customer.getFirstName().trim().equals(""))
			return false;
		if (customer.getLastName() == null || customer.getLastName().trim().equals(""))
			return false;
		if (customer.getEmail() == null || customer.getEmail().trim().equals(""))
			return false;
		if (customer.getUsername() == null || customer.getUsername().trim().equals(""))
			return false;
		return customer.getPassword() != null || !customer.getPassword().trim().equals("");
	}

	public boolean isSessionActive() {
		return sessionCustomer != null;
	}

	public void logOut() {
		sessionCustomer = null;

	}

	public void updateCustomer(Customer sessionCustomer, Customer customer) {
		if (!isCustomerValid(customer)) {
			throw new InvalidRequestException("Invalid data provided");
		}
		try {
			customer.setCustomerId(sessionCustomer.getCustomerId());
			bankdao.update(customer);

		} catch (InvalidRequestException e) {
			e.printStackTrace();
		}

	}

	public double getBalance(Customer sessionCustomer) {
		double balance = 0;
		try {
			Customer customer = bankdao.findCustomerByEmail(sessionCustomer.getEmail());
			balance = bankdao.getBalance(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return balance;
	}

	public boolean deposit(double value) {
		boolean result = false;
		try {
			Customer customer = bankdao.findCustomerByEmail(sessionCustomer.getEmail());
//			System.out.println("deposit value:- " + depositValue);
			double balanceBefore = bankdao.getBalance(customer);
			if (value < 0 && balanceBefore + value < 0) {
				System.out.println("your balance is " + balanceBefore);
				System.out.println("can not withdraw more than current balance");
				return false;
			}
//			System.out.println("balance before:- " + balanceBefore);
			double balance = balanceBefore + value;
//			System.out.println("balance after:- " + balance);
			result = bankdao.setBalance(customer, balance);
			Account account = bankdao.getAccount(customer);
			Transaction transaction = new Transaction();
			transaction.setAccountId(account.getAccountId());
			transaction.setTransactionDate(LocalDate.now().toString());
			transaction.setTransactionValue(value);
			bankdao.setTransaction(transaction);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Transaction> getAllTransaction(Customer sessionCustomer, String accountId) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		try {
			transactions = bankdao.getAllTransactions(accountId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return transactions;
		
	}

	public ArrayList<Account> getAllAccounts(Customer sessionCustomer) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			Customer customer = bankdao.findCustomerByEmail(sessionCustomer.getEmail());
			accounts = bankdao.getAllAccounts(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return accounts;
	}

	public String getBalance(Customer sessionCustomer2, String accountId) {
		double balance = 0;
		try {
			Customer customer = bankdao.findCustomerByEmail(sessionCustomer.getEmail());
			balance = bankdao.getBalance(customer, accountId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(balance);
	}

	public boolean deposit(double value, String accountId) {
		boolean result = false;
		try {
			Customer customer = bankdao.findCustomerByEmail(sessionCustomer.getEmail());
			double balanceBefore = bankdao.getBalance(customer, accountId);
			if (value < 0 && balanceBefore + value < 0) {
				System.out.println("your balance is " + balanceBefore);
				System.out.println("can not withdraw more than current balance");
				return false;
			}
			double balance = balanceBefore + value;
			result = bankdao.setBalance(customer, balance, accountId);
			Account account = bankdao.getAccount(customer, accountId);
			Transaction transaction = new Transaction();
			transaction.setAccountId(account.getAccountId());
			transaction.setTransactionDate(LocalDate.now().toString());
			transaction.setTransactionValue(value);
			bankdao.setTransaction(transaction);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean createNewAccount(Customer sessionCustomer2) {
		boolean result = false;
		try {
			Customer customer = bankdao.findCustomerByEmail(sessionCustomer.getEmail());
			Account account = new Account();
			account.setCustomerId(Integer.parseInt(customer.getCustomerId()) );
			account.setBalance(0);
			result = bankdao.createAccount(account);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
