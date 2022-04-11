package com.techelevator;

import java.text.NumberFormat;

public abstract class VendingMachineItem {

    protected String location;
    protected String name;
    protected int inventory;
    protected final int INVENTORY_MAX = 5;
    protected double price;

    public abstract String message();

    @Override
    public String toString() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return this.location + " | " + this.getAvailable() + " | " + this.name + ": " + f.format(this.price);
    }

    public String getAvailable() {
        if(inventory <= 0) return "SOLD OUT";

        return "" + inventory;
    }

    public void dispenseItem() {
        inventory--;
    }

    public VendingMachineItem() {
    }

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
