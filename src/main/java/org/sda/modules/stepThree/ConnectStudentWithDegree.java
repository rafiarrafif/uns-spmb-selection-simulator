package org.sda.modules.stepThree;

import org.sda.modules.stepOne.DatasetContainer;
import tech.tablesaw.api.Table;

public class ConnectStudentWithDegree {

    public static Table main(DatasetContainer datasets) {
        try {
            Table pilihanJurusan = datasets.pilihanJurusanData.dropDuplicateRows();
            Table peserta = datasets.studentData.selectColumns("ID Calon Mahasiswa");

            Table jurusanPertama = datasets.jurusanData.copy();
            jurusanPertama.column("Id_jurusan").setName("ID Jurusan Pilihan Pertama");
            jurusanPertama.column("Nama Jurusan").setName("Nama Jurusan Pilihan Pertama");
            jurusanPertama.column("Bobot TPS").setName("Bobot TPS Pilihan Pertama");
            jurusanPertama.column("Bobot LBI").setName("Bobot LBI Pilihan Pertama");
            jurusanPertama.column("Bobot LBE").setName("Bobot LBE Pilihan Pertama");
            jurusanPertama.column("Bobot PM").setName("Bobot PM Pilihan Pertama");
            jurusanPertama = jurusanPertama.selectColumns(
                "ID Jurusan Pilihan Pertama",
                "Nama Jurusan Pilihan Pertama",
                "Bobot TPS Pilihan Pertama",
                "Bobot LBI Pilihan Pertama",
                "Bobot LBE Pilihan Pertama",
                "Bobot PM Pilihan Pertama"
            );

            Table jurusanKedua = datasets.jurusanData.copy();
            jurusanKedua.column("Id_jurusan").setName("ID Jurusan Pilihan Kedua");
            jurusanKedua.column("Nama Jurusan").setName("Nama Jurusan Pilihan Kedua");
            jurusanKedua.column("Bobot TPS").setName("Bobot TPS Pilihan Kedua");
            jurusanKedua.column("Bobot LBI").setName("Bobot LBI Pilihan Kedua");
            jurusanKedua.column("Bobot LBE").setName("Bobot LBE Pilihan Kedua");
            jurusanKedua.column("Bobot PM").setName("Bobot PM Pilihan Kedua");
            jurusanKedua = jurusanKedua.selectColumns(
                "ID Jurusan Pilihan Kedua",
                "Nama Jurusan Pilihan Kedua",
                "Bobot TPS Pilihan Kedua",
                "Bobot LBI Pilihan Kedua",
                "Bobot LBE Pilihan Kedua",
                "Bobot PM Pilihan Kedua"
            );

            Table result = pilihanJurusan.joinOn("ID Calon Mahasiswa").inner(peserta);
            result = result.joinOn("ID Jurusan Pilihan Pertama").inner(jurusanPertama);
            result = result.joinOn("ID Jurusan Pilihan Kedua").inner(jurusanKedua);
            result = result.joinOn("ID Calon Mahasiswa").inner(datasets.nilaiTesData);

            System.out.println(
                "Total data mahasiswa yang berhasil terhubung: " + result.rowCount()
            );

            return result;
        } catch (Exception e) {
            System.err.println(
                "Error saat menghubungkan data mahasiswa dengan jurusan: " + e.getMessage()
            );
            return null;
        }
    }
}
