package com.revature.bank.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.revature.bank.menus.FirstMenu;

public class AppState {
	private static boolean isRunning;
	private final MenuRouter router;

	public AppState() {
//		System.out.println("App state class work");
		
		router = new MenuRouter();
		isRunning = true;
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		router.addMenu(new FirstMenu(consoleReader, router));
				
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
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
