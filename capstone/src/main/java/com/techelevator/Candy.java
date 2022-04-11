package com.techelevator;

public class Candy extends VendingMachineItem {
    @Override
    public String message() {
        return "Munch Munch, Yum!";
    }

    public Candy(String location, String name, double price) {
        this.location = location;
        this.name = name;
        this.inventory = INVENTORY_MAX;
        this.price = price;
    }
}
