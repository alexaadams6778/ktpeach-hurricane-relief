package com.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

public class TripExporterTest {

    @TempDir
    Path tempDirectory;

    private Path tripFile;
    private User victim;
    private Volunteer volunteer;
    private Shelter shelter;
    private HelpRequest request;
    private MapTools mapTools;
    private MockedStatic<MapTools> mockedMapTools;

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
        request = new HelpRequest(victim, "Need transportation", familyLocation, false,
                new ArrayList<Skill>(Arrays.asList(Skill.TRANSPORTATION, Skill.MEDICAL)),
                3, 0, "", Status.ASSIGNED, Urgency.HIGH);
        tripFile = tempDirectory.resolve("trip.txt");

        // Keep the real export and filesystem behavior, replacing only the map service.
        mapTools = mock(MapTools.class);
        when(mapTools.getDrivingDirections(any(Address.class), any(Address.class)))
                .thenReturn(new String[] {"Drive to the family", "Stop at the house"},
                        new String[] {"Turn toward the shelter", "Arrive at the shelter"});
        mockedMapTools = mockStatic(MapTools.class);
        mockedMapTools.when(MapTools::getInstance).thenReturn(mapTools);
    }

    @AfterEach
    public void tearDown() {
        Thread.interrupted();
    }

        @Test
    void testWritingFamilyMemberContactInfo() throws Exception {
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Phone: 843-111-1111\n"));
        assertTrue(saved.contains("Email: frankcarter@gmail.com\n"));
        assertFalse(saved.contains("sophie@example.com"));
    }

    @Test
    void testWritingNoEmergencyContacts() throws Exception {
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertEquals(2, saved.split("Name: ", -1).length - 1);
        assertFalse(saved.contains("Phone Number:"));
    }

    @Test
    void testWritingOneEmergencyContact() throws Exception {
        victim.getEmergencyContacts().add(new EmergencyContact("Alex", "Carter", 18,
                "alex@example.com", "843-333-3333", "Child"));
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Name: Alex Carter\nAge: 18\n"
                + "Phone Number: 843-333-3333\nEmail: alex@example.com\n"));
    }

    @Test
    void testWritingMultipleEmergencyContacts() throws Exception {
        victim.getEmergencyContacts().add(new EmergencyContact("Alex", "Carter", 18,
                "alex@example.com", "843-333-3333", "Child"));
        victim.getEmergencyContacts().add(new EmergencyContact("Pat", "Jones", 65,
                "pat@example.com", "843-444-4444", "Parent"));
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        int firstContact = saved.indexOf("Name: Alex Carter");
        int secondContact = saved.indexOf("Name: Pat Jones");
        assertTrue(firstContact >= 0);
        assertTrue(secondContact > firstContact);
        assertTrue(saved.contains("Age: 65\nPhone Number: 843-444-4444\nEmail: pat@example.com"));
    }

    @Test
    void testWritingNoPets() throws Exception {
        request.setPetInfo("Old pet information");
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Pet(s): No\n"));
        assertFalse(saved.contains("Pet Info:"));
    }

    @Test
    void testWritingPets() throws Exception {
        request.setNumberOfPets(2);
        request.setPetInfo("Two small dogs");
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Pet(s): Yes\nPet Info: Two small dogs\n"));
        assertFalse(saved.contains("Pet(s): No"));
    }

    @Test
    void testWritingRequestLocation() throws Exception {
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Location: 1 Vendue Range, Charleston, SC 29401\n"));
    }

    @Test
    void testWritingLocationDifferentFromAuthor() throws Exception {
        request.setLocation(new Location("30 Broad Street", "Columbia", "SC", "29201"));
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Location: 30 Broad Street, Columbia, SC 29201\n"));
    }

    @Test
    void testWritingAssistanceNeeded() throws Exception {
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Assistance Needed: Transportation Medical \n"));
    }

    @Test
    void testWritingNoAssistanceSkills() throws Exception {
        request.getNeededSkills().clear();
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Assistance Needed: \nDescription:"));
    }

    @Test
    void testWritingDescription() throws Exception {
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Description: Need transportation\n"));
    }

    @Test
    void testWritingDescriptionWithSpecialCharacters() throws Exception {
        request.setDescription("Bring \"large\" van\nUse side entrance");
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Description: Bring \"large\" van\nUse side entrance\n"));
    }

    @Test
    void testWritingTripReplacesExistingFile() throws Exception {
        Files.writeString(tripFile, "Old trip details that must be replaced");
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertFalse(saved.contains("Old trip details"));
        assertEquals(1, saved.split("Trip Information", -1).length - 1);
    }

    @Test
    void testWritingTripWithSpaceInFileName() throws Exception {
        tripFile = tempDirectory.resolve("Frank Carter trip.txt");
        TripExporter.exportTripInfo(request, volunteer, shelter, tripFile.toString());
        String saved = Files.readString(tripFile);
        assertTrue(saved.contains("Name: Frank Carter"));
    }

}
