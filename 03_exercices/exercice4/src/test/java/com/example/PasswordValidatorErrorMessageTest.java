package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordValidatorErrorMessageTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @Test
    @DisplayName("null doit retourner le message 'Password must not be null'")
    void shouldReturnNullMessage() {
        assertEquals("Password must not be null", validator.getErrorMessage(null));
    }

    @Test
    @DisplayName("Moins de 8 caractères doit retourner le bon message")
    void shouldReturnTooShortMessage() {
        assertEquals("Password must contain at least 8 characters", validator.getErrorMessage("Ab1!"));
    }

    @Test
    @DisplayName("Pas de minuscule doit retourner le bon message")
    void shouldReturnNoLowercaseMessage() {
        assertEquals("Password must contain at least one lowercase letter", validator.getErrorMessage("PASSWORD1!"));
    }

    @Test
    @DisplayName("Pas de majuscule doit retourner le bon message")
    void shouldReturnNoUppercaseMessage() {
        assertEquals("Password must contain at least one uppercase letter", validator.getErrorMessage("password1!"));
    }

    @Test
    @DisplayName("Pas de chiffre doit retourner le bon message")
    void shouldReturnNoDigitMessage() {
        assertEquals("Password must contain at least one digit", validator.getErrorMessage("Password!"));
    }

    @Test
    @DisplayName("Pas de caractère spécial doit retourner le bon message")
    void shouldReturnNoSpecialCharMessage() {
        assertEquals("Password must contain at least one special character", validator.getErrorMessage("Password1"));
    }

    @Test
    @DisplayName("Mot de passe valide doit retourner 'Password is valid'")
    void shouldReturnValidMessage() {
        assertEquals("Password is valid", validator.getErrorMessage("Password1!"));
    }

    // Bonus : @NullAndEmptySource
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("null et chaîne vide doivent être invalides")
    void shouldRejectNullAndEmpty(String password) {
        assertEquals(false, validator.isValid(password));
    }
}
