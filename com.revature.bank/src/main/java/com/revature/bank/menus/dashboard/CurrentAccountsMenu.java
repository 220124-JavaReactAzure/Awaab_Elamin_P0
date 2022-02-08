package com.revature.bank.menus.dashboard;

import java.io.BufferedReader;

import com.revature.bank.menus.Menu;
import com.revature.bank.models.Account;
import com.revature.bank.models.Customer;
import com.revature.bank.services.CustomerService;
import com.revature.bank.util.ArrayList;
import com.revature.bank.util.MenuRouter;

public class CurrentAccountsMenu extends Menu {
private CustomerService customerService;
	public CurrentAccountsMenu(BufferedReader consoleReader, MenuRouter router, CustomerService customerService) {
		super("currentAccounts", "/currentAccounts", consoleReader, router);
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
			ArrayList<Account> accounts = customerService.getAllAccounts(sessionCustomer);
			System.out.println(accounts);
			System.out.println("Enter the account id");
			String accountId = consoleReader.readLine();
			
			
			router.addMenu(new OneAccountMenu(consoleReader,router, customerService, accountId));
			router.transfer("/oneAccount");
			
		}
		
	}

}
