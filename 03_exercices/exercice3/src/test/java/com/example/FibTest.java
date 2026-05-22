package com.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FibTest {

    // --- Range 1 ---

    @Test
    void range1_resultIsNotEmpty() {
        Fib fib = new Fib(1);
        List<Integer> result = fib.getFibSeries();
        assertFalse(result.isEmpty());
    }

    @Test
    void range1_resultContainsOnlyZero() {
        Fib fib = new Fib(1);
        List<Integer> result = fib.getFibSeries();
        assertEquals(List.of(0), result);
    }

    // --- Range 6 ---

    @Test
    void range6_resultContains3() {
        Fib fib = new Fib(6);
        List<Integer> result = fib.getFibSeries();
        assertTrue(result.contains(3));
    }

    @Test
    void range6_resultHas6Elements() {
        Fib fib = new Fib(6);
        List<Integer> result = fib.getFibSeries();
        assertEquals(6, result.size());
    }

    @Test
    void range6_resultDoesNotContain4() {
        Fib fib = new Fib(6);
        List<Integer> result = fib.getFibSeries();
        assertFalse(result.contains(4));
    }

    @Test
    void range6_resultMatchesFibSequence() {
        Fib fib = new Fib(6);
        List<Integer> result = fib.getFibSeries();
        assertEquals(List.of(0, 1, 1, 2, 3, 5), result);
    }

    @Test
    void range6_resultIsSortedAscending() {
        Fib fib = new Fib(6);
        List<Integer> result = fib.getFibSeries();
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i) <= result.get(i + 1));
        }
    }
}
