package com.model;

public enum Urgency {
    CRITICAL("Critical"), HIGH("High"), MEDIUM("Medium"), LOW("Low");

    public final String label;

    private Urgency(String label) {
        this.label = label;
    } 
}
