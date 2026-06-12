package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FrameTest {

    private static final int MAX_PINS = 10;

    @Mock
    private IGenerateur generateur;

    @Test
    @DisplayName("Le premier lancer d'une série standard doit augmenter le score de la série")
    void shouldIncreaseScoreWhenFirstRollIsMadeInStandardFrame() {
        // Arrange
        int firstRollPins = 4;
        Frame frame = new Frame(generateur, false);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins);

        // Act
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertTrue(rollAccepted);
        assertEquals(firstRollPins, frame.getScore());
    }

    @Test
    @DisplayName("Le second lancer d'une série standard doit augmenter le score de la série")
    void shouldIncreaseScoreWhenSecondRollIsMadeInStandardFrame() {
        // Arrange
        int firstRollPins = 4;
        int secondRollPins = 3;
        Frame frame = new Frame(generateur, false);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins);
        when(generateur.randomPin(MAX_PINS - firstRollPins)).thenReturn(secondRollPins);

        // Act
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertTrue(rollAccepted);
        assertEquals(firstRollPins + secondRollPins, frame.getScore());
    }

    @Test
    @DisplayName("En cas de strike dans une série standard, un second lancer doit être refusé")
    void shouldRejectSecondRollWhenStandardFrameStartsWithStrike() {
        // Arrange
        Frame frame = new Frame(generateur, false);
        when(generateur.randomPin(MAX_PINS)).thenReturn(MAX_PINS);

        // Act
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertFalse(rollAccepted);
        assertEquals(MAX_PINS, frame.getScore());
    }

    @Test
    @DisplayName("Dans une série standard, un troisième lancer doit être refusé après deux lancers")
    void shouldRejectThirdRollWhenStandardFrameAlreadyHasTwoRolls() {
        // Arrange
        int firstRollPins = 4;
        int secondRollPins = 3;
        Frame frame = new Frame(generateur, false);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins);
        when(generateur.randomPin(MAX_PINS - firstRollPins)).thenReturn(secondRollPins);

        // Act
        frame.makeRoll();
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertFalse(rollAccepted);
        assertEquals(firstRollPins + secondRollPins, frame.getScore());
    }

    @Test
    @DisplayName("Dans la dernière série, un second lancer doit être autorisé après un strike")
    void shouldAcceptSecondRollWhenLastFrameStartsWithStrike() {
        // Arrange
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(MAX_PINS).thenReturn(4);

        // Act
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertTrue(rollAccepted);
    }

    @Test
    @DisplayName("Dans la dernière série, le score doit augmenter lors du second lancer après un strike")
    void shouldIncreaseScoreWhenSecondRollIsMadeAfterStrikeInLastFrame() {
        // Arrange
        int firstRollPins = 10;
        int secondRollPins = 4;
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins).thenReturn(secondRollPins);

        // Act
        frame.makeRoll();
        frame.makeRoll();

        // Assert
        assertEquals(firstRollPins + secondRollPins, frame.getScore());
    }

    @Test
    @DisplayName("Dans la dernière série, un troisième lancer doit être autorisé après un strike")
    void shouldAcceptThirdRollWhenLastFrameStartsWithStrike() {
        // Arrange
        int firstRollPins = 10;
        int secondRollPins = 4;
        int thirdRollPins = 3;
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins).thenReturn(secondRollPins);
        when(generateur.randomPin(MAX_PINS - secondRollPins)).thenReturn(thirdRollPins);

        // Act
        frame.makeRoll();
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertTrue(rollAccepted);
    }

    @Test
    @DisplayName("Dans la dernière série, le score doit augmenter lors du troisième lancer après un strike")
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterStrikeInLastFrame() {
        // Arrange
        int firstRollPins = 10;
        int secondRollPins = 4;
        int thirdRollPins = 3;
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins).thenReturn(secondRollPins);
        when(generateur.randomPin(MAX_PINS - secondRollPins)).thenReturn(thirdRollPins);

        // Act
        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();

        // Assert
        assertEquals(firstRollPins + secondRollPins + thirdRollPins, frame.getScore());
    }

    @Test
    @DisplayName("Dans la dernière série, un troisième lancer doit être autorisé après un spare")
    void shouldAcceptThirdRollWhenLastFrameStartsWithSpare() {
        // Arrange
        int firstRollPins = 4;
        int secondRollPins = 6;
        int thirdRollPins = 3;
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins).thenReturn(thirdRollPins);
        when(generateur.randomPin(MAX_PINS - firstRollPins)).thenReturn(secondRollPins);

        // Act
        frame.makeRoll();
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertTrue(rollAccepted);
    }

    @Test
    @DisplayName("Dans la dernière série, le score doit augmenter lors du troisième lancer après un spare")
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterSpareInLastFrame() {
        // Arrange
        int firstRollPins = 4;
        int secondRollPins = 6;
        int thirdRollPins = 3;
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins).thenReturn(thirdRollPins);
        when(generateur.randomPin(MAX_PINS - firstRollPins)).thenReturn(secondRollPins);

        // Act
        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();

        // Assert
        assertEquals(firstRollPins + secondRollPins + thirdRollPins, frame.getScore());
    }

    @Test
    @DisplayName("Dans la dernière série, un troisième lancer doit être refusé lorsqu'il n'y a ni strike ni spare")
    void shouldRejectThirdRollWhenLastFrameHasNoStrikeOrSpare() {
        // Arrange
        int firstRollPins = 4;
        int secondRollPins = 3;
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(firstRollPins);
        when(generateur.randomPin(MAX_PINS - firstRollPins)).thenReturn(secondRollPins);

        // Act
        frame.makeRoll();
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertFalse(rollAccepted);
        assertEquals(firstRollPins + secondRollPins, frame.getScore());
        verify(generateur, never()).randomPin(MAX_PINS - secondRollPins);
    }

    @Test
    @DisplayName("Dans la dernière série, un quatrième lancer doit toujours être refusé")
    void shouldRejectFourthRollInLastFrame() {
        // Arrange
        Frame frame = new Frame(generateur, true);
        when(generateur.randomPin(MAX_PINS)).thenReturn(10).thenReturn(10).thenReturn(0);

        // Act
        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();
        boolean rollAccepted = frame.makeRoll();

        // Assert
        assertFalse(rollAccepted);
        assertEquals(20, frame.getScore());
    }
}
