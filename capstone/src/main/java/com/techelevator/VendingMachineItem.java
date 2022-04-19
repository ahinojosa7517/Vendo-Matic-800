package com.techelevator;

import java.text.NumberFormat;

public abstract class VendingMachineItem {

    // constant variables
    protected final int INVENTORY_MAX = 5;

    // instance variables
    protected String location;
    protected String name;
    protected int inventory;
    protected double price;

    // abstract method - each time of item has a unique message
    public abstract String message();

    public String getAvailable() {
        if(inventory <= 0) {
            return "SOLD OUT";
        }

        return "" + inventory;
    }

    // dispenses item if available, returning false if SOLD OUT
    public boolean dispenseItem() {
        if(inventory <= 0) {
            return false;
        }
        inventory--;
        return true;
    }

    @Override
    public String toString() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return this.location + " | " + this.getAvailable() + " | " + this.name + ": " + f.format(this.price);
    }

    // constructor
    public VendingMachineItem() {
    }

    // getters and setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
