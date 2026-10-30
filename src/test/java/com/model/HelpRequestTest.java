package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelpRequestTest {

    private UUID requestID;
    private User author;
    private Volunteer volunteer;
    private Location location;
    private ArrayList<Skill> neededSkills;
    private HelpRequest request;

    @BeforeEach
    public void setup() throws Exception {
        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-12");
        location = new Location("1 Vendue Range", "Charleston", "SC", "29401");
        author = new Victim(UUID.randomUUID(), "Victim", "Frank", "Carter",
                "frankcarter123", "ilovesophie", "frankcarter@gmail.com",
                "843-111-1111", dob, location, new ArrayList<EmergencyContact>(),
                new ArrayList<String>());
        volunteer = new Volunteer(UUID.randomUUID(), "Volunteer", "Sophie", "Carter",
                "sophie123", "password", "sophie@example.com", "843-222-2222",
                dob, location, new ArrayList<EmergencyContact>(),
                new ArrayList<String>(), true);
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(Skill.CPR);
        skills.add(Skill.TRANSPORTATION);
        requestID = UUID.randomUUID();
        request = new HelpRequest(requestID, author, "Need transportation", location,
                true, skills, 3, 1, "One small dog", Status.OPEN, Urgency.HIGH);
    }
}
