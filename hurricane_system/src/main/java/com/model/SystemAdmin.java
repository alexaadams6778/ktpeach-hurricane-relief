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
        //todo
    }

    public void removeRequest(HelpRequest request) {
        //todo
    }
    
    //If shelter id matches an already existing shelter, update the shelter's information. Otherwise, add the new shelter to the list of shelters managed by this admin.
    public void updateShelterInfo(Shelter updatedShelter) {
        if(updatedShelter == null){
            return;
        }
        for (Shelter existing : sheltersManaged) {
            if(existing.getShelterID().equals(updatedShelter.getShelterID())) {
                existing.setName(updatedShelter.getName());
                existing.setLocation(updatedShelter.getLocation());
                existing.setMaxCapacity(updatedShelter.getMaxCapacity());
                existing.setCurrentOccupancy(updatedShelter.getCurrentOccupancy());
                existing.setAllowsPets(updatedShelter.getAllowsPets());
                if(existing.isOpen() != updatedShelter.isOpen()) {
                    existing.updateOperationalStatus();
                }
            }
        }
    }

    public void removeManagedShelter(Shelter shelter) {
        sheltersManaged.remove(shelter);
    }

    public void updateHurricaneData(HurricaneData hurricaneData) {
        //todo
    }

}