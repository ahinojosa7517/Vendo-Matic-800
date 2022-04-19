package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AuditTest {

    private static final String LOG_FILEPATH = "Log.txt";
    File logFile;
    Audit a;

    @Test
    public void log_writes_to_Log_txt() {
        // Arrange
        a = new Audit();
        logFile = new File(System.getProperty("user.dir"), LOG_FILEPATH);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        LocalDateTime now = LocalDateTime.now();

        try (Scanner file = new Scanner(logFile)) {
        } catch (FileNotFoundException e) {
            Assert.fail();
        }

        // Act
        a.log("log_writes_to_Log_txt");
        String lastLineCorrect = dtf.format(now) + " log_writes_to_Log_txt $0.00";

        String lastLine = "";
        try (Scanner fileIn = new Scanner(logFile)) {
            while(fileIn.hasNextLine()) { lastLine = fileIn.nextLine(); }
        } catch (FileNotFoundException e) {
            Assert.fail();
        }

        // Assert
        Assert.assertTrue(lastLine.equals(lastLineCorrect));

    }

}
