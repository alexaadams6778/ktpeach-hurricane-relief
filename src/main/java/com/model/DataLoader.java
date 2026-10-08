package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants{


    public ArrayList<User> loadUsers() {
        // TODO implement method and replace return
        ArrayList<User> ret = new ArrayList<>();
        return ret;
    }

    public ArrayList<Shelter> loadShelters() {

        ArrayList<Shelter> ret = new ArrayList<>();

        try {
            // Read the JSON file
            FileReader reader = new FileReader(SHELTER_FILE_NAME); 
            JSONParser parser = new JSONParser();
            JSONArray sheltersJSON = (JSONArray) parser.parse(reader);

            // Loop through the main JSON array
            for (Object item : sheltersJSON) {
                JSONObject shelterJSON = (JSONObject) item;

                // Extract standard primitives and UUID
                UUID shelterID = UUID.fromString((String) shelterJSON.get(SHELTER_ID));
                String name = (String) shelterJSON.get(SHELTER_NAME);
                
                // Cast to Long then convert to int
                int maxCapacity = ((Long) shelterJSON.get(SHELTER_MAXCAPACITY)).intValue();
                int currentOccupancy = ((Long) shelterJSON.get(SHELTER_CURRENTOCCUPANCY)).intValue();
                boolean allowsPets = (Boolean) shelterJSON.get(SHELTER_ALLOWSPETS);
                boolean isOpen = (Boolean) shelterJSON.get(SHELTER_ISOPEN);

                // Parse the nested Location object
                JSONObject locationJSON = (JSONObject) shelterJSON.get(SHELTER_LOCATION);
                String address = (String) locationJSON.get(LOCATION_ADDRESS);
                String city = (String) locationJSON.get(LOCATION_CITY);
                String state = (String) locationJSON.get(LOCATION_STATE);
                String zipCode = (String) locationJSON.get(LOCATION_ZIPCODE);
                Location location = new Location(address, city, state, zipCode);

                // Parse the nested Resources array
                JSONArray resourcesJSON = (JSONArray) shelterJSON.get(SHELTER_RESOURCES);
                ArrayList<Resource> resources = new ArrayList<>();
                
                for (Object resItem : resourcesJSON) {
                    JSONObject resourceObj = (JSONObject) resItem;
                    String resName = (String) resourceObj.get(RESOURCE_NAME);
                    int resQuantity = ((Long) resourceObj.get(RESOURCE_QUANTITY)).intValue();
                    String resUnit = (String) resourceObj.get(RESOURCE_UNIT);
                    
                    resources.add(new Resource(resName, resQuantity, resUnit));
                }

                // Instantiate the Shelter and add to the return list
                Shelter shelter = new Shelter(shelterID, name, location, maxCapacity, currentOccupancy, allowsPets, isOpen, resources);
                ret.add(shelter);
            }

        } catch (Exception e) {
            System.out.println("Error loading shelters: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(ret);
        return ret;
    }

    public ArrayList<HelpRequest> loadRequests() {
        // TODO implement method and replace return
        ArrayList<HelpRequest> ret = new ArrayList<>();
        return ret;
    }
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}


