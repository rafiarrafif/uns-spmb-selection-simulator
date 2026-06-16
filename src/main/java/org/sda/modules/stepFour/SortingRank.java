package org.sda.modules.stepFour;

import java.util.*;
import org.sda.core.MergeSort;
import org.sda.helpers.Peserta;
import tech.tablesaw.api.*;

public class SortingRank {

    public static Table main(Table weightedGrades, Table infoJurusan) {
        try {
            Map<Integer, Integer> kuotaMap = new HashMap<>();
            for (int i = 0; i < infoJurusan.rowCount(); i++) {
                int idJurusan = infoJurusan.intColumn("Id_jurusan").get(i);
                int dayaTampung = infoJurusan.intColumn("Daya Tampung").get(i);
                kuotaMap.put(idJurusan, dayaTampung);
            }

            List<Peserta> semuaPeserta = new ArrayList<>();
            for (int i = 0; i < weightedGrades.rowCount(); i++) {
                String idMhs = weightedGrades.stringColumn("ID Calon Mahasiswa").get(i);
                int jur1 = weightedGrades.intColumn("ID Jurusan Pertama").get(i);
                int jur2 = weightedGrades.intColumn("ID Jurusan Kedua").get(i);
                double bobot1 = weightedGrades.doubleColumn("Bobot Final Pertama").get(i);
                double bobot2 = weightedGrades.doubleColumn("Bobot Final Kedua").get(i);

                semuaPeserta.add(new Peserta(idMhs, jur1, jur2, bobot1, bobot2));
            }

            Map<Integer, List<Peserta>> kelompokJurusan1 = new HashMap<>();
            for (Peserta p : semuaPeserta) {
                kelompokJurusan1.computeIfAbsent(p.getJur1(), k -> new ArrayList<>()).add(p);
            }

            Set<String> mhsLolos = new HashSet<>();
            Map<String, Integer> diterimaDi = new HashMap<>();
            Map<String, String> peringkatPilihan1 = new HashMap<>();

            for (Map.Entry<Integer, List<Peserta>> entry : kelompokJurusan1.entrySet()) {
                int idJur = entry.getKey();
                List<Peserta> list = entry.getValue();
                MergeSort.sort(list, 1);
                int kuota = kuotaMap.getOrDefault(idJur, 0);
                int totalPendaftar = list.size();
                for (int rank = 0; rank < list.size(); rank++) {
                    Peserta p = list.get(rank);
                    int realRank = rank + 1;
                    peringkatPilihan1.put(p.getId(), realRank + "/" + totalPendaftar);

                    if (kuota > 0) {
                        mhsLolos.add(p.getId());
                        diterimaDi.put(p.getId(), idJur);
                        kuota--;
                    }
                }
                kuotaMap.put(idJur, kuota);
            }

            Map<Integer, List<Peserta>> kelompokJurusan2 = new HashMap<>();
            for (Peserta p : semuaPeserta) {
                kelompokJurusan2.computeIfAbsent(p.getJur2(), k -> new ArrayList<>()).add(p);
            }

            Map<String, String> peringkatPilihan2 = new HashMap<>();

            for (Map.Entry<Integer, List<Peserta>> entry : kelompokJurusan2.entrySet()) {
                int idJur = entry.getKey();
                List<Peserta> list = entry.getValue();
                MergeSort.sort(list, 2);

                int kuotaSisa = kuotaMap.getOrDefault(idJur, 0);
                int totalPendaftar = list.size();
                for (int rank = 0; rank < list.size(); rank++) {
                    Peserta p = list.get(rank);
                    int realRank = rank + 1;
                    peringkatPilihan2.put(p.getId(), realRank + "/" + totalPendaftar);

                    if (!mhsLolos.contains(p.getId()) && kuotaSisa > 0) {
                        mhsLolos.add(p.getId());
                        diterimaDi.put(p.getId(), idJur);
                        kuotaSisa--;
                    }
                }
                kuotaMap.put(idJur, kuotaSisa);
            }

            StringColumn resId = StringColumn.create("ID Calon Mahasiswa");
            StringColumn resStatus = StringColumn.create("Status");
            StringColumn resDiterima = StringColumn.create("Diterima Pada");
            StringColumn resRank1 = StringColumn.create("Peringkat pada jurusan pertama");
            StringColumn resRank2 = StringColumn.create("Peringkat pada jurusan kedua");
            for (Peserta p : semuaPeserta) {
                resId.append(p.getId());
                if (mhsLolos.contains(p.getId())) {
                    resStatus.append("Peserta Lolos");
                    resDiterima.append(String.valueOf(diterimaDi.get(p.getId())));
                    resRank1.append("-");
                    resRank2.append("-");
                } else {
                    resStatus.append("Peserta Tidak Lolos");
                    resDiterima.append("-");
                    resRank1.append(peringkatPilihan1.getOrDefault(p.getId(), "0/0"));
                    resRank2.append(peringkatPilihan2.getOrDefault(p.getId(), "0/0"));
                }
            }

            return Table.create("Hasil Seleksi PMB").addColumns(
                resId,
                resStatus,
                resDiterima,
                resRank1,
                resRank2
            );
        } catch (Exception e) {
            System.out.println("Error during sorting and ranking: " + e.getMessage());
            return null;
        }
    }
}
