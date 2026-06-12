package com.example;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private int score;
    private boolean lastFrame;
    private IGenerateur generateur;
    private List<Roll> rolls;

    public Frame(IGenerateur generateur, boolean lastFrame) {
        this.lastFrame = lastFrame;
        this.generateur = generateur;
        this.rolls = new ArrayList<>();
        this.score = 0;
    }

    public boolean makeRoll() {
        if (!canRoll()) {
            return false;
        }
        int max = computeMaxPins();
        int pins = generateur.randomPin(max);
        rolls.add(new Roll(pins));
        score += pins;
        return true;
    }

    private boolean canRoll() {
        int count = rolls.size();
        if (!lastFrame) {
            if (count == 0) return true;
            if (count == 1 && rolls.get(0).getPins() == 10) return false;
            return count < 2;
        } else {
            if (count < 2) return true;
            if (count == 2) {
                boolean firstStrike = rolls.get(0).getPins() == 10;
                boolean spare = !firstStrike && (rolls.get(0).getPins() + rolls.get(1).getPins() == 10);
                return firstStrike || spare;
            }
            return false;
        }
    }

    private int computeMaxPins() {
        int count = rolls.size();
        if (count == 0) return 10;
        if (!lastFrame) {
            return 10 - rolls.get(0).getPins();
        } else {
            if (count == 1) {
                int first = rolls.get(0).getPins();
                return first == 10 ? 10 : 10 - first;
            }
            return 10;
        }
    }

    public int getScore() {
        return score;
    }
}
