package com.model;

/**
 * @author KTPeach
 * Lists the possible stages of a help request.
 */
public enum Status {
    OPEN("Open"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete"),
    CANCELLED("Cancelled");

    public final String label;

    /**
     * Creates a status with a display label.
     *
     * @param label the readable status label
     */
    Status(String label) {
        this.label = label;
    }
}