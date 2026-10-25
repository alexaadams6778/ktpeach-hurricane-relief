package com.location;

/**
 * Represents an address after it has been converted
 * into latitude and longitude coordinates.
 */
public class MapLocation {

    private final Address address;
    private final Coordinates coordinates;

    /**
     * Creates a mapped location using an address and its coordinates.
     *
     * @param address the address of the location
     * @param coordinates the geographic coordinates of the location
     */
    public MapLocation(Address address, Coordinates coordinates) {
        this.address = address;
        this.coordinates = coordinates;
    }

    /**
     * Returns the address for this mapped location.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns the geographic coordinates for this mapped location.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Returns the formatted address of this mapped location.
     *
     * @return the address as a string
     */
    @Override
    public String toString() {
        return address.toString();
    }
}