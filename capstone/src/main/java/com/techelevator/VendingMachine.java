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

    // test-specific variables
    private List<Object> testParams = null;

    private int i;
    private String testResult;
    private String test;

    public void setTestParams(List<Object> testParams) {
        this.testParams = testParams;
    }

    public void restock() {
        vendingInventory = stockInventory();
    }

    public String makePurchase() {
        i = 1; testResult = ""; test = "";
        if(testParams != null) test = (String) testParams.get(0); // if available, set test title
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
                    System.out.print(printInventory());

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

                if(test.equals("test_makePurchase_purchase_potato_crisps")) testResult += item.getName();
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
                if(test.equals("test_makePurchase_change_is_correct")) testResult += coins + " ";

                coins = countCoins(balance, DIME);
                if(testParams == null)
                    System.out.println(coins + " dimes.");
                balance -= coins * DIME;
                balance = Double.parseDouble(d.format(balance));
                if(test.equals("test_makePurchase_change_is_correct")) testResult += coins + " ";

                coins = countCoins(balance, NICKEL);
                if(testParams == null)
                    System.out.println(coins + " nickels.");
                balance -= coins * NICKEL;
                balance = Double.parseDouble(d.format(balance));
                if(test.equals("test_makePurchase_change_is_correct")) testResult += coins + " ";

                coins = countCoins(balance, PENNY);
                if(testParams == null)
                    System.out.println(coins + " pennies.");
                if(test.equals("test_makePurchase_change_is_correct")) testResult += coins + " ";

                balance = 0.0;
//				System.out.println("ending balance " + balance);
                break;
            }
        }
        return testResult;
    }

    private int countCoins(double amount, double coin) { // returns the maximum amount of the given coin that will fit in amount
        int count = 0;

        while(amount >= coin) {
            amount -= coin;
            count++;
        }

        return count;
    }

    private int getItemIndex(String location) { // returns index in vendingInventory of the item at the given location
        int i = 0;
        while(i < vendingInventory.size()) {
            if(vendingInventory.get(i).getLocation().equals(location)) {
                return i;
            }

            i++;
        }
        return -1;
    }

    public String printInventory() { // prints current inventory
        String out = "";

        for(VendingMachineItem item : vendingInventory) {
            out += item + "\n";
        }

        return out;
    }

    private List<VendingMachineItem> stockInventory() { // reads inventoryFileName File and returns a fully stocked list based on the file
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

    // constructor
    public VendingMachine(String inventoryFileName, Menu menu, Audit a) {
        this.inventoryFileName = inventoryFileName;
        this.menu = menu;
        this.a = a;
        this.vendingInventory = stockInventory();
    }

    // getters and setters

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
