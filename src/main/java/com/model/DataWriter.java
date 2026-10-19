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
        shelterDetails.put(SHELTER_LOCATION, getLocationJSON(shelter.getLocation()));
        shelterDetails.put(SHELTER_MAXCAPACITY, shelter.getMaxCapacity());
        shelterDetails.put(SHELTER_CURRENTOCCUPANCY, shelter.getCurrentOccupancy());
        shelterDetails.put(SHELTER_ALLOWSPETS, shelter.getAllowsPets());
        shelterDetails.put(SHELTER_ISOPEN, shelter.isOpen());
        JSONArray availableResourcesJSON = new JSONArray();
        for (Resource resource : shelter.getAvailableResources()) {
            availableResourcesJSON.add(getResourcesJSON(resource));
        }
        shelterDetails.put(SHELTER_RESOURCES, availableResourcesJSON);
        return shelterDetails;
    }

    private static JSONObject getResourcesJSON(Resource resource) {
        JSONObject resourceJSON = new JSONObject();
        resourceJSON.put(RESOURCE_NAME, resource.getName());
        resourceJSON.put(RESOURCE_QUANTITY, resource.getQuantity());
        resourceJSON.put(RESOURCE_UNIT, resource.getUnit());
        return resourceJSON;
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
        userDetails.put(USER_TYPE, user.getType());
        userDetails.put(USER_FIRSTNAME, user.getFirstName());
        userDetails.put(USER_LASTNAME, user.getLastName());
        userDetails.put(USER_USERNAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());
        userDetails.put(USER_EMAIL, user.getEmail());
        userDetails.put(USER_PHONENUMBER, user.getPhoneNumber());
        userDetails.put(USER_DOB, new java.text.SimpleDateFormat("yyyy-MM-dd").format(user.getDob()));
        userDetails.put(USER_LOCATION, getLocationJSON(user.getLocation()));
        JSONArray emergencyContactsJSON = new JSONArray();
        for (EmergencyContact emergencyContact : user.getEmergencyContacts()) {
            emergencyContactsJSON.add(getEmergencyContactsJSON(emergencyContact));
        }
        userDetails.put(USER_EMERGENCYCONTACTS, emergencyContactsJSON);
        userDetails.put(USER_MEDICALINFO, user.getMedicalInfo());
        return userDetails;
    }

    private static JSONObject getLocationJSON(Location location) {
        JSONObject locationJSON = new JSONObject();
        locationJSON.put(LOCATION_ADDRESS, location.getAddress());
        locationJSON.put(LOCATION_CITY, location.getCity());
        locationJSON.put(LOCATION_STATE, location.getState());
        locationJSON.put(LOCATION_ZIPCODE, location.getZipCode());
        return locationJSON;
    }

    private static JSONObject getEmergencyContactsJSON(EmergencyContact emergencyContact) {
        JSONObject emergencyContactJSON = new JSONObject();
        emergencyContactJSON.put(CONTACT_FIRSTNAME, emergencyContact.getFirstName());
        emergencyContactJSON.put(CONTACT_LASTNAME, emergencyContact.getLastName());
        emergencyContactJSON.put(CONTACT_EMAIL, emergencyContact.getEmail());
        emergencyContactJSON.put(CONTACT_PHONENUMBER, emergencyContact.getPhoneNumber());
        emergencyContactJSON.put(CONTACT_RELATIONSHIP, emergencyContact.getRelationship());
        return emergencyContactJSON;
    }

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
        requestDetails.put(REQUEST_AUTHOR, request.getAuthor().getUserID().toString());
        requestDetails.put(REQUEST_DESCRIPTION, request.getDescription());
        requestDetails.put(REQUEST_LOCATION, getLocationJSON(request.getLocation()));
        requestDetails.put(REQUEST_REQUIRESCERTIFICATION, request.requiresCertification());
        JSONArray skills = new JSONArray();
        for (Skill skill : request.getNeededSkills()) {
            skills.add(skill.name());
        }
        requestDetails.put(REQUEST_NEEDEDSKILLS, skills);
        requestDetails.put(REQUEST_NUMBEROFPEOPLE, request.getNumberOfPeople());
        requestDetails.put(REQUEST_NUMBEROFPETS, request.getNumberOfPets());
        requestDetails.put(REQUEST_STATUS, request.getStatus().name());
        requestDetails.put(REQUEST_URGENCY, request.getUrgency().name());
        requestDetails.put(REQUEST_HURRICANEDATA, getHurricaneDataJSON(request.getHurricaneData()));
        requestDetails.put(REQUEST_COMMENTTHREAD, getCommentsJSON(request.getCommentThread()));
        return requestDetails;

    }

    private static JSONObject getHurricaneDataJSON(HurricaneData hurricaneData) {
        JSONObject hurricaneDataJSON = new JSONObject();
        hurricaneDataJSON.put(HURRICANE_WINDSPEEDS, hurricaneData.getWindSpeeds());
        hurricaneDataJSON.put(HURRICANE_CATEGORY, hurricaneData.getCategory());
        return hurricaneDataJSON;
    }

    private static JSONArray getCommentsJSON(ArrayList<Comment> comments) {
        JSONArray commentsJSON = new JSONArray();
        for (Comment comment : comments) {
            JSONObject commentJSON = new JSONObject();
            commentJSON.put(REQUEST_AUTHOR, comment.getAuthor().getUserID().toString());
            commentJSON.put(COMMENT_TIMESTAMP, comment.getTimestamp());
            commentJSON.put(COMMENT_TEXT, comment.getText());
            commentsJSON.add(commentJSON);
        }
        return commentsJSON;
    }
}
