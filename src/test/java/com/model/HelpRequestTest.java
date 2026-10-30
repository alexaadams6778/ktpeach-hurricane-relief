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
    private ArrayList<Skill> skills;
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
        skills = new ArrayList<Skill>();
        skills.add(Skill.CPR);
        skills.add(Skill.TRANSPORTATION);
        requestID = UUID.randomUUID();
        request = new HelpRequest(requestID, author, "Need transportation", location,
                true, skills, 3, 1, "One small dog", Status.OPEN, Urgency.HIGH);
    }

    @Test
    void testCreatingRequestWithExistingID() {
        assertEquals(requestID, request.getRequestID());
        assertSame(author, request.getAuthor());
        assertEquals("Need transportation", request.getDescription());
        assertSame(location, request.getLocation());
        assertTrue(request.requiresCertification());
        assertEquals(skills, request.getNeededSkills());
        assertEquals(3, request.getNumberOfPeople());
        assertEquals(1, request.getNumberOfPets());
        assertEquals("One small dog", request.getPetInfo());
        assertEquals(Status.OPEN, request.getStatus());
        assertEquals(Urgency.HIGH, request.getUrgency());
    }

    @Test
    void testCreatingRequestWithGeneratedID() {
        HelpRequest created = new HelpRequest(author, "Need food", location, false,
                skills, 1, 0, "", Status.OPEN, Urgency.LOW);
        assertNotNull(created.getRequestID());
        assertSame(author, created.getAuthor());
        assertEquals("Need food", created.getDescription());
        assertSame(location, created.getLocation());
        assertFalse(created.requiresCertification());
        assertEquals(skills, created.getNeededSkills());
        assertEquals(1, created.getNumberOfPeople());
        assertEquals(0, created.getNumberOfPets());
        assertEquals("", created.getPetInfo());
        assertEquals(Status.OPEN, created.getStatus());
        assertEquals(Urgency.LOW, created.getUrgency());
    }

    @Test
    void testCreatingRequestsWithDifferentIDs() {
        HelpRequest first = new HelpRequest(author, "Need food", location, false,
                skills, 1, 0, "", Status.OPEN, Urgency.LOW);
        HelpRequest second = new HelpRequest(author, "Need food", location, false,
                skills, 1, 0, "", Status.OPEN, Urgency.LOW);
        assertNotEquals(first.getRequestID(), second.getRequestID());
    }

    @Test
    void testCreatingRequestWithNullSkills() {
        HelpRequest created = new HelpRequest(author, "Need food", location, false,
                null, 1, 0, "", Status.OPEN, Urgency.LOW);
        assertNotNull(created.getNeededSkills());
        assertTrue(created.getNeededSkills().isEmpty());
    }

    @Test
    void testCreatingRequestWithEmptySkills() {
        HelpRequest created = new HelpRequest(author, "Need food", location, false,
                new ArrayList<Skill>(), 1, 0, "", Status.OPEN, Urgency.LOW);
        assertTrue(created.getNeededSkills().isEmpty());
    }

    @Test
    void testCreatingRequestWithNullStatus() {
        HelpRequest created = new HelpRequest(author, "Need food", location, false,
                skills, 1, 0, "", null, Urgency.LOW);
        assertEquals(Status.OPEN, created.getStatus());
    }

    @Test
    void testCreatingRequestWithExistingIDAndNullStatus() {
        HelpRequest created = new HelpRequest(requestID, author, "Need food", location,
                false, null, 1, 0, "", null, Urgency.LOW);
        assertEquals(requestID, created.getRequestID());
        assertEquals(Status.OPEN, created.getStatus());
        assertTrue(created.getNeededSkills().isEmpty());
    }

    @Test
    void testCreatingRequestWithDefaultHurricaneData() {
        assertNotNull(request.getHurricaneData());
        assertEquals(85, request.getHurricaneData().getWindSpeeds());
        assertEquals(1, request.getHurricaneData().getCategory());
    }

    @Test
    void testCreatingRequestWithEmptyComments() {
        assertNotNull(request.getCommentThread());
        assertTrue(request.getCommentThread().isEmpty());
    }

    @Test
    void testCreatingRequestWithoutAssignments() {
        assertNull(request.getAssignedVolunteer());
        assertNull(request.getSelectedShelter());
        assertNull(request.getGuestPhoneNumber());
        assertFalse(request.requiresMedicalStaff());
    }

    @Test
    void testSettingAssignedVolunteer() {
        request.setAssignedVolunteer(volunteer);
        assertSame(volunteer, request.getAssignedVolunteer());
    }

    @Test
    void testClearingAssignedVolunteer() {
        request.setAssignedVolunteer(volunteer);
        request.setAssignedVolunteer(null);
        assertNull(request.getAssignedVolunteer());
    }

    @Test
    void testRejectingNonVolunteerAssignment() {
        request.setAssignedVolunteer(author);
        assertNull(request.getAssignedVolunteer());
    }

    @Test
    void testInvalidAssignmentKeepsExistingVolunteer() {
        request.setAssignedVolunteer(volunteer);
        request.setAssignedVolunteer(author);
        assertSame(volunteer, request.getAssignedVolunteer());
    }

    @Test
    void testReplacingAssignedVolunteer() {
        Volunteer replacement = new Volunteer(UUID.randomUUID(), "Volunteer",
                "Alex", "Jones", "alex123", "password", "alex@example.com", "843-333-3333",
                author.getDob(), location, new ArrayList<EmergencyContact>(),
                new ArrayList<String>(), false);
        request.setAssignedVolunteer(volunteer);
        request.setAssignedVolunteer(replacement);
        assertSame(replacement, request.getAssignedVolunteer());
    }

    @Test
    void testSettingDescription() {
        request.setDescription("Need food and water");
        assertEquals("Need food and water", request.getDescription());
    }

    @Test
    void testSettingNullDescriptionKeepsExistingDescription() {
        request.setDescription(null);
        assertEquals("Need transportation", request.getDescription());
    }

    @Test
    void testSettingEmptyDescription() {
        request.setDescription("");
        assertEquals("", request.getDescription());
    }

    @Test
    void testSettingLocation() {
        Location updated = new Location("20 King Street", "Columbia", "SC", "29201");
        request.setLocation(updated);
        assertSame(updated, request.getLocation());
    }

    @Test
    void testSettingNullLocationKeepsExistingLocation() {
        request.setLocation(null);
        assertSame(location, request.getLocation());
    }

    @Test
    void testSettingNumberOfPeople() {
        request.setNumberOfPeople(5);
        assertEquals(5, request.getNumberOfPeople());
    }

    @Test
    void testSettingNumberOfPeopleToZero() {
        request.setNumberOfPeople(0);
        assertEquals(0, request.getNumberOfPeople());
    }

    @Test
    void testSettingNumberOfPets() {
        request.setNumberOfPets(2);
        assertEquals(2, request.getNumberOfPets());
    }

    @Test
    void testSettingNumberOfPetsToZero() {
        request.setNumberOfPets(0);
        assertEquals(0, request.getNumberOfPets());
    }

    @Test
    void testSettingPetInfo() {
        request.setPetInfo("Two cats in carriers");
        assertEquals("Two cats in carriers", request.getPetInfo());
    }

    @Test
    void testSettingEmptyPetInfo() {
        request.setPetInfo("");
        assertEquals("", request.getPetInfo());
    }

    @Test
    void testSettingStatus() {
        request.setStatus(Status.CANCELLED);
        assertEquals(Status.CANCELLED, request.getStatus());
    }

    @Test
    void testSettingNullStatusKeepsExistingStatus() {
        request.setStatus(Status.ASSIGNED);
        request.setStatus(null);
        assertEquals(Status.ASSIGNED, request.getStatus());
    }

    @Test
    void testSettingUrgency() {
        request.setUrgency(Urgency.CRITICAL);
        assertEquals(Urgency.CRITICAL, request.getUrgency());
    }

    @Test
    void testSettingNullUrgencyKeepsExistingUrgency() {
        request.setUrgency(null);
        assertEquals(Urgency.HIGH, request.getUrgency());
    }

    @Test
    void testSettingHurricaneData() {
        HurricaneData updated = new HurricaneData(130, 4);
        request.setHurricaneData(updated);
        assertSame(updated, request.getHurricaneData());
        assertEquals(130, request.getHurricaneData().getWindSpeeds());
        assertEquals(4, request.getHurricaneData().getCategory());
    }

    @Test
    void testSettingGuestPhoneNumber() {
        request.setGuestPhoneNumber("843-555-5555");
        assertEquals("843-555-5555", request.getGuestPhoneNumber());
    }

    @Test
    void testClearingGuestPhoneNumber() {
        request.setGuestPhoneNumber("843-555-5555");
        request.setGuestPhoneNumber(null);
        assertNull(request.getGuestPhoneNumber());
    }

    @Test
    void testAddingOneComment() {
        Comment comment = new Comment(author, "2026-10-30 12:00", "Waiting at the door");
        request.addComment(comment);
        assertEquals(1, request.getCommentThread().size());
        assertSame(comment, request.getCommentThread().get(0));
    }

    @Test
    void testAddingMultipleCommentsPreservesOrder() {
        Comment first = new Comment(author, "2026-10-30 12:00", "Waiting at the door");
        Comment second = new Comment(volunteer, "2026-10-30 12:05", "On my way");
        request.addComment(first);
        request.addComment(second);
        assertEquals(2, request.getCommentThread().size());
        assertSame(first, request.getCommentThread().get(0));
        assertSame(second, request.getCommentThread().get(1));
    }

    @Test
    void testAddingNullComment() {
        request.addComment(null);
        assertTrue(request.getCommentThread().isEmpty());
    }

    @Test
    void testAddingNullCommentKeepsExistingComments() {
        Comment comment = new Comment(author, "2026-10-30 12:00", "Waiting at the door");
        request.addComment(comment);
        request.addComment(null);
        assertEquals(1, request.getCommentThread().size());
        assertSame(comment, request.getCommentThread().get(0));
    }

    @Test
    void testCommentsAreSeparateForEachRequest() {
        HelpRequest second = new HelpRequest(author, "Need food", location, false,
                skills, 1, 0, "", Status.OPEN, Urgency.LOW);
        request.addComment(new Comment(author, "2026-10-30 12:00", "Waiting at the door"));
        assertEquals(1, request.getCommentThread().size());
        assertTrue(second.getCommentThread().isEmpty());
    }

    @Test
    void testOpenStatusBehavior() {
        request.setStatus(Status.OPEN);
        assertFalse(request.isActiveAssignment());
        assertFalse(request.isTerminal());
        request.updateStatus();
        assertEquals(Status.OPEN, request.getStatus());
    }

    @Test
    void testAssignedStatusBehavior() {
        request.setStatus(Status.ASSIGNED);
        assertTrue(request.isActiveAssignment());
        assertFalse(request.isTerminal());
        request.updateStatus();
        assertEquals(Status.IN_PROGRESS, request.getStatus());
    }

    @Test
    void testInProgressStatusBehavior() {
        request.setStatus(Status.IN_PROGRESS);
        assertTrue(request.isActiveAssignment());
        assertFalse(request.isTerminal());
        request.updateStatus();
        assertEquals(Status.IN_PROGRESS, request.getStatus());
    }

    @Test
    void testCompleteStatusBehavior() {
        request.setStatus(Status.COMPLETE);
        assertFalse(request.isActiveAssignment());
        assertTrue(request.isTerminal());
        request.updateStatus();
        assertEquals(Status.COMPLETE, request.getStatus());
    }

    @Test
    void testCancelledStatusBehavior() {
        request.setStatus(Status.CANCELLED);
        assertFalse(request.isActiveAssignment());
        assertTrue(request.isTerminal());
        request.updateStatus();
        assertEquals(Status.CANCELLED, request.getStatus());
    }

    @Test
    void testUpdatingStatusTwiceRemainsInProgress() {
        request.setStatus(Status.ASSIGNED);
        request.updateStatus();
        request.updateStatus();
        assertEquals(Status.IN_PROGRESS, request.getStatus());
    }

    @Test
    void testMatchingRequestWithRequiredSkill() {
        assertTrue(request.isARequest(location, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithEquivalentLocation() {
        Location matching = new Location("1 Vendue Range", "Charleston", "SC", "29401");
        assertNotSame(location, matching);
        assertTrue(request.isARequest(matching, true, Skill.TRANSPORTATION));
    }

    @Test
    void testMatchingRequestWithoutSkillFilter() {
        assertTrue(request.isARequest(location, true, null));
    }

    @Test
    void testMatchingRequestWithDifferentCertification() {
        assertFalse(request.isARequest(location, false, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithoutCertificationRequired() {
        HelpRequest created = new HelpRequest(author, "Need food", location, false,
                skills, 1, 0, "", Status.OPEN, Urgency.LOW);
        assertTrue(created.isARequest(location, false, Skill.CPR));
        assertFalse(created.isARequest(location, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithMissingSkill() {
        assertFalse(request.isARequest(location, true, Skill.EMT));
    }

    @Test
    void testMatchingRequestWithDifferentAddress() {
        Location different = new Location("2 Vendue Range", "Charleston", "SC", "29401");
        assertFalse(request.isARequest(different, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithDifferentCity() {
        Location different = new Location("1 Vendue Range", "Columbia", "SC", "29401");
        assertFalse(request.isARequest(different, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithDifferentState() {
        Location different = new Location("1 Vendue Range", "Charleston", "NC", "29401");
        assertFalse(request.isARequest(different, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithDifferentZipCode() {
        Location different = new Location("1 Vendue Range", "Charleston", "SC", "29402");
        assertFalse(request.isARequest(different, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithNullSearchLocation() {
        assertFalse(request.isARequest(null, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWhenBothLocationsAreNull() {
        HelpRequest created = new HelpRequest(author, "Need food", null, true,
                skills, 1, 0, "", Status.OPEN, Urgency.LOW);
        assertTrue(created.isARequest(null, true, Skill.CPR));
        assertFalse(created.isARequest(location, true, Skill.CPR));
    }

    @Test
    void testMatchingRequestWithNoSkills() {
        HelpRequest created = new HelpRequest(author, "Need food", location, false,
                null, 1, 0, "", Status.OPEN, Urgency.LOW);
        assertTrue(created.isARequest(location, false, null));
        assertFalse(created.isARequest(location, false, Skill.CPR));
    }

    @Test
    void testSettingSelectedShelter() {
        Shelter shelter = new Shelter("Charleston Shelter", location, 100, 0, true, true,
                new ArrayList<Resource>());
        request.setSelectedShelter(shelter);
        assertSame(shelter, request.getSelectedShelter());
    }

    @Test
    void testClearingSelectedShelter() {
        Shelter shelter = new Shelter("Charleston Shelter", location, 100, 0, true, true,
                new ArrayList<Resource>());
        request.setSelectedShelter(shelter);
        request.setSelectedShelter(null);
        assertNull(request.getSelectedShelter());
    }

    @Test
    void testSettingMedicalStaffRequirement() {
        request.setRequiresMedicalStaff(true);
        assertTrue(request.requiresMedicalStaff());
        request.setRequiresMedicalStaff(false);
        assertFalse(request.requiresMedicalStaff());
    }

    @Test
    void testToStringWithoutVolunteer() {
        assertEquals("Need transportation - Open - Unassigned", request.toString());
    }

    @Test
    void testToStringWithVolunteer() {
        request.setAssignedVolunteer(volunteer);
        request.setStatus(Status.IN_PROGRESS);
        assertEquals("Need transportation - In Progress - sophie123", request.toString());
    }

    @Test
    void testToStringAfterChanges() {
        request.setDescription("Need medical assistance");
        request.setStatus(Status.COMPLETE);
        request.setAssignedVolunteer(volunteer);
        request.setAssignedVolunteer(null);
        assertEquals("Need medical assistance - Complete - Unassigned", request.toString());
    }
}
