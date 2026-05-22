package org.example;

import com.example.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidatorTest {
    private PasswordValidator passwordValidator;

    @BeforeEach
    void setUp() {
        passwordValidator = new PasswordValidator();
    }

    @Nested
    @DisplayName("Validation globale")
    class GlobalValidation {

        @Test
        @DisplayName("Doit retourner true lorsqu'un mot de passe est valide")
        void shouldReturnTrueWhenPasswordIsValid() {
            boolean result = passwordValidator.isValid("Password1!");

            assertTrue(result);
        }

        @ParameterizedTest(name = "{index} => password={0}")
        @ValueSource(strings = {
                "short1!",
                "PASSWORD1!",
                "password1!",
                "Password!",
                "Password1"
        })
        @DisplayName("Doit retourner false lorsqu'un mot de passe est invalide")
        void shouldReturnFalseWhenPasswordIsInvalid(String password) {
            boolean result = passwordValidator.isValid(password);

            assertFalse(result);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Doit retourner false lorsqu'un mot de passe est null ou vide")
        void shouldReturnFalseWhenPasswordIsNullOrEmpty(String password) {
            boolean result = passwordValidator.isValid(password);

            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Messages d'erreur")
    class ErrorMessages {

        @ParameterizedTest(name = "{index} => password={0}, expectedMessage={1}")
        @CsvSource({
                "short1!, Password must contain at least 8 characters",
                "PASSWORD1!, Password must contain at least one lowercase letter",
                "password1!, Password must contain at least one uppercase letter",
                "Password!, Password must contain at least one digit",
                "Password1, Password must contain at least one special character",
                "Password1!, Password is valid"
        })
        @DisplayName("Doit retourner le bon message selon le mot de passe")
        void shouldReturnExpectedMessageForPassword(String password, String expectedMessage) {
            String result = passwordValidator.getErrorMessage(password);

            assertEquals(expectedMessage, result);
        }

        @Test
        @DisplayName("Doit retourner un message spécifique lorsqu'un mot de passe est null")
        void shouldReturnExpectedMessageWhenPasswordIsNull() {
            String result = passwordValidator.getErrorMessage(null);

            assertEquals("Password must not be null", result);
        }
    }

    @Nested
    @DisplayName("Validation avec MethodSource")
    class MethodSourceValidation {

        static Stream<Object[]> passwordCases() {
            return Stream.of(
                    new Object[]{"Password1!", true},
                    new Object[]{"Admin2024@", true},
                    new Object[]{"short1!", false},
                    new Object[]{"PASSWORD1!", false},
                    new Object[]{"password1!", false},
                    new Object[]{"Password!", false},
                    new Object[]{"Password1", false}
            );
        }

        @ParameterizedTest(name = "{index} => password={0}, expected={1}")
        @MethodSource("passwordCases")
        @DisplayName("Doit valider plusieurs mots de passe depuis une méthode source")
        void shouldValidatePasswordsUsingMethodSource(String password, boolean expectedResult) {
            boolean result = passwordValidator.isValid(password);

            assertEquals(expectedResult, result);
        }
    }
}
