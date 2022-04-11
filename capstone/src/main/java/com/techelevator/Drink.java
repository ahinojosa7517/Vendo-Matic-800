package com.techelevator;

public class Drink extends VendingMachineItem {
    @Override
    public String message() {
        return "Glug Glug, Yum!";
    }

    public Drink(String location, String name, double price) {
        this.location = location;
        this.name = name;
        this.inventory = INVENTORY_MAX;
        this.price = price;
    }
}
