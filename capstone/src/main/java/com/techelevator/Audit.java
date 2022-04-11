package com.techelevator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Audit {

    private static final String LOG_FILEPATH = "Log.txt";

    private File logFile;
    private NumberFormat currency = NumberFormat.getCurrencyInstance();

    public void log(String message) {
        this.log(message, 0);
    }

    public void log(String message, double balance) {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        try (PrintWriter out = new PrintWriter(new FileOutputStream(logFile, true))) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            out.print(dtf.format(now) + " " + message + " " + f.format(balance));

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Audit(){
        logFile = new File(LOG_FILEPATH);
    }

}
