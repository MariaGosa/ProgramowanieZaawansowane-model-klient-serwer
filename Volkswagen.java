package com.example.server;

import java.io.Serializable;

public class Volkswagen implements Serializable {
    private String golf;
    private String polo;
    private String passat;
    private String tCross;

    public Volkswagen(String golf, String polo, String passat, String tCross) {
        this.golf = golf;
        this.polo = polo;
        this.passat = passat;
        this.tCross = tCross;
    }

    @Override
    public String toString() {
        return "Volkswagen{" +
                "golf='" + golf + '\'' +
                ", polo='" + polo + '\'' +
                ", passat='" + passat + '\'' +
                ", tCross='" + tCross + '\'' +
                '}';
    }
}
