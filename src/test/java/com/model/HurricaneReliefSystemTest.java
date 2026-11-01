package com.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class HurricaneReliefSystemTest {

    private HurricaneReliefSystem system;
    private UserList userList;
    private RequestList requestList;
    private ArrayList<User> originalUsers;
    private ArrayList<HelpRequest> originalRequests;
    private MockedStatic<DataWriter> mockedDataWriter;
    private Date dob;
    private Location charlestonLocation;

    @BeforeEach
    public void setup() throws Exception {
        mockedDataWriter = mockStatic(DataWriter.class);

        system = HurricaneReliefSystem.getInstance();
        userList = UserList.getInstance();
        requestList = RequestList.getInstance();

        system.logout();

        originalUsers = new ArrayList<User>(userList.getUsers());
        originalRequests = new ArrayList<HelpRequest>(requestList.getRequests());

        userList.getUsers().clear();
        requestList.getRequests().clear();

        dob = new SimpleDateFormat("yyyy-MM-dd").parse("1990-04-12");
        charlestonLocation = new Location(
                "120 Broad Street", "Charleston", "SC", "29401");
    }

    @AfterEach
    public void tearDown() {
        system.logout();

        userList.getUsers().clear();
        userList.getUsers().addAll(originalUsers);

        requestList.getRequests().clear();
        requestList.getRequests().addAll(originalRequests);

        if (mockedDataWriter != null) {
            mockedDataWriter.close();
        }
    }

    private Victim createVictim(String username) {
        return new Victim(UUID.randomUUID(), "Victim", "Frank", "Carter",
                username, "password", "frank@example.com", "843-111-1111",
                dob, charlestonLocation, new ArrayList<EmergencyContact>(),
                new ArrayList<String>());
    }

    private Volunteer createVolunteer(String username) {
        Volunteer volunteer = new Volunteer(UUID.randomUUID(), "Volunteer",
                "Rosa", "Delgado", username, "password",
                "rosa@example.com", "843-222-2222", dob,
                charlestonLocation, new ArrayList<EmergencyContact>(),
                new ArrayList<String>(), true);
        volunteer.addSkill(Skill.TRANSPORTATION);
        return volunteer;
    }

    private HelpRequest createRequest(User author, Location location,
                                      ArrayList<Skill> skills,
                                      Status status) {
        return new HelpRequest(author, "Need transportation", location, false,
                skills, 3, 1, "Buddy - Dog", status, Urgency.HIGH);
    }

    @Test
    void testCreateLocationStoresProvidedAddressInformation() {
        Location location = system.createLocation(
                "1 Vendue Range", "Charleston", "SC", "29401");

        assertEquals("1 Vendue Range", location.getAddress());
        assertEquals("Charleston", location.getCity());
        assertEquals("SC", location.getState());
        assertEquals("29401", location.getZipCode());
    }

    @Test
    void testLoginWithValidCredentialsSetsCurrentUser() {
        Victim victim = createVictim("frank");
        userList.addUser(victim);

        User loggedInUser = system.login("frank", "password");

        assertSame(victim, loggedInUser);
        assertSame(victim, system.getCurrentUser());
    }

    @Test
    void testLoginWithInvalidCredentialsReturnsNull() {
        userList.addUser(createVictim("frank"));

        User loggedInUser = system.login("frank", "wrong-password");

        assertNull(loggedInUser);
        assertNull(system.getCurrentUser());
    }

    @Test
    void testCreateAccountAddsNewVictimToUserList() {
        User createdUser = system.createAccount("Victim", "Maya", "Jones",
                "mayajones", "password", "maya@example.com", "843-555-0199",
                dob, charlestonLocation);

        assertNotNull(createdUser);
        assertEquals("mayajones", createdUser.getUsername());
        assertSame(createdUser, userList.getUser("mayajones"));
    }

    @Test
    void testAddRequestAddsRequestAndSetsCurrentRequest() {
        Victim victim = createVictim("frank");
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(Skill.TRANSPORTATION);

        boolean added = system.addRequest(victim, "Need transportation",
                charlestonLocation, false, skills, 3, 1, "Buddy - Dog",
                Status.OPEN, Urgency.HIGH);

        assertTrue(added);
        assertEquals(1, requestList.getRequests().size());
        assertSame(system.getCurrentRequest(), requestList.getRequests().get(0));
        assertTrue(victim.getActiveRequests().contains(system.getCurrentRequest()));
    }

    @Test
    void testAddRequestRejectsBlankDescription() {
        Victim victim = createVictim("frank");

        boolean added = system.addRequest(victim, "   ", charlestonLocation,
                false, new ArrayList<Skill>(), 1, 0, "", Status.OPEN,
                Urgency.LOW);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testCancelRequestAllowsAuthorToCancelOpenRequest() {
        Victim victim = createVictim("frank");
        HelpRequest request = createRequest(victim, charlestonLocation,
                new ArrayList<Skill>(), Status.OPEN);
        requestList.addRequest(request);
        victim.addActiveRequest(request);

        boolean cancelled = system.cancelRequest(victim, request);

        assertTrue(cancelled);
        assertEquals(Status.CANCELLED, request.getStatus());
        assertFalse(victim.getActiveRequests().contains(request));
    }

    @Test
    void testCancelRequestRejectsDifferentUser() {
        Victim author = createVictim("frank");
        Victim differentUser = createVictim("maya");
        HelpRequest request = createRequest(author, charlestonLocation,
                new ArrayList<Skill>(), Status.OPEN);
        requestList.addRequest(request);

        boolean cancelled = system.cancelRequest(differentUser, request);

        assertFalse(cancelled);
        assertEquals(Status.OPEN, request.getStatus());
    }

    @Test
    void testCommentAddsValidCommentToRequest() {
        Victim victim = createVictim("frank");
        HelpRequest request = createRequest(victim, charlestonLocation,
                new ArrayList<Skill>(), Status.OPEN);

        boolean added = system.comment(request, victim,
                "2026-11-01 10:00", "We are waiting outside.");

        assertTrue(added);
        assertEquals(1, request.getCommentThread().size());
        assertEquals("We are waiting outside.",
                request.getCommentThread().get(0).getText());
    }

    @Test
    void testCommentRejectsBlankText() {
        Victim victim = createVictim("frank");
        HelpRequest request = createRequest(victim, charlestonLocation,
                new ArrayList<Skill>(), Status.OPEN);

        boolean added = system.comment(request, victim,
                "2026-11-01 10:00", " ");

        assertFalse(added);
        assertTrue(request.getCommentThread().isEmpty());
    }

    @Test
    void testFindAvailableRequestsReturnsOnlyQualifiedOpenRequests() {
        Volunteer volunteer = createVolunteer("rosa");

        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);

        HelpRequest matchingRequest = createRequest(createVictim("frank"),
                charlestonLocation, transportation, Status.OPEN);
        HelpRequest unavailableRequest = createRequest(createVictim("maya"),
                charlestonLocation, transportation, Status.ASSIGNED);

        requestList.addRequest(matchingRequest);
        requestList.addRequest(unavailableRequest);

        ArrayList<HelpRequest> matches = system.findAvailableRequests(volunteer);

        assertEquals(1, matches.size());
        assertSame(matchingRequest, matches.get(0));
    }

    @Test
    void testRecommendedRequestsPrioritizesNearbySkillMatch() {
        Volunteer volunteer = createVolunteer("rosa");

        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);

        HelpRequest recommended = createRequest(createVictim("frank"),
                charlestonLocation, transportation, Status.OPEN);

        Location tallahassee = new Location(
                "418 Oak Street", "Tallahassee", "FL", "32301");
        HelpRequest lowerPriority = createRequest(createVictim("maya"),
                tallahassee, new ArrayList<Skill>(), Status.OPEN);

        requestList.addRequest(lowerPriority);
        requestList.addRequest(recommended);

        ArrayList<HelpRequest> requests =
                system.getRecommendedRequests(volunteer);

        assertEquals(2, requests.size());
        assertSame(recommended, requests.get(0));
    }

    @Test
    void testRecommendationExplanationDescribesSkillAndLocationMatch() {
        Volunteer volunteer = createVolunteer("rosa");
        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);
        HelpRequest request = createRequest(createVictim("frank"),
                charlestonLocation, transportation, Status.OPEN);

        String explanation =
                system.getRecommendationExplanation(volunteer, request);

        assertEquals("Recommended: matches your specialty skills and is "
                + "located nearby in Charleston.", explanation);
    }

    @Test
    void testVolunteerAssignsQualifiedVolunteerToOpenRequest() {
        Volunteer volunteer = createVolunteer("rosa");
        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);
        HelpRequest request = createRequest(createVictim("frank"),
                charlestonLocation, transportation, Status.OPEN);

        boolean assigned = system.volunteer(volunteer, request);

        assertTrue(assigned);
        assertEquals(Status.ASSIGNED, request.getStatus());
        assertSame(volunteer, request.getAssignedVolunteer());
        assertTrue(volunteer.getAssignedRequests().contains(request));
    }

    @Test
    void testUpdateRequestStatusMovesAssignedRequestToInProgress() {
        Volunteer volunteer = createVolunteer("rosa");
        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);
        HelpRequest request = createRequest(createVictim("frank"),
                charlestonLocation, transportation, Status.OPEN);

        volunteer.acceptRequest(request);

        boolean updated = system.updateRequestStatus(volunteer, request);

        assertTrue(updated);
        assertEquals(Status.IN_PROGRESS, request.getStatus());
    }

    @Test
    void testCompleteRequestRejectsVolunteer() {
        Volunteer volunteer = createVolunteer("rosa");
        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);
        HelpRequest request = createRequest(createVictim("frank"),
                charlestonLocation, transportation, Status.OPEN);
        requestList.addRequest(request);
        volunteer.acceptRequest(request);

        boolean completed = system.completeRequest(volunteer, request);

        assertFalse(completed);
        assertEquals(Status.ASSIGNED, request.getStatus());
    }

    @Test
    void testCompleteRequestAllowsShelterManager() {
        Volunteer volunteer = createVolunteer("rosa");
        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);
        HelpRequest request = createRequest(createVictim("frank"),
                charlestonLocation, transportation, Status.OPEN);
        requestList.addRequest(request);
        volunteer.acceptRequest(request);

        User shelterManager = mock(User.class);
        when(shelterManager.getType()).thenReturn("ShelterManager");

        boolean completed = system.completeRequest(shelterManager, request);

        assertTrue(completed);
        assertEquals(Status.COMPLETE, request.getStatus());
        assertTrue(volunteer.isAvailable());
    }

    @Test
    void testUpdateHurricaneDataReplacesCurrentData() {
        HurricaneData updatedData = new HurricaneData(120, 3);

        system.updateHurricaneData(updatedData);

        assertSame(updatedData, system.viewHurricaneData());
    }
}