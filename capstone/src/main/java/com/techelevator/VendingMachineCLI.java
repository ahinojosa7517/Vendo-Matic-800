package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_RESTOCK = "Restock";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_RESTOCK, "Exit" };
	private static final String INVENTORY_FILE_NAME = "vendingmachine.csv";

	private Menu menu;
	private Audit a = new Audit();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		VendingMachine vendor = new VendingMachine(INVENTORY_FILE_NAME, menu, a);
		while(true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if(choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				System.out.println(vendor.getInventory());
			} else if(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				vendor.makePurchase();
			} else if(choice.equals(MAIN_MENU_OPTION_RESTOCK)) {
				// restock vendor
				vendor.restock();
			} else {
				// exit
				break;
			}
		}
		System.out.println("Exiting.\n\nBye!");
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
