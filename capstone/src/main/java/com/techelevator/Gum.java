package com.techelevator;

public class Gum extends VendingMachineItem {
    @Override
    public String message() {
        return "Chew Chew, Yum!";
    }

    public Gum(String location, String name, double price) {
        this.location = location;
        this.name = name;
        this.inventory = INVENTORY_MAX;
        this.price = price;
    }
}
