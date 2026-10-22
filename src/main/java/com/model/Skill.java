package com.model;

public enum Skill {
    EMT("EMT"), CPR("CPR"), MEDICAL("Medical"), POLICE("Police"), CONSTRUCTION("Construction"), TRANSPORTATION("Transportation");

    public final String label;

    private Skill(String label) {
        this.label = label;
    }
}
