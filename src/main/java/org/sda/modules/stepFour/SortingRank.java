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

            Map<Integer, List<Peserta>> applicantsByJur1 = new HashMap<>();
            for (Peserta p : semuaPeserta) {
                applicantsByJur1.computeIfAbsent(p.getJur1(), k -> new ArrayList<>()).add(p);
            }

            Set<String> mhsLolos = new HashSet<>();
            Map<Integer, List<Object[]>> hasilPerJurusan = new HashMap<>();

            for (Integer idJur : kuotaMap.keySet()) {
                List<Peserta> list = applicantsByJur1.getOrDefault(idJur, new ArrayList<>());
                MergeSort.sort(list, 1);

                int kuota = kuotaMap.get(idJur);
                List<Object[]> rows = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    Peserta p = list.get(i);
                    boolean diterima = (i < kuota);
                    if (diterima) {
                        mhsLolos.add(p.getId());
                        kuota--;
                    }
                    rows.add(new Object[] {
                        p.getId(),
                        (i + 1),
                        p.getBobot1(),
                        diterima ? "Diterima" : "Tidak Diterima",
                    });
                }
                hasilPerJurusan.put(idJur, rows);
                kuotaMap.put(idJur, kuota);
            }

            Map<Integer, List<Peserta>> applicantsByJur2 = new HashMap<>();
            for (Peserta p : semuaPeserta) {
                if (!mhsLolos.contains(p.getId())) {
                    applicantsByJur2.computeIfAbsent(p.getJur2(), k -> new ArrayList<>()).add(p);
                }
            }

            for (Integer idJur : kuotaMap.keySet()) {
                List<Peserta> list = applicantsByJur2.getOrDefault(idJur, new ArrayList<>());
                MergeSort.sort(list, 2);

                int kuotaSisa = kuotaMap.get(idJur);
                List<Object[]> rows = hasilPerJurusan.get(idJur);

                for (int i = 0; i < list.size(); i++) {
                    Peserta p = list.get(i);
                    boolean diterima = (i < kuotaSisa);
                    if (diterima) {
                        mhsLolos.add(p.getId());
                        kuotaSisa--;
                    }
                    rows.add(new Object[] {
                        p.getId(),
                        (i + 1),
                        p.getBobot2(),
                        diterima ? "Diterima" : "Tidak Diterima",
                    });
                }
            }

            Map<Integer, Table> finalResult = new HashMap<>();
            for (Integer idJur : hasilPerJurusan.keySet()) {
                Table table = Table.create("Jurusan_" + idJur).addColumns(
                    StringColumn.create("ID Peserta"),
                    IntColumn.create("Ranking"),
                    DoubleColumn.create("Nilai Final"),
                    StringColumn.create("Status")
                );

                for (Object[] row : hasilPerJurusan.get(idJur)) {
                    table.stringColumn("ID Peserta").append((String) row[0]);
                    table.intColumn("Ranking").append((Integer) row[1]);
                    table.doubleColumn("Nilai Final").append((Double) row[2]);
                    table.stringColumn("Status").append((String) row[3]);
                }
                finalResult.put(idJur, table);
            }

            return finalResult;
        } catch (Exception e) {
            System.out.println("Error during sorting and ranking: " + e.getMessage());
            return null;
        }
    }
}
