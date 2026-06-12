package org.sda.modules.stepThree;

import org.sda.modules.stepOne.DatasetContainer;
import tech.tablesaw.api.Table;

public class ConnectStudentWithDegree {

    public static void main(DatasetContainer datasets) {
        Table pilihanJurusan = datasets.pilihanJurusanData.dropDuplicateRows();
        Table peserta = datasets.studentData.selectColumns("ID Calon Mahasiswa");

        Table jurusanPertama = datasets.jurusanData.copy();
        jurusanPertama.column("Id_jurusan").setName("ID Jurusan Pilihan Pertama");
        jurusanPertama.column("Nama Jurusan").setName("Nama Jurusan Pilihan Pertama");
        jurusanPertama = jurusanPertama.selectColumns(
            "ID Jurusan Pilihan Pertama",
            "Nama Jurusan Pilihan Pertama"
        );

        Table jurusanKedua = datasets.jurusanData.copy();
        jurusanKedua.column("Id_jurusan").setName("ID Jurusan Pilihan Kedua");
        jurusanKedua.column("Nama Jurusan").setName("Nama Jurusan Pilihan Kedua");
        jurusanKedua = jurusanKedua.selectColumns(
            "ID Jurusan Pilihan Kedua",
            "Nama Jurusan Pilihan Kedua"
        );

        Table result = pilihanJurusan.joinOn("ID Calon Mahasiswa").inner(peserta);
        result = result.joinOn("ID Jurusan Pilihan Pertama").inner(jurusanPertama);
        result = result.joinOn("ID Jurusan Pilihan Kedua").inner(jurusanKedua);

        System.out.println("Total data mahasiswa yang berhasil terhubung: " + result.rowCount());
        System.out.println(result.structure());
        System.out.println(result.first(10));
    }
}
