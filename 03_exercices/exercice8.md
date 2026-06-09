# Tests BDD d’un système de réservation de salles

## Sujet

Vous travaillez sur un module Java permettant de réserver des salles de réunion dans une entreprise.

Le service métier doit permettre :


- de réserver une salle existante ;
- de vérifier la capacité de la salle ;
- de vérifier que la période demandée est valide ;
- de refuser une réservation si la salle est déjà occupée ;
- d’envoyer une confirmation lorsque la réservation est acceptée.


## Règles métier

Une salle possède :

- code
- nom
- capacité maximale


Une réservation possède :

- email utilisateur
- code salle
- nombre de participants
- date de début
- date de fin


Une réservation est refusée si :

- la salle n’existe pas
- le nombre de participants dépasse la capacité
- la date de fin est avant ou égale à la date de début
- la salle est déjà réservée sur le créneau demandé

Une réservation est acceptée si :

- la salle existe
- la capacité est suffisante
- le créneau est valide
- aucune réservation existante ne chevauche le créneau demandé


En cas d’acceptation, une confirmation doit être envoyée.

## Travail demandé

Créer plusieurs fichiers `.feature`, séparés par comportement métier.

Tester au minimum :


- réservation acceptée
- réservation acceptée à capacité maximale
- réservation refusée si salle inconnue
- réservation refusée si capacité insuffisante
- réservation refusée si période invalide
- réservation refusée si conflit de réservation
- réservation acceptée si le créneau commence après une réservation existante
- notification envoyée en cas de succès
- notification non envoyée en cas d’échec
