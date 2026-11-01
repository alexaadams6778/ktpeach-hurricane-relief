package com.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

// NOTE REQUIRED FROM BLACKBOARD AS ALLOWED: Formatted with AI
/**
 * Test cases and reasoning for UserList unit tests.
 *
 * +---------------------------------------------------+--------------------------------------------------------------------+
 * | Test                                              | Reasoning                                                          |
 * +---------------------------------------------------+--------------------------------------------------------------------+
 * | testGetInstanceNotNull                            | Verifies UserList singleton instance is created and not null       |
 * | testGetInstanceReturnsSameInstance                | Verifies multiple calls to getInstance return the identical object |
 * | testGetUsersReturnsList                           | Ensures getUsers returns the backing users list                    |
 * | testGetUserByValidUsername                        | Finds and returns user when matching username exists               |
 * | testGetUserByNonExistentUsername                  | Returns null when queried username is not in the system            |
 * | testGetUserByNullUsername                         | Returns null safely when queried username is null                  |
 * | testGetUserByEmptyUsername                        | Returns null when queried username is an empty string              |
 * | testGetUserCaseSensitivity                        | Validates that username lookup is case sensitive                   |
 * | testGetUserRetrievesCorrectUserFromMultiple       | Ensures correct user is found when multiple users are stored       |
 * | testGetUserWithValidCredentials                   | Verifies successful authentication with correct username & password|
 * | testGetUserWithIncorrectPassword                  | Rejects authentication when password does not match                |
 * | testGetUserWithIncorrectUsername                  | Rejects authentication when username does not match                |
 * | testGetUserWithBothCredentialsIncorrect           | Rejects authentication when both username and password are wrong   |
 * | testGetUserWithNullUsernameCredential             | Safely returns null when null username is passed to authentication |
 * | testGetUserWithNullPasswordCredential             | Safely returns null when null password is passed to authentication |
 * | testGetUserWithEmptyCredentials                   | Rejects authentication when empty strings are passed               |
 * | testGetUserByValidUUID                            | Finds and returns user when matching UUID exists                   |
 * | testGetUserByNonExistentUUID                      | Returns null when queried UUID is not in the system                |
 * | testGetUserByNullUUID                             | Safely returns null when queried UUID is null                      |
 * | testAddValidVictim                                | Successfully adds a new Victim user to the user list               |
 * | testAddValidVolunteer                             | Successfully adds a new Volunteer user to the user list            |
 * | testAddValidAdmin                                 | Successfully adds a new SystemAdmin user to the user list          |
 * | testAddValidShelterManager                        | Successfully adds a new ShelterManager user to the user list       |
 * | testAddNullUserReturnsFalse                       | Rejects adding a null user object and returns false                |
 * | testAddDuplicateUsernameReturnsFalse              | Rejects adding a user with an already registered username          |
 * | testAddUserPreservesAttributes                    | Ensures user attributes are unchanged after being added to list    |
 * | testCreateAccountVictim                           | Creates and stores a Victim account with correct attributes        |
 * | testCreateAccountVolunteer                        | Creates and stores a Volunteer account with default vehicle false  |
 * | testCreateAccountAdmin                            | Creates and stores a SystemAdmin account with correct attributes   |
 * | testCreateAccountShelterManager                   | Creates and stores a ShelterManager account with null shelter      |
 * | testCreateAccountUnsupportedTypeReturnsNull       | Returns null when an unknown account type string is provided       |
 * | testCreateAccountCaseInsensitiveType              | Accepts account type case-insensitively (e.g., lowercase "victim") |
 * | testCreateAccountDuplicateUsernameReturnsNull     | Rejects account creation when requested username is already taken  |
 * | testCreateAccountTriggersSave                     | Verifies DataWriter.saveUsers is invoked when account is created   |
 * | testSaveCallsDataWriter                           | Verifies save method triggers DataWriter.saveUsers                 |
 * | testCreateAccountWithNullTypeThrowsNPE            | Documents bug: null account type throws NullPointerException       |
 * | testCreateAccountAllowsEmptyUsernameBug           | Documents bug: empty username is allowed without validation        |
 * | testCreateAccountAllowsShortUsernameBug           | Documents bug: username shorter than 6 characters is accepted      |
 * | testCreateAccountAllowsEmailWithoutAtSignBug      | Documents bug: email address missing '@' is accepted               |
 * | testCreateAccountAllowsEmailWithoutDomainBug      | Documents bug: email address missing domain is accepted            |
 * +---------------------------------------------------+--------------------------------------------------------------------+
 */
public class UserListTest {

    private UserList userList;
    private ArrayList<User> originalUsers;
    private MockedStatic<DataWriter> mockedDataWriter;
    private Date testDob;
    private Location testLocation;
    private Victim testVictim;
    private Volunteer testVolunteer;
    private SystemAdmin testAdmin;
    private ShelterManager testManager;

    @BeforeEach
    public void setup() throws Exception {
        mockedDataWriter = mockStatic(DataWriter.class);

        userList = UserList.getInstance();
        originalUsers = new ArrayList<User>(userList.getUsers());
        userList.getUsers().clear();

        testDob = new SimpleDateFormat("yyyy-MM-dd").parse("1995-03-20");
        testLocation = new Location("100 Main Street", "Charleston", "SC", "29401");

        testVictim = new Victim(
                UUID.randomUUID(), "Victim", "Alice", "Smith",
                "alicesmith", "secret123", "alice@example.com", "843-555-1001",
                testDob, testLocation, new ArrayList<EmergencyContact>(), new ArrayList<String>());

        testVolunteer = new Volunteer(
                UUID.randomUUID(), "Volunteer", "Bob", "Jones",
                "bobjones", "secret456", "bob@example.com", "843-555-1002",
                testDob, testLocation, new ArrayList<EmergencyContact>(), new ArrayList<String>(), true);

        testAdmin = new SystemAdmin(
                UUID.randomUUID(), "Admin", "Carol", "White",
                "carolwhite", "secret789", "carol@example.com", "843-555-1003",
                testDob, testLocation, new ArrayList<EmergencyContact>(), new ArrayList<String>());

        testManager = new ShelterManager(
                UUID.randomUUID(), "ShelterManager", "Dan", "Brown",
                "danbrown", "secret321", "dan@example.com", "843-555-1004",
                testDob, testLocation, new ArrayList<EmergencyContact>(), new ArrayList<String>(), null);
    }

    @AfterEach
    public void tearDown() {
        if (mockedDataWriter != null) {
            mockedDataWriter.close();
        }

        userList.getUsers().clear();
        userList.getUsers().addAll(originalUsers);
    }

    // ==========================================
    // Singleton Tests
    // ==========================================

    @Test
    void testGetInstanceNotNull() {
        assertNotNull(userList);
    }

    @Test
    void testGetInstanceReturnsSameInstance() {
        UserList secondInstance = UserList.getInstance();
        assertSame(userList, secondInstance);
    }

    @Test
    void testGetUsersReturnsList() {
        assertNotNull(userList.getUsers());
        assertTrue(userList.getUsers().isEmpty());
    }

    // ==========================================
    // Tests for getUser(String username)
    // ==========================================

    @Test
    void testGetUserByValidUsername() {
        userList.addUser(testVictim);
        User found = userList.getUser("alicesmith");
        assertNotNull(found);
        assertSame(testVictim, found);
    }

    @Test
    void testGetUserByNonExistentUsername() {
        userList.addUser(testVictim);
        User found = userList.getUser("nonexistent_user");
        assertNull(found);
    }

    @Test
    void testGetUserByNullUsername() {
        userList.addUser(testVictim);
        User found = userList.getUser((String) null);
        assertNull(found);
    }

    @Test
    void testGetUserByEmptyUsername() {
        userList.addUser(testVictim);
        User found = userList.getUser("");
        assertNull(found);
    }

    @Test
    void testGetUserCaseSensitivity() {
        userList.addUser(testVictim);
        User found = userList.getUser("ALICESMITH");
        assertNull(found);
    }

    @Test
    void testGetUserRetrievesCorrectUserFromMultiple() {
        userList.addUser(testVictim);
        userList.addUser(testVolunteer);
        userList.addUser(testAdmin);

        User found = userList.getUser("bobjones");
        assertNotNull(found);
        assertSame(testVolunteer, found);
        assertEquals("Bob", found.getFirstName());
    }

    // ==========================================
    // Tests for getUser(String username, String password)
    // ==========================================

    @Test
    void testGetUserWithValidCredentials() {
        userList.addUser(testVictim);
        User authenticated = userList.getUser("alicesmith", "secret123");
        assertNotNull(authenticated);
        assertSame(testVictim, authenticated);
    }

    @Test
    void testGetUserWithIncorrectPassword() {
        userList.addUser(testVictim);
        User authenticated = userList.getUser("alicesmith", "wrongpassword");
        assertNull(authenticated);
    }

    @Test
    void testGetUserWithIncorrectUsername() {
        userList.addUser(testVictim);
        User authenticated = userList.getUser("wronguser", "secret123");
        assertNull(authenticated);
    }

    @Test
    void testGetUserWithBothCredentialsIncorrect() {
        userList.addUser(testVictim);
        User authenticated = userList.getUser("wronguser", "wrongpassword");
        assertNull(authenticated);
    }

    @Test
    void testGetUserWithNullUsernameCredential() {
        userList.addUser(testVictim);
        User authenticated = userList.getUser(null, "secret123");
        assertNull(authenticated);
    }

    @Test
    void testGetUserWithNullPasswordCredential() {
        userList.addUser(testVictim);
        User authenticated = userList.getUser("alicesmith", null);
        assertNull(authenticated);
    }

    @Test
    void testGetUserWithEmptyCredentials() {
        userList.addUser(testVictim);
        User authenticated = userList.getUser("", "");
        assertNull(authenticated);
    }

    // ==========================================
    // Tests for getUser(UUID userID)
    // ==========================================

    @Test
    void testGetUserByValidUUID() {
        userList.addUser(testVictim);
        User found = userList.getUser(testVictim.getUserID());
        assertNotNull(found);
        assertSame(testVictim, found);
    }

    @Test
    void testGetUserByNonExistentUUID() {
        userList.addUser(testVictim);
        User found = userList.getUser(UUID.randomUUID());
        assertNull(found);
    }

    @Test
    void testGetUserByNullUUID() {
        userList.addUser(testVictim);
        User found = userList.getUser((UUID) null);
        assertNull(found);
    }

    // ==========================================
    // Tests for addUser(User user)
    // ==========================================

    @Test
    void testAddValidVictim() {
        boolean added = userList.addUser(testVictim);
        assertTrue(added);
        assertEquals(1, userList.getUsers().size());
        assertSame(testVictim, userList.getUsers().get(0));
    }

    @Test
    void testAddValidVolunteer() {
        boolean added = userList.addUser(testVolunteer);
        assertTrue(added);
        assertEquals(1, userList.getUsers().size());
        assertSame(testVolunteer, userList.getUsers().get(0));
    }

    @Test
    void testAddValidAdmin() {
        boolean added = userList.addUser(testAdmin);
        assertTrue(added);
        assertEquals(1, userList.getUsers().size());
        assertSame(testAdmin, userList.getUsers().get(0));
    }

    @Test
    void testAddValidShelterManager() {
        boolean added = userList.addUser(testManager);
        assertTrue(added);
        assertEquals(1, userList.getUsers().size());
        assertSame(testManager, userList.getUsers().get(0));
    }

    @Test
    void testAddNullUserReturnsFalse() {
        boolean added = userList.addUser(null);
        assertFalse(added);
        assertTrue(userList.getUsers().isEmpty());
    }

    @Test
    void testAddDuplicateUsernameReturnsFalse() {
        userList.addUser(testVictim);
        Victim duplicate = new Victim(
                UUID.randomUUID(), "Victim", "Different", "Person",
                "alicesmith", "otherpass", "other@example.com", "843-555-9999",
                testDob, testLocation, new ArrayList<EmergencyContact>(), new ArrayList<String>());

        boolean added = userList.addUser(duplicate);
        assertFalse(added);
        assertEquals(1, userList.getUsers().size());
    }

    @Test
    void testAddUserPreservesAttributes() {
        userList.addUser(testVictim);
        User retrieved = userList.getUser("alicesmith");
        assertEquals("Alice", retrieved.getFirstName());
        assertEquals("Smith", retrieved.getLastName());
        assertEquals("alicesmith", retrieved.getUsername());
        assertEquals("secret123", retrieved.getPassword());
        assertEquals("alice@example.com", retrieved.getEmail());
        assertEquals("843-555-1001", retrieved.getPhoneNumber());
        assertEquals(testDob, retrieved.getDob());
        assertEquals(testLocation, retrieved.getLocation());
    }

    // ==========================================
    // Tests for createAccount(...)
    // ==========================================

    @Test
    void testCreateAccountVictim() {
        User created = userList.createAccount(
                "Victim", "Emma", "Stone", "emmastone", "pass123",
                "emma@example.com", "843-555-2001", testDob, testLocation);

        assertNotNull(created);
        assertTrue(created instanceof Victim);
        assertEquals("emmastone", created.getUsername());
        assertEquals(1, userList.getUsers().size());
        mockedDataWriter.verify(DataWriter::saveUsers);
    }

    @Test
    void testCreateAccountVolunteer() {
        User created = userList.createAccount(
                "Volunteer", "Liam", "Neeson", "liamneeson", "pass123",
                "liam@example.com", "843-555-2002", testDob, testLocation);

        assertNotNull(created);
        assertTrue(created instanceof Volunteer);
        Volunteer vol = (Volunteer) created;
        assertFalse(vol.hasVehicle());
        assertTrue(vol.getSkills().isEmpty());
    }

    @Test
    void testCreateAccountAdmin() {
        User created = userList.createAccount(
                "Admin", "Grace", "Hopper", "gracehopper", "pass123",
                "grace@example.com", "843-555-2003", testDob, testLocation);

        assertNotNull(created);
        assertTrue(created instanceof SystemAdmin);
        assertEquals("gracehopper", created.getUsername());
    }

    @Test
    void testCreateAccountShelterManager() {
        User created = userList.createAccount(
                "ShelterManager", "Henry", "Ford", "henryford", "pass123",
                "henry@example.com", "843-555-2004", testDob, testLocation);

        assertNotNull(created);
        assertTrue(created instanceof ShelterManager);
        ShelterManager sm = (ShelterManager) created;
        assertNull(sm.getAssignedShelter());
    }

    @Test
    void testCreateAccountUnsupportedTypeReturnsNull() {
        User created = userList.createAccount(
                "Guest", "Unknown", "User", "unknownuser", "pass123",
                "unknown@example.com", "843-555-2005", testDob, testLocation);

        assertNull(created);
        assertTrue(userList.getUsers().isEmpty());
    }

    @Test
    void testCreateAccountCaseInsensitiveType() {
        User created = userList.createAccount(
                "victim", "Lowercase", "Type", "lowercasetype", "pass123",
                "lower@example.com", "843-555-2006", testDob, testLocation);

        assertNotNull(created);
        assertTrue(created instanceof Victim);
    }

    @Test
    void testCreateAccountDuplicateUsernameReturnsNull() {
        userList.addUser(testVictim);
        User created = userList.createAccount(
                "Victim", "Another", "Alice", "alicesmith", "pass123",
                "alice2@example.com", "843-555-2007", testDob, testLocation);

        assertNull(created);
        assertEquals(1, userList.getUsers().size());
    }

    @Test
    void testCreateAccountTriggersSave() {
        userList.createAccount(
                "Victim", "Save", "Test", "savetest", "pass123",
                "save@example.com", "843-555-2008", testDob, testLocation);

        mockedDataWriter.verify(DataWriter::saveUsers);
    }

    @Test
    void testSaveCallsDataWriter() {
        userList.save();
        mockedDataWriter.verify(DataWriter::saveUsers);
    }

    // ==========================================
    // Bug-exposing and boundary error tests
    // ==========================================

    @Test
    void testCreateAccountWithNullTypeThrowsNPE() {
        assertThrows(NullPointerException.class, () -> {
            userList.createAccount(
                    null, "Null", "Type", "nulltypeuser", "pass123",
                    "nulltype@example.com", "843-555-3001", testDob, testLocation);
        });
    }

    @Test
    void testCreateAccountAllowsEmptyUsernameBug() {
        User user = userList.createAccount(
                "Victim", "Empty", "User", "", "pass123",
                "empty@example.com", "843-555-3002", testDob, testLocation);

        assertNotNull(user);
        assertEquals("", user.getUsername());
    }

    @Test
    void testCreateAccountAllowsShortUsernameBug() {
        User user = userList.createAccount(
                "Victim", "Short", "User", "abc", "pass123",
                "short@example.com", "843-555-3003", testDob, testLocation);

        assertNotNull(user);
        assertEquals("abc", user.getUsername());
    }

    @Test
    void testCreateAccountAllowsEmailWithoutAtSignBug() {
        User user = userList.createAccount(
                "Victim", "NoAt", "User", "noatuser", "pass123",
                "noatsignemail", "843-555-3004", testDob, testLocation);

        assertNotNull(user);
        assertEquals("noatsignemail", user.getEmail());
    }

    @Test
    void testCreateAccountAllowsEmailWithoutDomainBug() {
        User user = userList.createAccount(
                "Victim", "NoDomain", "User", "nodomainuser", "pass123",
                "user@", "843-555-3005", testDob, testLocation);

        assertNotNull(user);
        assertEquals("user@", user.getEmail());
    }
}
