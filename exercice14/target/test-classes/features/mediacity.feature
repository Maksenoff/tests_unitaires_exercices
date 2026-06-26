# language: fr
Fonctionnalité: Gestion des réservations à la médiathèque

  Scénario: Réservation d'un ouvrage indisponible
    Etant donné le livre "Dune" est emprunté par un adhérent
    Quand un autre adhérent réserve "Dune"
    Alors la réservation est enregistrée avec succès

  Scénario: Plusieurs réservations sur le même ouvrage
    Etant donné le livre "Fondation" est emprunté par un adhérent
    Quand deux adhérents différents réservent "Fondation"
    Alors la file d'attente contient 2 réservations pour "Fondation"

  Scénario: Restitution d'un ouvrage réservé
    Etant donné le livre "Neuromancien" est emprunté par "Alice"
    Et "Bob" a réservé "Neuromancien"
    Quand "Alice" rend "Neuromancien"
    Alors la prochaine réservation pour "Neuromancien" est pour "Bob"

  Scénario: Refus d'une réservation pour un adhérent suspendu
    Etant donné l'adhérent "Charlie" est suspendu
    Quand "Charlie" tente de réserver "Le Seigneur des Anneaux"
    Alors la réservation est refusée

  Scénario: Calcul de pénalité pour retard de retour
    Etant donné "Alice" a emprunté "1984" le 1 janvier 2026
    Quand "Alice" rend "1984" le 31 janvier 2026
    Alors la pénalité est de "1.35" €
