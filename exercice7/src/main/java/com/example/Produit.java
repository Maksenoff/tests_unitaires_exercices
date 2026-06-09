package com.example;

public class Produit {
    private final String reference;
    private final String nom;
    private final double prixUnitaire;
    private final int stock;

    public Produit(String reference, String nom, double prixUnitaire, int stock) {
        this.reference = reference;
        this.nom = nom;
        this.prixUnitaire = prixUnitaire;
        this.stock = stock;
    }

    public String getReference() { return reference; }
    public String getNom() { return nom; }
    public double getPrixUnitaire() { return prixUnitaire; }
    public int getStock() { return stock; }
}
