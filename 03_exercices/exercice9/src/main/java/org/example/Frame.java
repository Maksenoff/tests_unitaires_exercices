package org.example;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private static final int MAX_PINS = 10;
    private static final int MAX_ROLLS_IN_LAST_FRAME = 3;

    private final boolean lastFrame;
    private final IGenerateur generateur;
    private final List<Roll> rolls = new ArrayList<>();

    public Frame(IGenerateur generateur, boolean lastFrame) {
        if (generateur == null) {
            throw new IllegalArgumentException("Le générateur est obligatoire.");
        }
        this.generateur = generateur;
        this.lastFrame = lastFrame;
    }

    public boolean makeRoll() {
        if (!canRoll()) {
            return false;
        }

        int pins = generateur.randomPin(maxPinsForNextRoll());
        rolls.add(new Roll(pins));
        return true;
    }

    public int getScore() {
        return rolls.stream()
                .mapToInt(Roll::getPins)
                .sum();
    }

    public List<Roll> getRolls() {
        return List.copyOf(rolls);
    }

    private boolean canRoll() {
        if (rolls.isEmpty()) {
            return true;
        }

        if (!lastFrame) {
            return rolls.size() < 2 && !isStrikeOnFirstRoll();
        }

        if (rolls.size() >= MAX_ROLLS_IN_LAST_FRAME) {
            return false;
        }

        if (rolls.size() == 2) {
            return isStrikeOnFirstRoll() || isSpareOnFirstTwoRolls();
        }

        return true;
    }

    private int maxPinsForNextRoll() {
        if (rolls.isEmpty()) {
            return MAX_PINS;
        }

        if (!lastFrame) {
            return MAX_PINS - firstRollPins();
        }

        if (rolls.size() == 1) {
            return isStrikeOnFirstRoll() ? MAX_PINS : MAX_PINS - firstRollPins();
        }

        if (isSpareOnFirstTwoRolls() || secondRollPins() == MAX_PINS) {
            return MAX_PINS;
        }

        return MAX_PINS - secondRollPins();
    }

    private boolean isStrikeOnFirstRoll() {
        return firstRollPins() == MAX_PINS;
    }

    private boolean isSpareOnFirstTwoRolls() {
        return rolls.size() >= 2 && firstRollPins() + secondRollPins() == MAX_PINS;
    }

    private int firstRollPins() {
        return rolls.get(0).getPins();
    }

    private int secondRollPins() {
        return rolls.get(1).getPins();
    }
}
