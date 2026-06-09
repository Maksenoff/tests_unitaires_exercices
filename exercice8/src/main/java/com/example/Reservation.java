package com.example;

import java.time.LocalDateTime;

public class Reservation {
    private final String emailUtilisateur;
    private final String codeSalle;
    private final int nbParticipants;
    private final LocalDateTime debut;
    private final LocalDateTime fin;

    public Reservation(String emailUtilisateur, String codeSalle, int nbParticipants,
                       LocalDateTime debut, LocalDateTime fin) {
        this.emailUtilisateur = emailUtilisateur;
        this.codeSalle = codeSalle;
        this.nbParticipants = nbParticipants;
        this.debut = debut;
        this.fin = fin;
    }

    public String getEmailUtilisateur() { return emailUtilisateur; }
    public String getCodeSalle() { return codeSalle; }
    public int getNbParticipants() { return nbParticipants; }
    public LocalDateTime getDebut() { return debut; }
    public LocalDateTime getFin() { return fin; }
}
