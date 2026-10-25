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
            exportTripInfo();
            completeScenario();
        }

        public static void main(String[] args) {
            HurricaneReliefSystemUI ui = new HurricaneReliefSystemUI();
            ui.run();
        }

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
                    dob = new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-12");
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
            System.out.println("Rosa volunteers for:\n");

            if (carterRequest.getStatus() == Status.OPEN) {
                boolean assigned = hurricaneReliefSystem.volunteer(rosa, carterRequest);

                if (!assigned) {
                    System.out.println("Rosa could not be assigned to the Carter request.");
                    return;
                }
            }

            System.out.println(hurricaneReliefSystem
                    .formatRecommendedRequest(rosa, carterRequest));
        }

        public void findShelter() {
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

        public void exportTripInfo() {
            User user = hurricaneReliefSystem.getCurrentUser();
            if (user.getType().equalsIgnoreCase("Volunteer")) {
                Volunteer rosa = (Volunteer) user;
                TripExporter.exportTripInfo(carterRequest, rosa, carterRequest.getSelectedShelter(), "carter-trip.txt");
                System.out.println("Trip information exported to carter-trip.txt");
            }
        }

        /**
         * Demonstrates volunteer completion denial and shelter-manager completion
         * after the Carter family checks into their selected shelter.
         */
        public void completeScenario() {
            System.out.println("\nCompletion permissions:");

            User rosa = hurricaneReliefSystem.getCurrentUser();

            boolean rosaCompleted = hurricaneReliefSystem
                    .completeRequest(rosa, carterRequest);

            System.out.println("Rosa completion attempt: " + rosaCompleted);

            User felix = hurricaneReliefSystem.login(
                    "felix.shelter", "shelterAdmin2026");

            if (!(felix instanceof ShelterManager)) {
                System.out.println("Felix could not be logged in as a shelter manager.");
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