package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setFirstName("Kyle");
        login.setLastName("Smith");
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");
    }

    @Test void usernameValid() { assertTrue(login.checkUserName()); }
    @Test void usernameInvalidWithoutUnderscore() {
        login.setUsername("kyle!");
        assertFalse(login.checkUserName());
    }
    @Test void usernameInvalidWhenTooLong() {
        login.setUsername("long_name");
        assertFalse(login.checkUserName());
    }
    @Test void passwordValid() { assertTrue(login.checkPasswordComplexity()); }
    @Test void passwordInvalidWhenTooShort() {
        login.setPassword("Pass1!");
        assertFalse(login.checkPasswordComplexity());
    }
    @Test void passwordRequiresUppercase() {
        login.setPassword("password1!");
        assertFalse(login.checkPasswordComplexity());
    }
    @Test void phoneValid() { assertTrue(login.checkCellPhoneNumber("+27838968976")); }
    @Test void phoneInvalidWithoutCountryCode() { assertFalse(login.checkCellPhoneNumber("0838968976")); }
    @Test void nullPhoneInvalid() { assertFalse(login.checkCellPhoneNumber(null)); }
    @Test void registrationSuccessful() { assertEquals("Registration successful.", login.registerUser()); }
    @Test void invalidUsernameRegistrationMessage() {
        login.setUsername("kyle!!!!");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", login.registerUser());
    }
    @Test void invalidPasswordRegistrationMessage() {
        login.setPassword("password");
        assertTrue(login.registerUser().startsWith("Password is not correctly formatted"));
    }
    @Test void invalidPhoneRegistrationMessage() {
        login.setCellPhoneNumber("08966553");
        assertTrue(login.registerUser().startsWith("Cell number is incorrectly formatted"));
    }
    @Test void loginSuccessful() { assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!")); }
    @Test void loginFailsForWrongUsername() { assertFalse(login.loginUser("wrong", "Ch&&sec@ke99!")); }
    @Test void loginFailsForWrongPassword() { assertFalse(login.loginUser("kyl_1", "wrong")); }
    @Test void successfulLoginMessage() {
        assertEquals("Welcome Kyle Smith it is great to see you again.",
                login.returnLoginStatus(login.loginUser("kyl_1", "Ch&&sec@ke99!")));
    }
}
