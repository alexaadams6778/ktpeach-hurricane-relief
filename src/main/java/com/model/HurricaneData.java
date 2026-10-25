package com.model;

/**
 * Stores wind-speed and category information for a hurricane.
 */
public class HurricaneData {

    private int windSpeeds;
    private int category;

    /**
     * Creates hurricane data using a wind speed and hurricane category.
     *
     * @param windSpeeds the recorded wind speed
     * @param category the hurricane category
     */
    public HurricaneData(int windSpeeds, int category) {
        this.windSpeeds = windSpeeds;
        this.category = category;
    }

    /**
     * Returns the recorded hurricane wind speed.
     *
     * @return the wind speed
     */
    public int getWindSpeeds() {
        return windSpeeds;
    }

    /**
     * Returns the hurricane category.
     *
     * @return the hurricane category
     */
    public int getCategory() {
        return category;
    }

    /**
     * Updates the hurricane wind speed and category.
     *
     * @param windSpeeds the updated wind speed
     * @param category the updated hurricane category
     */
    public void updateData(int windSpeeds, int category) {
        this.windSpeeds = windSpeeds;
        this.category = category;
    }
}