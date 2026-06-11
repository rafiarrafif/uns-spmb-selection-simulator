package org.sda.modules.stepOne;

import tech.tablesaw.api.Table;

public class DatasetContainer {

    public final Table studentData;
    public final Table nilaiTesData;
    public final Table pilihanJurusanData;
    public final Table jurusanData;

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
