package com.revature.bank.menus.dashboard;

import java.io.BufferedReader;

import com.revature.bank.menus.Menu;
import com.revature.bank.models.Customer;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.MenuRouter;

public class Profile extends Menu {
	private CustomerService customerService;

	public Profile(BufferedReader consoleReader, MenuRouter router, CustomerService customerService) {
		super("Profile", "/profile", consoleReader, router);
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
		while (customerService.isSessionActive()) {
			System.out.println("First Name:- " + sessionCustomer.getFirstName());
			System.out.println("Last Name:- " + sessionCustomer.getLastName());
			System.out.println("Email:- " + sessionCustomer.getEmail());
			System.out.println("username:- " + sessionCustomer.getUsername());
			System.out.println("password:-" + sessionCustomer.getPassword() + "\n");

			String menu = "1) Update the profile\n" + "2) return to main menu\n" + "3) logout\n" + ">";
			System.out.println(menu);
			String userSelection = consoleReader.readLine();
			switch (userSelection) {
			case "1":
				router.transfer("/update_profile");
				break;
			case "2":
				router.transfer("/main");
				break;
			case "3":
				customerService.logOut();
				break;

			default:
				System.out.println("invalid selection");
				break;
			}

		}

	}

}
