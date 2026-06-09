package com.example;

public class RecuCommande {
    private final String referenceProduit;
    private final int quantite;
    private final double montantTotal;
    private final String message;

    public RecuCommande(String referenceProduit, int quantite, double montantTotal, String message) {
        this.referenceProduit = referenceProduit;
        this.quantite = quantite;
        this.montantTotal = montantTotal;
        this.message = message;
    }

    public String getReferenceProduit() { return referenceProduit; }
    public int getQuantite() { return quantite; }
    public double getMontantTotal() { return montantTotal; }
    public String getMessage() { return message; }
}
