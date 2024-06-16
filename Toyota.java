package com.example.server;

import java.io.Serializable;

public class Toyota implements Serializable {
    private String yaris;
    private String corolla;
    private String supra;
    private String aygo;

    public Toyota(String yaris, String corolla, String supra, String aygo) {
        this.yaris = yaris;
        this.corolla = corolla;
        this.supra = supra;
        this.aygo = aygo;
    }

    @Override
    public String toString() {
        return "Toyota{" +
                "yaris='" + yaris + '\'' +
                ", corolla='" + corolla + '\'' +
                ", supra='" + supra + '\'' +
                ", aygo='" + aygo + '\'' +
                '}';
    }
}
