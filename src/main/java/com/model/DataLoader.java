package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author KTPeach
 * DataLoader, loads users, shelters, and help requests from JSON files.
 */

public class DataLoader extends DataConstants{

    /**
     * Loads recognized user types and their profiles from the users JSON file
     * @return an ArrayList of users
     */

    public static ArrayList<User> loadUsers() {
        ArrayList<User> ret = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            BufferedReader reader = getReaderFromFile(USER_FILE_NAME, USER_FILE_NAME_JSON);
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
                        JSONObject contactObj = (JSONObject) cItem;
                        String cFirst = (String) contactObj.get(CONTACT_FIRSTNAME);
                        String cLast = (String) contactObj.get(CONTACT_LASTNAME);
                        Number ageValue = (Number) contactObj.get(CONTACT_AGE);
                        int cAge = ageValue.intValue();
                        String cEmail = (String) contactObj.get(CONTACT_EMAIL);
                        String cPhone = (String) contactObj.get(CONTACT_PHONENUMBER);
                        String cRel = (String) contactObj.get(CONTACT_RELATIONSHIP);
                        emergencyContacts.add(new EmergencyContact(cFirst, cLast, cAge, cEmail, cPhone, cRel));
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
                    user = new Victim(userID, type, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo);
                }
                if(type != null && type.equalsIgnoreCase("Admin")) {
                    user = new SystemAdmin(userID, type, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo);
                }
                if(type != null && type.equalsIgnoreCase("Volunteer")) {
                    Boolean hasVehicleObj = (Boolean) userJSON.get(USER_HASVEHICLE);
                    boolean hasVehicle = (hasVehicleObj != null) ? hasVehicleObj : false;
                    String vehicleDesc = (String) userJSON.get(USER_VEHICLEDESCRIPTION);

                    Volunteer volunteer = new Volunteer(userID, type, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo, hasVehicle);
                    if (vehicleDesc != null) {
                        volunteer.setVehicleDescription(vehicleDesc);
                    }
                    JSONArray skillsJSON = (JSONArray) userJSON.get(USER_SKILLS);
                    if (skillsJSON != null) {
                        for (Object skill : skillsJSON) {
                            volunteer.addSkill(Skill.valueOf((String) skill));
                        }
                    }
                    JSONArray certificationsJSON = (JSONArray) userJSON.get(USER_CERTIFICATIONS);
                    if (certificationsJSON != null) {
                        for (Object certification : certificationsJSON) {
                            volunteer.addCertification((String) certification);
                        }
                    }
                    volunteer.setApproved(Boolean.TRUE.equals(userJSON.get(USER_APPROVED)));
                    user = volunteer;
                }
                if(type != null && (type.equalsIgnoreCase("ShelterManager") || type.equalsIgnoreCase("sheltermanager"))) {
                    String shelterIDStr = (String) userJSON.get(USER_ASSIGNEDSHELTER);
                    Shelter assignedShelter = null;
                    if (shelterIDStr != null && !shelterIDStr.trim().isEmpty()) {
                        try {
                            UUID shelterID = UUID.fromString(shelterIDStr);
                            for (Shelter s : ShelterList.getInstance().getShelters()) {
                                if (s.getShelterID().equals(shelterID)) {
                                    assignedShelter = s;
                                    break;
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            // Safe fallback if UUID format is invalid
                        }
                    }
                    user = new ShelterManager(userID, type, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo, assignedShelter);
                }
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

    /**
     * Loads shelters, locations, and resources from the shelters JSON file
     * @return an ArrayList of shelters
     */

    public static ArrayList<Shelter> loadShelters() {

        ArrayList<Shelter> ret = new ArrayList<>();

        try {
            // Read the JSON file
            BufferedReader reader = getReaderFromFile(SHELTER_FILE_NAME, SHELTER_FILE_NAME_JSON);
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
                if (resourcesJSON != null) {
                    for (Object resItem : resourcesJSON) {
                        JSONObject resourceObj = (JSONObject) resItem;
                        String resName = (String) resourceObj.get(RESOURCE_NAME);
                        int resQuantity = ((Long) resourceObj.get(RESOURCE_QUANTITY)).intValue();
                        String resUnit = (String) resourceObj.get(RESOURCE_UNIT);
                        
                        resources.add(new Resource(resName, resQuantity, resUnit));
                    }
                }

                // Parse occupants with safe fallback
                JSONArray occupantsJSON = (JSONArray) shelterJSON.get(SHELTER_OCCUPANTS);
                ArrayList<String> occupants = new ArrayList<>();
                if (occupantsJSON != null) {
                    for (Object occItem : occupantsJSON) {
                        occupants.add((String) occItem);
                    }
                }

                // Parse pets with safe fallback
                JSONArray petsJSON = (JSONArray) shelterJSON.get(SHELTER_PETS);
                ArrayList<String> pets = new ArrayList<>();
                if (petsJSON != null) {
                    for (Object petItem : petsJSON) {
                        pets.add((String) petItem);
                    }
                }

                // Parse hasMedicalStaff with safe fallback
                Boolean hasMedStaffObj = (Boolean) shelterJSON.get(SHELTER_HASMEDICALSTAFF);
                boolean hasMedicalStaff = (hasMedStaffObj != null) ? hasMedStaffObj : false;

                // Instantiate using the 11-parameter overloaded Shelter constructor
                Shelter shelter = new Shelter(shelterID, name, location, maxCapacity, currentOccupancy, allowsPets, isOpen, resources, occupants, pets, hasMedicalStaff);
                ret.add(shelter);
            }

        } catch (Exception e) {
            System.out.println("Error loading shelters: " + e.getMessage());
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Loads requests and comments from JSON
     * @return an ArrayList of requests
     */
    
    public static ArrayList<HelpRequest> loadRequests() {
        ArrayList<HelpRequest> ret = new ArrayList<>();

        try {
            BufferedReader reader = getReaderFromFile(REQUEST_FILE_NAME, REQUEST_FILE_NAME_JSON);
            JSONParser parser = new JSONParser();
            JSONArray requestsJSON = (JSONArray)parser.parse(reader);

            for(Object item : requestsJSON) {
                JSONObject reqJSON = (JSONObject) item;

                UUID requestID = UUID.fromString((String)reqJSON.get(REQUEST_ID));
                UUID authorID = UUID.fromString((String)reqJSON.get(REQUEST_AUTHOR));
                User author = UserList.getInstance().getUser(authorID);

                String description = (String)reqJSON.get(REQUEST_DESCRIPTION);
                boolean requiresCertification = (Boolean)reqJSON.get(REQUEST_REQUIRESCERTIFICATION);
                int numberOfPeople = ((Long)reqJSON.get(REQUEST_NUMBEROFPEOPLE)).intValue();
                int numberOfPets = ((Long)reqJSON.get(REQUEST_NUMBEROFPETS)).intValue();
                String petInfo = (String)reqJSON.get(REQUEST_PETINFO);

                JSONObject locJSON = (JSONObject)reqJSON.get(REQUEST_LOCATION);
                String address = (String)locJSON.get(LOCATION_ADDRESS);
                String city = (String)locJSON.get(LOCATION_CITY);
                String state = (String)locJSON.get(LOCATION_STATE);
                String zip = (String)locJSON.get(LOCATION_ZIPCODE);
                Location location = new Location(address, city, state, zip);

                JSONArray skillsJSON = (JSONArray)reqJSON.get(REQUEST_NEEDEDSKILLS);
                ArrayList<Skill> neededSkills = new ArrayList<>();
                if (skillsJSON != null) {
                    for(Object skillItem : skillsJSON) {
                        neededSkills.add(Skill.valueOf((String)skillItem));
                    }
                }
                Status status = Status.valueOf((String)reqJSON.get(REQUEST_STATUS));
                Urgency urgency = Urgency.valueOf((String)reqJSON.get(REQUEST_URGENCY));

                HelpRequest request = new HelpRequest(requestID, author, description, location, requiresCertification, neededSkills, numberOfPeople, numberOfPets, petInfo, status, urgency);

                JSONObject hurrJSON = (JSONObject)reqJSON.get(REQUEST_HURRICANEDATA);
                if(hurrJSON != null) {
                    int windSpeeds = ((Long)hurrJSON.get(HURRICANE_WINDSPEEDS)).intValue();
                    int category = ((Long)hurrJSON.get(HURRICANE_CATEGORY)).intValue();
                    request.setHurricaneData(new HurricaneData(windSpeeds, category));
                }

                request.setRequiresMedicalStaff(Boolean.TRUE.equals(
                        reqJSON.get(REQUEST_REQUIRESMEDICALSTAFF)));
                String selectedShelterID = (String) reqJSON.get(REQUEST_SELECTEDSHELTER);
                if (selectedShelterID != null) {
                    for (Shelter shelter : ShelterList.getInstance().getShelters()) {
                        if (shelter.getShelterID().toString().equals(selectedShelterID)) {
                            request.setSelectedShelter(shelter);
                            break;
                        }
                    }
                }


                JSONArray commentsJSON = (JSONArray)reqJSON.get(REQUEST_COMMENTTHREAD);
                if(commentsJSON != null) {
                    for(Object cItem : commentsJSON) {
                        JSONObject commentObj = (JSONObject)cItem;
                        
                        UUID cAuthorID = UUID.fromString((String)commentObj.get(REQUEST_AUTHOR));
                        User cAuthor = UserList.getInstance().getUser(cAuthorID);
                        
                        String timestamp = (String)commentObj.get(COMMENT_TIMESTAMP);
                        String text = (String)commentObj.get(COMMENT_TEXT);

                        request.addComment(new Comment(cAuthor, timestamp, text));
                    }
                }

                String assignedVolIDStr = (String) reqJSON.get(REQUEST_ASSIGNEDVOLUNTEER);
                if (assignedVolIDStr != null && !assignedVolIDStr.trim().isEmpty()) {
                    try {
                        UUID assignedVolID = UUID.fromString(assignedVolIDStr);
                        User assignedVol = UserList.getInstance().getUser(assignedVolID);
                        if (assignedVol != null) {
                            request.setAssignedVolunteer(assignedVol);
                            if (assignedVol instanceof Volunteer) {
                                Volunteer vol = (Volunteer) assignedVol;
                                vol.restoreAssignment(request);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        // Safe fallback if UUID format is invalid
                    }
                }
                
                ret.add(request);
            }
        } catch (Exception e) {
            System.out.println("Error loading requests: " + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    private static BufferedReader getReaderFromFile(String fileName, String jsonFileName){
		try {
			if(isJUnitTest()){
				InputStream inputStream = DataLoader.class.getResourceAsStream(jsonFileName);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				return new BufferedReader(inputStreamReader);
			} else {
				FileReader reader = new FileReader(fileName);
				return new BufferedReader(reader);
			}
		} catch(Exception e){
			System.out.println("Can't load");
			return null;
		}
			
	}
}
