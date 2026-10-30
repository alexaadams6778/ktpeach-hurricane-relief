package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import com.location.Address;
import com.location.MapTools;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.location.MapTools;

public class TripExporterTest {

    @TempDir
    Path tempDirectory;
    private User victim;
    private Volunteer volunteer;
    private Shelter shelter;
    private HelpRequest request;
    private MapTools mapTools;
    private Path tripFile;

    @BeforeEach
    public void setup() throws Exception {
        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-12");
        Location familyLocation = new Location("1 Vendue Range", "Charleston", "SC", "29401");
        Location volunteerLocation = new Location("10 King Street", "Charleston", "SC", "29403");
        Location shelterLocation = new Location("20 Meeting Street", "Charleston", "SC", "29402");
        victim = new Victim(UUID.randomUUID(), "Victim", "Frank", "Carter",
                "frankcarter123", "ilovesophie", "frankcarter@gmail.com", "843-111-1111",
                dob, familyLocation, new ArrayList<EmergencyContact>(), new ArrayList<String>());
        volunteer = new Volunteer(UUID.randomUUID(), "Volunteer", "Sophie", "Carter",
                "sophie123", "password", "sophie@example.com", "843-222-2222", dob,
                volunteerLocation, new ArrayList<EmergencyContact>(), new ArrayList<String>(), true);
        shelter = new Shelter("Charleston Shelter", shelterLocation, 100, 2, true, true,
                new ArrayList<Resource>());
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(Skill.TRANSPORTATION);
        skills.add(Skill.MEDICAL);
        request = new HelpRequest(victim, "Need transportation", familyLocation, false, skills,
                3, 0, "", Status.ASSIGNED, Urgency.HIGH);
        tripFile = tempDirectory.resolve("carter-family-trip");
    }

    @AfterEach
    public void tearDown() {
        Thread.interrupted();
    }
}
