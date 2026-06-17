package org.sda.modules.stepFour;

import java.util.Map;
import tech.tablesaw.api.Table;

public class SaveNotPass {

    public static void main(Map<Integer, Table> hasilSeleksi) {
        try {
            String directoryPath = "src/main/output/final/";

            java.io.File directory = new java.io.File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            for (Map.Entry<Integer, Table> entry : hasilSeleksi.entrySet()) {
                Table table = entry.getValue();
                String fileName = directoryPath + table.name() + ".csv";
                table.write().csv(fileName);
                System.out.println("Berhasil menyimpan: " + fileName);
            }
        } catch (Exception e) {
            System.out.println(
                "Terjadi masalah saat menyimpan data peserta yang tidak lolos. Harap periksa kembali sesuai dengan ketentuan README.md"
            );
            System.out.println("Error: " + e.getMessage());
        }
    }
}
