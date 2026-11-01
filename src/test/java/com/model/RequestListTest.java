package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestListTest {

    private RequestList requestList;
    private ArrayList<HelpRequest> originalRequests;
    private Victim author;
    private Location charlestonLocation;
    private Location columbiaLocation;

    @BeforeEach
    public void setup() throws Exception {
        requestList = RequestList.getInstance();
        originalRequests = new ArrayList<HelpRequest>(requestList.getRequests());

        requestList.getRequests().clear();

        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("1990-04-12");
        charlestonLocation = new Location(
                "1 Vendue Range", "Charleston", "SC", "29401");
        columbiaLocation = new Location(
                "20 King Street", "Columbia", "SC", "29201");

        author = new Victim(UUID.randomUUID(), "Victim", "Frank", "Carter",
                "frankcarter", "password", "frank@example.com",
                "843-111-1111", dob, charlestonLocation,
                new ArrayList<EmergencyContact>(), new ArrayList<String>());
    }

    @AfterEach
    public void tearDown() {
        requestList.getRequests().clear();
        requestList.getRequests().addAll(originalRequests);
    }

    private HelpRequest createRequest(Location location,
                                      boolean requiresCertification,
                                      ArrayList<Skill> skills,
                                      Status status) {
        return new HelpRequest(author, "Need transportation", location,
                requiresCertification, skills, 3, 1, "Buddy - Dog",
                status, Urgency.HIGH);
    }

    @Test
    void testGetInstanceReturnsSameRequestList() {
        RequestList secondReference = RequestList.getInstance();

        assertSame(requestList, secondReference);
    }

    @Test
    void testAddRequestAddsValidRequest() {
        ArrayList<Skill> skills = new ArrayList<Skill>();
        skills.add(Skill.TRANSPORTATION);

        boolean added = requestList.addRequest(author, "Need transportation",
                charlestonLocation, false, skills, 3, 1, "Buddy - Dog",
                Status.OPEN, Urgency.HIGH);

        assertTrue(added);
        assertEquals(1, requestList.getRequests().size());
        assertEquals("Need transportation",
                requestList.getRequests().get(0).getDescription());
    }

    @Test
    void testAddRequestRejectsNullAuthor() {
        boolean added = requestList.addRequest(null, "Need transportation",
                charlestonLocation, false, new ArrayList<Skill>(), 3, 1,
                "Buddy - Dog", Status.OPEN, Urgency.HIGH);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testAddRequestRejectsBlankDescription() {
        boolean added = requestList.addRequest(author, "   ",
                charlestonLocation, false, new ArrayList<Skill>(), 3, 1,
                "Buddy - Dog", Status.OPEN, Urgency.HIGH);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testAddRequestRejectsNullLocation() {
        boolean added = requestList.addRequest(author, "Need transportation",
                null, false, new ArrayList<Skill>(), 3, 1, "Buddy - Dog",
                Status.OPEN, Urgency.HIGH);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testAddRequestRejectsNegativeNumberOfPeople() {
        boolean added = requestList.addRequest(author, "Need transportation",
                charlestonLocation, false, new ArrayList<Skill>(), -1, 1,
                "Buddy - Dog", Status.OPEN, Urgency.HIGH);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testAddRequestRejectsNegativeNumberOfPets() {
        boolean added = requestList.addRequest(author, "Need transportation",
                charlestonLocation, false, new ArrayList<Skill>(), 3, -1,
                "Buddy - Dog", Status.OPEN, Urgency.HIGH);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testAddRequestRejectsNullStatus() {
        boolean added = requestList.addRequest(author, "Need transportation",
                charlestonLocation, false, new ArrayList<Skill>(), 3, 1,
                "Buddy - Dog", null, Urgency.HIGH);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testAddRequestRejectsNullUrgency() {
        boolean added = requestList.addRequest(author, "Need transportation",
                charlestonLocation, false, new ArrayList<Skill>(), 3, 1,
                "Buddy - Dog", Status.OPEN, null);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testAddExistingRequestAddsRequestOnce() {
        HelpRequest request = createRequest(charlestonLocation, false,
                new ArrayList<Skill>(), Status.OPEN);

        boolean firstAdd = requestList.addRequest(request);
        boolean secondAdd = requestList.addRequest(request);

        assertTrue(firstAdd);
        assertFalse(secondAdd);
        assertEquals(1, requestList.getRequests().size());
    }

    @Test
    void testAddRequestRejectsNullRequestObject() {
        boolean added = requestList.addRequest((HelpRequest) null);

        assertFalse(added);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testGetRequestsByLocationReturnsOnlyMatchingLocation() {
        HelpRequest charlestonRequest = createRequest(charlestonLocation,
                false, new ArrayList<Skill>(), Status.OPEN);
        HelpRequest columbiaRequest = createRequest(columbiaLocation,
                false, new ArrayList<Skill>(), Status.OPEN);

        requestList.addRequest(charlestonRequest);
        requestList.addRequest(columbiaRequest);

        ArrayList<HelpRequest> matches =
                requestList.getRequests(charlestonLocation);

        assertEquals(1, matches.size());
        assertSame(charlestonRequest, matches.get(0));
    }

    @Test
    void testGetRequestsByNullLocationReturnsAllRequests() {
        requestList.addRequest(createRequest(charlestonLocation, false,
                new ArrayList<Skill>(), Status.OPEN));
        requestList.addRequest(createRequest(columbiaLocation, false,
                new ArrayList<Skill>(), Status.OPEN));

        ArrayList<HelpRequest> matches = requestList.getRequests(null);

        assertEquals(2, matches.size());
    }

    @Test
    void testGetRequestsByCertificationReturnsMatchingRequests() {
        HelpRequest certifiedRequest = createRequest(charlestonLocation, true,
                new ArrayList<Skill>(), Status.OPEN);
        HelpRequest regularRequest = createRequest(charlestonLocation, false,
                new ArrayList<Skill>(), Status.OPEN);

        requestList.addRequest(certifiedRequest);
        requestList.addRequest(regularRequest);

        ArrayList<HelpRequest> matches =
                requestList.getRequests(charlestonLocation, true);

        assertEquals(1, matches.size());
        assertSame(certifiedRequest, matches.get(0));
    }

    @Test
    void testGetRequestsBySkillReturnsMatchingRequests() {
        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);

        ArrayList<Skill> medical = new ArrayList<Skill>();
        medical.add(Skill.MEDICAL);

        HelpRequest transportationRequest = createRequest(
                charlestonLocation, false, transportation, Status.OPEN);
        HelpRequest medicalRequest = createRequest(
                charlestonLocation, false, medical, Status.OPEN);

        requestList.addRequest(transportationRequest);
        requestList.addRequest(medicalRequest);

        ArrayList<HelpRequest> matches = requestList.getRequests(
                charlestonLocation, false, Skill.TRANSPORTATION);

        assertEquals(1, matches.size());
        assertSame(transportationRequest, matches.get(0));
    }

    @Test
    void testGetRequestsWithNullSkillIgnoresSkillFilter() {
        ArrayList<Skill> transportation = new ArrayList<Skill>();
        transportation.add(Skill.TRANSPORTATION);

        requestList.addRequest(createRequest(charlestonLocation, false,
                transportation, Status.OPEN));
        requestList.addRequest(createRequest(charlestonLocation, false,
                new ArrayList<Skill>(), Status.OPEN));

        ArrayList<HelpRequest> matches = requestList.getRequests(
                charlestonLocation, false, null);

        assertEquals(2, matches.size());
    }

    @Test
    void testRemoveRequestRemovesStoredRequest() {
        HelpRequest request = createRequest(charlestonLocation, false,
                new ArrayList<Skill>(), Status.OPEN);
        requestList.addRequest(request);

        boolean removed = requestList.removeRequest(request);

        assertTrue(removed);
        assertTrue(requestList.getRequests().isEmpty());
    }

    @Test
    void testRemoveRequestRejectsNullRequest() {
        boolean removed = requestList.removeRequest(null);

        assertFalse(removed);
    }

    @Test
    void testRemoveRequestReturnsFalseForRequestNotInList() {
        HelpRequest request = createRequest(charlestonLocation, false,
                new ArrayList<Skill>(), Status.OPEN);

        boolean removed = requestList.removeRequest(request);

        assertFalse(removed);
    }
}