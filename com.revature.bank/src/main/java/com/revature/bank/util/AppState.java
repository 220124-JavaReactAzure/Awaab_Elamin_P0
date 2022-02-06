package com.revature.bank.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.revature.bank.menus.FirstMenu;
import com.revature.bank.menus.LoginMenu;
import com.revature.bank.menus.RegisterMenu;
import com.revature.bank.menus.dashboard.MainMenu;
import com.revature.bank.menus.dashboard.Profile;
import com.revature.bank.menus.dashboard.UpdateProfile;
import com.revature.bank.services.CustomerService;

public class AppState {
	private static boolean isRunning;
	private final MenuRouter router;

	public AppState() {
//		System.out.println("App state class work");
		
		router = new MenuRouter();
		isRunning = true;
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		CustomerService customerService = new CustomerService();
		router.addMenu(new FirstMenu(consoleReader, router));
		router.addMenu(new RegisterMenu(consoleReader,router, customerService));
		router.addMenu(new LoginMenu(consoleReader, router, customerService));
		router.addMenu(new MainMenu(consoleReader, router, customerService));
		router.addMenu(new Profile(consoleReader, router, customerService));
		router.addMenu(new UpdateProfile(consoleReader, router, customerService));
				
	}
	public static void shutdown() {
		isRunning = false;
	}
	public void startup() {
		try {
			while(isRunning) {
				router.transfer("/StartUp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
