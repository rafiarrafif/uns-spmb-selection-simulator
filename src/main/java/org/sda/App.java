package org.sda;

import org.sda.helpers.Break;
import org.sda.modules.stepOne.DatasetContainer;
import org.sda.modules.stepOne.ImportDataset;
import org.sda.modules.stepThree.ConnectStudentWithDegree;
import org.sda.modules.stepThree.graphVisualization;
import org.sda.modules.stepTwo.Screening;
import tech.tablesaw.api.Table;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        // Step 1: Import Dataset
        DatasetContainer datasets = ImportDataset.main(args);
        Break.waitForUser("Dataset berhasil di import.");

        // Step 2: Screening berkas administrasi mahasiswa
        datasets.studentData = Screening.main(datasets.studentData);
        Break.waitForUser("Data peserta telah di screening administrasi.");

        // Step 3: Menghubungkan data mahasiswa dengan jurusan yang dipilih
        Table studentWithDegree = ConnectStudentWithDegree.main(datasets);
        Break.waitForUser("Data mahasiswa berhasil dihubungkan dengan jurusan yang dipilih.");
        graphVisualization.main(studentWithDegree);
        System.out.println("System shutting down...");
    }
}
