package com.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GradingCalculatorTest {
    /*
    Score : 95%, Présence : 90 => Note: A
    Score : 85%, Présence : 90 => Note: B
    Score : 65%, Présence : 90 => Note: C
    Score : 95%, Présence : 65 => Note: B
    Score : 95%, Présence : 55 => Note: F
    Score : 65%, Présence : 55 => Note: F
    Score : 50%, Présence : 90 => Note: F
     */
    private GradingCalculator gradingCalculator;

    @Test
    void shouldReturnAWhenGradeIs95AndPresenceIs90() {
        //Arrange
        int grade = 95;
        int presence = 90;
        char resultAwait = 'A';
        gradingCalculator = new GradingCalculator(grade,presence);

        //Act
        char gradGet = gradingCalculator.getGrade();

        //Assert
        Assertions.assertEquals(resultAwait,gradGet);
    }

    @Test
    void shouldReturnBWhenGradeIs585AndPresenceIs90() {
        //Arrange
        int grade = 85;
        int presence = 90;
        char resultAwait = 'B';
        gradingCalculator = new GradingCalculator(grade,presence);

        //Act
        char gradGet = gradingCalculator.getGrade();

        //Assert
        Assertions.assertEquals(resultAwait,gradGet);
    }

    @Test
    void shouldReturnBWhenGradeIs95AndPresenceIs65() {
        //Arrange
        int grade = 95;
        int presence = 65;
        char resultAwait = 'B';
        gradingCalculator = new GradingCalculator(grade,presence);

        //Act
        char gradGet = gradingCalculator.getGrade();

        //Assert
        Assertions.assertEquals(resultAwait,gradGet);
    }

    @Test
    void shouldReturnFWhenGradeIs95AndPresenceIs55() {
        //Arrange
        int grade = 95;
        int presence = 55;
        char resultAwait = 'F';
        gradingCalculator = new GradingCalculator(grade,presence);

        //Act
        char gradGet = gradingCalculator.getGrade();

        //Assert
        Assertions.assertEquals(resultAwait,gradGet);
    }

    @Test
    void shouldReturnFWhenGradeIs65AndPresenceIs55() {
        //Arrange
        int grade = 65;
        int presence = 55;
        char resultAwait = 'F';
        gradingCalculator = new GradingCalculator(grade,presence);

        //Act
        char gradGet = gradingCalculator.getGrade();

        //Assert
        Assertions.assertEquals(resultAwait,gradGet);
    }

    @Test
    void shouldReturnFWhenGradeIs50AndPresenceIs90() {
        //Arrange
        int grade = 50;
        int presence = 90;
        char resultAwait = 'F';
        gradingCalculator = new GradingCalculator(grade,presence);

        //Act
        char gradGet = gradingCalculator.getGrade();

        //Assert
        Assertions.assertEquals(resultAwait,gradGet);
    }



}
