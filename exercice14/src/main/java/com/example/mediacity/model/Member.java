package com.example.mediacity.model;

public record Member(Long id, String name, int lateReturnsThisYear, boolean suspended) {
    public Member withLateReturns(int count) {
        boolean nowSuspended = count >= 3;
        return new Member(id, name, count, nowSuspended);
    }
}
