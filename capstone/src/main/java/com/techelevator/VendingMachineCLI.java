package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.*;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, "Exit" };
	private static final String INVENTORY_FILE_NAME = "vendingmachine.csv";

	private Menu menu;
	private List<VendingMachineItem> vendingInventory = stockInventory();
	private double balance = 0.0;
	private Audit a = new Audit();

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {



		while(true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				printInventory();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				makePurchase();
			} else {
				// exit
				break;
			}
		}
		System.out.println("Bye!");
	}

	private void makePurchase() {
		NumberFormat f = NumberFormat.getCurrencyInstance();
		Scanner userIn = new Scanner(System.in);
		while(true) {
			System.out.println("Current balance: " + f.format(balance));
			String choice = (String) menu.getChoiceFromOptions(new String[]{"Feed Money", "Select Product", "Finish Transaction"});

			if(choice.equals("Feed Money")){
				Integer moneyChoice = (Integer) menu.getChoiceFromOptions(new Integer[]{1, 2, 5, 10});
				a.log("FEED MONEY: " + f.format(moneyChoice), moneyChoice + balance);

				balance += moneyChoice;
			}
			if(choice.equals("Select Product")) {
				printInventory();

				System.out.print("Please choose an option >>> ");
				choice = userIn.nextLine();

				VendingMachineItem item = getItem(choice);

				if(item == null) {
					System.out.println("Selection is invalid.");
					continue;
				}
				if(item.getAvailable().equals("SOLD OUT")) {
					System.out.println("Selection is sold out.");
					continue;
				}
				if(item.getPrice() > balance) {
					System.out.println("Not enough money provided.");
					continue;
				}

				a.log(item.getName() + " " + item.getLocation() + " " + balance, balance - item.getPrice());

				System.out.println();
				System.out.println("Dispensing " + item.getName() + " for " + f.format(item.getPrice()));
				System.out.println(item.message());
				System.out.println();
				// dispense
				balance -= item.getPrice();
				item.dispenseItem();
			}
			if(choice.equals("Finish Transaction")){
				System.out.println("Your change is " + f.format(balance));
				a.log("GIVE CHANGE " + f.format(balance), 0.0);

				int coins;

				coins = countCoins(balance, 0.25);
				balance -= coins * 0.25;
				System.out.println(coins + " quarters.");

				coins = countCoins(balance, 0.10);
				balance -= coins * 0.10;
				System.out.println(coins + " dimes.");

				coins = countCoins(balance, 0.05);
				balance -= coins * 0.05;
				System.out.println(coins + " nickels.");

				coins = countCoins(balance, 0.01);
				balance -= coins * 0.01;
				System.out.println(coins + " pennies.");

				balance = 0.0;
				System.out.println("ending balance " + balance);
				break;
			}
		}
	}

	private int countCoins(double amount, double coin) {
		int count = 0;

		while(amount > coin) {
			amount -= coin;
			count++;
		}

		return count;
	}

	private VendingMachineItem getItem(String location) {
		for(VendingMachineItem item : vendingInventory) {
			if(item.getLocation().equals(location)) {
				return item;
			}
		}

		return null;
	}

	private void printInventory() {
		for(VendingMachineItem item : vendingInventory) {
			System.out.println(item);
		}
	}

	private List<VendingMachineItem> stockInventory() {
//		Menu menu = new Menu(System.in, System.out);
		File vendingStock = new File(INVENTORY_FILE_NAME);
		List<VendingMachineItem> vendingInventory = new ArrayList<>();

		try (Scanner fileIn = new Scanner(vendingStock)) {
			while(fileIn.hasNextLine()) {
				String line = fileIn.nextLine();
				String[] splitLine = line.split("\\|");
				VendingMachineItem item;

				if(splitLine[3].equals("Chip")) {
					item = new Chip(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]));
				} else if(splitLine[3].equals("Candy")) {
					item = new Candy(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]));
				} else if(splitLine[3].equals("Drink")) {
					item = new Drink(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]));
				} else if(splitLine[3].equals("Gum")) {
					item = new Gum(splitLine[0], splitLine[1], Double.parseDouble(splitLine[2]));
				} else {
					item = new VendingMachineItem() {
						@Override
						public String message() {
							return null;
						}
					};
				}

				vendingInventory.add(item);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return vendingInventory;
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
