package com.revature.bank.menus;

import java.io.BufferedReader;

import com.revature.bank.exceptions.AuthenticationException;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.MenuRouter;

public class LoginMenu extends Menu {
	private final CustomerService customerService;

	public LoginMenu(BufferedReader consoleReader, MenuRouter router, CustomerService customerService) {
		super("Login", "/login", consoleReader, router);
		this.customerService = customerService;
	}

	@Override
	public void render() throws Exception {
		System.out.println("Please enter your credentials for you account.");
		System.out.print("Username: ");
		String username = consoleReader.readLine();
		System.out.print("Password: ");
		String password = consoleReader.readLine();
		try {
			if(customerService.authenticateCustomer(username, password))router.transfer("/main");
			System.out.println("the user you provided is not correct");
			router.transfer("/login");

		} catch (AuthenticationException e) {
			System.out.println("no user match, please check the input or try to register");
			e.printStackTrace();
		}
	}

}
