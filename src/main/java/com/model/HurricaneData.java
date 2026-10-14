package com.model;

public class HurricaneData {
    
    private int windSpeeds;
    private int category;

    public HurricaneData(int windSpeeds, int category) {
        this.windSpeeds = windSpeeds;
        this.category = category;
    }

    public int getWindSpeeds() {
        return windSpeeds;
    }

    public int getCategory() {
        return category;
    }

}
