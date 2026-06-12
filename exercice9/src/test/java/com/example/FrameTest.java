package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FrameTest {

    // --- Série standard ---

    @Test
    void shouldIncreaseScoreWhenFirstRollIsMadeInStandardFrame() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(5);
        Frame frame = new Frame(generateur, false);

        boolean result = frame.makeRoll();

        assertTrue(result);
        assertEquals(5, frame.getScore());
    }

    @Test
    void shouldIncreaseScoreWhenSecondRollIsMadeInStandardFrame() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(3);
        Frame frame = new Frame(generateur, false);

        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertTrue(result);
        assertEquals(6, frame.getScore());
    }

    @Test
    void shouldRejectSecondRollWhenStandardFrameStartsWithStrike() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(10);
        Frame frame = new Frame(generateur, false);

        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertFalse(result);
    }

    @Test
    void shouldRejectThirdRollWhenStandardFrameAlreadyHasTwoRolls() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(3);
        Frame frame = new Frame(generateur, false);

        frame.makeRoll();
        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertFalse(result);
    }

    // --- Dernière série ---

    @Test
    void shouldIncreaseScoreWhenSecondRollIsMadeAfterStrikeInLastFrame() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(10).thenReturn(7);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();

        assertEquals(17, frame.getScore());
    }

    @Test
    void shouldAcceptSecondRollWhenLastFrameStartsWithStrike() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(10).thenReturn(5);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertTrue(result);
    }

    @Test
    void shouldAcceptThirdRollWhenLastFrameStartsWithStrike() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(10).thenReturn(5).thenReturn(3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertTrue(result);
    }

    @Test
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterStrikeInLastFrame() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(10).thenReturn(5).thenReturn(3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();

        assertEquals(18, frame.getScore());
    }

    @Test
    void shouldAcceptThirdRollWhenLastFrameStartsWithSpare() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(6).thenReturn(4).thenReturn(3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertTrue(result);
    }

    @Test
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterSpareInLastFrame() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(6).thenReturn(4).thenReturn(3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();

        assertEquals(13, frame.getScore());
    }

    @Test
    void shouldRejectThirdRollWhenLastFrameHasNoStrikeOrSpare() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertFalse(result);
    }

    @Test
    void shouldRejectFourthRollInLastFrame() {
        IGenerateur generateur = mock(IGenerateur.class);
        when(generateur.randomPin(anyInt())).thenReturn(10).thenReturn(5).thenReturn(3);
        Frame frame = new Frame(generateur, true);

        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();
        boolean result = frame.makeRoll();

        assertFalse(result);
    }
}
