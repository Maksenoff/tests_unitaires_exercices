package com.example.demoapi.service;

import com.example.demoapi.exception.DemoItemNotFoundException;
import com.example.demoapi.model.DemoItem;
import com.example.demoapi.repository.DemoItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Démonstration TDD sur la couche service.
 *
 * Objectif pédagogique :
 * - tester une règle métier sans démarrer Spring ;
 * - utiliser JUnit pour les assertions ;
 * - utiliser Mockito pour isoler le service de son repository ;
 * - structurer les tests en Arrange / Act / Assert.
 */

// Indique à JUnit 5 d'utiliser l'extension Mockito pendant l'exécution du test.
// Cette extension permet à Mockito d'initialiser automatiquement les objets annotés
// avec @Mock et @InjectMocks avant chaque test.
@ExtendWith(MockitoExtension.class)
class DemoItemServiceTest {

    // Crée un faux objet Mockito à la place d'une vraie dépendance.
    // Ici, on ne veut pas utiliser la vraie classe, mais contrôler son comportement
    // dans le test avec des instructions comme when(...).thenReturn(...)
    // ou vérifier ses appels avec verify(...).
    @Mock
    private DemoItemRepository repository;

    // Crée une vraie instance de la classe que l'on veut tester,
    // puis injecte automatiquement dedans les dépendances annotées avec @Mock.
    // Cela permet de tester la logique réelle de cette classe,
    // tout en remplaçant ses dépendances par des mocks contrôlés.
    @InjectMocks
    private DemoItemService service;

    @Test
    void shouldCreateItem_whenLabelIsValid() {
        // Arrange : on prépare les données et le comportement du mock.
        when(repository.save("demo")).thenReturn(new DemoItem(1L, "demo"));

        // Act : on appelle la méthode testée.
        DemoItem result = service.create("demo");

        // Assert : on vérifie le résultat et l'interaction avec le repository.
        assertEquals(1L, result.id());
        assertEquals("demo", result.label());
        verify(repository).save("demo");
    }

    @Test
    void shouldTrimLabel_whenCreatingItem() {
        // Arrange
        when(repository.save("demo")).thenReturn(new DemoItem(1L, "demo"));

        // Act
        DemoItem result = service.create("  demo  ");

        // Assert
        assertEquals("demo", result.label());
        verify(repository).save("demo");
    }

    @Test
    void shouldThrowException_whenLabelIsBlank() {
        // Arrange : pas de mock nécessaire, car le repository ne doit pas être appelé.

        // Act + Assert : assertThrows vérifie qu'une exception précise est levée.
        assertThrows(IllegalArgumentException.class, () -> service.create("   "));
        verify(repository, never()).save(org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void shouldReturnItem_whenIdExists() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(new DemoItem(1L, "demo")));

        // Act
        DemoItem result = service.getById(1L);

        // Assert
        assertEquals("demo", result.label());
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowNotFoundException_whenIdDoesNotExist() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(DemoItemNotFoundException.class, () -> service.getById(99L));
        verify(repository).findById(99L);
    }
}
