package com.revature.bank.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.bank.models.Account;
import com.revature.bank.models.Customer;
import com.revature.bank.models.Transaction;
import com.revature.bank.util.ArrayList;
import com.revature.bank.util.List;
import com.revature.bank.util.db.ConnectionFactory;

public class BankDAO implements IDAO<Customer> {
	File customersFile = new File("src/main/resources/data.txt");

	public Customer findByUsernameAndPassword(String username, String password) {

		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {

			String sql = "select customer_id,customer_first_name,customer_last_name,customer_email,customer_username"
					+ ",customer_password" + " from customer where customer_username = ? and customer_password = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerId(rs.getString("customer_id"));
				customer.setFirstName(rs.getString("customer_first_name"));
				customer.setLastName(rs.getString("customer_last_name"));
				customer.setEmail(rs.getString("customer_email"));
				customer.setUsername(rs.getString("customer_username"));
				customer.setPassword(rs.getString("customer_password"));

				return customer;
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Customer create(Customer customer) {

//		try(FileWriter fileWriter = new FileWriter(customersFile, true);) {
//			fileWriter.write(customer.toFileString() + "\n");
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException("Error persistin user to file");
//		}
//		return customer;
		String sql = "INSERT INTO customer(customer_first_name, customer_last_name,"
				+ "customer_email,customer_username,customer_password)VALUES(?,?,?,?,?)";
		int rs = 0;
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
//			INSERT INTO table_name(column1, column2, …)
//			VALUES (value1, value2, …

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getUsername());
			pstmt.setString(5, customer.getPassword());
			rs = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	public int createAccount(String customerId) {
		int rows = 0;
		String sql = "INSERT INTO account(customer_id, balance) VALUES(?,?)";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(customerId));
			pstmt.setDouble(2, 0);
			int rs = pstmt.executeUpdate();
			rows = rs;
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}
	public boolean createAccount(Account account) {
		return createAccount(String.valueOf(account.getCustomerId())) == 1;
	}

	public Customer findCustomerByEmail(String email) {
		String sql = "SELECT customer_id, customer_first_name, customer_last_name,customer_email,customer_username,customer_password\r\n"
				+ "FROM customer WHERE customer_email  = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer();
//				String.valueOf(rs.getInt
				customer.setCustomerId(String.valueOf(rs.getInt("customer_id")));
				customer.setFirstName(rs.getString("customer_first_name"));
				customer.setFirstName(rs.getString("customer_last_name"));
				customer.setEmail(rs.getString("customer_email"));
				customer.setUsername(rs.getString("customer_username"));
				customer.setPassword(rs.getString("customer_password"));
				return customer;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Customer> finAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Customer customer) {
		// "UPDATE Messages SET description = ?, author
		// = ? WHERE id = ? AND seq_num = ?");
		int i = 0;
		String syntax = "UPDATE customer SET customer_first_name = ?, customer_last_name = ?,"
				+ "customer_email = ?, customer_username = ?, customer_password = ?" + "WHERE " + "customer_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(syntax);
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getUsername());
			pstmt.setString(5, customer.getPassword());
			pstmt.setInt(6, Integer.parseInt(customer.getCustomerId()));
			i = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i != 0;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public double getBalance(Customer customer) {
		double balance = 0;
		String sql = "select balance from account where customer_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(customer.getCustomerId()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				balance = rs.getDouble("balance");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;

	}

	public boolean setBalance(Customer customer, double balance) {
		int i = 0;
		String syntax = "UPDATE account SET balance = ? WHERE " + "customer_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(syntax);
			pstmt.setDouble(1, balance);
			pstmt.setInt(2, Integer.parseInt(customer.getCustomerId()));
			i = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i != 0;
	}

	public Account getAccount(Customer customer) {
		Account account = null;
		String sql = "select account_id, customer_id, balance from account where customer_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(customer.getCustomerId()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setCustomerId(rs.getInt("customer_id"));
				account.setBalance(rs.getDouble("balance"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account;
	}

	public void setTransaction(Transaction transaction) {
		String sql = "insert into transactions(account_id, transaction_date, transaction_value) values (?,?,?)";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, transaction.getAccountId());
			pstmt.setString(2, transaction.getTransactionDate());
			pstmt.setDouble(3, transaction.getTransactionValue());
//			System.out.println("transaction value:- " + transaction.getTransactionValue());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Transaction> getAllTransactions(String accountId) {
		ArrayList<Transaction> transactions = new ArrayList<>();
		String sql = "select transaction_id,account_id,transaction_date, transaction_value from transactions where account_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()){
			System.out.println(accountId);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,Integer.parseInt(accountId));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(rs.getInt("transaction_id"));
				transaction.setAccountId(rs.getInt("account_id"));
				transaction.setTransactionDate(rs.getString("transaction_date"));
				transaction.setTransactionValue(rs.getDouble("transaction_value"));
				transactions.add(transaction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}

	public ArrayList<Account> getAllAccounts(Customer customer) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		String sql = "select account_id, customer_id, balance from account where customer_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(customer.getCustomerId()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Account account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setCustomerId(rs.getInt("customer_id"));
				account.setBalance(rs.getDouble("balance"));
				accounts.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	public double getBalance(Customer customer, String accountId) {
		double balance = 0;
		String sql = "select balance from account where customer_id = ? and account_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(customer.getCustomerId()));
			pstmt.setInt(2, Integer.parseInt(accountId));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				balance = rs.getDouble("balance");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	public boolean setBalance(Customer customer, double balance, String accountId) {
		int i = 0;
		String syntax = "UPDATE account SET balance = ? WHERE " + "customer_id = ? and account_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(syntax);
			pstmt.setDouble(1, balance);
			pstmt.setInt(2, Integer.parseInt(customer.getCustomerId()));
			pstmt.setInt(3, Integer.parseInt(accountId));
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i != 0;
	}

	public Account getAccount(Customer customer, String accountId) {
		Account account = null;
		String sql = "select account_id, customer_id, balance from account where customer_id = ? and account_id = ?";
		try (Connection conn = ConnectionFactory.getInstance().connectPostgre()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(customer.getCustomerId()));
			pstmt.setInt(2, Integer.parseInt(accountId));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				account = new Account();
				account.setAccountId(rs.getInt("account_id"));
				account.setCustomerId(rs.getInt("customer_id"));
				account.setBalance(rs.getDouble("balance"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account;
	}

	

}
