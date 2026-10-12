package com.model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import com.model.DataLoader;
import com.model.HurricaneReliefSystem;

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
            adminLoginScenario();
        }

        public void loginScenario() {
            System.out.println("Please Login");
            User user = hurricaneReliefSystem.login("maya.thompson", "securePassword123");
            if (user == null) {
                System.out.println("Sorry we could not find your account. Please try again.");
                return;
            }
            System.out.println("Welcome back, " + user.getFirstName() + " " + user.getLastName() + ", Account Type: " + user.getType());
        
        }

        public void signUpScenario() {
            System.out.println("Please Sign Up");
            Date dob;
            try{
                dob = new SimpleDateFormat("yyyy-MM-dd").parse("1988-11-03");
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return;
            }

            Location location = new Location("92 Harbor Drive", "Pensacola", "FL", "32501");
        
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

        public void adminLoginScenario() {
            User user = hurricaneReliefSystem.login("jordan.lee", "securePassword456");
            if (user == null) {
                System.out.println("Sorry we could not find your account. Please try again.");
                return;
            }
            System.out.println("Welcome back, " + user.getFirstName() + " " + user.getLastName() + ", Account Type: " + user.getType());
            
        }

        public static void main(String[] args) {
            HurricaneReliefSystemUI ui = new HurricaneReliefSystemUI();
            ui.run();
        }

    }