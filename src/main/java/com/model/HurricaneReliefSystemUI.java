package com.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;

    public class HurricaneReliefSystemUI {
        private HurricaneReliefSystem hurricaneReliefSystem;
        private HelpRequest carterRequest;

        public HurricaneReliefSystemUI() {
            hurricaneReliefSystem = HurricaneReliefSystem.getInstance();
        }

        public void run() {
            createRequest();
            matchRequest();
            findShelter();
        }

        public static void main(String[] args) {
            HurricaneReliefSystemUI ui = new HurricaneReliefSystemUI();
            ui.run();
        }

        public void createRequest() {
            Location carterAddress = hurricaneReliefSystem.createLocation("1 Vendue Range", "Charleston", "SC", "29401");
            Date dob;
            try {
                dob = new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-12");
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                return;
            }
            hurricaneReliefSystem.createAccount("Victim", "Frank", "Carter", "frankcarter123", "ilovesophie", "frankcarter@gmail.com", "843-111-1111", dob, carterAddress);
            hurricaneReliefSystem.login("frankcarter123", "ilovesophie");
            hurricaneReliefSystem.addEmergencyContact(new EmergencyContact("Deborah", "Carter", 42, "deborahcarter@gmail.com", "843-222-2222", "Wife"));
            hurricaneReliefSystem.addEmergencyContact(new EmergencyContact("Sophie", "Carter", 15, "sophiecarter@gmail.com", "843-333-3333", "Daughter"));
            ArrayList<Skill> neededSkills = new ArrayList<Skill>();
            neededSkills.add(Skill.TRANSPORTATION);
            hurricaneReliefSystem.addRequest(hurricaneReliefSystem.getCurrentUser(),
            "Our power has gone out, it's hot and humid in the house, my daughter needs medical attention for her asthma, and we need transportation to a shelter. We are bringing our dog, Buddy.", 
            carterAddress, false, neededSkills, 3, 1, "Buddy - Dog", Status.OPEN, Urgency.HIGH);
            carterRequest = hurricaneReliefSystem.getCurrentRequest();
            hurricaneReliefSystem.getCurrentRequest().setRequiresMedicalStaff(true);
            hurricaneReliefSystem.saveRequests();
            hurricaneReliefSystem.saveUsers();
            hurricaneReliefSystem.logout();
        }

        public void matchRequest() {
            hurricaneReliefSystem.login("rosa.delgado", "fordTruck2026");
            Volunteer rosa = (Volunteer) hurricaneReliefSystem.getCurrentUser();
            rosa.addSkill(Skill.TRANSPORTATION);
            rosa.setVehicleDescription("Ford F-350 Truck");
            hurricaneReliefSystem.saveUsers();
            ArrayList<HelpRequest> requests = hurricaneReliefSystem.getRecommendedRequests(rosa);
            System.out.println("Open Requests: " + requests.size());
            for (HelpRequest request : requests) {
                System.out.println(hurricaneReliefSystem.formatRecommendedRequest(rosa, request));
            }
        }

        public void findShelter() {
        // Accepts pets, has medical staff, and has three human spaces.
        ArrayList<Shelter> suitableShelters =
                hurricaneReliefSystem.findShelter(true, true, 3);
        try {
            Shelter closestShelter = hurricaneReliefSystem.findShelterByDrivingDistance(carterRequest.getLocation(), suitableShelters);
            if (closestShelter == null) {
                System.out.println("No suitable shelter was found.");
                return;
            }
            carterRequest.setSelectedShelter(closestShelter);
            hurricaneReliefSystem.saveRequests();
            System.out.println("Closest suitable shelter:");
            System.out.println(closestShelter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}