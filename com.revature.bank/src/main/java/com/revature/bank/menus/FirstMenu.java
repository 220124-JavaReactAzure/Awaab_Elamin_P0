package com.revature.bank.menus;

import java.io.BufferedReader;

import com.revature.bank.util.AppState;
import com.revature.bank.util.MenuRouter;

public class FirstMenu extends Menu{

	public FirstMenu(BufferedReader consoleReader, MenuRouter router) {
		super("StartUp","/StartUp",consoleReader, router);
//		System.out.println("Start up menu is working");
	}

	@Override
	public void render() throws Exception {
		System.out.print(
				"Welcome to Awaab Bank\n" + "1) Login\n" + "2) Register\n" + "3) Exits\n" + "> ");
		
		String userSelection = consoleReader.readLine();

		switch (userSelection) {
		case "1":
			router.transfer("/login");
			break;
		case "2":
			router.transfer("/register");
			break;
		case "3":
			AppState.shutdown();
			break;
		default:
			System.out.println("What on earth are you trying to tell me to do?!?!");
			break;
		}
		
	}
		
}


