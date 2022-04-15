package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

public class VendingMachineItemTest {

    private VendingMachineItem testItem;

    @Test
    public void test_getInventory() {
        // Arrange
        testItem = new Candy("Z25", "snickers", 1.25);

        // Act & Assert

        Assert.assertEquals(5, testItem.getInventory());
        testItem.dispenseItem();
        Assert.assertEquals(4, testItem.getInventory());
        for(int i = 0; i < 4; i++) testItem.dispenseItem();
        Assert.assertEquals(0, testItem.getInventory());
        testItem.dispenseItem();
        Assert.assertEquals(0, testItem.getInventory());

    }

    @Test
    public void test_getAvailable() {
        // Arrange
        testItem = new Chip("Z25", "snickers", 1.25);

        // Act & Assert

        Assert.assertTrue(testItem.getAvailable().equals("5"));
        testItem.dispenseItem();
        Assert.assertTrue(testItem.getAvailable().equals("4"));
        for(int i = 0; i < 4; i++) testItem.dispenseItem();
        Assert.assertTrue(testItem.getAvailable().equals("SOLD OUT"));
        testItem.dispenseItem();
        Assert.assertTrue(testItem.getAvailable().equals("SOLD OUT"));

    }

    @Test
    public void test_toString() {
        // Arrange
        testItem = new Drink("Z25", "snickers", 1.25);

        // Act & Assert

        Assert.assertTrue(testItem.toString().equals("Z25 | 5 | snickers: $1.25"));

    }

    @Test
    public void test_message() {
        // Arrange, Act, & Assert

        testItem = new Candy("Z25", "snickers", 1.25);
        Assert.assertTrue(testItem.message().equals("Munch Munch, Yum!"));

        testItem = new Chip("Z25", "snickers", 1.25);
        Assert.assertTrue(testItem.message().equals("Crunch Crunch, Yum!"));

        testItem = new Drink("Z25", "snickers", 1.25);
        Assert.assertTrue(testItem.message().equals("Glug Glug, Yum!"));

        testItem = new Gum("Z25", "snickers", 1.25);
        Assert.assertTrue(testItem.message().equals("Chew Chew, Yum!"));

    }

}
