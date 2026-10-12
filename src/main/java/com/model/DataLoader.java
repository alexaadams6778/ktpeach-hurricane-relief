package com.model;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants{
    public static ArrayList<User> loadUsers() {
        ArrayList<User> ret = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray usersJSON = (JSONArray) parser.parse(reader);

            for(Object item : usersJSON) {
                JSONObject userJSON = (JSONObject) item;

                UUID userID = UUID.fromString((String) userJSON.get(USER_ID));
                String type = (String) userJSON.get(USER_TYPE);
                String firstName = (String) userJSON.get(USER_FIRSTNAME);
                String lastName = (String) userJSON.get(USER_LASTNAME);
                String username = (String) userJSON.get(USER_USERNAME);
                String password = (String) userJSON.get(USER_PASSWORD);
                String email = (String) userJSON.get(USER_EMAIL);
                String phoneNumber = (String) userJSON.get(USER_PHONENUMBER);

                
                String dobString = (String) userJSON.get(USER_DOB);
                Date dob = dateFormat.parse(dobString);

                JSONObject locJSON = (JSONObject) userJSON.get(USER_LOCATION);
                String address = (String) locJSON.get(LOCATION_ADDRESS);
                String city = (String) locJSON.get(LOCATION_CITY);
                String state = (String) locJSON.get(LOCATION_STATE);
                String zip = (String) locJSON.get(LOCATION_ZIPCODE);
                Location location = new Location(address, city, state, zip);

                // Parse the nested EmergencyContacts array
                JSONArray contactsJSON = (JSONArray) userJSON.get(USER_EMERGENCYCONTACTS);
                ArrayList<EmergencyContact> emergencyContacts = new ArrayList<>();
                if(contactsJSON != null) {
                    for(Object cItem : contactsJSON) {
                        JSONObject contactObj = (JSONObject) item;
                        String cFirst = (String) contactObj.get(CONTACT_FIRSTNAME);
                        String cLast = (String) contactObj.get(CONTACT_LASTNAME);
                        String cEmail = (String) contactObj.get(CONTACT_EMAIL);
                        String cPhone = (String) contactObj.get(CONTACT_PHONENUMBER);
                        String cRel = (String) contactObj.get(CONTACT_RELATIONSHIP);
                        emergencyContacts.add(new EmergencyContact(cFirst, cLast, cEmail, cPhone, cRel));
                    }
                }

                JSONArray medicalJSON = (JSONArray) userJSON.get(USER_MEDICALINFO);
                ArrayList<String> medicalInfo = new ArrayList<>();
                if(medicalJSON != null) {
                    for(Object mItem : medicalJSON) {
                        medicalInfo.add((String) mItem);
                    }
                }

                User user = null;
                if(type != null && type.equalsIgnoreCase("Victim")) {
                    user = new Victim(userID, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo);
                }
                // TODO: Add elif blocks for Volunteer and SystemAdmin once their fields are added to the JSON

                if (user != null) {
                    ret.add(user);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }

        return ret;
    }

    public static ArrayList<Shelter> loadShelters() {

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

        return ret;
    }

    public static ArrayList<HelpRequest> loadRequests() {
        // TODO implement method and replace return
        ArrayList<HelpRequest> ret = new ArrayList<>();
        return ret;
    }
}
