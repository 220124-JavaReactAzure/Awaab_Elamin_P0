package com.revature.bank.menus.dashboard;

import java.io.BufferedReader;

import com.revature.bank.menus.Menu;
import com.revature.bank.models.Customer;
import com.revature.bank.models.Transaction;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.ArrayList;
import com.revature.bank.util.MenuRouter;

public class OneAccountMenu extends Menu {
	CustomerService customerService;
	String accountId;

	public OneAccountMenu(BufferedReader consoleReader, MenuRouter router, CustomerService customerService,
			String accountId) {
		super("OneAccountMenu", "/oneAccount", consoleReader, router);
		this.customerService = customerService;
		this.accountId = accountId;
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
			System.out.print(
					"1) Balance\n" + "2) Withdraw \n" + "3) Deposit\n" + "4) View Transaction \n" + "5) return main\n");
			String userSelection = consoleReader.readLine();
			switch (userSelection) {
			case "1":
				System.out.println("Balance");
				System.out.println("Balance = " + customerService.getBalance(sessionCustomer, accountId));
				router.transfer("/main");
				break;
			case "2":
				System.out.println("Withdraw");
				System.out.println("Enter the value > ");
				double withdrawValue = Double.parseDouble(consoleReader.readLine()) * -1;
//				System.out.println("withdraw value:- " + withdrawValue);
				if (customerService.deposit(withdrawValue, accountId)) {
					System.out.println("Withdraw done correcltly");
				} else {
					System.out.println("Withdraw did not make!");
				}
				router.transfer("/main");
				break;
			case "3":
				System.out.println("Deposit");
				System.out.println("Enter the value:- ");
				double depositValue = Double.parseDouble(consoleReader.readLine());
				if (customerService.deposit(depositValue, accountId)) {
					System.out.println("Deposit done correcltly");
				} else {
					System.out.println("Deposit did not make!");
				}
				router.transfer("/main");
				break;
			case "4":
				System.out.println("View Transaction");
				ArrayList<Transaction> transactions = customerService.getAllTransaction(sessionCustomer, accountId);
				System.out.println("Account id = " + accountId);
				System.out.println(transactions);
				if (transactions.size() == 0) {
					System.out.println("there is no transactions!");
				}				
				router.transfer("/main");
				break;
			case "5":
				router.transfer("/main");
				break;
			default:
				break;
			}
		}

	}

}
