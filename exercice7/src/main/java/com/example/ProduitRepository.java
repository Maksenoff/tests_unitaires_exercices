package com.example;

import java.util.Optional;

public interface ProduitRepository {
    Optional<Produit> findByReference(String reference);
}
