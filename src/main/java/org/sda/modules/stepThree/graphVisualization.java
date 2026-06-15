package org.sda.modules.stepThree;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

public class graphVisualization {

    public static void main(Table studentWithDegree) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Hubungan Calon Mahasiswa dan Jurusan");

        String cssStyle =
            "node { size: 20px; text-size: 16px; text-alignment: center; text-background-mode: rounded-box; text-background-color: white; }" +
            "node.cama { fill-color: #3498db; shape: box; }" +
            "node.jurusan { fill-color: #e74c3c; shape: circle; }" +
            "edge { size: 1.5px; arrow-size: 8px; arrow-shape: arrow; }" +
            "edge.pilihan1 { fill-color: #2ecc71; }" +
            "edge.pilihan2 { fill-color: #f1c40f; }";
        graph.setAttribute("ui.stylesheet", cssStyle);

        for (Row row : studentWithDegree) {
            String idCama = row.getString("ID Calon Mahasiswa");
            String namaJurusan1 = row.getString("Nama Jurusan Pilihan Pertama");
            String namaJurusan2 = row.getString("Nama Jurusan Pilihan Kedua");

            if (graph.getNode(idCama) == null) {
                Node n = graph.addNode(idCama);
                n.setAttribute("ui.label", idCama);
                n.setAttribute("ui.class", "cama");
            }

            if (namaJurusan1 != null && graph.getNode(namaJurusan1) == null) {
                Node n = graph.addNode(namaJurusan1);
                n.setAttribute("ui.label", namaJurusan1);
                n.setAttribute("ui.class", "jurusan");
            }

            if (namaJurusan2 != null && graph.getNode(namaJurusan2) == null) {
                Node n = graph.addNode(namaJurusan2);
                n.setAttribute("ui.label", namaJurusan2);
                n.setAttribute("ui.class", "jurusan");
            }

            if (namaJurusan1 != null) {
                String edgeId1 = idCama + "_to_" + namaJurusan1 + "_pil1";
                if (graph.getEdge(edgeId1) == null) {
                    Edge e = graph.addEdge(edgeId1, idCama, namaJurusan1, true);
                    e.setAttribute("ui.label", "Pilihan 1");
                    e.setAttribute("ui.class", "pilihan1");
                }
            }

            if (namaJurusan2 != null) {
                String edgeId2 = idCama + "_to_" + namaJurusan2 + "_pil2";
                if (graph.getEdge(edgeId2) == null) {
                    Edge e = graph.addEdge(edgeId2, idCama, namaJurusan2, true);
                    e.setAttribute("ui.label", "Pilihan 2");
                    e.setAttribute("ui.class", "pilihan2");
                }
            }
        }

        graph.display();
    }
}
