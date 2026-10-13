package com.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

    public class HurricaneReliefSystemUI {
        private HurricaneReliefSystem hurricaneReliefSystem;

        public HurricaneReliefSystemUI() {
            hurricaneReliefSystem = HurricaneReliefSystem.getInstance();
        }

        public void run() {
            loginScenario();
            System.out.println();
            signUpScenario();
            System.out.println();
            displaySheltersScenario();
            System.out.println();
            adminScenario();
        }

        // logging in

        public void loginScenario() {
            System.out.println("Please Login");
            User user = hurricaneReliefSystem.login("maya.thompson", "securePassword123");
            if (user == null) {
                System.out.println("Sorry we could not find your account. Please try again.");
                return;
            }
            System.out.println("Welcome back, " + user.getFirstName() + " " + user.getLastName() + ", Account Type: " + user.getType());
        
        }

        // account creation

        public void signUpScenario() {
            System.out.println("Please Sign Up");
            Date dob;
            try{
                dob = new SimpleDateFormat("yyyy-MM-dd").parse("1988-11-03");
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            Location location = hurricaneReliefSystem.createLocation("92 Harbor Drive", "Pensacola", "FL", "32501");
        
            User user = hurricaneReliefSystem.createAccount("Volunteer", "Colby", "Reese", "colby.reese", "jackcheese", "colby.reese@gmail.com", "111-111-1111", dob, location);
            if (user == null) {
                System.out.println("Sorry, that username already exists. Please try again.");
                return;
            }
            System.out.println("Welcome, " + user.getFirstName() + " " + user.getLastName() + ", Account Type: " + user.getType());
            
            hurricaneReliefSystem.logout();
            if(hurricaneReliefSystem.getCurrentUser() == null) {
                System.out.println("You have been logged out.");
            } else {
                System.out.println("Logout failed. Please try again.");
            }
        }

        // testing shelters with data stuff

        public void displaySheltersScenario() {
            System.out.println("Shelters from JSON File:");
            ArrayList<Shelter> shelters = new ArrayList<>(ShelterList.getInstance().getShelters());
            for (Shelter shelter : shelters) {
                System.out.print(shelter);
                System.out.print("\n\n" + "----------------------------------------------" + "\n");
            }
        }

        // admin logging in and adding shelter

        public void adminScenario() {
            User user = hurricaneReliefSystem.login("jordan.lee", "securePassword456");
            if (user == null) {
                System.out.println("Sorry we could not find your account. Please try again.");
                return;
            }
            System.out.println("Welcome back, " + user.getFirstName() + " " + user.getLastName() + ", Account Type: " + user.getType());
            Location temp = hurricaneReliefSystem.createLocation("300 Main", "Columbia", "SC", "29208");
            ArrayList<Resource> resources = new ArrayList<Resource>();
            resources.add(new Resource("Water", 30, "Bottles"));
            if (hurricaneReliefSystem.addShelter("300 Main", temp, 100, 50, false, true, resources)) {
                System.out.println("Shelter was added");
            } else {
                System.out.println("Shelter is already in the system");
            }
            hurricaneReliefSystem.saveShelters();
            hurricaneReliefSystem.logout();
            displaySheltersScenario();
        }

        public static void main(String[] args) {
            HurricaneReliefSystemUI ui = new HurricaneReliefSystemUI();
            ui.run();
        }

    }