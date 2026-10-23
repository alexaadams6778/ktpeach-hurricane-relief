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
        }

        public static void main(String[] args) {
            HurricaneReliefSystemUI ui = new HurricaneReliefSystemUI();
            ui.run();
        }

        public void createRequest() {
            hurricaneReliefSystem.createAccount("Victim", "Frank", "Carter", "frankcarter123", "ilovesophie", "frankcarter@gmail.com", "843-111-1111", null, null);
            hurricaneReliefSystem.login("frankcarter123", "ilovesophie");
            hurricaneReliefSystem.addRequest(hurricaneReliefSystem.getCurrentUser(), "Our power has gone out, it's hot and humid in the house, and my daughter needs medical attention for her asthma", null, false, null, 0, 0, null, null, null)
        }

    }