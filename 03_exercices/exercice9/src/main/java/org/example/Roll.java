package org.example;

public class Roll {
    private final int pins;

    public Roll(int pins) {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Le nombre de quilles doit être compris entre 0 et 10.");
        }
        this.pins = pins;
    }

    public int getPins() {
        return pins;
    }
}
