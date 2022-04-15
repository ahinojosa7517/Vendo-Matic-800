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

    private final double QUARTER = 0.25;
    private final double DIME = 0.10;
    private final double NICKEL = 0.05;
    private final double PENNY = 0.01;

    private String inventoryFileName;
    private double balance = 0.0;
    private Menu menu;
    private Audit a;
    private List<VendingMachineItem> vendingInventory;
    private NumberFormat f = NumberFormat.getCurrencyInstance();
    private DecimalFormat d = new DecimalFormat("#0.00");
    private List<Object> testParams = null;

    public void setTestParams(List<Object> testParams) {
        this.testParams = testParams;
    }

    public void restock() {
        vendingInventory = stockInventory();
    }

    public String makePurchase() {
        int i = 1; String testResult = ""; String test = "";// test-specific variables
        if(testParams != null) test = (String) testParams.get(0);
        Scanner userIn = new Scanner(System.in);
        VendingMachineItem item;
        while(true) {
            String choice;
            if(testParams == null) {
                System.out.println("Current balance: " + f.format(balance));
                choice = (String) menu.getChoiceFromOptions(new String[]{"Feed Money", "Select Product", "Finish Transaction"});
            } else {
                choice = (String) testParams.get(i);
                i++;
            }

            if(choice.equals("Feed Money")){
                Integer moneyChoice;
                if(testParams == null) {
                    moneyChoice = (Integer) menu.getChoiceFromOptions(new Integer[]{1, 2, 5, 10});
                    a.log("FEED MONEY: " + f.format(moneyChoice), moneyChoice + balance);
                } else {
                    moneyChoice = (Integer) testParams.get(i);
                    i++;
                }

                balance += moneyChoice;
                if(test.equals("test_makePurchase_feed_money")) testResult += "" + balance;
            }
            if(choice.equals("Select Product")) {
                if(testParams == null) {
                    printInventory();

                    System.out.print("Please choose an option >>> ");
                    choice = userIn.nextLine();
                } else {
                    choice = (String) testParams.get(i);
                    i++;
                }

                item = vendingInventory.get(getItemIndex(choice));

                if(item == null) {
                    if(testParams == null)
                        System.out.println("Selection is invalid.");
                    continue;
                }
                if(item.getAvailable().equals("SOLD OUT")) {
                    if(testParams == null)
                        System.out.println("Selection is sold out.");
                    continue;
                }
                if(item.getPrice() > balance) {
                    if(testParams == null)
                        System.out.println("Not enough money provided.");
                    continue;
                }

                a.log(item.getName() + " " + item.getLocation() + " " + f.format(balance), balance - item.getPrice());

                if(testParams == null) {
                    System.out.println();
                    System.out.println("Dispensing " + item.getName() + " for " + f.format(item.getPrice()));
                    System.out.println(item.message());
                    System.out.println();
                }
                // dispense
                balance -= item.getPrice();
                balance = Double.parseDouble(d.format(balance));
                item.dispenseItem();
//                vendingInventory.get(getItemIndex(choice)).dispenseItem();
            }
            if(choice.equals("Finish Transaction")){
                if(testParams == null)
                    System.out.println("Your change is " + f.format(balance));
                a.log("GIVE CHANGE " + f.format(balance), 0.0);

                int coins;

                coins = countCoins(balance, QUARTER);
                if(testParams == null)
                    System.out.println(coins + " quarters.");
                balance -= coins * QUARTER;
                balance = Double.parseDouble(d.format(balance));

                coins = countCoins(balance, DIME);
                if(testParams == null)
                    System.out.println(coins + " dimes.");
                balance -= coins * DIME;
                balance = Double.parseDouble(d.format(balance));

                coins = countCoins(balance, NICKEL);
                if(testParams == null)
                    System.out.println(coins + " nickels.");
                balance -= coins * NICKEL;
                balance = Double.parseDouble(d.format(balance));

                coins = countCoins(balance, PENNY);
                if(testParams == null)
                    System.out.println(coins + " pennies.");

                balance = 0.0;
//				System.out.println("ending balance " + balance);
                break;
            }
        }
        return testResult;
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

    public VendingMachine(String inventoryFileName, Menu menu, Audit a, List<Object> testParams) {
        this.inventoryFileName = inventoryFileName;
        this.menu = menu;
        this.a = a;
        this.vendingInventory = stockInventory();
        this.testParams = testParams;
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
