package com.model;

public enum Status {
    OPEN("Open"),
    ASSIGNED("Assigned"),
    CLAIMED("Claimed"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete"),
    RESOLVED("Resolved"),
    CANCELLED("Cancelled");

    public final String label;

    private Status(String label) {
        this.label = label;
    }
}