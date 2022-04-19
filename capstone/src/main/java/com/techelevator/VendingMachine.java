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

    // constant class variables
    private final double QUARTER = 0.25;
    private final double DIME = 0.10;
    private final double NICKEL = 0.05;
    private final double PENNY = 0.01;

    // instance variables
    private String inventoryFileName;
    private double balance = 0.0;
    private Menu menu;
    private Audit a;
    private List<VendingMachineItem> vendingInventory;

    // formatting objects
    private NumberFormat f = NumberFormat.getCurrencyInstance();
    private DecimalFormat d = new DecimalFormat("#0.00");

    // test-specific variables
    private List<Object> testParams = null;
    private int testIterator;
    private String testResult;
    private String testTitle;

    // test-specific method
    public void setTestParams(List<Object> testParams) {
        this.testParams = testParams;
    }

    // restock all inventory
    public void restock() {
        vendingInventory = stockInventory();
    }

    // purchase method - returns String for testing purposes
    public String makePurchase() {
        testIterator = 1; testResult = ""; testTitle = "";
        // if available, set test title
        if(testParams != null) {
            testTitle = (String) testParams.get(0);
        }
        Scanner userIn = new Scanner(System.in);
        VendingMachineItem item;

        // purchase menu loop
        while(true) {
            String choice;
            // check if running a test - if not, take user input
            if(testParams == null) {
                System.out.println("Current balance: " + f.format(balance));
                choice = (String) menu.getChoiceFromOptions(new String[]{"Feed Money", "Select Product", "Finish Transaction"});
            } else {
                // else == testing - take input from testParams list
                choice = (String) testParams.get(testIterator);
                testIterator++;
            }

            // input selected to feed money to the machine
            if(choice.equals("Feed Money")) {
                this.feedMoney();
            }
            // input selected to purchase a product
            if(choice.equals("Select Product")) {
                // check if running a test - if not, take user input
                if(testParams == null) {
                    System.out.print(getInventory());

                    System.out.print("Please choose an option >>> ");
                    choice = userIn.nextLine();
                } else {
                    // else == testing - take input from testParams list
                    choice = (String) testParams.get(testIterator);
                    testIterator++;
                }

                // getItemIndex() returns -1 if item can't be found - catches and assigns null
                try {
                    // store variable associated with input
                    item = vendingInventory.get(getItemIndex(choice));
                } catch(IndexOutOfBoundsException e) {
                    item = null;
                }

                // selection can't be found, sends user back to purchase menu
                if(item == null) {
                    if(testParams == null)
                        System.out.println("Selection is invalid.");
                    continue;
                }
                // selection is too expensive for the current balance, sends user back to purchase menu
                if(item.getPrice() > balance) {
                    if(testParams == null)
                        System.out.println("Not enough money provided.");
                    continue;
                }

                // dispense item, printing error if SOLD OUT
                if(item.dispenseItem()) {
                    balance -= item.getPrice();
                    balance = Double.parseDouble(d.format(balance));
                    // logs successfully dispensed item
                    a.log(item, f.format(balance), balance - item.getPrice());
                } else {
                    if(testParams == null)
                        System.out.println("Selection is sold out.");
                    continue;
                }

                // check if running a test - if not, output dispense message
                if(testParams == null) {
                    System.out.println();
                    System.out.println("Dispensing " + item.getName() + " for " + f.format(item.getPrice()));
                    System.out.println(item.message());
                    System.out.println();
                }

                // adding to testResult if matching testTitle
                if(testTitle.equals("test_makePurchase_purchase_potato_crisps")) {
                    testResult += item.getName();
                }
            }
            // input selected to finish the transaction
            if(choice.equals("Finish Transaction")){
                this.finishTransaction();
                break;
            }
        }
        return testResult;
    }

    // helper method to finish the transaction
    private void finishTransaction() {
        // check if running a test - if not, output change message
        if(testParams == null) {
            System.out.println("Your change is " + f.format(balance));
        }
        // logs change given
        a.log("GIVE CHANGE " + f.format(balance), 0.0);

        int coins;

        coins = countCoins(balance, QUARTER);
        // check if running a test - if not, output change message
        if(testParams == null) {
            System.out.println(coins + " quarters.");
        }
        balance -= coins * QUARTER;
        // bug fix - balance would lose precision, rounding to nearest penny
        balance = Double.parseDouble(d.format(balance));
        // adding to testResult if matching testTitle
        if(testTitle.equals("test_makePurchase_change_is_correct")) {
            testResult += coins + " ";
        }

        coins = countCoins(balance, DIME);
        // check if running a test - if not, output change message
        if(testParams == null) {
            System.out.println(coins + " dimes.");
        }
        balance -= coins * DIME;
        // bug fix - balance would lose precision, rounding to nearest penny
        balance = Double.parseDouble(d.format(balance));
        // adding to testResult if matching testTitle
        if(testTitle.equals("test_makePurchase_change_is_correct")) {
            testResult += coins + " ";
        }

        coins = countCoins(balance, NICKEL);
        // check if running a test - if not, output change message
        if(testParams == null) {
            System.out.println(coins + " nickels.");
        }
        balance -= coins * NICKEL;
        // bug fix - balance would lose precision, rounding to nearest penny
        balance = Double.parseDouble(d.format(balance));
        // adding to testResult if matching testTitle
        if(testTitle.equals("test_makePurchase_change_is_correct")) {
            testResult += coins + " ";
        }

        coins = countCoins(balance, PENNY);
        // check if running a test - if not, output change message
        if(testParams == null) {
            System.out.println(coins + " pennies.");
        }
        // adding to testResult if matching testTitle
        if(testTitle.equals("test_makePurchase_change_is_correct")) {
            testResult += coins + " ";
        }

        balance = 0.0;
    }

    // helper method for feed money function
    private void feedMoney() {
        int moneyChoice;
        // check if running a test - if not, take user input
        if(testParams == null) {
            moneyChoice = (Integer) menu.getChoiceFromOptions(new Integer[]{1, 2, 5, 10});
            a.log("FEED MONEY: " + f.format(moneyChoice), moneyChoice + balance);
        } else {
            // else == testing - take input from testParams list
            moneyChoice = (Integer) testParams.get(testIterator);
            testIterator++;
        }

        balance += moneyChoice;

        // adding to testResult if matching testTitle
        if(testTitle.equals("test_makePurchase_feed_money")) {
            testResult += "" + balance;
        }
    }

    // returns the maximum amount of the given coin that will fit in amount
    private int countCoins(double amount, double coin) {
        int count = 0;

        while(amount >= coin) {
            amount -= coin;
            count++;
        }

        return count;
    }

    // returns index in vendingInventory of the item at the given location
    private int getItemIndex(String location) {
        for(int i = 0; i < vendingInventory.size(); i++) {
            if(vendingInventory.get(i).getLocation().equals(location)) {
                return i;
            }
        }
        return -1;
    }

    // returns current inventory
    public String getInventory() {
        String out = "";

        for(VendingMachineItem item : vendingInventory) {
            out += item + "\n";
        }

        return out;
    }

    // reads inventoryFileName File and returns a fully stocked list based on the file
    private List<VendingMachineItem> stockInventory() {
        File vendingStock = new File(System.getProperty("user.dir"), inventoryFileName);
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
                    item = null;
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
