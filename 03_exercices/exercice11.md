#  API Spring Boot avec tests TDD et BDD

## Contexte

Vous devez développer une API REST Spring Boot permettant de gérer des tickets de support.

L'objectif principal du TP n'est pas de créer une application métier complexe, mais de mettre en pratique les notions vues.
L'application ne doit pas utiliser de base de données. Les données seront stockées en mémoire afin de concentrer le TP sur la structure de l'API et les tests.

---

## Fonctionnalités attendues

L'API doit permettre de :

1. Créer un ticket de support.
2. Consulter un ticket par son identifiant.
3. Lister tous les tickets.
4. Modifier le statut d'un ticket.
5. Gérer les erreurs métier et les erreurs de validation.

---

## Règles métier

Un ticket possède au minimum :

- un identifiant généré automatiquement ;
- un titre ;
- une priorité ;
- un statut.

Les priorités possibles sont :

- `LOW` ;
- `MEDIUM` ;
- `HIGH`.

Les statuts possibles sont :

- `OPEN` ;
- `IN_PROGRESS` ;
- `RESOLVED`.

À la création, un ticket doit toujours être créé avec le statut `OPEN`.

Le titre est obligatoire et doit contenir au moins 3 caractères utiles.

La priorité est obligatoire.

Un ticket inexistant doit produire une erreur adaptée.

Les changements de statut autorisés sont :

- `OPEN` vers `IN_PROGRESS` ;
- `OPEN` vers `RESOLVED` ;
- `IN_PROGRESS` vers `RESOLVED`.

Un ticket déjà `RESOLVED` ne peut plus changer de statut.

---

## Endpoints attendus

Vous devez proposer une API REST cohérente contenant au minimum les routes suivantes :

- création d'un ticket ;
- consultation d'un ticket par identifiant ;
- liste des tickets ;
- modification du statut d'un ticket.

Vous devez utiliser des codes HTTP adaptés :

- `201 Created` pour une création réussie ;
- `200 OK` pour une consultation ou une modification réussie ;
- `400 Bad Request` pour une erreur de validation ;
- `404 Not Found` lorsqu'un ticket n'existe pas ;
- `409 Conflict` lorsqu'une règle métier empêche l'action.

---

## Contraintes techniques

Le projet doit être un projet Maven Spring Boot.

Le stockage doit être fait en mémoire via une classe repository.

Le projet doit respecter une organisation claire en packages :

- `controller` ;
- `service` ;
- `repository` ;
- `model` ou `dto` ;
- `exception` ;
- `bdd` côté tests.

---

## Travail demandé sur la partie TDD

Vous devez écrire des tests unitaires sur la couche service.

Ces tests doivent notamment vérifier :

- la création correcte d'un ticket ;
- le statut par défaut à la création ;
- la recherche d'un ticket existant ;
- le comportement lorsqu'un ticket n'existe pas ;
- les transitions de statut autorisées ;
- le refus d'une transition interdite.

Les tests doivent être lisibles, nommés correctement et structurés en Arrange / Act / Assert.

Vous devez utiliser Mockito pour isoler la couche service de son repository.

---

## Travail demandé sur la partie tests d'API

Vous devez écrire des tests de contrôleur avec MockMvc.

Ces tests doivent notamment vérifier :

- le code HTTP retourné ;
- le contenu JSON retourné ;
- le comportement en cas de requête invalide ;
- le comportement lorsqu'une ressource n'existe pas ;
- le comportement en cas de conflit métier.

---

## Travail demandé sur la partie intégration

Vous devez écrire au moins un test d'intégration Spring Boot.

Ce test doit charger le contexte Spring et vérifier un parcours simple via l'API :

- créer un ticket ;
- récupérer ce ticket ;
- modifier son statut.

---

## Travail demandé sur la partie BDD

Vous devez écrire au moins un fichier `.feature` en Gherkin.

Les scénarios doivent être compréhensibles par une personne non technique.

Ils doivent utiliser les mots-clés :

- `Feature` ;
- `Scenario` ;
- `Given` ;
- `When` ;
- `Then` ;
- `And`.

Les scénarios doivent couvrir au minimum :

- la création d'un ticket valide ;
- la résolution d'un ticket ;
- le refus de modification d'un ticket déjà résolu ;
- la consultation d'un ticket inexistant.

Les scénarios doivent être connectés à du code Java via des step definitions Cucumber.

---

## Livrables attendus

Vous devez fournir :

1. Le projet Spring Boot complet.
2. Les tests unitaires de service.
3. Les tests de contrôleur.
4. Un test d'intégration.
5. Les scénarios BDD `.feature`.
6. Les classes de configuration et de steps Cucumber.
7. Un projet exécutable avec `mvn test`.

---

## Conseils

Commencez par les règles métier dans le service.

Écrivez les tests de service avant ou pendant l'implémentation.

Ajoutez ensuite le contrôleur REST.

Testez le contrôleur avec MockMvc sans lancer toute l'application.

Terminez par les tests d'intégration et les scénarios BDD.

Ne cherchez pas à ajouter une base de données ou une architecture trop complexe : le but est de maîtriser les tests.
