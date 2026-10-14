package com.model;

public class Location {

    private String address;
    private String city;
    private String state;
    private String zipCode;

    public Location(String address, String city, String state, String zipCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void updateLocation() {
        
    }

    public void toggleLocation() {
        
    }

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
