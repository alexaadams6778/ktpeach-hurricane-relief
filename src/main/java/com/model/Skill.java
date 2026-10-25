package com.model;

/**
 * @author KTPeach
 * Lists the specialized skills a volunteer may have
 * or that a help request may require.
 */
public enum Skill {
    EMT("EMT"),
    CPR("CPR"),
    MEDICAL("Medical"),
    POLICE("Police"),
    CONSTRUCTION("Construction"),
    TRANSPORTATION("Transportation");

    public final String label;

    /**
     * Creates a skill with a display label.
     *
     * @param label the readable skill label
     */
    Skill(String label) {
        this.label = label;
    }
}