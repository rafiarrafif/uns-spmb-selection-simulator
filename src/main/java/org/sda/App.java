package org.sda;

import org.sda.StepOne.DatasetContainer;
import org.sda.StepOne.ImportDataset;
import org.sda.helpers.Break;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        DatasetContainer datasets = ImportDataset.main(args); // Step 1: Import Dataset
        Break.waitForUser("Dataset berhasil di import.");
        System.out.println("System shutting down...");
        System.exit(0);
    }
}
