package com.revature.bank.menus;

import java.io.BufferedReader;

import com.revature.bank.exceptions.InvalidRequestException;
import com.revature.bank.models.Customer;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.MenuRouter;

public class RegisterMenu extends Menu {
	CustomerService customerService;

	public RegisterMenu(BufferedReader consoleReader, MenuRouter router, CustomerService customerService) {
		super("Register", "/register", consoleReader, router);
		this.customerService = customerService;
	}

	@Override
	public void render() throws Exception {
		System.out.println("Customer Registration:-");
		System.out.print("First Name: ");
		String firstName = consoleReader.readLine();
		System.out.print("Last Name: ");
		String lastName = consoleReader.readLine();
		System.out.print("Email: ");
		String email = consoleReader.readLine();
		System.out.print("Username: ");
		String username = consoleReader.readLine();
		System.out.print("Password: ");
		String password = consoleReader.readLine();

//		System.out.printf("Provided by user: firstName: %s, lastName: %s, email: %s, username: %s, password: %s",
//				firstName, lastName, email, username, password).println();

		Customer customer = new Customer(firstName, lastName, email, username, password);

//		System.out.printf("Data provided: %s\n", customer.toString()).println();

		try {
			customerService.registerNewCustomer(customer);
			System.out.println("registeration done correctly");
			router.transfer("/login");
		} catch (InvalidRequestException e) {
//			e.printStackTrace();
			System.out.println("\n\n YOU HAVE PROVIDED INVALID DATA PLEASE TRY AGAIN\n\n\n");

			router.transfer("/StartUp");
		}
	}

}
