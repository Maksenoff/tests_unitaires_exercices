package com.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiceScoreTest {

    private DiceScore diceScore;

    @Mock
    private Ide de;

    @BeforeEach
    void setUp() {
        diceScore = new DiceScore(de);
    }

    @Test
    public void shouldReturn20WhenBothDiceAre5(){
        // Arrange
        int expected = 20;
        when(de.getRoll()).thenReturn(5);

        // Act
        int result = diceScore.getScore();

        // Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void shouldReturn30WhenBothDiceAre6(){
        // Arrange
        int expected = 30;
        when(de.getRoll()).thenReturn(6);

        // Act
        int result = diceScore.getScore();

        // Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void shouldReturn6WhenDiceAre6And4(){
        // Arrange
        int expected = 6;
        when(de.getRoll()).thenReturn(6).thenReturn(4);

        // Act
        int result = diceScore.getScore();

        // Assert
        Assertions.assertEquals(expected, result);
    }


}
