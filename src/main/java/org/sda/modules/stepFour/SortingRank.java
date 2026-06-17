package org.sda.modules.stepFour;

import java.util.*;
import org.sda.core.MergeSort;
import org.sda.helpers.Peserta;
import tech.tablesaw.api.*;

public class SortingRank {

    public static Map<Integer, Table> main(Table weightedGrades, Table infoJurusan) {
        try {
            Map<Integer, Integer> kuotaMap = new HashMap<>();
            for (int i = 0; i < infoJurusan.rowCount(); i++) {
                kuotaMap.put(
                    infoJurusan.intColumn("Id_jurusan").get(i),
                    infoJurusan.intColumn("Daya Tampung").get(i)
                );
            }

            List<Peserta> semuaPeserta = new ArrayList<>();
            for (int i = 0; i < weightedGrades.rowCount(); i++) {
                semuaPeserta.add(
                    new Peserta(
                        weightedGrades.stringColumn("ID Calon Mahasiswa").get(i),
                        weightedGrades.intColumn("ID Jurusan Pertama").get(i),
                        weightedGrades.intColumn("ID Jurusan Kedua").get(i),
                        weightedGrades.doubleColumn("Bobot Final Pertama").get(i),
                        weightedGrades.doubleColumn("Bobot Final Kedua").get(i)
                    )
                );
            }

            Map<String, String> statusDiterima = new HashMap<>(); // ID Peserta -> ID Jurusan yang menerima
            Map<Integer, List<Peserta>> daftarDiterimaPerJurusan = new HashMap<>();

            Map<Integer, List<Peserta>> applicantsByJur1 = new HashMap<>();
            for (Peserta p : semuaPeserta)
                applicantsByJur1.computeIfAbsent(p.getJur1(), k -> new ArrayList<>()).add(p);

            for (Integer idJur : kuotaMap.keySet()) {
                List<Peserta> list = applicantsByJur1.getOrDefault(idJur, new ArrayList<>());
                MergeSort.sort(list, 1);
                int kuota = kuotaMap.get(idJur);
                for (int i = 0; i < Math.min(list.size(), kuota); i++) {
                    Peserta p = list.get(i);
                    statusDiterima.put(
                        p.getId(),
                        "Jurusan_" + idJur + "|" + (i + 1) + "|" + p.getBobot1()
                    );
                    daftarDiterimaPerJurusan.computeIfAbsent(idJur, k -> new ArrayList<>()).add(p);
                }
            }

            Map<Integer, List<Peserta>> applicantsByJur2 = new HashMap<>();
            for (Peserta p : semuaPeserta) {
                if (!statusDiterima.containsKey(p.getId())) {
                    applicantsByJur2.computeIfAbsent(p.getJur2(), k -> new ArrayList<>()).add(p);
                }
            }

            for (Integer idJur : kuotaMap.keySet()) {
                List<Peserta> list = applicantsByJur2.getOrDefault(idJur, new ArrayList<>());
                MergeSort.sort(list, 2);
                int kuotaTerpakai = daftarDiterimaPerJurusan
                    .getOrDefault(idJur, new ArrayList<>())
                    .size();
                int kuotaSisa = kuotaMap.get(idJur) - kuotaTerpakai;

                for (int i = 0; i < Math.min(list.size(), kuotaSisa); i++) {
                    Peserta p = list.get(i);
                    statusDiterima.put(
                        p.getId(),
                        "Jurusan_" + idJur + "|" + (kuotaTerpakai + i + 1) + "|" + p.getBobot2()
                    );
                    daftarDiterimaPerJurusan.computeIfAbsent(idJur, k -> new ArrayList<>()).add(p);
                }
            }

            Map<Integer, Table> finalResult = new HashMap<>();
            for (Integer idJur : kuotaMap.keySet()) {
                List<Peserta> listJurusan = daftarDiterimaPerJurusan.getOrDefault(
                    idJur,
                    new ArrayList<>()
                );
                Table table = Table.create("Jurusan_" + idJur).addColumns(
                    StringColumn.create("ID Peserta"),
                    IntColumn.create("Ranking"),
                    DoubleColumn.create("Nilai Final"),
                    StringColumn.create("Status")
                );

                for (int i = 0; i < listJurusan.size(); i++) {
                    Peserta p = listJurusan.get(i);
                    String[] info = statusDiterima.get(p.getId()).split("\\|");
                    table.stringColumn("ID Peserta").append(p.getId());
                    table.intColumn("Ranking").append(Integer.parseInt(info[1]));
                    table.doubleColumn("Nilai Final").append(Double.parseDouble(info[2]));
                    table.stringColumn("Status").append("Diterima");
                }
                finalResult.put(idJur, table);
            }
            return finalResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
