# Tests BDD d’un service de commande

## Sujet

Vous travaillez dans une équipe Java chargée de développer un module métier de commande pour une boutique en ligne.

Le service à développer doit permettre :

* de vérifier qu’un produit existe ;
* de vérifier le stock disponible ;
* de calculer le total d’une commande ;
* d’appliquer une remise selon le profil client ;
* de refuser une commande si le stock est insuffisant.


## Règles métier

Une commande contient :


- email client
- référence produit
- quantité commandée


Un produit contient :


- référence
- nom
- prix unitaire
- stock disponible


Les remises sont les suivantes :


- STANDARD : 0 %
- PREMIUM  : 10 %
- VIP      : 20 %


Si le produit demandé n’existe pas, la commande doit être refusée.

Si la quantité demandée est supérieure au stock disponible, la commande doit être refusée.

Si la commande est acceptée, le système retourne un reçu de commande contenant :


- référence produit
- quantité
- montant total
- message de confirmation


## Travail demandé


Vous devez créer plusieurs fichiers `.feature`, séparés par comportement métier.

Vous devez tester au minimum :


- commande acceptée pour client STANDARD
- commande acceptée pour client PREMIUM
- commande acceptée pour client VIP
- commande refusée si produit inconnu
- commande refusée si stock insuffisant


Vous devez utiliser Mockito pour simuler la dépendance qui fournit les informations produit.


Les rapports suivants doivent être générés :

- cucumber-report.html
- cucumber-report.json
- site jacoco html

