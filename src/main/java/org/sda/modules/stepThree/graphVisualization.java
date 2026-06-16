package org.sda.modules.stepThree;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

public class graphVisualization {

    public static void main(Table studentWithDegree) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Hubungan Calon Mahasiswa dan Jurusan");

        String cssStyle =
            "node { size: 15px; text-size: 14px; text-alignment: center; text-background-mode: rounded-box; text-background-color: #FFFFFF; z-index: 3; }" +
            "node.jurusan { fill-color: #089FFB; text-color: #000000; text-background-color: #FFFFFF; text-size: 18px; text-style: bold; z-index: 5; }" +
            "node.jurusandimmed { fill-color: #089FFB; text-color: #000000; text-background-color: #FFFFFF; text-size: 18px; text-style: bold; z-index: 5; }" +
            "node.cama { fill-color: #B6B6B6; text-mode: hidden; z-index: 1; }" +
            "edge { size: 1.5px; arrow-size: 8px; arrow-shape: arrow; z-index: 2; }" +
            "edge.pilihan1 { fill-color: #B6B6B6; }" +
            "edge.pilihan2 { fill-color: #B6B6B6; }" +
            "node.camaactive { fill-color: #FBCB09; text-color: #000000; text-background-color: #FFFFFF; text-mode: normal; text-size: 12px; z-index: 4; }" +
            "edge.pilihan1active { fill-color: #D23C05; size: 2.5px; z-index: 3; }" +
            "edge.pilihan2active { fill-color: #D23C05; size: 2.5px; z-index: 3; }";

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
                    e.setAttribute("ui.class", "pilihan1");
                }
            }

            if (namaJurusan2 != null) {
                String edgeId2 = idCama + "_to_" + namaJurusan2 + "_pil2";
                if (graph.getEdge(edgeId2) == null) {
                    Edge e = graph.addEdge(edgeId2, idCama, namaJurusan2, true);
                    e.setAttribute("ui.class", "pilihan2");
                }
            }
        }

        Viewer viewer = graph.display();
        viewer.enableAutoLayout();
        View view = viewer.getDefaultView();
        if (view instanceof java.awt.Component) {
            java.awt.Component viewComponent = (java.awt.Component) view;
            org.graphstream.ui.view.camera.Camera camera = view.getCamera();
            org.graphstream.ui.view.ViewerPipe pipe = viewer.newViewerPipe();

            viewComponent.addMouseWheelListener(
                new java.awt.event.MouseWheelListener() {
                    @Override
                    public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                        int rotation = e.getWheelRotation();
                        boolean isCtrlDown = e.isControlDown();
                        boolean isShiftDown = e.isShiftDown();

                        if (isCtrlDown) {
                            double currentZoom = camera.getViewPercent();
                            double zoomFactor = (rotation < 0) ? 0.9 : 1.1;
                            double newZoom = currentZoom * zoomFactor;
                            if (newZoom > 0.05 && newZoom < 5.0) {
                                camera.setViewPercent(newZoom);
                            }
                        } else if (isShiftDown) {
                            org.graphstream.ui.geom.Point3 currentCenter = camera.getViewCenter();
                            double shiftAmount = 0.1 * camera.getViewPercent();
                            double newX = currentCenter.x + (rotation * shiftAmount);
                            camera.setViewCenter(newX, currentCenter.y, 0);
                        } else {
                            org.graphstream.ui.geom.Point3 currentCenter = camera.getViewCenter();
                            double shiftAmount = 0.1 * camera.getViewPercent();
                            double newY = currentCenter.y - (rotation * shiftAmount);
                            camera.setViewCenter(currentCenter.x, newY, 0);
                        }
                    }
                }
            );

            pipe.addViewerListener(
                new org.graphstream.ui.view.ViewerListener() {
                    @Override
                    public void viewClosed(String viewName) {}

                    @Override
                    public void buttonPushed(String id) {
                        Node clickedNode = graph.getNode(id);
                        if (clickedNode == null) return;

                        String clickedClass = clickedNode.getAttribute("ui.class").toString();

                        if (
                            "jurusan".equals(clickedClass) || "jurusandimmed".equals(clickedClass)
                        ) {
                            for (Node n : graph) {
                                String nClass = n.getAttribute("ui.class").toString();
                                if (nClass.contains("jurusan")) {
                                    n.setAttribute("ui.class", "jurusandimmed");
                                } else {
                                    n.setAttribute("ui.class", "cama");
                                }
                            }

                            graph.edges().forEach(e -> {
                                String currentClass = e.getAttribute("ui.class").toString();
                                if (currentClass.contains("pilihan1")) {
                                    e.setAttribute("ui.class", "pilihan1");
                                } else if (currentClass.contains("pilihan2")) {
                                    e.setAttribute("ui.class", "pilihan2");
                                }
                            });

                            clickedNode.setAttribute("ui.class", "jurusan");

                            java.util.stream.Stream<Edge> enteringEdges =
                                clickedNode.enteringEdges();
                            enteringEdges.forEach(edge -> {
                                String edgeClass = edge.getAttribute("ui.class").toString();

                                if ("pilihan1".equals(edgeClass)) {
                                    edge.setAttribute("ui.class", "pilihan1active");
                                } else if ("pilihan2".equals(edgeClass)) {
                                    edge.setAttribute("ui.class", "pilihan2active");
                                }

                                Node studentNode = edge.getSourceNode();
                                studentNode.setAttribute("ui.class", "camaactive");
                            });
                        }
                    }

                    @Override
                    public void buttonReleased(String id) {}

                    @Override
                    public void mouseOver(String id) {}

                    @Override
                    public void mouseLeft(String id) {}
                }
            );

            new Thread(() -> {
                while (true) {
                    pipe.pump();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
