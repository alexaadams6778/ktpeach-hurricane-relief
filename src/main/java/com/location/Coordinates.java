package com.location;

/**
 * Represents a geographic latitude and longitude pair.
 */
public class Coordinates {

    private double latitude;
    private double longitude;

    /**
     * Creates geographic coordinates.
     *
     * @param latitude the latitude value
     * @param longitude the longitude value
     */
    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Returns the latitude value.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude value.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the coordinates in latitude, longitude format.
     *
     * @return the formatted coordinates
     */
    @Override
    public String toString() {
        return latitude + ", " + longitude;
    }
}