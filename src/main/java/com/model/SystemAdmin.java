package com.model;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Date;

public class SystemAdmin extends User {
    
    private ArrayList<Shelter> sheltersManaged;

    public SystemAdmin(UUID userID, String userType, String firstName, String lastName, String username, String password, String email, String phoneNumber, Date dob, Location location,
                       ArrayList<EmergencyContact> emergencyContacts, ArrayList<String> medicalInfo) {
                        super(userID, userType, firstName, lastName, username, password, email, phoneNumber, dob, location, emergencyContacts, medicalInfo);
                        this.sheltersManaged = new ArrayList<Shelter>();
                       }

    public void approveVolunteer(Volunteer volunteer) {
        if (volunteer != null) {
            volunteer.setApproved(true);
        }
    }

    public void removeRequest(HelpRequest request) {
        RequestList requestList = RequestList.getInstance();

        if(requestList.removeRequest(request))
            requestList.save();
    }

    public boolean addShelter(String name, Location location, int maxCapacity, int currentOccupancy, boolean allowsPets, boolean isOpen, ArrayList<Resource> resources) {
        ShelterList shelterList = ShelterList.getInstance();
        Shelter shelter = new Shelter(name, location, maxCapacity, currentOccupancy, allowsPets, isOpen, resources);
        if (shelterList.addShelter(shelter)) {
            sheltersManaged.add(shelter);
            return true;
        }
        return false;
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
<<<<<<< HEAD
        if (hurricaneData == null) {
            return;
        }
        for(HelpRequest request : RequestList.getInstance().getRequests()) {
            if(request.getStatus()!= Status.RESOLVED && request.getStatus() != Status.CANCELLED) {
                request.setHurricaneData(hurricaneData);
            }
=======
        if (hurricaneData != null) {
            HurricaneReliefSystem.getInstance().updateHurricaneData(hurricaneData);
>>>>>>> a8ca23a551f807ee420766ca83e82cf3e6f3677d
        }
    }

}