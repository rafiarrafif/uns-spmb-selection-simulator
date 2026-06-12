package org.sda;

import org.sda.helpers.Break;
import org.sda.modules.stepOne.DatasetContainer;
import org.sda.modules.stepOne.ImportDataset;
import org.sda.modules.stepThree.ConnectStudentWithDegree;
import org.sda.modules.stepTwo.Screening;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        DatasetContainer datasets = ImportDataset.main(args); // Step 1: Import Dataset
        Break.waitForUser("Dataset berhasil di import.");
        datasets.studentData = Screening.main(datasets.studentData);
        Break.waitForUser("Data peserta telah di screening administrasi.");
        ConnectStudentWithDegree.main(datasets);
        System.out.println("System shutting down...");
        System.exit(0);
    }
}
