package com.example;

import java.util.List;

public class ReservationService {
    private final SalleRepository salleRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public ReservationService(SalleRepository salleRepository,
                              ReservationRepository reservationRepository,
                              NotificationService notificationService) {
        this.salleRepository = salleRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    public ConfirmationReservation reserver(Reservation reservation) {
        Salle salle = salleRepository.findByCode(reservation.getCodeSalle())
                .orElseThrow(() -> new ReservationException("Salle inconnue"));

        if (!reservation.getFin().isAfter(reservation.getDebut())) {
            throw new ReservationException("Période invalide");
        }

        if (reservation.getNbParticipants() > salle.getCapaciteMax()) {
            throw new ReservationException("Capacité insuffisante");
        }

        List<Reservation> existantes = reservationRepository.findBySalle(reservation.getCodeSalle());
        boolean conflit = existantes.stream().anyMatch(r ->
                reservation.getDebut().isBefore(r.getFin()) &&
                reservation.getFin().isAfter(r.getDebut())
        );
        if (conflit) {
            throw new ReservationException("Créneau indisponible");
        }

        ConfirmationReservation confirmation = new ConfirmationReservation(
                reservation.getCodeSalle(),
                reservation.getEmailUtilisateur(),
                reservation.getDebut(),
                reservation.getFin(),
                "Réservation confirmée"
        );
        notificationService.envoyerConfirmation(confirmation);
        return confirmation;
    }
}
