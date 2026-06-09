package com.example;

public class Calculator {

    private Integer memory;

    public int add(int left, int right) {
        return left + right;
    }

    public int subtract(int left, int right) {
        return left - right;
    }

    public int multiply(int left, int right) {
        return left * right;
    }

    public int divide(int left, int right) {
        if (right == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed");
        }

        return left / right;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public boolean isPositive(int number) {
        return number > 0;
    }

    public Integer getMemory() {
        return memory;
    }

    public void saveInMemory(int value) {
        memory = value;
    }

    public void clearMemory() {
        memory = null;
    }

    public int[] createRange(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("End must be greater than or equal to start");
        }

        int[] range = new int[end - start + 1];

        for (int i = 0; i < range.length; i++) {
            range[i] = start + i;
        }

        return range;
    }
}
