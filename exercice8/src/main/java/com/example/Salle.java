package com.example;

public class Salle {
    private final String code;
    private final String nom;
    private final int capaciteMax;

    public Salle(String code, String nom, int capaciteMax) {
        this.code = code;
        this.nom = nom;
        this.capaciteMax = capaciteMax;
    }

    public String getCode() { return code; }
    public String getNom() { return nom; }
    public int getCapaciteMax() { return capaciteMax; }
}
