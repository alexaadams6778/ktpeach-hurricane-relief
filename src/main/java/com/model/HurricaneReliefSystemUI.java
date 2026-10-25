package com.model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author KTPeach
 * Runs the console demonstration scenarios for the Hurricane Relief System.
 * Demonstrates request creation, volunteer matching, shelter selection,
 * trip export, family check-in, and role-based completion permissions.
 */
public class HurricaneReliefSystemUI {

    private HurricaneReliefSystem hurricaneReliefSystem;
    private HelpRequest carterRequest;

    /**
     * Creates the scenario driver and accesses the shared system facade.
     */
    public HurricaneReliefSystemUI() {
        hurricaneReliefSystem = HurricaneReliefSystem.getInstance();
    }

    /**
     * Runs every demonstration scenario in sequence.
     */
    public void run() {
        createRequest();
        matchRequest();
        findShelter();
        exportTripInfo();
        permissionsCheck();
    }

    /**
     * Starts the console scenario demonstration.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        HurricaneReliefSystemUI ui = new HurricaneReliefSystemUI();
        ui.run();
    }

    /**
     * Finds the existing Carter request or creates it when it does not exist.
     * The scenario includes a transportation need, a pet, and a medical-staff need.
     */
    public void createRequest() {
        User carter = hurricaneReliefSystem.login(
                "frankcarter123", "ilovesophie");

        if (carter != null) {
            for (HelpRequest request : hurricaneReliefSystem.findRequest()) {
                if (request.getAuthor() != null
                        && request.getAuthor().getUsername()
                        .equals("frankcarter123")) {
                    carterRequest = request;
                    hurricaneReliefSystem.logout();
                    return;
                }
            }
        }

        Location carterAddress = hurricaneReliefSystem.createLocation(
                "1 Vendue Range", "Charleston", "SC", "29401");

        if (carter == null) {
            Date dob;

            try {
                dob = new SimpleDateFormat("yyyy-MM-dd")
                        .parse("1980-05-12");
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                return;
            }

            hurricaneReliefSystem.createAccount("Victim", "Frank", "Carter",
                    "frankcarter123", "ilovesophie",
                    "frankcarter@gmail.com", "843-111-1111", dob,
                    carterAddress);

            carter = hurricaneReliefSystem.login(
                    "frankcarter123", "ilovesophie");

            hurricaneReliefSystem.addEmergencyContact(
                    new EmergencyContact("Deborah", "Carter", 42,
                    "deborahcarter@gmail.com", "843-222-2222", "Wife"));

            hurricaneReliefSystem.addEmergencyContact(
                    new EmergencyContact("Sophie", "Carter", 15,
                    "sophiecarter@gmail.com", "843-333-3333", "Daughter"));
        }

        ArrayList<Skill> neededSkills = new ArrayList<Skill>();
        neededSkills.add(Skill.TRANSPORTATION);

        hurricaneReliefSystem.addRequest(carter,
                "Our power has gone out, it's hot and humid in the house, "
                + "my daughter needs medical attention for her asthma, and "
                + "we need transportation to a shelter. We are bringing "
                + "our dog, Buddy.",
                carterAddress, false, neededSkills, 3, 1, "Buddy - Dog",
                Status.OPEN, Urgency.HIGH);

        carterRequest = hurricaneReliefSystem.getCurrentRequest();
        carterRequest.setRequiresMedicalStaff(true);

        hurricaneReliefSystem.saveRequests();
        hurricaneReliefSystem.saveUsers();
        hurricaneReliefSystem.logout();
    }

    /**
     * Logs in Rosa, displays her recommended open requests,
     * and assigns her to the Carter family's request.
     */
    public void matchRequest() {
        hurricaneReliefSystem.login("rosa.delgado", "fordTruck2026");

        Volunteer rosa = (Volunteer) hurricaneReliefSystem.getCurrentUser();
        rosa.addSkill(Skill.TRANSPORTATION);
        rosa.setVehicleDescription("Ford F-350 Truck");
        hurricaneReliefSystem.saveUsers();

        ArrayList<HelpRequest> requests =
                hurricaneReliefSystem.getRecommendedRequests(rosa);

        System.out.println("Open Requests: " + requests.size());

        for (HelpRequest request : requests) {
            System.out.println(hurricaneReliefSystem
                    .formatRecommendedRequest(rosa, request));
        }

        System.out.println("Rosa volunteers for:\n");

        if (carterRequest.getStatus() == Status.OPEN) {
            boolean assigned = hurricaneReliefSystem
                    .volunteer(rosa, carterRequest);

            if (!assigned) {
                System.out.println(
                        "Rosa could not be assigned to the Carter request.");
                return;
            }
        }

        System.out.println(hurricaneReliefSystem
                .formatRecommendedRequest(rosa, carterRequest));
    }

    /**
     * Finds shelters meeting the Carter family's pet, medical, and capacity needs.
     * It then chooses the closest suitable shelter by driving distance.
     */
    public void findShelter() {
        ArrayList<Shelter> suitableShelters =
                hurricaneReliefSystem.findShelter(true, true, 3);

        try {
            Shelter closestShelter =
                    hurricaneReliefSystem.findShelterByDrivingDistance(
                            carterRequest.getLocation(), suitableShelters);

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
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Exports a text file with the volunteer's trip information and directions.
     */
    public void exportTripInfo() {
        User user = hurricaneReliefSystem.getCurrentUser();

        if (user.getType().equalsIgnoreCase("Volunteer")) {
            Volunteer rosa = (Volunteer) user;

            TripExporter.exportTripInfo(carterRequest, rosa,
                    carterRequest.getSelectedShelter(), "carter-trip.txt");

            System.out.println("Trip information exported to carter-trip.txt");
        }
    }

    /**
     * Demonstrates that volunteers cannot complete a request, while a shelter
     * manager can complete it after the family checks into the shelter.
     */
    public void permissionsCheck() {
        System.out.println("\nCompletion permissions:\n");

        User rosa = hurricaneReliefSystem.getCurrentUser();

        boolean rosaCompleted = hurricaneReliefSystem
                .completeRequest(rosa, carterRequest);

        System.out.println("Rosa completion attempt: " + rosaCompleted);

        User felix = hurricaneReliefSystem.login(
                "felix.shelter", "shelterAdmin2026");

        if (!(felix instanceof ShelterManager)) {
            System.out.println(
                    "Felix could not be logged in as a shelter manager.");
            return;
        }

        Shelter shelter = carterRequest.getSelectedShelter();

        ArrayList<String> familyMembers = new ArrayList<String>();
        familyMembers.add("Frank Carter");
        familyMembers.add("Deborah Carter");
        familyMembers.add("Sophie Carter");

        boolean checkedIn = hurricaneReliefSystem.checkInFamily(
                shelter, familyMembers, carterRequest.getPetInfo());

        System.out.println("Carter family check-in: " + checkedIn);
        System.out.println("Updated shelter after check-in:");
        System.out.println(shelter);

        boolean felixCompleted = hurricaneReliefSystem
                .completeRequest(felix, carterRequest);

        System.out.println("Felix completion attempt: " + felixCompleted);
        System.out.println("Final request status: "
                + carterRequest.getStatus().label);

        hurricaneReliefSystem.saveShelters();
        hurricaneReliefSystem.saveRequests();
    }
}