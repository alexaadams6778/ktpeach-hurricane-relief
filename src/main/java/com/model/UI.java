package com.model;

import com.location.Address;
import com.location.MapTools;

/**
 * Provides a simple console test for finding the closest shelter
 * and displaying driving directions from a Charleston address.
 */
public class UI {

    /**
     * Runs the shelter-location demonstration using sample Charleston-area shelters.
     */
    public void run() {
        Address userAddress = new Address(
                "1 Vendue Range",
                "Charleston",
                "SC",
                "29401"
        );

        Address closestShelter;

        Address[] shelters = {
                new Address("2950 Carner Avenue",
                        "North Charleston", "SC", "29405"),

                new Address("5200 Lackawanna Boulevard",
                        "North Charleston", "SC", "29405"),

                new Address("3841 Leeds Avenue",
                        "North Charleston", "SC", "29405"),

                new Address("3625 Ashley Phosphate Road",
                        "North Charleston", "SC", "29418"),

                new Address("6401 Dorchester Road",
                        "North Charleston", "SC", "29418"),

                new Address("1624 State Road",
                        "Summerville", "SC", "29483"),

                new Address("1053 Russellville Road",
                        "St. Stephen", "SC", "29479"),

                new Address("200 Macedonia Foxes Circle",
                        "St. Stephen", "SC", "29479"),

                new Address("101 Westview Boulevard",
                        "Goose Creek", "SC", "29445"),

                new Address("1101 Boone Hill Road",
                        "Summerville", "SC", "29483")
        };

        try {
            closestShelter = MapTools.getInstance()
                    .findClosestAddress(userAddress, shelters);

            System.out.println("User address: " + userAddress);
            System.out.println("Closest shelter: " + closestShelter);

            String[] directions = MapTools.getInstance()
                    .getDrivingDirections(userAddress, closestShelter);

            for (String direction : directions) {
                System.out.println(direction);
            }

        } catch (Exception e) {
            System.out.println("There was a problem finding a shelter.");
            e.printStackTrace();
        }
    }

    /**
     * Starts the console shelter-location demonstration.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        new UI().run();
    }
}