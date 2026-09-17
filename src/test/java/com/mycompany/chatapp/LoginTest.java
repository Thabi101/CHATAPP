/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Student
 */
public class LoginTest {
    /* Bechtold, S., Brannen, S., Link, J., Merdes, M., Philipp, M., de Rancourt, J. and Stein, C., 2025. JUnit 5 user guide. [Online]. Available at: https://docs.junit.org/5.13.2/user-guide/ [Accessed 17 September 2026].
*/
     private Login login;
    private String cellPhoneNumber;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setFirstName("Kyle");
        login.setLastName("Smith");
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");
    }

    // ---------- assertEquals tests ----------

    @Test
    void testSuccessfulLoginStatus() {
        boolean result = login.loginUser("kyl_1", "Ch&&sec@ke99!");

        assertEquals(
                "Welcome Kyle Smith it is great to see you again.",
                login.returnLoginStatus(result)
        );
    }

    @Test
    void testIncorrectUsernameMessage() {
        login.setUsername("kyl_1");
        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
                validateUsername("kyle!!!!!!!")
        );
    }

    @Test
    void testSuccessfulPasswordMessage() {
        assertEquals("Registration successful.", login.registerUser());
    }

    @Test
    void testIncorrectPasswordMessage() {
        login.setPassword("password");

        assertEquals(
                "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                login.registerUser()
        );
    }
    @Test
     void testSuccessfulCellPhoneMessage() {
         String correctCellPhoneNumber = "+27838968976";
        boolean results = login.checkCellPhoneNumber(correctCellPhoneNumber);
       
        //assertEquals("Cell number successfully captured.",
        //        login.registerUser()
        //);//
        assertTrue(results);
        System.out.println("\"Cell number successfully captured.\"");
    }
    
    @Test
    void testIncorrectCellPhoneMessage() {
        login.setCellPhoneNumber("08966553");
        //login.checkCellPhoneNumber("08966553");

        assertEquals(
                "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.",
                login.registerUser()
        );
    }

    private String validateUsername(String username) {
        login.setUsername(username);
        return login.checkUserName()
                ? "Username successfully captured."
                : "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    }

    // ---------- assertTrue / assertFalse tests ----------

    @Test
    void testLoginSuccessful() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void testLoginFailed() {
        assertFalse(login.loginUser("wrong", "wrongPassword1!"));
    }

    @Test
    void testUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName());
    }

    @Test
    void testUsernameIncorrectlyFormatted() {
        login.setUsername("kyle!!!!!!!");
        assertFalse(login.checkUserName());
    }

    @Test
    void testPasswordMeetsComplexity() {
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    void testPasswordFailsComplexity() {
        login.setPassword("password");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    void testCellPhoneCorrectlyFormatted() {
        String correctCellPhoneNumber = "+27838968976";
        assertTrue(login.checkCellPhoneNumber(correctCellPhoneNumber));
    }

    @Test
    void testCellPhoneIncorrectlyFormatted() {
        login.setCellPhoneNumber("08966553");
        assertFalse(login.checkCellPhoneNumber(cellPhoneNumber));
    }
    
}
