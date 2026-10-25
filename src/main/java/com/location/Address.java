package com.location;

/**
 * Represents a street address that can be used with map and routing tools.
 */
public class Address {

    private String street;
    private String city;
    private String state;
    private String zip;

    /**
     * Creates an address from street, city, state, and ZIP code information.
     *
     * @param street the street address
     * @param city the city
     * @param state the state abbreviation
     * @param zip the ZIP code
     */
    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    /**
     * Returns the street address.
     *
     * @return the street address
     */
    public String getStreet() {
        return street;
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
    public String getZip() {
        return zip;
    }

    /**
     * Looks up geographic coordinates for this address.
     *
     * @return the address coordinates, or null if they cannot be found
     */
    public Coordinates getCoordinates() {
        try {
            return MapTools.getInstance().getCoordinates(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the formatted full address.
     *
     * @return the formatted address
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", street, city, state, zip);
    }
}