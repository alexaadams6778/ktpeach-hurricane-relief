package com.model;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class TripExporter {
    
    public static void exportTripInfo(HelpRequest request, Volunteer volunteer, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("------------------------- Trip Information ------------------------" + "\n" + "Family Member Info:" + "\n");
            for (String familyMember : getFamilyMemberInfo(volunteer)) {
                fileWriter.write(familyMember + "\n");
            }
            
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getFamilyMemberInfo(User user) {
        ArrayList<String> familyMemberInfo = new ArrayList<String>();
        for (EmergencyContact emergencyContact : user.getEmergencyContacts()) {
            familyMemberInfo.add(emergencyContact.getFirstName() + "\n" + emergencyContact.getLastName() +
            "\n" + emergencyContact.getAge());
        }
        return familyMemberInfo;
    }

}
