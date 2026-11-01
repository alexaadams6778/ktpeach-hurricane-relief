package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// NOTE REQUIRED FROM BLACKBOARD AS ALLOWED: Formatted with AI
/**
 * Test cases and reasoning for Shelter unit tests.
 *
 * +---------------------------------------------------+--------------------------------------------------------------------+
 * | Test                                              | Reasoning                                                          |
 * +---------------------------------------------------+--------------------------------------------------------------------+
 * | testCreatingShelterWithExistingID                 | Verifies saved ID is kept and all constructor fields are set       |
 * | testCreatingShelterWithGeneratedID                | Verifies a new shelter gets an automatically generated ID          |
 * | testCreatingSheltersWithDifferentIDs              | Ensures two new shelters never share the same ID                   |
 * | testCreatingShelterStartsWithNoOccupantsOrPets    | Ensures a new shelter starts with empty occupant and pet lists     |
 * | testCreatingShelterStartsWithoutMedicalStaff      | Verifies medical staff defaults to false                           |
 * | testCreatingShelterWithAllDataAndExistingID       | Verifies full constructor stores lists and medical staff with ID   |
 * | testCreatingShelterWithExistingIDAndNullLists     | Ensures null lists become empty lists instead of staying null      |
 * | testCreatingShelterWithAllDataAndGeneratedID      | Verifies full constructor without ID stores medical staff          |
 * | testCreatingShelterWithGeneratedIDAndNullLists    | Ensures null lists become empty lists without an ID                |
 * | testSettingName                                   | Verifies setName updates the shelter name                          |
 * | testSettingLocation                               | Verifies setLocation updates the shelter location                  |
 * | testSettingMaxCapacity                            | Verifies setMaxCapacity updates the maximum capacity               |
 * | testSettingCurrentOccupancy                       | Verifies setCurrentOccupancy updates the occupancy                 |
 * | testSettingAllowsPets                             | Verifies setAllowsPets updates the pet policy                      |
 * | testSettingOccupants                              | Verifies setOccupants replaces the occupant list                   |
 * | testSettingPets                                   | Verifies setPets replaces the pet list                             |
 * | testSettingMedicalStaff                           | Verifies medical staff can be turned on and off                    |
 * | testHasVacancyWithSpaceLeft                       | Returns true when an open shelter is below capacity                |
 * | testNoVacancyWhenFull                             | Returns false when the shelter is at capacity                      |
 * | testNoVacancyWhenOverCapacity                     | Returns false when occupancy is over capacity                      |
 * | testNoVacancyWhenClosed                           | Returns false when the shelter is closed                           |
 * | testAvailableSpaces                               | Verifies available spaces equals capacity minus occupancy          |
 * | testAvailableSpacesWhenFull                       | Returns zero available spaces when the shelter is full             |
 * | testAvailableSpacesNeverNegative                  | Ensures available spaces never go below zero                       |
 * | testAvailableResourcesSkipsEmptyResources         | Ensures resources with zero quantity are left out                  |
 * | testAvailableResourcesWhenAllEmpty                | Returns an empty list when every resource is out of stock          |
 * | testAvailableResourcesWithNoResources             | Returns an empty list when the shelter has no resources            |
 * | testCheckingInUser                                | Verifies check-in adds one occupant and records their name         |
 * | testCheckingInUserWhenFull                        | Rejects check-in when the shelter is full                          |
 * | testCheckingInUserWhenClosed                      | Rejects check-in when the shelter is closed                        |
 * | testCheckingInNullUser                            | Safely ignores a null user without crashing                        |
 * | testCheckingInUsersUntilFull                      | Ensures check-in stops once capacity is reached                    |
 * | testCheckingInFamilyThatFits                      | Checks in a family that fits within the available spaces           |
 * | testCheckingInFamilyTooLarge                      | Rejects a family larger than the available spaces                  |
 * | testCheckingInFamilyWithPetAllowed                | Records the pet when the shelter allows pets                       |
 * | testCheckingInFamilyWithPetNotAllowed             | Rejects a family with a pet when pets are not allowed              |
 * | testCheckingInFamilyWithBlankPet                  | Ensures a blank pet entry is not recorded as a pet                 |
 * | testCheckingInEmptyFamily                         | Verifies an empty family leaves occupancy unchanged                |
 * | testUpdatingStatusClosesShelter                   | Verifies updating status closes an open shelter                    |
 * | testUpdatingStatusTwiceReopensShelter             | Verifies updating status twice reopens the shelter                 |
 * | testToStringIncludesName                          | Ensures the summary includes the shelter name                      |
 * | testToStringWithoutMedicalStaff                   | Ensures the summary shows "No" without medical staff               |
 * | testToStringWithMedicalStaff                      | Ensures the summary shows "Yes" with medical staff                 |
 * | testToStringShowsAvailableSpaces                  | Ensures the summary shows the available spaces                     |
 * +---------------------------------------------------+--------------------------------------------------------------------+
 */
public class ShelterTest {

    private UUID shelterID;
    private Location location;
    private ArrayList<Resource> resources;
    private Shelter shelter;
    private User user;

    @BeforeEach
    public void setup() throws Exception {
        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-12");
        location = new Location("1 Vendue Range", "Charleston", "SC", "29401");
        resources = new ArrayList<Resource>();
        resources.add(new Resource("Water", 30, "Bottles"));
        resources.add(new Resource("Blankets", 0, "Blankets"));
        shelterID = UUID.randomUUID();
        shelter = new Shelter(shelterID, "Charleston Shelter", location, 3, 1,
                true, true, resources);
        user = new Victim(UUID.randomUUID(), "Victim", "Frank", "Carter",
                "frankcarter123", "ilovesophie", "frankcarter@gmail.com",
                "843-111-1111", dob, location, new ArrayList<EmergencyContact>(),
                new ArrayList<String>());
    }

    @Test
    void testCreatingShelterWithExistingID() {
        assertEquals(shelterID, shelter.getShelterID());
        assertEquals("Charleston Shelter", shelter.getName());
        assertSame(location, shelter.getLocation());
        assertEquals(3, shelter.getMaxCapacity());
        assertEquals(1, shelter.getCurrentOccupancy());
        assertTrue(shelter.getAllowsPets());
        assertTrue(shelter.isOpen());
    }

    @Test
    void testCreatingShelterWithGeneratedID() {
        Shelter created = new Shelter("New Shelter", location, 50, 0, false, true,
                resources);
        assertNotNull(created.getShelterID());
        assertEquals("New Shelter", created.getName());
        assertEquals(50, created.getMaxCapacity());
        assertEquals(0, created.getCurrentOccupancy());
        assertFalse(created.getAllowsPets());
        assertTrue(created.isOpen());
    }

    @Test
    void testCreatingSheltersWithDifferentIDs() {
        Shelter first = new Shelter("A", location, 10, 0, true, true, resources);
        Shelter second = new Shelter("A", location, 10, 0, true, true, resources);
        assertNotEquals(first.getShelterID(), second.getShelterID());
    }

    @Test
    void testCreatingShelterStartsWithNoOccupantsOrPets() {
        assertNotNull(shelter.getOccupants());
        assertTrue(shelter.getOccupants().isEmpty());
        assertNotNull(shelter.getPets());
        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    void testCreatingShelterStartsWithoutMedicalStaff() {
        assertFalse(shelter.hasMedicalStaff());
    }

    @Test
    void testCreatingShelterWithAllDataAndExistingID() {
        ArrayList<String> occupants = new ArrayList<String>();
        occupants.add("Frank Carter");
        ArrayList<String> pets = new ArrayList<String>();
        pets.add("Max (Dog)");
        Shelter created = new Shelter(shelterID, "Full Shelter", location, 10, 1,
                true, true, resources, occupants, pets, true);
        assertEquals(shelterID, created.getShelterID());
        assertSame(occupants, created.getOccupants());
        assertSame(pets, created.getPets());
        assertTrue(created.hasMedicalStaff());
    }

    @Test
    void testCreatingShelterWithExistingIDAndNullLists() {
        Shelter created = new Shelter(shelterID, "Full Shelter", location, 10, 0,
                true, true, resources, null, null, false);
        assertNotNull(created.getOccupants());
        assertNotNull(created.getPets());
    }

    @Test
    void testCreatingShelterWithAllDataAndGeneratedID() {
        Shelter created = new Shelter("Full Shelter", location, 10, 0, true, true,
                resources, new ArrayList<String>(), new ArrayList<String>(), true);
        assertNotNull(created.getShelterID());
        assertTrue(created.hasMedicalStaff());
    }

    @Test
    void testCreatingShelterWithGeneratedIDAndNullLists() {
        Shelter created = new Shelter("Full Shelter", location, 10, 0, true, true,
                resources, null, null, false);
        assertNotNull(created.getOccupants());
        assertTrue(created.getOccupants().isEmpty());
        assertNotNull(created.getPets());
        assertTrue(created.getPets().isEmpty());
    }

    @Test
    void testSettingName() {
        shelter.setName("Updated Shelter");
        assertEquals("Updated Shelter", shelter.getName());
    }

    @Test
    void testSettingLocation() {
        Location updated = new Location("20 King Street", "Columbia", "SC", "29201");
        shelter.setLocation(updated);
        assertSame(updated, shelter.getLocation());
    }

    @Test
    void testSettingMaxCapacity() {
        shelter.setMaxCapacity(100);
        assertEquals(100, shelter.getMaxCapacity());
    }

    @Test
    void testSettingCurrentOccupancy() {
        shelter.setCurrentOccupancy(2);
        assertEquals(2, shelter.getCurrentOccupancy());
    }

    @Test
    void testSettingAllowsPets() {
        shelter.setAllowsPets(false);
        assertFalse(shelter.getAllowsPets());
    }

    @Test
    void testSettingOccupants() {
        ArrayList<String> occupants = new ArrayList<String>();
        occupants.add("Sophie Carter");
        shelter.setOccupants(occupants);
        assertSame(occupants, shelter.getOccupants());
    }

    @Test
    void testSettingPets() {
        ArrayList<String> pets = new ArrayList<String>();
        pets.add("Whiskers (Cat)");
        shelter.setPets(pets);
        assertSame(pets, shelter.getPets());
    }

    @Test
    void testSettingMedicalStaff() {
        shelter.setHasMedicalStaff(true);
        assertTrue(shelter.hasMedicalStaff());
        shelter.setHasMedicalStaff(false);
        assertFalse(shelter.hasMedicalStaff());
    }

    @Test
    void testHasVacancyWithSpaceLeft() {
        assertTrue(shelter.hasVacancy());
    }

    @Test
    void testNoVacancyWhenFull() {
        shelter.setCurrentOccupancy(3);
        assertFalse(shelter.hasVacancy());
    }

    @Test
    void testNoVacancyWhenOverCapacity() {
        shelter.setCurrentOccupancy(5);
        assertFalse(shelter.hasVacancy());
    }

    @Test
    void testNoVacancyWhenClosed() {
        shelter.updateOperationalStatus();
        assertFalse(shelter.hasVacancy());
    }

    @Test
    void testAvailableSpaces() {
        assertEquals(2, shelter.getAvailableSpaces());
    }

    @Test
    void testAvailableSpacesWhenFull() {
        shelter.setCurrentOccupancy(3);
        assertEquals(0, shelter.getAvailableSpaces());
    }

    @Test
    void testAvailableSpacesNeverNegative() {
        shelter.setCurrentOccupancy(5);
        assertEquals(0, shelter.getAvailableSpaces());
    }

    @Test
    void testAvailableResourcesSkipsEmptyResources() {
        ArrayList<Resource> available = shelter.getAvailableResources();
        assertEquals(1, available.size());
        assertEquals("Water", available.get(0).getName());
    }

    @Test
    void testAvailableResourcesWhenAllEmpty() {
        ArrayList<Resource> empty = new ArrayList<Resource>();
        empty.add(new Resource("Food", 0, "Meals"));
        Shelter created = new Shelter("Empty Shelter", location, 10, 0, true, true,
                empty);
        assertTrue(created.getAvailableResources().isEmpty());
    }

    @Test
    void testAvailableResourcesWithNoResources() {
        Shelter created = new Shelter("No Resources", location, 10, 0, true, true,
                new ArrayList<Resource>());
        assertTrue(created.getAvailableResources().isEmpty());
    }

    @Test
    void testCheckingInUser() {
        shelter.checkInUser(user);
        assertEquals(2, shelter.getCurrentOccupancy());
        assertEquals(1, shelter.getOccupants().size());
        assertEquals("Frank Carter", shelter.getOccupants().get(0));
    }

    @Test
    void testCheckingInUserWhenFull() {
        shelter.setCurrentOccupancy(3);
        shelter.checkInUser(user);
        assertEquals(3, shelter.getCurrentOccupancy());
        assertTrue(shelter.getOccupants().isEmpty());
    }

    @Test
    void testCheckingInUserWhenClosed() {
        shelter.updateOperationalStatus();
        shelter.checkInUser(user);
        assertEquals(1, shelter.getCurrentOccupancy());
        assertTrue(shelter.getOccupants().isEmpty());
    }

    @Test
    void testCheckingInNullUser() {
        shelter.checkInUser(null);
        assertEquals(1, shelter.getCurrentOccupancy());
        assertTrue(shelter.getOccupants().isEmpty());
    }

    @Test
    void testCheckingInUsersUntilFull() {
        shelter.checkInUser(user);
        shelter.checkInUser(user);
        shelter.checkInUser(user);
        assertEquals(3, shelter.getCurrentOccupancy());
        assertEquals(2, shelter.getOccupants().size());
    }

    @Test
    void testCheckingInFamilyThatFits() {
        ArrayList<String> family = new ArrayList<String>();
        family.add("Frank Carter");
        family.add("Sophie Carter");
        assertTrue(shelter.checkInFamily(family, null));
        assertEquals(3, shelter.getCurrentOccupancy());
        assertEquals(2, shelter.getOccupants().size());
        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    void testCheckingInFamilyTooLarge() {
        ArrayList<String> family = new ArrayList<String>();
        family.add("Frank Carter");
        family.add("Sophie Carter");
        family.add("Alex Carter");
        assertFalse(shelter.checkInFamily(family, null));
        assertEquals(1, shelter.getCurrentOccupancy());
        assertTrue(shelter.getOccupants().isEmpty());
    }

    @Test
    void testCheckingInFamilyWithPetAllowed() {
        ArrayList<String> family = new ArrayList<String>();
        family.add("Frank Carter");
        assertTrue(shelter.checkInFamily(family, "Max (Dog)"));
        assertEquals(1, shelter.getPets().size());
        assertEquals("Max (Dog)", shelter.getPets().get(0));
    }

    @Test
    void testCheckingInFamilyWithPetNotAllowed() {
        shelter.setAllowsPets(false);
        ArrayList<String> family = new ArrayList<String>();
        family.add("Frank Carter");
        assertFalse(shelter.checkInFamily(family, "Max (Dog)"));
        assertEquals(1, shelter.getCurrentOccupancy());
        assertTrue(shelter.getOccupants().isEmpty());
        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    void testCheckingInFamilyWithBlankPet() {
        ArrayList<String> family = new ArrayList<String>();
        family.add("Frank Carter");
        assertTrue(shelter.checkInFamily(family, "   "));
        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    void testCheckingInEmptyFamily() {
        assertTrue(shelter.checkInFamily(new ArrayList<String>(), null));
        assertEquals(1, shelter.getCurrentOccupancy());
    }

    @Test
    void testUpdatingStatusClosesShelter() {
        shelter.updateOperationalStatus();
        assertFalse(shelter.isOpen());
    }

    @Test
    void testUpdatingStatusTwiceReopensShelter() {
        shelter.updateOperationalStatus();
        shelter.updateOperationalStatus();
        assertTrue(shelter.isOpen());
    }

    @Test
    void testToStringIncludesName() {
        assertTrue(shelter.toString().contains("Name: Charleston Shelter"));
    }

    @Test
    void testToStringWithoutMedicalStaff() {
        assertTrue(shelter.toString().contains("Medical Staff: No"));
    }

    @Test
    void testToStringWithMedicalStaff() {
        shelter.setHasMedicalStaff(true);
        assertTrue(shelter.toString().contains("Medical Staff: Yes"));
    }

    @Test
    void testToStringShowsAvailableSpaces() {
        assertTrue(shelter.toString().contains("Available Spaces: 2"));
    }
} 

//