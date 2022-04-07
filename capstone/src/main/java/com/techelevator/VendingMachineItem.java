package com.techelevator;

public abstract class VendingMachineItem {

    private String location;
    private String name;
    private int inventory;
    private final int INVENTORY_MAX = 5;
    private double price;

    public abstract String message();

    public String getAvailable() {
        if(inventory <= 0) return "SOLD OUT";

        return "" + inventory;
    }

    public VendingMachineItem() {
    }

    public VendingMachineItem(String location, String name, int inventory, double price) {
        this.location = location;
        this.name = name;
        this.inventory = inventory;
        this.price = price;
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
