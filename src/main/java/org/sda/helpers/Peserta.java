package org.sda.helpers;

public class Peserta {

    private String id;
    private int jur1;
    private int jur2;
    private double bobot1;
    private double bobot2;

    public Peserta(String id, int jur1, int jur2, double bobot1, double bobot2) {
        this.id = id;
        this.jur1 = jur1;
        this.jur2 = jur2;
        this.bobot1 = bobot1;
        this.bobot2 = bobot2;
    }

    public String getId() {
        return id;
    }

    public int getJur1() {
        return jur1;
    }

    public int getJur2() {
        return jur2;
    }

    public double getBobot1() {
        return bobot1;
    }

    public double getBobot2() {
        return bobot2;
    }
}
