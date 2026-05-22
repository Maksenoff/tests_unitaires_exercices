package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiceScoreTest {

    @Mock
    private Ide de;

    private DiceScore diceScore;

    @BeforeEach
    void setUp() {
        diceScore = new DiceScore(de);
    }

    @Test
    void deuxDesIdentiquesRetourneValeurX2Plus10() {
        when(de.getRoll()).thenReturn(4, 4);
        int result = diceScore.getScore();
        assertEquals(18, result);
    }

    @Test
    void deuxDesSix_retourne30() {
        when(de.getRoll()).thenReturn(6, 6);
        int result = diceScore.getScore();
        assertEquals(30, result);
    }

    @Test
    void desDifferents_retourneLePlusGrand() {
        when(de.getRoll()).thenReturn(3, 5);
        int result = diceScore.getScore();
        assertEquals(5, result);
    }

    @Test
    void desDifferents_premierPlusGrand() {
        when(de.getRoll()).thenReturn(6, 2);
        int result = diceScore.getScore();
        assertEquals(6, result);
    }

    @Test
    void desIdentiquesA1_retourne12() {
        when(de.getRoll()).thenReturn(1, 1);
        int result = diceScore.getScore();
        assertEquals(12, result);
    }
}
