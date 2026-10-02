package com.model;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

public class SystemAdmin extends User {
    
    private ArrayList<Shelter> sheltersManaged;

    public SystemAdmin(UUID userID, String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location,
                       ArrayList<EmergencyContact> emergencyContacts, ArrayList<String> medicalInfo) {
                        super(userID, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo);
                        this.sheltersManaged = new ArrayList<Shelter>();
                       }

    public void approveVolunteer(Volunteer volunteer) {
        
    }

    public void removeRequest(HelpRequest request) {

    }

    public void updateShelterInfo(Shelter shelter) {

    }

    public void updateHurricaneData(HurricaneData hurricaneData) {
        
    }

}