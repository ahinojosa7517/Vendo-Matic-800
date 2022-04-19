package com.techelevator;

import com.techelevator.view.Menu;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VendingMachineTest {

    private VendingMachine vend;

    @Before
    public void initialize_vend() {
        vend = new VendingMachine("vendingmachine.csv", new Menu(System.in, System.out), new Audit());
    }

    @Test
    public void test_restock() {
        // Arrange testParams.add();
        List<Object> testParams = new ArrayList<>();
        testParams.add("test_restock"); // testing method name
        testParams.add("Feed Money"); // purchase menu - Feed Money
        testParams.add(5); // feed money - amount $5
        testParams.add("Select Product"); // purchase menu - Select Product
        testParams.add("A1");
        testParams.add("Finish Transaction");

        // Act
        vend.setTestParams(testParams); // pass in inputs, telling the object it's being run as a unit test
        vend.makePurchase();
        vend.restock();
        String result = vend.getInventory();

        // Assert
        Assert.assertTrue(result.equals(
                        "A1 | 5 | Potato Crisps: $3.05\n" +
                        "A2 | 5 | Stackers: $1.45\n" +
                        "A3 | 5 | Grain Waves: $2.75\n" +
                        "A4 | 5 | Cloud Popcorn: $3.65\n" +
                        "B1 | 5 | Moonpie: $1.80\n" +
                        "B2 | 5 | Cowtales: $1.50\n" +
                        "B3 | 5 | Wonka Bar: $1.50\n" +
                        "B4 | 5 | Crunchie: $1.75\n" +
                        "C1 | 5 | Cola: $1.25\n" +
                        "C2 | 5 | Dr. Salt: $1.50\n" +
                        "C3 | 5 | Mountain Melter: $1.50\n" +
                        "C4 | 5 | Heavy: $1.50\n" +
                        "D1 | 5 | U-Chews: $0.85\n" +
                        "D2 | 5 | Little League Chew: $0.95\n" +
                        "D3 | 5 | Chiclets: $0.75\n" +
                        "D4 | 5 | Triplemint: $0.75\n"));
    }

    @Test
    public void test_printInventory() {
        // Arrange testParams.add();
        List<Object> testParams = new ArrayList<>();
        testParams.add("test_printInventory"); // testing method name
        testParams.add("Feed Money"); // purchase menu - Feed Money
        testParams.add(5); // feed money - amount $5
        testParams.add("Select Product"); // purchase menu - Select Product
        testParams.add("A1");
        testParams.add("Finish Transaction");

        // Act
        vend.setTestParams(testParams);
        vend.makePurchase();
        String result = vend.getInventory();

        // Assert
        Assert.assertTrue(result.equals(
                "A1 | 4 | Potato Crisps: $3.05\n" +
                "A2 | 5 | Stackers: $1.45\n" +
                "A3 | 5 | Grain Waves: $2.75\n" +
                "A4 | 5 | Cloud Popcorn: $3.65\n" +
                "B1 | 5 | Moonpie: $1.80\n" +
                "B2 | 5 | Cowtales: $1.50\n" +
                "B3 | 5 | Wonka Bar: $1.50\n" +
                "B4 | 5 | Crunchie: $1.75\n" +
                "C1 | 5 | Cola: $1.25\n" +
                "C2 | 5 | Dr. Salt: $1.50\n" +
                "C3 | 5 | Mountain Melter: $1.50\n" +
                "C4 | 5 | Heavy: $1.50\n" +
                "D1 | 5 | U-Chews: $0.85\n" +
                "D2 | 5 | Little League Chew: $0.95\n" +
                "D3 | 5 | Chiclets: $0.75\n" +
                "D4 | 5 | Triplemint: $0.75\n"));
    }

    @Test
    public void test_makePurchase_change_is_correct() {
        // Arrange testParams.add();
        List<Object> testParams = new ArrayList<>();
        testParams.add("test_makePurchase_change_is_correct"); // testing method name
        testParams.add("Feed Money"); // purchase menu - Feed Money
        testParams.add(5); // feed money - amount $5
        testParams.add("Select Product"); // purchase menu - Select Product
        testParams.add("A1");
        testParams.add("Finish Transaction");

        // Act
        vend.setTestParams(testParams);
        String result = vend.makePurchase();

        // Assert
        Assert.assertTrue(result.equals("7 2 0 0 ")); // expected result: 7 quarters, 2 dimes, 0 nickels, 0 pennies
    }

    @Test
    public void test_makePurchase_purchase_potato_crisps() {
        // Arrange testParams.add();
        List<Object> testParams = new ArrayList<>();
        testParams.add("test_makePurchase_purchase_potato_crisps"); // testing method name
        testParams.add("Feed Money"); // purchase menu - Feed Money
        testParams.add(5); // feed money - amount $5
        testParams.add("Select Product"); // purchase menu - Select Product
        testParams.add("A1");
        testParams.add("Finish Transaction");

        // Act
        vend.setTestParams(testParams);
        String result = vend.makePurchase();

        // Assert
        Assert.assertTrue(result.equals("Potato Crisps"));
    }

    @Test
    public void test_makePurchase_feed_money() {
        // Arrange
        List<Object> testParams = new ArrayList<>();
        testParams.add("test_makePurchase_feed_money"); // testing method name
        testParams.add("Feed Money"); // purchase menu - Feed Money
        testParams.add(5); // feed money - amount $5
        testParams.add("Finish Transaction"); // purchase menu - Finish Transaction

        // Act
        vend.setTestParams(testParams);
        String result = vend.makePurchase();

        // Assert
        Assert.assertTrue(result.equals("5.0"));
    }

}
