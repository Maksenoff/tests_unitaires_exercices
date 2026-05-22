package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordValidatorCsvSourceTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @ParameterizedTest(name = "{index} => password={0}, expected={1}")
    @CsvSource({
            "Password1!, true",
            "Admin2024@, true",
            "short1!, false",
            "PASSWORD1!, false",
            "password1!, false",
            "Password!, false",
            "Password1, false"
    })
    @DisplayName("Doit valider plusieurs mots de passe avec résultat attendu")
    void shouldValidatePasswordWithExpectedResult(String password, boolean expectedResult) {
        assertEquals(expectedResult, validator.isValid(password));
    }
}
