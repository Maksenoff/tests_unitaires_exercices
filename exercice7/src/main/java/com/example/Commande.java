package com.example;

public class Commande {
    private final String emailClient;
    private final String referenceProduit;
    private final int quantite;
    private final TypeClient typeClient;

    public Commande(String emailClient, String referenceProduit, int quantite, TypeClient typeClient) {
        this.emailClient = emailClient;
        this.referenceProduit = referenceProduit;
        this.quantite = quantite;
        this.typeClient = typeClient;
    }

    public String getEmailClient() { return emailClient; }
    public String getReferenceProduit() { return referenceProduit; }
    public int getQuantite() { return quantite; }
    public TypeClient getTypeClient() { return typeClient; }
}
