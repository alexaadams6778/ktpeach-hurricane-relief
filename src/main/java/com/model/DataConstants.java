package com.model;

public abstract class DataConstants {

    // User
    protected static final String USER_FILE_NAME = "src/main/java/com/json/users.json";
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

    // Request
    protected static final String REQUEST_FILE_NAME = "src/main/java/com/json/requests.json";
    protected static final String REQUEST_ID = "requestID";
    protected static final String REQUEST_AUTHOR = "author";
    protected static final String REQUEST_DESCRIPTION = "description";
    protected static final String REQUEST_LOCATION = "location";
    protected static final String REQUEST_REQUIRESCERTIFICATION = "requiresCertification";
    protected static final String REQUEST_NEEDEDSKILLS = "neededSkills";
    protected static final String REQUEST_NUMBEROFPEOPLE = "numberOfPeople";
    protected static final String REQUEST_NUMBEROFPETS = "numberOfPets";
    protected static final String REQUEST_STATUS = "status";
    protected static final String REQUEST_URGENCY = "urgency";
    protected static final String REQUEST_HURRICANEDATA = "hurricaneData";
    protected static final String REQUEST_COMMENTTHREAD = "commentThread";

    // Shelter
    protected static final String SHELTER_FILE_NAME = "src/main/java/com/json/shelters.json";
    protected static final String SHELTER_ID = "shelterID";
    protected static final String SHELTER_NAME = "name";
    protected static final String SHELTER_LOCATION = "location";
    protected static final String SHELTER_MAXCAPACITY = "maxCapacity";
    protected static final String SHELTER_CURRENTOCCUPANCY = "currentOccupancy";
    protected static final String SHELTER_ALLOWSPETS = "allowsPets";
    protected static final String SHELTER_ISOPEN = "isOpen";
    protected static final String SHELTER_RESOURCES = "resources";
    
    // Location
    protected static final String LOCATION_ADDRESS = "address";
    protected static final String LOCATION_CITY = "city";
    protected static final String LOCATION_STATE = "state";
    protected static final String LOCATION_ZIPCODE = "zipCode";

    // Resource
    protected static final String RESOURCE_NAME = "name";
    protected static final String RESOURCE_QUANTITY = "quantity";
    protected static final String RESOURCE_UNIT = "unit";
}