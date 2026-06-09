package com.example;

import java.util.Optional;

public interface SalleRepository {
    Optional<Salle> findByCode(String code);
}
