package com.revature.bank.menus.dashboard;

import java.io.BufferedReader;
import java.sql.SQLException;

import com.revature.bank.exceptions.InvalidRequestException;
import com.revature.bank.menus.Menu;
import com.revature.bank.models.Customer;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.MenuRouter;

public class UpdateProfile extends Menu {
	private CustomerService customerService;
	public UpdateProfile(BufferedReader consoleReader, MenuRouter router, CustomerService customerService) {
		super("UpdateProfile", "/update_profile", consoleReader, router);
		this.customerService = customerService;
	}

	@Override
	public void render() throws Exception {
		Customer sessionCustomer = customerService.getSessionCustomer();
		if (sessionCustomer == null) {
			System.out.println("session time out,...");
			router.transfer("/login");
			return;
		}
		System.out.println("Update Profile\n");
		
		Customer customer = new Customer();
		
		System.out.println("First Name:- ");
		customer.setFirstName(consoleReader.readLine());
		
		System.out.println("Last Name:- ");
		customer.setLastName(consoleReader.readLine());
		
		System.out.println("Email:- ");
		customer.setEmail(consoleReader.readLine());
		
		System.out.println("Username:- ");
		customer.setUsername(consoleReader.readLine());
		
		System.out.println("password:- ");
		customer.setPassword(consoleReader.readLine());
		
		try {
			customerService.updateCustomer(sessionCustomer,customer);
		}catch (InvalidRequestException e) {
				e.printStackTrace();
		}
		catch ( Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("customer updated");
		router.transfer("/main");
		

	}

}
