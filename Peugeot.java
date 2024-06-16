package com.example.server;

import java.io.Serializable;

public class Peugeot implements Serializable {
    private String peugeot207;
    private String peugeot508;
    private String peugeot5008;
    private String peugeot406;

    public Peugeot(String peugeot207, String peugeot508, String peugeot5008, String peugeot406) {
        this.peugeot207 = peugeot207;
        this.peugeot508 = peugeot508;
        this.peugeot5008 = peugeot5008;
        this.peugeot406 = peugeot406;
    }

    @Override
    public String toString() {
        return "Peugeot{" +
                "peugeot207='" + peugeot207 + '\'' +
                ", peugeot508='" + peugeot508 + '\'' +
                ", peugeot5008='" + peugeot5008 + '\'' +
                ", peugeot406='" + peugeot406 + '\'' +
                '}';
    }
}
