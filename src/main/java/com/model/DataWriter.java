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
            jsonShelters.add(getShelterJSON(shelters.get(i)));
        }

        try (FileWriter file = new FileWriter(SHELTER_FILE_NAME)) {

            file.write(jsonShelters.toJSONString());
            file.flush();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public static JSONObject getShelterJSON(Shelter shelter) {
        JSONObject shelterDetails = new JSONObject();
        shelterDetails.put(SHELTER_ID, shelter.getShelterID().toString());
        shelterDetails.put(SHELTER_NAME, shelter.getName());
        shelterDetails.put(SHELTER_LOCATION, shelter.getLocation());
        shelterDetails.put(SHELTER_MAXCAPACITY, shelter.getMaxCapacity());
        shelterDetails.put(SHELTER_CURRENTOCCUPANCY, shelter.getCurrentOccupancy());
        shelterDetails.put(SHELTER_ALLOWSPETS, shelter.getAllowsPets());
        shelterDetails.put(SHELTER_ISOPEN, shelter.isOpen());
        shelterDetails.put(SHELTER_RESOURCES, shelter.getAvailableResources());

        return shelterDetails;
    }

    public static void saveUsers() {
            UserList userList = UserList.getInstance();
            ArrayList<User> users = userList.getUsers();
            JSONArray jsonUsers = new JSONArray();

            for (int i = 0; i < users.size(); i++) {
                jsonUsers.add(getUserJSON(users.get(i)));
            }

            try (FileWriter file = new FileWriter(USER_FILE_NAME)) {

                file.write(jsonUsers.toJSONString());
                file.flush();

            } catch(IOException e) {
                e.printStackTrace();
            }
        }


    public static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put(USER_ID, user.getUserID().toString());
        userDetails.put(USER_FIRSTNAME, user.getFirstName());
        userDetails.put(USER_LASTNAME, user.getLastName());
        userDetails.put(USER_USERNAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_PHONENUMBER, user.getPhoneNumber());
        userDetails.put(USER_DOB, user.getDob());
        userDetails.put(USER_LOCATION, user.getLocation());
        userDetails.put(USER_EMERGENCYCONTACTS, user.getEmergencyContacts());
        userDetails.put(USER_MEDICALINFO, user.getMedicalInfo());
        return userDetails;
    }

    /*

    public static void saveRequests() {
            RequestList requestList = RequestList.getInstance();
            ArrayList<HelpRequest> requests = requestList.getRequests();
            JSONArray jsonRequests = new JSONArray();

            for (int i = 0; i < requests.size(); i++) {
                jsonRequests.add(getRequestJSON(requests.get(i)));
            }

            try (FileWriter file = new FileWriter(REQUEST_FILE_NAME)) {

                file.write(jsonRequests.toJSONString());
                file.flush();

            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    public static JSONObject getRequestJSON(HelpRequest request) {
        JSONObject requestDetails = new JSONObject();
        requestDetails.put(REQUEST_ID, request.getRequestID().toString());
        requestDetails.put(REQUEST_AUTHOR, request.getAuthor());
        requestDetails.put(REQUEST_DESCRIPTION, request.getDescription());
        requestDetails.put(REQUEST_LOCATION, request.getLocation());
        requestDetails.put(REQUEST_REQUIRESCERTIFICATION, request.getRequiresCertification());
        requestDetails.put(REQUEST_NEEDEDSKILLS, request.getNeededSkills());
        requestDetails.put(REQUEST_NUMBEROFPEOPLE, request.getNuumberOfPeople());
        requestDetails.put(REQUEST_NUMBEROFPETS, request.getNumberOfPets());
        requestDetails.put(REQUEST_STATUS, request.getStatus());
        requestDetails.put(REQUEST_URGENCY, request.getUrgency());
        requestDetails.put(REQUEST_HURRICANEDATA, request.getHurricaneData());
        requestDetails.put(REQUEST_COMMENTTHREAD, request.get());
        return requestDetails;
        }

    */
}
