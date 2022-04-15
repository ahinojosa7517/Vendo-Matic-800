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
    public void test_makePurchase_feed_and_purchase() {
        // Arrange testParams.add();
        List<Object> testParams = new ArrayList<>();
        testParams.add("test_makePurchase_feed_and_purchase"); // testing method name
        testParams.add("Feed Money"); // purchase menu - Feed Money
        testParams.add(5); // feed money - amount $5
        testParams.add("Select Product"); // purchase menu - Select Product
        testParams.add("A1");
        testParams.add("Finish Transaction");


        // Act
        vend.setTestParams(testParams);
        String result = vend.makePurchase();

        // Assert
        Assert.assertTrue(result.equals("5.0"));
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
