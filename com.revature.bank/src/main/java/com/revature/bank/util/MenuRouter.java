package com.revature.bank.util;

import com.revature.bank.menus.Menu;

public class MenuRouter {
	private final LinkedList<Menu> menus;

	public MenuRouter() {
//		System.out.println("menu router is working");
		menus = new LinkedList<>();
	}

	public void transfer(String route) throws Exception {
		for (int i = 0; i < menus.size(); i++) {
			Menu currentMenu = menus.get(i);
			if (currentMenu.getRoute().equals(route)) {
				currentMenu.render();
			}
		}
	}

	public void addMenu(Menu menu) {
		menus.add(menu);

	}

}
