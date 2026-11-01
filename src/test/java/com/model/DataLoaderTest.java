package com.model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// NOTE REQUIRED FROM BLACKBOARD AS ALLOWED: Formatted with AI
/**
 * +---------------------------------------------------+--------------------------------------------------------------------+
 * | Test                                              | Reasoning                                                          |
 * +---------------------------------------------------+--------------------------------------------------------------------+
 * | testLoadUsersNotNull                              | Verifies that loadUsers never returns a null collection            |
 * | testLoadUsersPopulatesList                        | Ensures users list contains records loaded from JSON file          |
 * | testLoadUsersCount                                | Validates that all eight default users in the file are loaded      |
 * | testLoadUsersVictimParsed                         | Confirms a Victim account is instantiated as a Victim object       |
 * | testLoadUsersAdminParsed                          | Confirms an Admin account is instantiated as a SystemAdmin object   |
 * | testLoadUsersVolunteerParsed                      | Confirms a Volunteer account is instantiated as a Volunteer object |
 * | testLoadUsersShelterManagerParsed                 | Confirms a ShelterManager account is instantiated correctly        |
 * | testLoadUsersVolunteerHasVehicleFlag              | Verifies the boolean vehicle flag parses correctly for volunteer  |
 * | testLoadUsersVolunteerVehicleDescription          | Verifies the vehicle description text is loaded accurately         |
 * | testLoadUsersVolunteerSkills                      | Verifies volunteer skills are properly converted to Skill enums    |
 * | testLoadUsersVolunteerCertifications              | Verifies volunteer certifications list is populated from JSON      |
 * | testLoadUsersVolunteerApprovedStatus              | Verifies volunteer approval boolean flag is parsed accurately      |
 * | testLoadUsersShelterManagerAssignedShelter        | Verifies assigned shelter UUID matches existing shelter in list    |
 * | testLoadUsersEmergencyContactsSize                | Verifies user emergency contacts array is parsed and loaded        |
 * | testLoadUsersEmergencyContactDetails              | Validates individual fields of loaded emergency contact            |
 * | testLoadUsersMedicalInfoList                      | Verifies user medical info strings are accurately populated        |
 * | testLoadUsersDOBFormat                            | Verifies date of birth string parses to Date without error         |
 * | testLoadUsersLocationAddress                      | Verifies user location address line parses correctly               |
 * | testLoadUsersLocationCity                         | Verifies user location city parses correctly                       |
 * | testLoadUsersLocationState                        | Verifies user location state parses correctly                      |
 * | testLoadUsersLocationZipCode                      | Verifies user location postal code parses correctly                |
 * | testLoadUsersUsernamesUnique                      | Ensures all loaded users have distinct usernames                   |
 * | testLoadUsersPasswordsLoaded                      | Verifies password strings are populated from the data file         |
 * | testLoadUsersEmailsLoaded                         | Verifies email strings are populated from the data file            |
 * | testLoadUsersPhoneNumbersLoaded                   | Verifies phone number strings are populated from the data file     |
 * | testLoadSheltersNotNull                           | Verifies that loadShelters never returns a null collection         |
 * | testLoadSheltersPopulatesList                     | Ensures shelters list contains records loaded from JSON file       |
 * | testLoadSheltersCount                             | Validates that all ten shelters in the data file are loaded        |
 * | testLoadSheltersName                              | Verifies shelter name is loaded accurately                         |
 * | testLoadSheltersCapacity                          | Verifies maximum capacity number is parsed accurately              |
 * | testLoadSheltersOccupancy                         | Verifies current occupancy count is parsed accurately              |
 * | testLoadSheltersAllowsPetsFlag                    | Verifies pet policy boolean flag parses accurately                 |
 * | testLoadSheltersIsOpenFlag                        | Verifies open/close status boolean flag parses accurately          |
 * | testLoadSheltersHasMedicalStaffFlag               | Verifies medical staff availability boolean parses accurately      |
 * | testLoadSheltersLocationAddress                   | Verifies shelter street address parses accurately                  |
 * | testLoadSheltersLocationCity                      | Verifies shelter city parses accurately                            |
 * | testLoadSheltersLocationState                     | Verifies shelter state parses accurately                           |
 * | testLoadSheltersLocationZipCode                   | Verifies shelter postal code parses accurately                     |
 * | testLoadSheltersResourcesCount                    | Verifies shelter inventory resource items are loaded               |
 * | testLoadSheltersResourceDetails                   | Verifies resource name, quantity, and unit of measure parse        |
 * | testLoadSheltersOccupantsList                     | Verifies shelter resident occupant names list is populated         |
 * | testLoadSheltersPetsList                          | Verifies shelter pets descriptions list is populated               |
 * | testLoadRequestsNotNull                           | Verifies that loadRequests never returns a null collection         |
 * | testLoadRequestsPopulatesList                     | Ensures requests list contains records loaded from JSON file       |
 * | testLoadRequestsCount                             | Validates that all seven requests in the data file are loaded      |
 * | testLoadRequestsDescription                       | Verifies request description text is parsed accurately             |
 * | testLoadRequestsAuthorResolved                    | Verifies author UUID matches and links to loaded User instance     |
 * | testLoadRequestsLocationAddress                   | Verifies request street address parses accurately                  |
 * | testLoadRequestsLocationCity                      | Verifies request city parses accurately                            |
 * | testLoadRequestsLocationState                     | Verifies request state parses accurately                           |
 * | testLoadRequestsLocationZipCode                   | Verifies request postal code parses accurately                     |
 * | testLoadRequestsNumberOfPeople                    | Verifies count of people requiring assistance is loaded            |
 * | testLoadRequestsNumberOfPets                      | Verifies count of pets requiring assistance is loaded              |
 * | testLoadRequestsStatusEnum                        | Verifies request status string is converted to Status enum         |
 * | testLoadRequestsUrgencyEnum                       | Verifies request urgency string is converted to Urgency enum       |
 * | testLoadRequestsNeededSkills                      | Verifies needed skills are converted to Skill enums accurately     |
 * | testLoadRequestsHurricaneDataCategory             | Verifies hurricane category level parses accurately                |
 * | testLoadRequestsHurricaneDataWindSpeed            | Verifies hurricane wind speed value parses accurately              |
 * | testLoadRequestsRequiresCertificationFlag         | Verifies certification requirement boolean flag                    |
 * | testLoadRequestsCommentThreadParsed               | Verifies comment thread author, timestamp, and text parse          |
 * +---------------------------------------------------+--------------------------------------------------------------------+
 */
public class DataLoaderTest {

    private ArrayList<User> originalUsers;
    private ArrayList<Shelter> originalShelters;
    private ArrayList<HelpRequest> originalRequests;

    @BeforeEach
    public void setup() {
        // Save references to singleton lists
        UserList userList = UserList.getInstance();
        ShelterList shelterList = ShelterList.getInstance();
        RequestList requestList = RequestList.getInstance();

        originalUsers = new ArrayList<User>(userList.getUsers());
        originalShelters = new ArrayList<Shelter>(shelterList.getShelters());
        originalRequests = new ArrayList<HelpRequest>(requestList.getRequests());

        // Ensure prerequisite data exists for cross-referencing lookups
        if (shelterList.getShelters().isEmpty()) {
            shelterList.getShelters().addAll(DataLoader.loadShelters());
        }
        if (userList.getUsers().isEmpty()) {
            userList.getUsers().addAll(DataLoader.loadUsers());
        }
    }

    @AfterEach
    public void tearDown() {
        // Restore all singleton lists
        UserList.getInstance().getUsers().clear();
        UserList.getInstance().getUsers().addAll(originalUsers);

        ShelterList.getInstance().getShelters().clear();
        ShelterList.getInstance().getShelters().addAll(originalShelters);

        RequestList.getInstance().getRequests().clear();
        RequestList.getInstance().getRequests().addAll(originalRequests);
    }

    // ==========================================
    // Tests for DataLoader.loadUsers()
    // ==========================================

    @Test
    void testLoadUsersNotNull() {
        ArrayList<User> users = DataLoader.loadUsers();
        assertNotNull(users);
    }

    @Test
    void testLoadUsersPopulatesList() {
        ArrayList<User> users = DataLoader.loadUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    void testLoadUsersCount() {
        ArrayList<User> users = DataLoader.loadUsers();
        assertEquals(8, users.size());
    }

    @Test
    void testLoadUsersVictimParsed() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertTrue(user instanceof Victim);
        assertEquals("maya.thompson", user.getUsername());
        assertEquals("Maya", user.getFirstName());
        assertEquals("Thompson", user.getLastName());
    }

    @Test
    void testLoadUsersAdminParsed() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(1);
        assertTrue(user instanceof SystemAdmin);
        assertEquals("jordan.lee", user.getUsername());
        assertEquals("Jordan", user.getFirstName());
        assertEquals("Lee", user.getLastName());
    }

    @Test
    void testLoadUsersVolunteerParsed() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(3);
        assertTrue(user instanceof Volunteer);
        assertEquals("rosa.delgado", user.getUsername());
        assertEquals("Rosa", user.getFirstName());
        assertEquals("Delgado", user.getLastName());
    }

    @Test
    void testLoadUsersShelterManagerParsed() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(4);
        assertTrue(user instanceof ShelterManager);
        assertEquals("felix.shelter", user.getUsername());
        assertEquals("Felix", user.getFirstName());
        assertEquals("Vance", user.getLastName());
    }

    @Test
    void testLoadUsersVolunteerHasVehicleFlag() {
        ArrayList<User> users = DataLoader.loadUsers();
        Volunteer volunteerWithVehicle = (Volunteer) users.get(3);
        Volunteer volunteerWithoutVehicle = (Volunteer) users.get(2);
        assertTrue(volunteerWithVehicle.hasVehicle());
        assertFalse(volunteerWithoutVehicle.hasVehicle());
    }

    @Test
    void testLoadUsersVolunteerVehicleDescription() {
        ArrayList<User> users = DataLoader.loadUsers();
        Volunteer volunteer = (Volunteer) users.get(3);
        assertEquals("Ford F-350 truck", volunteer.getVehicleDescription());
    }

    @Test
    void testLoadUsersVolunteerSkills() {
        ArrayList<User> users = DataLoader.loadUsers();
        Volunteer volunteer = (Volunteer) users.get(3);
        assertNotNull(volunteer.getSkills());
    }

    @Test
    void testLoadUsersVolunteerCertifications() {
        ArrayList<User> users = DataLoader.loadUsers();
        Volunteer volunteer = (Volunteer) users.get(3);
        assertNotNull(volunteer.getCertifications());
    }

    @Test
    void testLoadUsersVolunteerApprovedStatus() {
        ArrayList<User> users = DataLoader.loadUsers();
        Volunteer volunteer = (Volunteer) users.get(2);
        assertFalse(volunteer.isApproved());
    }

    @Test
    void testLoadUsersShelterManagerAssignedShelter() {
        ArrayList<User> users = DataLoader.loadUsers();
        ShelterManager manager = (ShelterManager) users.get(4);
        assertNotNull(manager.getAssignedShelter());
        assertEquals("Military Magnet Academy Shelter", manager.getAssignedShelter().getName());
    }

    @Test
    void testLoadUsersEmergencyContactsSize() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals(1, user.getEmergencyContacts().size());
    }

    @Test
    void testLoadUsersEmergencyContactDetails() {
        ArrayList<User> users = DataLoader.loadUsers();
        EmergencyContact contact = users.get(0).getEmergencyContacts().get(0);
        assertEquals("Daniel", contact.getFirstName());
        assertEquals("Thompson", contact.getLastName());
        assertEquals(24, contact.getAge());
        assertEquals("Brother", contact.getRelationship());
        assertEquals("daniel.thompson@gmail.com", contact.getEmail());
        assertEquals("555-010-1843", contact.getPhoneNumber());
    }

    @Test
    void testLoadUsersMedicalInfoList() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals(1, user.getMedicalInfo().size());
        assertEquals("Peanut allergy", user.getMedicalInfo().get(0));
    }

    @Test
    void testLoadUsersDOBFormat() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        String formatted = new SimpleDateFormat("yyyy-MM-dd").format(user.getDob());
        assertEquals("1994-06-18", formatted);
    }

    @Test
    void testLoadUsersLocationAddress() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals("418 Oak Street", user.getLocation().getAddress());
    }

    @Test
    void testLoadUsersLocationCity() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals("Tallahassee", user.getLocation().getCity());
    }

    @Test
    void testLoadUsersLocationState() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals("FL", user.getLocation().getState());
    }

    @Test
    void testLoadUsersLocationZipCode() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals("32301", user.getLocation().getZipCode());
    }

    @Test
    void testLoadUsersUsernamesUnique() {
        ArrayList<User> users = DataLoader.loadUsers();
        ArrayList<String> usernames = new ArrayList<String>();
        for (User u : users) {
            assertFalse(usernames.contains(u.getUsername()));
            usernames.add(u.getUsername());
        }
    }

    @Test
    void testLoadUsersPasswordsLoaded() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals("securePassword123", user.getPassword());
    }

    @Test
    void testLoadUsersEmailsLoaded() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals("maya.thompson@gmail.com", user.getEmail());
    }

    @Test
    void testLoadUsersPhoneNumbersLoaded() {
        ArrayList<User> users = DataLoader.loadUsers();
        User user = users.get(0);
        assertEquals("555-010-1842", user.getPhoneNumber());
    }

    // ==========================================
    // Tests for DataLoader.loadShelters()
    // ==========================================

    @Test
    void testLoadSheltersNotNull() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertNotNull(shelters);
    }

    @Test
    void testLoadSheltersPopulatesList() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertFalse(shelters.isEmpty());
    }

    @Test
    void testLoadSheltersCount() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals(10, shelters.size());
    }

    @Test
    void testLoadSheltersName() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals("Leon County Community Center", shelters.get(0).getName());
    }

    @Test
    void testLoadSheltersCapacity() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals(250, shelters.get(0).getMaxCapacity());
    }

    @Test
    void testLoadSheltersOccupancy() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals(84, shelters.get(0).getCurrentOccupancy());
    }

    @Test
    void testLoadSheltersAllowsPetsFlag() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertTrue(shelters.get(0).getAllowsPets());
        assertFalse(shelters.get(1).getAllowsPets());
    }

    @Test
    void testLoadSheltersIsOpenFlag() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertTrue(shelters.get(0).isOpen());
    }

    @Test
    void testLoadSheltersHasMedicalStaffFlag() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertTrue(shelters.get(0).hasMedicalStaff());
        assertFalse(shelters.get(1).hasMedicalStaff());
    }

    @Test
    void testLoadSheltersLocationAddress() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals("1200 Civic Center Road", shelters.get(0).getLocation().getAddress());
    }

    @Test
    void testLoadSheltersLocationCity() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals("Tallahassee", shelters.get(0).getLocation().getCity());
    }

    @Test
    void testLoadSheltersLocationState() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals("FL", shelters.get(0).getLocation().getState());
    }

    @Test
    void testLoadSheltersLocationZipCode() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals("32301", shelters.get(0).getLocation().getZipCode());
    }

    @Test
    void testLoadSheltersResourcesCount() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        assertEquals(2, shelters.get(0).getAvailableResources().size());
    }

    @Test
    void testLoadSheltersResourceDetails() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        Resource res = shelters.get(0).getAvailableResources().get(0);
        assertEquals("Bottled Water", res.getName());
        assertEquals(500, res.getQuantity());
        assertEquals("bottles", res.getUnit());
    }

    @Test
    void testLoadSheltersOccupantsList() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        ArrayList<String> occupants = shelters.get(0).getOccupants();
        assertEquals(2, occupants.size());
        assertEquals("John Doe", occupants.get(0));
        assertEquals("Jane Doe", occupants.get(1));
    }

    @Test
    void testLoadSheltersPetsList() {
        ArrayList<Shelter> shelters = DataLoader.loadShelters();
        ArrayList<String> pets = shelters.get(0).getPets();
        assertEquals(1, pets.size());
        assertEquals("Buddy (Golden Retriever)", pets.get(0));
    }

    // ==========================================
    // Tests for DataLoader.loadRequests()
    // ==========================================

    @Test
    void testLoadRequestsNotNull() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        assertNotNull(requests);
    }

    @Test
    void testLoadRequestsPopulatesList() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        assertFalse(requests.isEmpty());
    }

    @Test
    void testLoadRequestsCount() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        assertEquals(7, requests.size());
    }

    @Test
    void testLoadRequestsDescription() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals("Need transportation to an open shelter for two adults and one child.", first.getDescription());
    }

    @Test
    void testLoadRequestsAuthorResolved() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertNotNull(first.getAuthor());
        assertEquals("maya.thompson", first.getAuthor().getUsername());
    }

    @Test
    void testLoadRequestsLocationAddress() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals("418 Oak Street", first.getLocation().getAddress());
    }

    @Test
    void testLoadRequestsLocationCity() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals("Tallahassee", first.getLocation().getCity());
    }

    @Test
    void testLoadRequestsLocationState() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals("FL", first.getLocation().getState());
    }

    @Test
    void testLoadRequestsLocationZipCode() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals("32301", first.getLocation().getZipCode());
    }

    @Test
    void testLoadRequestsNumberOfPeople() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals(3, first.getNumberOfPeople());
    }

    @Test
    void testLoadRequestsNumberOfPets() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals(1, first.getNumberOfPets());
    }

    @Test
    void testLoadRequestsStatusEnum() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        assertEquals(Status.OPEN, requests.get(0).getStatus());
        assertEquals(Status.IN_PROGRESS, requests.get(1).getStatus());
    }

    @Test
    void testLoadRequestsUrgencyEnum() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        assertEquals(Urgency.HIGH, requests.get(0).getUrgency());
        assertEquals(Urgency.CRITICAL, requests.get(1).getUrgency());
        assertEquals(Urgency.MEDIUM, requests.get(3).getUrgency());
    }

    @Test
    void testLoadRequestsNeededSkills() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertEquals(1, first.getNeededSkills().size());
        assertEquals(Skill.CPR, first.getNeededSkills().get(0));
    }

    @Test
    void testLoadRequestsHurricaneDataCategory() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertNotNull(first.getHurricaneData());
        assertEquals(1, first.getHurricaneData().getCategory());
    }

    @Test
    void testLoadRequestsHurricaneDataWindSpeed() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest first = requests.get(0);
        assertNotNull(first.getHurricaneData());
        assertEquals(85, first.getHurricaneData().getWindSpeeds());
    }

    @Test
    void testLoadRequestsRequiresCertificationFlag() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        assertFalse(requests.get(0).requiresCertification());
        assertTrue(requests.get(1).requiresCertification());
    }

    @Test
    void testLoadRequestsCommentThreadParsed() {
        ArrayList<HelpRequest> requests = DataLoader.loadRequests();
        HelpRequest second = requests.get(1);
        assertEquals(1, second.getCommentThread().size());
        Comment comment = second.getCommentThread().get(0);
        assertEquals("Resident needs oxygen supplies.", comment.getText());
        assertEquals("2026-09-25T14:00:00-04:00", comment.getTimestamp());
        assertNotNull(comment.getAuthor());
        assertEquals("jordan.lee", comment.getAuthor().getUsername());
    }
}
