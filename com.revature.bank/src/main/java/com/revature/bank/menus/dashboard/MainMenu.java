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
			System.out.print("1) Profile\n" + "2) View Balance \n" + "3) View Transaction\n" + "4) Withdraw\n"
					+ "5) Deposit\n" + "6) Logout\n");
			String userSelection = consoleReader.readLine();
			switch (userSelection) {
			case "1":
				System.out.println("View/edit profile selected");
				router.transfer("/profile");
				break;
			case "2":
				System.out.println("Balance");
				System.out.println("Balance = " + customerService.getBalance(sessionCustomer));
				router.transfer("/main");
				break;
			case "3":
				System.out.println("View Transaction");
				ArrayList<Transaction> transactions = customerService.getAllTransaction(sessionCustomer);
				System.out.println(transactions);
				router.transfer("/main");
				break;
			case "4":
				System.out.println("Withdraw");
				System.out.println("Enter the value > ");
				double withdrawValue = Double.parseDouble(consoleReader.readLine()) * -1;
//				System.out.println("withdraw value:- " + withdrawValue);
				if (customerService.deposit(withdrawValue)) {
					System.out.print("Withdraw done correcltly");
				} else {
					System.out.print("Withdraw did not make!");
				}
				router.transfer("/main");
				break;
			case "5":
				System.out.println("Deposit");
				System.out.println("Enter the value:- ");
				double depositValue = Double.parseDouble(consoleReader.readLine());
//				System.out.println("Deposit value:- " + depositValue);
//				consoleReader.readLine();
				if (customerService.deposit(depositValue)) {
					System.out.println("Deposit done correcltly");
				} else {
					System.out.println("Deposit did not make!");
				}
				router.transfer("/main");
				break;
			case "6":
				customerService.logOut();
//				router.transfer("/StartUp");
				break;
			default:
				System.out.println("invalid selection");
				break;
			}
		}
		router.transfer("/StartUp");

	}

}
