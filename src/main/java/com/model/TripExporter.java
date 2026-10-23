package com.model;

import java.util.ArrayList;
import com.location.MapTools;
import com.location.Address;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class TripExporter {
    
    public static void exportTripInfo(HelpRequest request, Volunteer volunteer, Shelter shelter, String fileName) {
        try {
            Address address1 = new Address(volunteer.getLocation().getAddress(), volunteer.getLocation().getCity(), volunteer.getLocation().getState(), volunteer.getLocation().getZipCode());
            Address address2 = new Address(request.getLocation().getAddress(), request.getLocation().getCity(), request.getLocation().getState(), request.getLocation().getZipCode());
            Address address3 = new Address(shelter.getLocation().getAddress(), shelter.getLocation().getCity(), shelter.getLocation().getState(), shelter.getLocation().getZipCode());
            MapTools mapTools = MapTools.getInstance();
            FileWriter fileWriter = new FileWriter(fileName);
            User author = request.getAuthor();
            LocalDate birthday = author.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(birthday, LocalDate.now()).getYears();
            fileWriter.write("------------------------- Trip Information ------------------------\n");
            fileWriter.write("Family Member Info:\n");
            fileWriter.write("Name: " + author.getFirstName() + " " + author.getLastName() + "\n");
            fileWriter.write("Age: " + age + "\n");
            fileWriter.write("Phone: " + author.getPhoneNumber() + "\n");
            fileWriter.write("Email: " + author.getEmail() + "\n\n");
            for (String familyMember : getFamilyMemberInfo(request.getAuthor())) {
                fileWriter.write(familyMember + "\n");
            }
            if (request.getNumberOfPets() != 0) {
                fileWriter.write("Pet(s): Yes" + "\n" + "Pet Info: " + request.getPetInfo() + "\n");
            } else {
                fileWriter.write("Pet(s): No" + "\n");
            }
            fileWriter.write("Location: " + request.getLocation() + "\n");
            fileWriter.write("Assistance Needed: ");
            for (Skill skill : request.getNeededSkills()) {
                fileWriter.write(skill.label + " ");
            }
            fileWriter.write("\nDescription: " + request.getDescription() + "\n");
            fileWriter.write("\n" + "------------------------- Directions to Location -------------------------" + "\n");
            try {
                String[] directions1 = mapTools.getDrivingDirections(address1, address2);
                for (int i = 0; i < directions1.length; i++) {
                    fileWriter.write(directions1[i] + "\n");
                }
                fileWriter.write("\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            fileWriter.write("\n" + "------------------------- Directions to Shelter -------------------------" + "\n");
            try {
                String[] directions2 = mapTools.getDrivingDirections(address2, address3);
                for (int i = 0; i < directions2.length; i++) {
                    fileWriter.write(directions2[i] + "\n");
                }
                fileWriter.write("\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            fileWriter.write("\nShelter Name: " + shelter.getName() + "\nAddress: " + shelter.getLocation());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getFamilyMemberInfo(User user) {
        ArrayList<String> familyMemberInfo = new ArrayList<String>();
        for (EmergencyContact emergencyContact : user.getEmergencyContacts()) {
            familyMemberInfo.add(emergencyContact + "\n");
        }
        return familyMemberInfo;
    }
}
