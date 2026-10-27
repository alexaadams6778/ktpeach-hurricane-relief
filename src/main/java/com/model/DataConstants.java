package com.model;

/**
 * @author KTPeach
 * DataConstants, file paths and JSON keys used to load and save hurricane relief data
 */

public abstract class DataConstants {

    // User
    protected static final String USER_FILE_NAME = "src/main/java/com/json/users.json";
    protected static final String USER_FILE_NAME_JSON = "/data/users.json";
    protected static final String USER_TYPE = "type";
    protected static final String USER_ID = "userID";
    protected static final String USER_FIRSTNAME = "firstName";
    protected static final String USER_LASTNAME = "lastName";
    protected static final String USER_USERNAME = "username";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_EMAIL = "email";
    protected static final String USER_PHONENUMBER = "phoneNumber";
    protected static final String USER_DOB = "dob";
    protected static final String USER_LOCATION = "location";
    protected static final String USER_EMERGENCYCONTACTS = "emergencyContacts";
    protected static final String USER_MEDICALINFO = "medicalInfo";
    protected static final String CONTACT_FIRSTNAME = "firstName";
    protected static final String CONTACT_LASTNAME = "lastName";
    protected static final String CONTACT_AGE = "age";
    protected static final String CONTACT_EMAIL = "email";
    protected static final String CONTACT_PHONENUMBER = "phoneNumber";
    protected static final String CONTACT_RELATIONSHIP = "relationship";
    protected static final String CONTACT_LOCATION = "location";

    // ShelterManager & Volunteer
    protected static final String USER_ASSIGNEDSHELTER = "assignedShelter";
    protected static final String USER_HASVEHICLE = "hasVehicle";
    protected static final String USER_VEHICLEDESCRIPTION = "vehicleDescription";
    protected static final String USER_SKILLS = "skills";
    protected static final String USER_CERTIFICATIONS = "certifications";
    protected static final String USER_APPROVED = "approved";

    // Request
    protected static final String REQUEST_FILE_NAME = "src/main/java/com/json/requests.json";
protected static final String REQUEST_FILE_NAME_JSON = "/data/requests.json";
    protected static final String REQUEST_ID = "requestID";
    protected static final String REQUEST_AUTHOR = "author";
    protected static final String REQUEST_DESCRIPTION = "description";
    protected static final String REQUEST_LOCATION = "location";
    protected static final String REQUEST_REQUIRESCERTIFICATION = "requiresCertification";
    protected static final String REQUEST_NEEDEDSKILLS = "neededSkills";
    protected static final String REQUEST_NUMBEROFPEOPLE = "numberOfPeople";
    protected static final String REQUEST_NUMBEROFPETS = "numberOfPets";
    protected static final String REQUEST_PETINFO = "petInfo";
    protected static final String REQUEST_STATUS = "status";
    protected static final String REQUEST_URGENCY = "urgency";
    protected static final String REQUEST_HURRICANEDATA = "hurricaneData";
    protected static final String REQUEST_COMMENTTHREAD = "commentThread";
    protected static final String REQUEST_ASSIGNEDVOLUNTEER = "assignedVolunteer";
    protected static final String REQUEST_SELECTEDSHELTER = "selectedShelter";
    protected static final String REQUEST_REQUIRESMEDICALSTAFF = "requiresMedicalStaff";

    // Shelter
    protected static final String SHELTER_FILE_NAME = "src/main/java/com/json/shelters.json";
    protected static final String SHELTER_FILE_NAME_JSON = "/data/shelters.json";
    protected static final String SHELTER_ID = "shelterID";
    protected static final String SHELTER_NAME = "name";
    protected static final String SHELTER_LOCATION = "location";
    protected static final String SHELTER_MAXCAPACITY = "maxCapacity";
    protected static final String SHELTER_CURRENTOCCUPANCY = "currentOccupancy";
    protected static final String SHELTER_ALLOWSPETS = "allowsPets";
    protected static final String SHELTER_ISOPEN = "isOpen";
    protected static final String SHELTER_RESOURCES = "resources";
    protected static final String SHELTER_OCCUPANTS = "occupants";
    protected static final String SHELTER_PETS = "pets";
    protected static final String SHELTER_HASMEDICALSTAFF = "hasMedicalStaff";
    protected static final String SHELTER_CHECKEDINREQUESTS = "checkedInRequests";
    
    // Location
    protected static final String LOCATION_ADDRESS = "address";
    protected static final String LOCATION_CITY = "city";
    protected static final String LOCATION_STATE = "state";
    protected static final String LOCATION_ZIPCODE = "zipCode";

    // Resource
    protected static final String RESOURCE_NAME = "name";
    protected static final String RESOURCE_QUANTITY = "quantity";
    protected static final String RESOURCE_UNIT = "unit";

    // Hurricane Data
    protected static final String HURRICANE_WINDSPEEDS = "windSpeeds";
    protected static final String HURRICANE_CATEGORY = "category";

    // Comment
    protected static final String COMMENT_ID = "commentID";
    protected static final String COMMENT_TIMESTAMP = "timestamp";
    protected static final String COMMENT_TEXT = "text";

    public static boolean isJUnitTest() {  
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
		  if (element.getClassName().startsWith("org.junit.")) {
			return true;
		  }           
		}
		return false;
	  }
}