package org.sda.StepOne;

import tech.tablesaw.api.Table;

public class ImportDataset {

    public static void main(String[] args) {
        try {
            Table studentData = Table.read().csv("src/main/datasets/peserta.csv");
            Table nilaiTesData = Table.read().csv("src/main/datasets/nilai_tes_peserta.csv");
            Table pilihanJurusanData = Table.read().csv("src/main/datasets/pilihan_jurusan.csv");
            Table jurusanData = Table.read().csv("src/main/datasets/detail_jurusan.csv");

            System.out.println("Total Data Peserta: " + studentData.rowCount());
            System.out.println("Total Data Nilai Tes: " + nilaiTesData.rowCount());
            System.out.println("Total Data Pilihan Jurusan: " + pilihanJurusanData.rowCount());
            System.out.println("Total Data Jurusan: " + jurusanData.rowCount());
        } catch (Exception e) {
            System.out.println(
                "Terjadi masalah saat mengimpor dataset. Harap periksa kembali sesuai dengan ketentuan README.md"
            );
            System.out.println("Error: " + e.getMessage());
        }
    }
}
