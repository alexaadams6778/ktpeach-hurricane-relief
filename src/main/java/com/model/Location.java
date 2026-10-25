package com.model;

/**
 * @author KTPeach
 * Represents a street address, city, state, and ZIP code.
 * Locations are used for users, shelters, and help requests.
 */
public class Location {

    private String address;
    private String city;
    private String state;
    private String zipCode;

    /**
     * Creates a location from address information.
     *
     * @param address the street address
     * @param city the city
     * @param state the state abbreviation
     * @param zipCode the ZIP code
     */
    public Location(String address, String city, String state, String zipCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    /**
     * Returns the street address.
     *
     * @return the street address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the state abbreviation.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the ZIP code.
     *
     * @return the ZIP code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Compares this location with another Location object.
     *
     * @param location the location to compare
     * @return true if address, city, state, and ZIP code all match
     */
    public boolean equals(Location location) {
        return this.getAddress().equals(location.getAddress())
                && this.getCity().equals(location.getCity())
                && this.getState().equals(location.getState())
                && this.getZipCode().equals(location.getZipCode());
    }

    /**
     * Returns the formatted full address.
     *
     * @return the formatted location
     */
    @Override
    public String toString() {
        return address + ", " + city + ", " + state + " " + zipCode;
    }
}