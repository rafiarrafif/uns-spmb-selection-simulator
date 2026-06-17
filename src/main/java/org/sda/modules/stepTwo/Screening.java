package org.sda.modules.stepTwo;

import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;

/**
 * Screening pada kasus penerimaan UNS adalah sebagai berikut:
 * 1. Peserta harus lulus pada tahun 2023 atau lebih baru.
 * 2. Data perserta harus lengkap, tidak boleh ada nilai yang kosong.
 */
public class Screening {

    public static Table main(Table studentData) {
        try {
            System.out.println(
                "Total Data Peserta yang akan discreening: " + studentData.rowCount()
            );

            Selection hasNullSelection = Selection.withRange(0, 0);
            for (Column<?> col : studentData.columns()) {
                hasNullSelection = hasNullSelection.or(col.isMissing());
            }

            Selection lulusTerlambatSelection = studentData
                .intColumn("Tahun Lulus")
                .isLessThan(2023);
            Selection gagalSelection = hasNullSelection.or(lulusTerlambatSelection);
            Selection allRows = Selection.withRange(0, studentData.rowCount());
            Selection lolosSelection = allRows.andNot(gagalSelection);

            Table lolosData = studentData.where(lolosSelection);
            Table gagalData = studentData.where(gagalSelection);

            System.out.println("Total Data Peserta setelah screening : " + lolosData.rowCount());
            System.out.println("Total Data Peserta gagal seleksi    : " + gagalData.rowCount());

            gagalData.write().csv("src/main/output/peserta-gagal-administrasi.csv");
            return lolosData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while screening student data", e);
        }
    }
}
