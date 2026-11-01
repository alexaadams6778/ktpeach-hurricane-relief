package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShelterListTest {

    private ShelterList shelterList = ShelterList.getInstance();
    private ArrayList<Shelter> savedShelters;
    private Location columbia;
    private Location charleston;
    private Shelter petShelter;
    private Shelter communityCenter;
    private Shelter closedShelter;

    @BeforeEach
    public void setup() {
        savedShelters = new ArrayList<Shelter>(shelterList.getShelters());
        shelterList.clearShelters();

        columbia = new Location("1 Main St", "Columbia", "SC", "29201");
        charleston = new Location("1 Vendue Range", "Charleston", "SC", "29401");

        petShelter = new Shelter("Columbia Pet Shelter", columbia, 100, 10, true, true,
                new ArrayList<Resource>());
        petShelter.setHasMedicalStaff(true);

        communityCenter = new Shelter("Columbia Community Center", columbia, 50, 48, false,
                true, new ArrayList<Resource>());

        closedShelter = new Shelter("Charleston Shelter", charleston, 30, 0, true, false,
                new ArrayList<Resource>());
        closedShelter.setHasMedicalStaff(true);

        shelterList.addShelter(petShelter);
        shelterList.addShelter(communityCenter);
        shelterList.addShelter(closedShelter);
    }

    @AfterEach
    public void tearDown() {
        shelterList.clearShelters();
        shelterList.getShelters().addAll(savedShelters);
    }

    @Test
    void testGetInstanceReturnsSameList() {
        assertSame(shelterList, ShelterList.getInstance());
    }

    @Test
    void testGettingAllShelters() {
        assertEquals(3, shelterList.getShelters().size());
    }

    @Test
    void testClearingShelters() {
        shelterList.clearShelters();
        assertTrue(shelterList.getShelters().isEmpty());
    }

    @Test
    void testAddingShelterWithDetails() {
        assertTrue(shelterList.addShelter("New Shelter", columbia, 20, 0, true, true,
                new ArrayList<Resource>()));
        assertEquals(4, shelterList.getShelters().size());
    }

    @Test
    void testAddingShelterObject() {
        Shelter created = new Shelter("New Shelter", columbia, 20, 0, true, true,
                new ArrayList<Resource>());
        assertTrue(shelterList.addShelter(created));
        assertTrue(shelterList.getShelters().contains(created));
    }

    @Test
    void testAddingNullShelter() {
        assertFalse(shelterList.addShelter(null));
        assertEquals(3, shelterList.getShelters().size());
    }

    @Test
    void testAddingDuplicateName() {
        assertFalse(shelterList.addShelter("Charleston Shelter", charleston, 10, 0, true,
                true, new ArrayList<Resource>()));
        assertEquals(3, shelterList.getShelters().size());
    }

    @Test
    void testAddingDuplicateNameDifferentCase() {
        assertFalse(shelterList.addShelter("CHARLESTON SHELTER", charleston, 10, 0, true,
                true, new ArrayList<Resource>()));
        assertEquals(3, shelterList.getShelters().size());
    }

    @Test
    void testRemovingShelter() {
        assertTrue(shelterList.removeShelter(petShelter));
        assertEquals(2, shelterList.getShelters().size());
        assertFalse(shelterList.getShelters().contains(petShelter));
    }

    @Test
    void testRemovingNullShelter() {
        assertFalse(shelterList.removeShelter(null));
        assertEquals(3, shelterList.getShelters().size());
    }

    @Test
    void testRemovingShelterNotInList() {
        Shelter other = new Shelter("Other Shelter", columbia, 10, 0, true, true,
                new ArrayList<Resource>());
        assertFalse(shelterList.removeShelter(other));
        assertEquals(3, shelterList.getShelters().size());
    }

    @Test
    void testRemovingShelterBySameID() {
        Shelter sameID = new Shelter(petShelter.getShelterID(), "Columbia Pet Shelter",
                columbia, 100, 10, true, true, new ArrayList<Resource>());
        assertTrue(shelterList.removeShelter(sameID));
        assertEquals(2, shelterList.getShelters().size());
    }

    @Test
    void testFindingByFullName() {
        ArrayList<Shelter> result = shelterList.getShelters("Charleston Shelter");
        assertEquals(1, result.size());
        assertSame(closedShelter, result.get(0));
    }

    @Test
    void testFindingByPartialName() {
        assertEquals(2, shelterList.getShelters("Columbia").size());
    }

    @Test
    void testFindingByNameIgnoresCase() {
        assertEquals(2, shelterList.getShelters("columbia").size());
    }

    @Test
    void testFindingByNameNotFound() {
        assertTrue(shelterList.getShelters("Greenville").isEmpty());
    }

    @Test
    void testFindingByEmptyName() {
        assertEquals(3, shelterList.getShelters("").size());
    }

    @Test
    void testFindingByNullName() {
        assertEquals(3, shelterList.getShelters((String) null).size());
    }

    @Test
    void testFindingByNameAndLocation() {
        assertEquals(2, shelterList.getShelters("Columbia", columbia).size());
    }

    @Test
    void testFindingByNameAndEquivalentLocation() {
        Location sameAddress = new Location("1 Main St", "Columbia", "SC", "29201");
        assertNotSame(columbia, sameAddress);
        assertEquals(2, shelterList.getShelters("Columbia", sameAddress).size());
    }

    @Test
    void testFindingByNameAndWrongLocation() {
        assertTrue(shelterList.getShelters("Columbia", charleston).isEmpty());
    }

    @Test
    void testFindingByNameAndNullLocation() {
        assertEquals(2, shelterList.getShelters("Columbia", null).size());
    }

    @Test
    void testFindingSheltersThatAllowPets() {
        ArrayList<Shelter> result = shelterList.getShelters("Columbia", columbia, true);
        assertEquals(1, result.size());
        assertSame(petShelter, result.get(0));
    }

    @Test
    void testFindingSheltersThatDoNotAllowPets() {
        ArrayList<Shelter> result = shelterList.getShelters("Columbia", columbia, false);
        assertEquals(1, result.size());
        assertSame(communityCenter, result.get(0));
    }

    @Test
    void testFindingOpenSheltersWithNoRequirements() {
        ArrayList<Shelter> result = shelterList.getShelters(false, false, 1);
        assertEquals(2, result.size());
        assertFalse(result.contains(closedShelter));
    }

    @Test
    void testFindingOpenSheltersThatAllowPets() {
        ArrayList<Shelter> result = shelterList.getShelters(true, false, 1);
        assertEquals(1, result.size());
        assertSame(petShelter, result.get(0));
    }

    @Test
    void testFindingOpenSheltersWithMedicalStaff() {
        ArrayList<Shelter> result = shelterList.getShelters(false, true, 1);
        assertEquals(1, result.size());
        assertSame(petShelter, result.get(0));
    }

    @Test
    void testFindingSheltersWithEnoughSpace() {
        ArrayList<Shelter> result = shelterList.getShelters(false, false, 5);
        assertEquals(1, result.size());
        assertSame(petShelter, result.get(0));
    }

    @Test
    void testFindingSheltersWithExactSpace() {
        ArrayList<Shelter> result = shelterList.getShelters(false, false, 2);
        assertEquals(2, result.size());
    }

    @Test
    void testFindingSheltersWithAllRequirementsNoMatch() {
        assertTrue(shelterList.getShelters(true, true, 100).isEmpty());
    }

    @Test
    void testClosestShelterWithNullList() throws Exception {
        assertNull(shelterList.findShelterByDrivingDistance(columbia, null));
    }

    @Test
    void testClosestShelterWithEmptyList() throws Exception {
        assertNull(shelterList.findShelterByDrivingDistance(columbia,
                new ArrayList<Shelter>()));
    }
}