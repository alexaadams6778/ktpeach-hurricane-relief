package com.model;

public enum Status {
    OPEN("Open"), CLAIMED("Claimed"), IN_PROGRESS("In Progress"), RESOLVED("Resolved"), CANCELLED("Cancelled");

    public final String label;

    private Status(String label) {
        this.label = label;
    } 
}
