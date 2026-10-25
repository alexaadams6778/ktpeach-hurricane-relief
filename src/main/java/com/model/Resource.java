package com.model;

/**
 * Represents a supply or resource available at a shelter.
 * Examples include water, food, beds, or medical supplies.
 */
public class Resource {

    private String name;
    private int quantity;
    private String unit;

    /**
     * Creates a resource with a name, quantity, and unit of measurement.
     *
     * @param name the resource name
     * @param quantity the available quantity
     * @param unit the unit used to measure the resource
     */
    public Resource(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Returns the resource name.
     *
     * @return the resource name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the available quantity.
     *
     * @return the resource quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the resource measurement unit.
     *
     * @return the resource unit
     */
    public String getUnit() {
        return unit;
    }
}