package com.techelevator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Audit {

    // constant file name
    private static final String LOG_FILENAME = "Log.txt";

    // instance variables
    private File logFile;
    private NumberFormat currency = NumberFormat.getCurrencyInstance();

    // takes VendingMachineItem object, logs that object + message
    public void log(VendingMachineItem item, String message, double balance) {
        this.log(item.getName() + " " + item.getLocation() + " " + message, balance);
    }

    // logs a message with a default balance of $0.00
    public void log(String message) {
        this.log(message, 0);
    }

    // logs a message with the given balance at the end of the line
    public void log(String message, double balance) {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        try (PrintWriter out = new PrintWriter(new FileOutputStream(logFile, true))) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            out.println(dtf.format(now) + " " + message + " " + f.format(balance));

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // constructor
    public Audit() {
        logFile = new File(System.getProperty("user.dir"), LOG_FILENAME);
    }

}
