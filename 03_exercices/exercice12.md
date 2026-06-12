# API Spring Boot avec TDD, BDD, Cucumber et JaCoCo

## Contexte

Vous devez développer une API REST Spring Boot permettant de gérer des **salles de réunion** et leurs **réservations**.

L’objectif principal du TP n’est pas de créer une application métier complète, mais de mettre en pratique les notions vues.

## Fonctionnalités attendues

### 1. Gestion des salles

L’API doit permettre de créer une salle de réunion.

Une salle possède au minimum :

- un identifiant ;
- un nom ;
- une capacité.

Règles attendues :

- le nom est obligatoire ;
- la capacité doit être supérieure ou égale à 1.

L’API doit aussi permettre de lister les salles existantes.

### 2. Gestion des réservations

L’API doit permettre de créer une réservation pour une salle.

Une réservation possède au minimum :

- un identifiant ;
- l’identifiant de la salle ;
- le nom de la personne qui réserve ;
- une date/heure de début ;
- une date/heure de fin ;
- un statut.

Règles attendues :

- la salle doit exister ;
- le nom de la personne qui réserve est obligatoire ;
- la date/heure de fin doit être strictement après la date/heure de début ;
- deux réservations confirmées ne peuvent pas se chevaucher pour une même salle ;
- une réservation peut être annulée ;
- une réservation déjà annulée ne doit pas pouvoir être annulée une seconde fois.

## Endpoints attendus

Vous devez prévoir au minimum les endpoints suivants :

| Méthode | URI | Rôle |
|---|---|---|
| POST | `/api/rooms` | Créer une salle |
| GET | `/api/rooms` | Lister les salles |
| POST | `/api/reservations` | Créer une réservation |
| GET | `/api/reservations/{id}` | Consulter une réservation |
| PATCH | `/api/reservations/{id}/cancel` | Annuler une réservation |

## Gestion des erreurs attendue

L’API doit retourner des réponses HTTP cohérentes :

- `400 Bad Request` en cas de données invalides ;
- `404 Not Found` si une salle ou une réservation demandée n’existe pas ;
- `409 Conflict` si une règle métier empêche l’action demandée.

## Travail demandé côté tests

Vous devez écrire au minimum :

### Tests unitaires de service

Ils doivent utiliser JUnit et Mockito.

Ils doivent vérifier les règles métier importantes, notamment :

- création d’une réservation valide ;
- refus si la salle n’existe pas ;
- refus si le créneau est invalide ;
- refus si le créneau chevauche une réservation existante ;
- annulation d’une réservation confirmée ;
- refus d’annulation si la réservation est déjà annulée.

### Tests de controller

Ils doivent utiliser MockMvc.

Ils doivent vérifier au minimum :

- création correcte d’une salle ;
- erreur de validation lors d’une création invalide ;
- création correcte d’une réservation ;
- retour `404` si une ressource demandée n’existe pas ;
- retour `409` lors d’un conflit métier.

### Test d’intégration

Il doit lancer le contexte Spring et vérifier au moins un parcours complet via HTTP.

Exemple de parcours attendu :

1. créer une salle ;
2. créer une réservation ;
3. consulter la réservation ;
4. annuler la réservation.

### Scénarios BDD Cucumber

Vous devez écrire au minimum trois scénarios BDD :

- réservation acceptée quand la salle existe et que le créneau est libre ;
- réservation refusée quand la salle n’existe pas ;
- réservation refusée quand le créneau chevauche une réservation existante.

Les scénarios doivent être écrits en Gherkin avec `Given`, `When`, `Then` et éventuellement `And`.

## Rapports attendus

Le projet doit permettre de générer :

- un rapport Cucumber HTML ;
- un rapport Cucumber JSON ;
- un rapport JaCoCo HTML.

La commande de validation finale attendue est :

```bash
mvn clean verify
```

Les rapports doivent être consultables dans le dossier `target`.

## Contraintes

- Projet Maven.
- API Spring Boot.
- Pas de base de données obligatoire : un repository en mémoire est accepté.
- Code lisible, structuré et commenté.
- Tests lisibles, avec noms explicites.
