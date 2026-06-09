package com.example;

public class CommandeService {
    private final ProduitRepository produitRepository;

    public CommandeService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public RecuCommande passerCommande(Commande commande) {
        Produit produit = produitRepository.findByReference(commande.getReferenceProduit())
                .orElseThrow(() -> new CommandeException("Produit inconnu"));

        if (commande.getQuantite() > produit.getStock()) {
            throw new CommandeException("Stock insuffisant");
        }

        double remise = switch (commande.getTypeClient()) {
            case STANDARD -> 0.0;
            case PREMIUM  -> 0.10;
            case VIP      -> 0.20;
        };

        double montant = produit.getPrixUnitaire() * commande.getQuantite() * (1 - remise);

        return new RecuCommande(
                commande.getReferenceProduit(),
                commande.getQuantite(),
                montant,
                "Commande acceptée"
        );
    }
}
