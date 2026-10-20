package com.model;

/**
 * @author KTPeach
 * Wind speed and category information for a hurricane
 */

public class HurricaneData {
    
    private int windSpeeds;
    private int category;

    /**
     * Creates a HurricaneData object
     * @param windSpeeds the recorded wind speed as an int
     * @param category the hurricane category as an int
     */

    public HurricaneData(int windSpeeds, int category) {
        this.windSpeeds = windSpeeds;
        this.category = category;
    }

    /**
     * Returns the wind speeds
     * @return the wind speeds as an int
     */

    public int getWindSpeeds() {
        return windSpeeds;
    }

    /**
     * Returns the category
     * @return the category as an int
     */

    public int getCategory() {
        return category;
    }

    public void updateData(int windSpeeds, int category) {
        this.windSpeeds = windSpeeds;
        this.category = category;
    }

}
