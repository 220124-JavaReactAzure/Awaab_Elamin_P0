package com.revature.bank.menus.dashboard;

import java.io.BufferedReader;

import com.revature.bank.menus.Menu;
import com.revature.bank.models.Customer;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.MenuRouter;

public class AccountMenu extends Menu {
	CustomerService customerService;

	public AccountMenu(BufferedReader consoleReader, MenuRouter router, CustomerService customerService) {
		super("Account", "/account", consoleReader, router);
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
		while (customerService.isSessionActive()) {
			System.out.print("1) Create new Account\n" + "2) View current accounts \n" + "3) return to main menu\n"
					+ "4) Logout\n");
			String userSelection = consoleReader.readLine();
			switch (userSelection) {
			case "1":
				System.out.println("Create new Account");
				if (customerService.createNewAccount(sessionCustomer)) {
					System.out.println("Account Created correctly");
				} else {
					System.out.println("Accout did not created!");
				}
				router.transfer("/currentAccounts");
				break;
			case "2":
				System.out.println("View current accounts");
				router.transfer("/currentAccounts");
				break;
			case "3":
				router.transfer("/main");
				break;
			case "4":
				customerService.logOut();
				break;
			default:
				System.out.println("invalid selection");
				break;
			}
		}

	}

}
