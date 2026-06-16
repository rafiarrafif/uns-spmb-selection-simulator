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
        Break.waitForUser("mengimpor seluruh dataset");
        DatasetContainer datasets = ImportDataset.main(args);

        // Step 2: Screening berkas administrasi mahasiswa
        Break.waitForUser("melakukan screening data peserta");
        datasets.studentData = Screening.main(datasets.studentData);

        // Step 3: Menghubungkan data mahasiswa dengan jurusan yang dipilih
        Break.waitForUser("melakukan pengabungan data peserta dengan jurusan yang dipilih");
        Table studentWithDegree = ConnectStudentWithDegree.main(datasets);

        Break.waitForUser(
            "membuka visualisasi graf untuk data peserta dengan jurusan yang dipilih"
        );
        graphVisualization.visualize(studentWithDegree);
        System.out.println("System shutting down...");
    }
}
