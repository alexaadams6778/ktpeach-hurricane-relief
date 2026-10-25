package com.location;

import java.util.ArrayList;

/**
 * Represents a route between two mapped locations.
 * Stores the start, end, and geographic points along the route.
 */
public class DrivingRoute {

    private final MapLocation startingLocation;
    private final MapLocation endingLocation;
    private final ArrayList<Coordinates> routePoints;

    /**
     * Creates a driving route.
     *
     * @param startingLocation the route starting location
     * @param endingLocation the route ending location
     * @param routePoints the geographic points along the route
     */
    public DrivingRoute(
            MapLocation startingLocation,
            MapLocation endingLocation,
            ArrayList<Coordinates> routePoints
    ) {
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.routePoints = routePoints;
    }

    /**
     * Returns the route starting location.
     *
     * @return the starting location
     */
    public MapLocation getStartingLocation() {
        return startingLocation;
    }

    /**
     * Returns the route ending location.
     *
     * @return the ending location
     */
    public MapLocation getEndingLocation() {
        return endingLocation;
    }

    /**
     * Returns the geographic points along the route.
     *
     * @return the route points
     */
    public ArrayList<Coordinates> getRoutePoints() {
        return routePoints;
    }
}