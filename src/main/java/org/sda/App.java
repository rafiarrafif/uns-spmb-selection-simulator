package org.sda;

import org.sda.StepOne.DatasetContainer;
import org.sda.StepOne.ImportDataset;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        DatasetContainer datasets = ImportDataset.main(args); // Step 1: Import Dataset
        System.out.println("Dataset berhasil diimpor. Lanjut ke langkah berikutnya...");
    }
}
