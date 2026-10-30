package com.model;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DataWriterTest {
    
    private ArrayList<User> users = UserList.getInstance().getUsers();
    private ArrayList<Shelter> shelters = ShelterList.getInstance().getShelters();
    private ArrayList<HelpRequest> requests = RequestList.getInstance().getRequests();
    private Date dob;
    private Location location;
    private User victim;
    private Volunteer volunteer;
    private Shelter shelter;
    private HelpRequest request;

    @BeforeEach
    public void setup() {
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-12");
        } catch (Exception e) {
            e.printStackTrace();
        }
        location = new Location("1 Vendue Range", "Charleston", "SC", "29401");
        victim = new Victim(UUID.randomUUID(), "Victim", "Frank", "Carter",
                "frankcarter123", "ilovesophie", "frankcarter@gmail.com",
                "843-111-1111", dob, location, new ArrayList<EmergencyContact>(),
                new ArrayList<String>());
        volunteer = new Volunteer(UUID.randomUUID(), "Volunteer", "Sophie", "Carter",
                "sophie123", "password", "sophie@example.com", "843-222-2222",
                dob, location, new ArrayList<EmergencyContact>(), new ArrayList<String>(), true);
        shelter = new Shelter("Charleston Shelter", location, 100, 2, true, true,
                new ArrayList<Resource>());
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(Skill.CPR);
        skills.add(Skill.TRANSPORTATION);
        request = new HelpRequest(victim, "Need transportation", location, true, skills,
                3, 1, "Buddy - Dog", Status.OPEN, Urgency.HIGH);
        users.clear();
        shelters.clear();
        requests.clear();
        DataWriter.saveUsers();
        DataWriter.saveShelters();
        DataWriter.saveRequests();
    }

    @AfterEach 
    public void tearDown() {
        users.clear();
        shelters.clear();
        requests.clear();
        DataWriter.saveUsers();
        DataWriter.saveShelters();
        DataWriter.saveRequests();
    }

    @Test
    void testWritingZeroUsers() {
        assertEquals(0, DataLoader.loadUsers().size());
    }

    @Test
    void testWritingOneUser() {
        users.add(victim);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertEquals(1, DataLoader.loadUsers().size());
        assertEquals("frankcarter123", saved.getUsername());
    }

    @Test
    void testWritingMultipleUsers() {
        users.add(victim);
        users.add(volunteer);
        DataWriter.saveUsers();
        ArrayList<User> saved = DataLoader.loadUsers();
        assertEquals(2, saved.size());
        assertEquals("frankcarter123", saved.get(0).getUsername());
        assertEquals("sophie123", saved.get(1).getUsername());
    }

@Test
    void testWritingUserProfile() {
        users.add(victim);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertEquals(victim.getUserID(), saved.getUserID());
        assertEquals("Victim", saved.getType());
        assertEquals("Frank", saved.getFirstName());
        assertEquals("Carter", saved.getLastName());
        assertEquals("ilovesophie", saved.getPassword());
        assertEquals("frankcarter@gmail.com", saved.getEmail());
        assertEquals("843-111-1111", saved.getPhoneNumber());
        assertEquals("1980-05-12", new SimpleDateFormat("yyyy-MM-dd").format(saved.getDob()));
    }

    @Test
    void testWritingUserLocation() {
        users.add(victim);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertEquals("1 Vendue Range", saved.getLocation().getAddress());
        assertEquals("Charleston", saved.getLocation().getCity());
        assertEquals("SC", saved.getLocation().getState());
        assertEquals("29401", saved.getLocation().getZipCode());
    }

    @Test
    void testWritingEmptyUserDetails() {
        users.add(victim);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertTrue(saved.getEmergencyContacts().isEmpty());
        assertTrue(saved.getMedicalInfo().isEmpty());
    }

    @Test
    void testWritingEmergencyContacts() {
        victim.getEmergencyContacts().add(new EmergencyContact("Sophie", "Carter", 42,
                "sophie@example.com", "843-222-2222", "Spouse"));
        victim.getEmergencyContacts().add(new EmergencyContact("Alex", "Carter", 18,
                "alex@example.com", "843-333-3333", "Child"));
        users.add(victim);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertEquals(2, saved.getEmergencyContacts().size());
        EmergencyContact contact = saved.getEmergencyContacts().get(0);
        assertEquals("Sophie", contact.getFirstName());
        assertEquals("Carter", contact.getLastName());
        assertEquals(42, contact.getAge());
        assertEquals("sophie@example.com", contact.getEmail());
        assertEquals("843-222-2222", contact.getPhoneNumber());
        assertEquals("Spouse", contact.getRelationship());
        assertEquals("Alex", saved.getEmergencyContacts().get(1).getFirstName());
    }

    @Test
    void testWritingMedicalInfo() {
        victim.getMedicalInfo().addAll(Arrays.asList("Asthma", "Peanut allergy"));
        users.add(victim);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertEquals(Arrays.asList("Asthma", "Peanut allergy"), saved.getMedicalInfo());
    }

        @Test
    void testWritingVolunteerDetails() {
        volunteer.addSkill(Skill.CPR);
        volunteer.addSkill(Skill.EMT);
        volunteer.addCertification("First Aid");
        volunteer.addCertification("CPR Certificate");
        volunteer.setVehicleDescription("Blue van");
        volunteer.setApproved(true);
        users.add(volunteer);
        DataWriter.saveUsers();
        Volunteer saved = (Volunteer) DataLoader.loadUsers().get(0);
        assertTrue(saved.hasVehicle());
        assertTrue(saved.isApproved());
        assertEquals("Blue van", saved.getVehicleDescription());
        assertEquals(Arrays.asList(Skill.CPR, Skill.EMT), saved.getSkills());
        assertEquals(Arrays.asList("First Aid", "CPR Certificate"), saved.getCertifications());
    }

    @Test
    void testWritingVolunteerDefaults() {
        Volunteer noVehicle = new Volunteer(UUID.randomUUID(), "Volunteer", "Alex", "Carter",
                "alex123", "password", "alex@example.com", "843-333-3333", dob, location,
                new ArrayList<EmergencyContact>(), new ArrayList<String>(), false);
        users.add(noVehicle);
        DataWriter.saveUsers();
        Volunteer saved = (Volunteer) DataLoader.loadUsers().get(0);
        assertFalse(saved.hasVehicle());
        assertFalse(saved.isApproved());
        assertEquals("", saved.getVehicleDescription());
        assertTrue(saved.getSkills().isEmpty());
        assertTrue(saved.getCertifications().isEmpty());
    }

    @Test
    void testWritingManagerWithShelter() {
        shelters.add(shelter);
        DataWriter.saveShelters();
        ShelterManager manager = new ShelterManager(UUID.randomUUID(), "ShelterManager",
                "Sam", "Jones", "sam123", "password", "sam@example.com", "843-444-4444",
                dob, location, new ArrayList<EmergencyContact>(), new ArrayList<String>(), shelter);
        users.add(manager);
        DataWriter.saveUsers();
        ShelterManager saved = (ShelterManager) DataLoader.loadUsers().get(0);
        assertEquals(shelter.getShelterID(), saved.getAssignedShelter().getShelterID());
    }

    @Test
    void testWritingManagerWithoutShelter() {
        ShelterManager manager = new ShelterManager(UUID.randomUUID(), "ShelterManager",
                "Sam", "Jones", "sam123", "password", "sam@example.com", "843-444-4444",
                dob, location, new ArrayList<EmergencyContact>(), new ArrayList<String>(), null);
        users.add(manager);
        DataWriter.saveUsers();
        ShelterManager saved = (ShelterManager) DataLoader.loadUsers().get(0);
        assertNull(saved.getAssignedShelter());
        JSONObject json = DataWriter.getUserJSON(manager);
        assertTrue(json.containsKey("assignedShelter"));
        assertNull(json.get("assignedShelter"));
    }

    @Test
    void testWritingAdmin() {
        User admin = new SystemAdmin(UUID.randomUUID(), "Admin", "Pat", "Smith",
                "admin123", "password", "pat@example.com", "843-555-5555", dob, location,
                new ArrayList<EmergencyContact>(), new ArrayList<String>());
        users.add(admin);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertTrue(saved instanceof SystemAdmin);
        assertEquals(admin.getUserID(), saved.getUserID());
        assertEquals("admin123", saved.getUsername());
    }

    @Test
    void testWritingSpecialCharactersInUser() {
        victim.changeUsername("Frank \"Ace\"\nCarter");
        users.add(victim);
        DataWriter.saveUsers();
        User saved = DataLoader.loadUsers().get(0);
        assertEquals("Frank \"Ace\"\nCarter", saved.getUsername());
    }


    @Test
    void testWritingUsersAfterRemoval() {
        users.add(victim);
        DataWriter.saveUsers();
        users.clear();
        DataWriter.saveUsers();
        assertTrue(DataLoader.loadUsers().isEmpty());
    }

    @Test
    void testWritingZeroShelters() {
        assertEquals(0, DataLoader.loadShelters().size());
    }

    @Test
    void testWritingOneShelter() {
        shelters.add(shelter);
        DataWriter.saveShelters();
        Shelter saved = DataLoader.loadShelters().get(0);
        assertEquals(1, DataLoader.loadShelters().size());
        assertEquals(shelter.getShelterID(), saved.getShelterID());
        assertEquals("Charleston Shelter", saved.getName());
        assertEquals(100, saved.getMaxCapacity());
        assertEquals(2, saved.getCurrentOccupancy());
        assertTrue(saved.getAllowsPets());
        assertTrue(saved.isOpen());
    }

    @Test
    void testWritingShelterLocation() {
        shelters.add(shelter);
        DataWriter.saveShelters();
        Shelter saved = DataLoader.loadShelters().get(0);
        assertEquals("1 Vendue Range", saved.getLocation().getAddress());
        assertEquals("Charleston", saved.getLocation().getCity());
        assertEquals("SC", saved.getLocation().getState());
        assertEquals("29401", saved.getLocation().getZipCode());
    }

    @Test
    void testWritingShelterResources() {
        ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.add(new Resource("Water", 25, "bottles"));
        resources.add(new Resource("Food", 10, "boxes"));
        shelter = new Shelter("Charleston Shelter", location, 100, 2, true, true, resources);
        shelters.add(shelter);
        DataWriter.saveShelters();
        Shelter saved = DataLoader.loadShelters().get(0);
        assertEquals(2, saved.getAvailableResources().size());
        Resource resource = saved.getAvailableResources().get(0);
        assertEquals("Water", resource.getName());
        assertEquals(25, resource.getQuantity());
        assertEquals("bottles", resource.getUnit());
        assertEquals("Food", saved.getAvailableResources().get(1).getName());
    }

    @Test
    void testWritingEmptyShelterDetails() {
        shelters.add(shelter);
        DataWriter.saveShelters();
        Shelter saved = DataLoader.loadShelters().get(0);
        assertTrue(saved.getAvailableResources().isEmpty());
        assertTrue(saved.getOccupants().isEmpty());
        assertTrue(saved.getPets().isEmpty());
        assertFalse(saved.hasMedicalStaff());
    }

    @Test
    void testWritingShelterOccupantsAndPets() {
        shelter.setOccupants(new ArrayList<String>(Arrays.asList("Frank Carter", "Sophie Carter")));
        shelter.setPets(new ArrayList<String>(Arrays.asList("Buddy", "Mittens")));
        shelter.setHasMedicalStaff(true);
        shelters.add(shelter);
        DataWriter.saveShelters();
        Shelter saved = DataLoader.loadShelters().get(0);
        assertEquals(Arrays.asList("Frank Carter", "Sophie Carter"), saved.getOccupants());
        assertEquals(Arrays.asList("Buddy", "Mittens"), saved.getPets());
        assertTrue(saved.hasMedicalStaff());
    }

    @Test
    void testWritingClosedShelterWithoutPets() {
        Shelter closed = new Shelter("Closed Shelter", location, 20, 0, false, false,
                new ArrayList<Resource>());
        shelters.add(closed);
        DataWriter.saveShelters();
        Shelter saved = DataLoader.loadShelters().get(0);
        assertFalse(saved.isOpen());
        assertFalse(saved.getAllowsPets());
        assertEquals(0, saved.getCurrentOccupancy());
    }

    @Test
    void testWritingMultipleShelters() {
        shelters.add(shelter);
        shelters.add(new Shelter("Second Shelter", location, 50, 0, false, true,
                new ArrayList<Resource>()));
        DataWriter.saveShelters();
        ArrayList<Shelter> saved = DataLoader.loadShelters();
        assertEquals(2, saved.size());
        assertEquals("Charleston Shelter", saved.get(0).getName());
        assertEquals("Second Shelter", saved.get(1).getName());
    }

    @Test
    void testWritingZeroRequests() {
        assertEquals(0, DataLoader.loadRequests().size());
    }

    @Test
    void testWritingOneRequest() {
        users.add(victim);
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertEquals(1, DataLoader.loadRequests().size());
        assertEquals(request.getRequestID(), saved.getRequestID());
        assertSame(victim, saved.getAuthor());
        assertEquals("Need transportation", saved.getDescription());
        assertTrue(saved.requiresCertification());
        assertEquals(3, saved.getNumberOfPeople());
        assertEquals(1, saved.getNumberOfPets());
        assertEquals("Buddy - Dog", saved.getPetInfo());
    }

    @Test
    void testWritingRequestLocation() {
        users.add(victim);
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertEquals("1 Vendue Range", saved.getLocation().getAddress());
        assertEquals("Charleston", saved.getLocation().getCity());
        assertEquals("SC", saved.getLocation().getState());
        assertEquals("29401", saved.getLocation().getZipCode());
    }

    @Test
    void testWritingRequestSkills() {
        users.add(victim);
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertEquals(Arrays.asList(Skill.CPR, Skill.TRANSPORTATION), saved.getNeededSkills());
    }

    @Test
    void testWritingRequestWithoutSkillsOrPets() {
        users.add(victim);
        request.getNeededSkills().clear();
        request.setNumberOfPets(0);
        request.setPetInfo("");
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertTrue(saved.getNeededSkills().isEmpty());
        assertEquals(0, saved.getNumberOfPets());
        assertEquals("", saved.getPetInfo());
    }

    @Test
    void testWritingRequestStatusAndUrgency() {
        users.add(victim);
        request.setStatus(Status.IN_PROGRESS);
        request.setUrgency(Urgency.CRITICAL);
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertEquals(Status.IN_PROGRESS, saved.getStatus());
        assertEquals(Urgency.CRITICAL, saved.getUrgency());
    }

    @Test
    void testWritingHurricaneData() {
        users.add(victim);
        request.setHurricaneData(new HurricaneData(130, 4));
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertEquals(130, saved.getHurricaneData().getWindSpeeds());
        assertEquals(4, saved.getHurricaneData().getCategory());
    }

    @Test
    void testWritingRequestComments() {
        users.add(victim);
        users.add(volunteer);
        request.addComment(new Comment(victim, "2026-10-30 12:00", "Waiting by the door"));
        request.addComment(new Comment(volunteer, "2026-10-30 12:05", "On my way"));
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertEquals(2, saved.getCommentThread().size());
        Comment comment = saved.getCommentThread().get(0);
        assertSame(victim, comment.getAuthor());
        assertEquals("2026-10-30 12:00", comment.getTimestamp());
        assertEquals("Waiting by the door", comment.getText());
        assertEquals("On my way", saved.getCommentThread().get(1).getText());
        assertSame(volunteer, saved.getCommentThread().get(1).getAuthor());
    }

    @Test
    void testWritingUnassignedRequest() {
        users.add(victim);
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertNull(saved.getAssignedVolunteer());
        assertNull(saved.getSelectedShelter());
        assertTrue(saved.getCommentThread().isEmpty());
        assertFalse(saved.requiresMedicalStaff());
        JSONObject json = DataWriter.getRequestJSON(request);
        assertTrue(json.containsKey("assignedVolunteer"));
        assertNull(json.get("assignedVolunteer"));
        assertTrue(json.containsKey("selectedShelter"));
        assertNull(json.get("selectedShelter"));
    }

    @Test
    void testWritingAssignedVolunteer() {
        users.add(victim);
        users.add(volunteer);
        request.setAssignedVolunteer(volunteer);
        request.setStatus(Status.ASSIGNED);
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertSame(volunteer, saved.getAssignedVolunteer());
        assertEquals(Status.ASSIGNED, saved.getStatus());
    }

    @Test
    void testWritingSelectedShelterAndMedicalNeeds() {
        users.add(victim);
        shelters.add(shelter);
        DataWriter.saveShelters();
        request.setSelectedShelter(shelter);
        request.setRequiresMedicalStaff(true);
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertSame(shelter, saved.getSelectedShelter());
        assertTrue(saved.requiresMedicalStaff());
    }

    @Test
    void testWritingSpecialCharactersInRequest() {
        users.add(victim);
        request.setDescription("Need \"urgent\" help\nBring supplies");
        DataWriter.saveUsers();
        requests.add(request);
        DataWriter.saveRequests();
        HelpRequest saved = DataLoader.loadRequests().get(0);
        assertEquals("Need \"urgent\" help\nBring supplies", saved.getDescription());
    }

    @Test
    void testWritingMultipleRequests() {
        users.add(victim);
        DataWriter.saveUsers();
        requests.add(request);
        HelpRequest second = new HelpRequest(victim, "Need food", location, false,
                new ArrayList<Skill>(), 1, 0, "", Status.OPEN, Urgency.LOW);
        requests.add(second);
        DataWriter.saveRequests();
        ArrayList<HelpRequest> saved = DataLoader.loadRequests();
        assertEquals(2, saved.size());
        assertEquals(request.getRequestID(), saved.get(0).getRequestID());
        assertEquals(second.getRequestID(), saved.get(1).getRequestID());
        assertFalse(saved.get(1).requiresCertification());
    }
}
