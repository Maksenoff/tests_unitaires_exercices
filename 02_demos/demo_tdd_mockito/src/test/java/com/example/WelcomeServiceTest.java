package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de WelcomeService")
public class WelcomeServiceTest {

    @Mock
    private MessageSender messageSender;

    private WelcomeService welcomeService;

    @BeforeEach
    void setUp() {
        welcomeService = new WelcomeService(messageSender);
    }

    @Test
    @DisplayName("Doit retourner true lorsque le message est envoyé")
    void shouldReturnTrueWhenMessageIsSent() {
        when(messageSender.send("john@example.com", "Welcome john@example.com"))
                .thenReturn(true);

        boolean result = welcomeService.sendWelcomeMessage("john@example.com");

        assertTrue(result);
        // Vérifie que la méthode send(...) du mock a bien été appelée
        // avec exactement les arguments attendus pendant l'exécution du test.
        verify(messageSender).send("john@example.com", "Welcome john@example.com");
    }

    @Test
    @DisplayName("Doit retourner false lorsque l'envoi échoue")
    void shouldReturnFalseWhenMessageSendingFails() {
        when(messageSender.send("john@example.com", "Welcome john@example.com"))
                .thenReturn(false);

        boolean result = welcomeService.sendWelcomeMessage("john@example.com");

        assertFalse(result);
        // Vérifie que la méthode du mock a bien été appelée pendant le test.
        // Par défaut, verify(mock).method(...) vérifie que l'appel a eu lieu exactement une fois.
        // On peut rendre ce contrôle explicite avec times(1), ou vérifier d'autres cas :
        // - times(2) : appelé exactement 2 fois
        // - never() : jamais appelé
        // - atLeastOnce() : appelé au moins une fois
        // - atLeast(n) : appelé au moins n fois
        // - atMost(n) : appelé au maximum n fois
        verify(messageSender, times(1)).send("john@example.com", "Welcome john@example.com");
        verify(messageSender).send("john@example.com", "Welcome john@example.com");
    }

    @Test
    @DisplayName("Doit retourner false lorsque l'email est vide")
    void shouldReturnFalseWhenEmailIsBlank() {
        boolean result = welcomeService.sendWelcomeMessage(" ");

        assertFalse(result);
        // Vérifie que la méthode send(...) du mock n'a jamais été appelée.
        // Ici, cela confirme que le service s'arrête avant d'utiliser la dépendance
        // lorsque l'email fourni est invalide.
        verify(messageSender, never()).send(" ", "Welcome  ");
    }


}
