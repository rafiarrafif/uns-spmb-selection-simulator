package org.sda.modules.stepThree;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

public class gradeWeighting {

    public static Table main(Table rawGrades) {
        try {
            Table finalGrades = Table.create("Final Grade");

            StringColumn idCalonMahasiswa = StringColumn.create("ID Calon Mahasiswa");
            IntColumn idJurusanPertama = IntColumn.create("ID Jurusan Pertama");
            IntColumn idJurusanKedua = IntColumn.create("ID Jurusan Kedua");
            DoubleColumn bobotFinalPertama = DoubleColumn.create("Bobot Final Pertama");
            DoubleColumn bobotFinalKedua = DoubleColumn.create("Bobot Final Kedua");

            for (int i = 0; i < rawGrades.rowCount(); i++) {
                idCalonMahasiswa.append(rawGrades.stringColumn(0).get(i));
                idJurusanPertama.append(rawGrades.intColumn(1).get(i));
                idJurusanKedua.append(rawGrades.intColumn(2).get(i));

                double nilaiTPS = rawGrades.intColumn(15).get(i);
                double nilaiLBI = rawGrades.intColumn(16).get(i);
                double nilaiLBE = rawGrades.intColumn(17).get(i);
                double nilaiPM = rawGrades.intColumn(18).get(i);

                double bobotTPSPertama = rawGrades.doubleColumn(6).get(i);
                double bobotLBIPertama = rawGrades.doubleColumn(7).get(i);
                double bobotLBEPertama = rawGrades.doubleColumn(8).get(i);
                double bobotPMPertama = rawGrades.doubleColumn(9).get(i);
                double finalGradePertama =
                    Math.round(
                        ((nilaiTPS * bobotTPSPertama) +
                            (nilaiLBI * bobotLBIPertama) +
                            (nilaiLBE * bobotLBEPertama) +
                            (nilaiPM * bobotPMPertama)) * 100
                    ) / 100.0;
                bobotFinalPertama.append(finalGradePertama);

                double bobotTPSKedua = rawGrades.doubleColumn(11).get(i);
                double bobotLBIKedua = rawGrades.doubleColumn(12).get(i);
                double bobotLBEKedua = rawGrades.doubleColumn(13).get(i);
                double bobotPMKedua = rawGrades.doubleColumn(14).get(i);
                double finalGradeKedua =
                    Math.round(
                        ((nilaiTPS * bobotTPSKedua) +
                            (nilaiLBI * bobotLBIKedua) +
                            (nilaiLBE * bobotLBEKedua) +
                            (nilaiPM * bobotPMKedua)) * 100
                    ) / 100.0;
                bobotFinalKedua.append(finalGradeKedua);
            }

            finalGrades.addColumns(
                idCalonMahasiswa,
                idJurusanPertama,
                idJurusanKedua,
                bobotFinalPertama,
                bobotFinalKedua
            );
            return finalGrades;
        } catch (Exception e) {
            System.out.println(
                "An error occurred while calculating grade weighting: " + e.getMessage()
            );
            return null;
        }
    }
}
