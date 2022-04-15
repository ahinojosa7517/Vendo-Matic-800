package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {

    private String inventoryFileName;
    private double balance = 0.0;
    private Menu menu;
    private Audit a;
    private List<VendingMachineItem> vendingInventory;
    private NumberFormat f = NumberFormat.getCurrencyInstance();
    private DecimalFormat d = new DecimalFormat("#0.00");


    public void restock() {
        vendingInventory = stockInventory();
    }

    public void makePurchase() {
        Scanner userIn = new Scanner(System.in);
        VendingMachineItem item;
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

                item = vendingInventory.get(getItemIndex(choice));

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

                a.log(item.getName() + " " + item.getLocation() + " " + f.format(balance), balance - item.getPrice());

                System.out.println();
                System.out.println("Dispensing " + item.getName() + " for " + f.format(item.getPrice()));
                System.out.println(item.message());
                System.out.println();
                // dispense
                balance -= item.getPrice();
                balance = Double.parseDouble(d.format(balance));
                item.dispenseItem();
//                vendingInventory.get(getItemIndex(choice)).dispenseItem();
            }
            if(choice.equals("Finish Transaction")){
                System.out.println("Your change is " + f.format(balance));
                a.log("GIVE CHANGE " + f.format(balance), 0.0);

                int coins;

                coins = countCoins(balance, 0.25);
                System.out.println(coins + " quarters.");
                balance -= coins * 0.25;
                balance = Double.parseDouble(d.format(balance));

                coins = countCoins(balance, 0.10);
                System.out.println(coins + " dimes.");
                balance -= coins * 0.10;
                balance = Double.parseDouble(d.format(balance));

                coins = countCoins(balance, 0.05);
                System.out.println(coins + " nickels.");
                balance -= coins * 0.05;
                balance = Double.parseDouble(d.format(balance));

                coins = countCoins(balance, 0.01);
                System.out.println(coins + " pennies.");

                balance = 0.0;
//				System.out.println("ending balance " + balance);
                break;
            }
        }
    }

    private int countCoins(double amount, double coin) {
        int count = 0;

        while(amount >= coin) {
            amount -= coin;
            count++;
        }

        return count;
    }

//    private VendingMachineItem getItem(String location) {
//        for(VendingMachineItem item : vendingInventory) {
//            if(item.getLocation().equals(location)) {
//                return item;
//            }
//        }
//
//        return null;
//    }

    private int getItemIndex(String location) {
        int i = 0;
        while(i < vendingInventory.size()) {
            if(vendingInventory.get(i).getLocation().equals(location)) {
                return i;
            }

            i++;
        }
        return -1;
    }

    public void printInventory() {
        for(VendingMachineItem item : vendingInventory) {
            System.out.println(item);
        }
    }

    private List<VendingMachineItem> stockInventory() {
//		Menu menu = new Menu(System.in, System.out);
        File vendingStock = new File(inventoryFileName);
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

    public VendingMachine(String inventoryFileName, Menu menu, Audit a) {
        this.inventoryFileName = inventoryFileName;
        this.menu = menu;
        this.a = a;
        this.vendingInventory = stockInventory();
    }

    //    public VendingMachine() {
//    }

    public String getInventoryFileName() {
        return inventoryFileName;
    }

    public void setInventoryFileName(String inventoryFileName) {
        this.inventoryFileName = inventoryFileName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
