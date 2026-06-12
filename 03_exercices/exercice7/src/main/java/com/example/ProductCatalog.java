package com.example;

import java.util.Optional;

public interface ProductCatalog {

    Optional<Product> findByReference(String reference);
}
