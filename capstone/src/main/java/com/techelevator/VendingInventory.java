package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VendingInventory {

    public final String FILE_NAME = "vendingmachine.csv";

    public VendingInventory() {
        Menu menu = new Menu(System.in, System.out);
        File vendingStock = new File(FILE_NAME);

        try (Scanner fileIn = new Scanner(vendingStock)) {
            while(fileIn.hasNextLine()) {
                String line = fileIn.nextLine();


            }
        } catch (FileNotFoundException e) {
            // error
        }
    }
}
