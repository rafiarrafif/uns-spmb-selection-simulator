package org.sda.modules.stepOne;

import tech.tablesaw.api.Table;

public class DatasetContainer {

    public Table studentData;
    public Table nilaiTesData;
    public Table pilihanJurusanData;
    public Table jurusanData;

    public DatasetContainer(
        Table studentData,
        Table nilaiTesData,
        Table pilihanJurusanData,
        Table jurusanData
    ) {
        this.studentData = studentData;
        this.nilaiTesData = nilaiTesData;
        this.pilihanJurusanData = pilihanJurusanData;
        this.jurusanData = jurusanData;
    }
}
