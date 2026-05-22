package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @Test
    @DisplayName("Password1! doit être valide")
    void shouldAcceptPassword1() {
        assertTrue(validator.isValid("Password1!"));
    }

    @Test
    @DisplayName("Admin2024@ doit être valide")
    void shouldAcceptAdmin2024() {
        assertTrue(validator.isValid("Admin2024@"));
    }

    @Test
    @DisplayName("short1! doit être invalide (moins de 8 caractères)")
    void shouldRejectShortPassword() {
        assertFalse(validator.isValid("short1!"));
    }

    @Test
    @DisplayName("PASSWORD1! doit être invalide (pas de minuscule)")
    void shouldRejectNoLowerCase() {
        assertFalse(validator.isValid("PASSWORD1!"));
    }

    @Test
    @DisplayName("password1! doit être invalide (pas de majuscule)")
    void shouldRejectNoUpperCase() {
        assertFalse(validator.isValid("password1!"));
    }

    @Test
    @DisplayName("Password! doit être invalide (pas de chiffre)")
    void shouldRejectNoDigit() {
        assertFalse(validator.isValid("Password!"));
    }

    @Test
    @DisplayName("Password1 doit être invalide (pas de caractère spécial)")
    void shouldRejectNoSpecialChar() {
        assertFalse(validator.isValid("Password1"));
    }

    @Test
    @DisplayName("null doit être invalide")
    void shouldRejectNull() {
        assertFalse(validator.isValid(null));
    }
}
