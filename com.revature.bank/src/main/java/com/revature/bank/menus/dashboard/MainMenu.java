package com.revature.bank.menus.dashboard;

import java.io.BufferedReader;

import com.revature.bank.menus.Menu;
import com.revature.bank.models.Customer;
import com.revature.bank.models.Transaction;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.ArrayList;
import com.revature.bank.util.MenuRouter;

public class MainMenu extends Menu {
	CustomerService customerService;

	public MainMenu(BufferedReader consoleReader, MenuRouter router, CustomerService customerService) {
		super("Main", "/main", consoleReader, router);
		this.customerService = customerService;
	}

	@Override
	public void render() throws Exception {
		Customer sessionCustomer = customerService.getSessionCustomer();
		if (sessionCustomer == null) {
			System.out.println("session time out,...");
			router.transfer("/StartUp");
			return;
		}
		System.out.println("Welcome " + sessionCustomer.getFirstName() + " " + sessionCustomer.getLastName());
		while (customerService.isSessionActive()) {
			System.out.print("1) Profile\n" + "2) accounts \n" + "3) Logout\n");
			String userSelection = consoleReader.readLine();
			switch (userSelection) {
			case "1":
				System.out.println("View/edit profile selected");
				router.transfer("/profile");
				break;
			case "2":
				router.transfer("/account");
				break;
			case "3":
				customerService.logOut();
				break;
			default:
				System.out.println("invalid selection");
				break;
			}
		}
		router.transfer("/StartUp");

	}

}
