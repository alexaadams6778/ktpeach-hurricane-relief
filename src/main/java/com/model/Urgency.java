package com.model;

/**
 * Lists the urgency levels that can be assigned to a help request.
 */
public enum Urgency {
    CRITICAL("Critical"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    public final String label;

    /**
     * Creates an urgency level with a display label.
     *
     * @param label the readable urgency label
     */
    Urgency(String label) {
        this.label = label;
    }
}