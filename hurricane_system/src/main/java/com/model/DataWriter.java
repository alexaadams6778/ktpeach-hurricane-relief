package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {
    
    public static void saveShelters() {
        ShelterList shelterList = ShelterList.getInstance();
        ArrayList<Shelter> shelters = shelterList.getShelters();
        JSONArray jsonShelters = new JSONArray();

        for (int i = 0; i < shelters.size(); i++) {
        
        }

        try (FileWriter file = new FileWriter(SHELTER_FILE_NAME)) {

            file.write(jsonShelters.toJSONString());
            file.flush();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

/*

    public static JSONObject getShelterJSON(Shelter shelter) {
        JSONObject shelterDetails = new JSONObject();
        shelterDetails.put(SHELTER_ID, shelter.getID().toString());
        shelterDetails.put(SHELTER_NAME, shelter.getName());
        shelterDetails.put(SHELTER_LOCATION, shelter.getLocation());
        shelterDetails.put(SHELTER_MAXCAPACITY, shelter.getMaxCapacity());
        shelterDetails.put(SHELTER_CURRENTOCCUPANCY, shelter.getCurrentOccupancy());
        shelterDetails.put(SHELTER_ALLOWSPETS, shelter.allowsPets());
        shelterDetails.put(SHELTER_ISOPEN, shelter.isOpen());
        shelterDetails.put(SHELTER_RESOURCES, shelter.getResources());

        return shelterDetails;
    }

*/

}
