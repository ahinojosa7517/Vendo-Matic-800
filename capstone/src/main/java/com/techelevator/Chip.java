package com.techelevator;

public class Chip extends VendingMachineItem {
    @Override
    public String message() {
        return "Crunch Crunch, Yum!";
    }

    public Chip(String location, String name, double price) {
        this.location = location;
        this.name = name;
        this.inventory = INVENTORY_MAX;
        this.price = price;
    }
}
