# Exercice BDD — Parcours client d'une boutique en ligne

## Objectif pédagogique

L'objectif de cet exercice est de pratiquer le **Behavior Driven Development** avec **Cucumber**, **Gherkin**, **JUnit Platform** et **Mockito**.

Vous devez écrire des scénarios BDD décrivant le comportement attendu d'une application de boutique en ligne, puis automatiser ces scénarios avec des classes de step definitions.

À la fin de l'exercice, l'exécution de la commande Maven doit produire :

- le résultat des scénarios dans la console ;
- un rapport HTML Cucumber dans `target/cucumber-report.html` ;
- un rapport JSON Cucumber dans `target/cucumber-report.json`.



## Contexte fonctionnel

On souhaite développer une application simplifiée de boutique en ligne.

L'application doit permettre à un utilisateur de :

1. créer un compte ;
2. se connecter ;
3. rechercher des produits ;
4. naviguer par catégorie ;
5. ajouter des produits à une commande ;
6. supprimer ou diminuer la quantité de produits dans une commande ;
7. valider une commande.

## Contraintes techniques attendues

Le projet doit respecter les points suivants :

- utiliser Maven ;
- utiliser Cucumber avec le moteur JUnit Platform ;
- placer les fichiers `.feature` dans `src/test/resources/features` ;
- placer les step definitions dans un package dédié ;
- utiliser Mockito pour isoler les services de leurs repositories ;
- utiliser les assertions JUnit Jupiter ;
- générer les rapports Cucumber HTML et JSON à chaque exécution des tests ;
- garder les scénarios lisibles, courts et orientés comportement métier.

## User Story 1 — Création de compte

En tant qu'utilisateur, je veux créer un compte pour pouvoir passer des commandes.

### Critères d'acceptation

- L'utilisateur peut accéder à un formulaire d'inscription.
- L'utilisateur doit fournir un email, un nom d'utilisateur et un mot de passe.
- L'utilisateur reçoit une confirmation après l'inscription.
- Une erreur est renvoyée lors de la création d'un compte avec un identifiant déjà existant.

## User Story 2 — Connexion

En tant qu'utilisateur, je veux me connecter à mon compte pour accéder à l'application et passer des commandes.

### Critères d'acceptation

- L'utilisateur peut accéder à un formulaire de connexion.
- L'utilisateur doit entrer son nom d'utilisateur et son mot de passe.
- L'utilisateur voit un message d'erreur en cas de connexion échouée.
- L'utilisateur est redirigé vers la page d'accueil après une connexion réussie.

## User Story 3 — Recherche de produits

En tant qu'utilisateur, je veux rechercher des produits pour trouver rapidement ce dont j'ai besoin.

### Critères d'acceptation

- L'utilisateur peut accéder à une barre de recherche.
- L'utilisateur voit une liste de résultats pertinente après avoir entré un mot-clé.
- L'utilisateur peut rechercher des produits par prix maximum.

## User Story 4 — Navigation par catégorie

En tant qu'utilisateur, je veux naviguer par catégorie de produits pour découvrir ce qui est disponible.

### Critères d'acceptation

- L'utilisateur peut accéder à une page de catégories.
- L'utilisateur peut sélectionner une catégorie.
- L'application retourne les produits correspondant à cette catégorie.

## User Story 5 — Ajout de produit à une commande

En tant qu'utilisateur, je veux ajouter des produits à ma commande.

### Critères d'acceptation

- L'utilisateur peut cliquer sur un bouton "Ajouter à la commande" depuis une page produit.
- L'utilisateur voit une confirmation que le produit a été ajouté à la commande.
- Si le produit est déjà présent dans la commande, sa quantité est augmentée de 1.
- Une erreur est renvoyée si la commande n'existe pas.

## User Story 6 — Suppression de produit d'une commande

En tant qu'utilisateur, je veux supprimer des produits de ma commande.

### Critères d'acceptation

- L'utilisateur peut cliquer sur un bouton "Supprimer" à côté de chaque produit dans la commande.
- Si la quantité du produit est supérieure à 1, elle est diminuée de 1.
- Si la quantité du produit est égale à 1, le produit est retiré de la commande.
- Une erreur est renvoyée si le produit n'est pas présent dans la commande.
- Une erreur est renvoyée si la commande n'existe pas.

## User Story 7 — Validation de commande

En tant qu'utilisateur, je veux valider une commande.

### Critères d'acceptation

- L'utilisateur peut accéder à un formulaire de commande.
- L'utilisateur reçoit une confirmation de commande après validation.
- Une erreur est renvoyée si la commande n'existe pas.

## Travail demandé

1. Lire les user stories et les critères d'acceptation.
2. Écrire les scénarios Gherkin correspondant aux comportements attendus.
3. Implémenter les step definitions.
4. Utiliser Mockito pour simuler les repositories.
5. Exécuter les tests avec `mvn test`.
6. Vérifier que tous les scénarios passent.
7. Ouvrir le rapport HTML généré dans `target/cucumber-report.html`.
