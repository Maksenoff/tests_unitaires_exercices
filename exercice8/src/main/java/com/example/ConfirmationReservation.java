package com.example;

import java.time.LocalDateTime;

public class ConfirmationReservation {
    private final String codeSalle;
    private final String emailUtilisateur;
    private final LocalDateTime debut;
    private final LocalDateTime fin;
    private final String message;

    public ConfirmationReservation(String codeSalle, String emailUtilisateur,
                                   LocalDateTime debut, LocalDateTime fin, String message) {
        this.codeSalle = codeSalle;
        this.emailUtilisateur = emailUtilisateur;
        this.debut = debut;
        this.fin = fin;
        this.message = message;
    }

    public String getCodeSalle() { return codeSalle; }
    public String getEmailUtilisateur() { return emailUtilisateur; }
    public LocalDateTime getDebut() { return debut; }
    public LocalDateTime getFin() { return fin; }
    public String getMessage() { return message; }
}
