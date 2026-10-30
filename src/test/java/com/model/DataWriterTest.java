package com.model;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DataWriterTest {
    
    private UserList userList = UserList.getInstance();
    private ArrayList<User> users = userList.getUsers();
    private HurricaneReliefSystem hurricaneReliefSystem = HurricaneReliefSystem.getInstance();

    @BeforeEach
    public void setup() {
        users.clear();
        DataWriter.saveUsers();
    }

    @AfterEach 
    public void tearDown() {
        users.clear();
        DataWriter.saveUsers();
    }

    @Test
    void testWritingZeroUsers() {
        users = DataLoader.loadUsers();
        assertEquals(0, users.size());
    }

    @Test
    void testWritingOneUser() {
        Date dob;
        try {
            dob = new SimpleDateFormat("yyyy-MM-dd").parse("1980-05-12");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return;
        }
        Location carterAddress = new Location("1 Vendue Range", "Charleston", "SC", "29401");
        users.add(hurricaneReliefSystem.createAccount("Victim", "Frank", "Carter",
                    "frankcarter123", "ilovesophie",
                    "frankcarter@gmail.com", "843-111-1111", dob,
                    carterAddress));
		assertEquals("frankcarter123", DataLoader.loadUsers().get(0).getUsername());
    }

}
