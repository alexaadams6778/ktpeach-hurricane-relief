package com.model;

public class Location {

    private String address;
    private String city;
    private String state;
    private String zipCode;
/**
     * Creates a Location object
     * @param address the street address as a String
     * @param city the city as a String
     * @param state the state as a String
     * @param zipCode the zip code as a String
     */
    public Location(String address, String city, String state, String zipCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
/**
     * Returns the address
     * @return the address as a String
     */
    public String getAddress() {
        return address;
    }
/**
     * Returns the city
     * @return the city as a String
     */
    public String getCity() {
        return city;
    }
/**
     * Returns the state
     * @return the state as a String
     */
    public String getState() {
        return state;
    }
/**
     * Returns the zip code
     * @return the zip code as a String
     */
    public String getZipCode() {
        return zipCode;
    }
/**
     * Compares this location with another location for equality
     * @param location the other location to compare with
     * @return true if the locations are equal, false otherwise
     */
    public boolean equals(Location location) {
        return this.getAddress().equals(location.getAddress()) &&
        this.getCity().equals(location.getCity()) &&
        this.getState().equals(location.getState()) &&
        this.getZipCode().equals(location.getZipCode());
    }

    @Override
    public String toString() {
        return address + ", " + city + ", " + state + " " + zipCode;
    }
}
