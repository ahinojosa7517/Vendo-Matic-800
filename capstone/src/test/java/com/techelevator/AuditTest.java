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

    @Before
    public void initialize_audit_object() {
        a = new Audit();
        logFile = new File(LOG_FILEPATH);
        try (Scanner file = new Scanner(logFile)) {
        } catch (FileNotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void log_writes_to_Log_txt() {
        // Arrange
        // @Before

        // Act
        String lastLine = "";
        try (Scanner fileIn = new Scanner(logFile)) {
            while(fileIn.hasNextLine()) { lastLine = fileIn.nextLine(); }
        } catch (FileNotFoundException e) {
            Assert.fail();
        }
        String lastLineCorrect;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
        LocalDateTime now = LocalDateTime.now();
        a.log("log_writes_to_Log_txt");
        lastLineCorrect = dtf.format(now) + " log_writes_to_Log_txt $0.00";

        // Assert
        Assert.assertTrue(lastLine.equals(lastLineCorrect));

    }

}
