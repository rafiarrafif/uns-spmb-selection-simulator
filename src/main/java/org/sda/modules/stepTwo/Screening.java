package org.sda.modules.stepTwo;

import tech.tablesaw.api.Table;

/**
 * Screening pada kasus penerimaan UNS adalah sebagai berikut:
 * 1. Peserta harus lulus pada tahun 2023 atau lebih baru.
 * 2. Data perserta harus lengkap, tidak boleh ada nilai yang kosong.
 */
public class Screening {

    public static Table main(Table studentData) {
        System.out.println("Total Data Peserta yang akan discreening: " + studentData.rowCount());
        Table screenedData = studentData
            .where(studentData.intColumn("Tahun Lulus").isGreaterThanOrEqualTo(2023))
            .dropRowsWithMissingValues();
        System.out.println("Total Data Peserta setelah screening: " + screenedData.rowCount());
        return screenedData;
    }
}
