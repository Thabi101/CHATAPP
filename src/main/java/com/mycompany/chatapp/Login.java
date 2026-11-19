/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import javax.lang.model.SourceVersion;

/**
 * Handles user registration validation and login authentication for QuickChat (Part 1).
 */
public class Login {
    private static final int MAX_USERNAME_LENGTH = 5;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String SA_PHONE_REGEX = "^\\+27[0-9]{9}$";

    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private String password = "";
    private String cellPhoneNumber = "";

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? "" : firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? "" : lastName.trim();
    }

    public void setUsername(String username) {
        this.username = username == null ? "" : username.trim();
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber == null ? "" : cellPhoneNumber.trim();
    }

    /**
     * Validates that the username contains an underscore and is at most 5 characters long.
     *
     * @return true if username format is valid
     */
    public boolean checkUserName() {
        return username != null
                && username.contains("_")
                && username.length() <= MAX_USERNAME_LENGTH;
    }

    /**
     * Validates password complexity: at least 8 characters, one uppercase letter,
     * one digit, and one special character.
     *
     * @return true if password meets all complexity requirements
     */
    public boolean checkPasswordComplexity() {
        if (password == null) {
            return false;
        }
        return password.length() >= MIN_PASSWORD_LENGTH
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^A-Za-z0-9].*");
    }

    /**
     * Validates South African international cell number format (+27 followed by 9 digits).
     *
     * @param number cell phone number to validate
     * @return true if number matches +27XXXXXXXXX
     */
    public boolean checkCellPhoneNumber(String number) {
        return number != null && number.matches(SA_PHONE_REGEX);
    }

    /**
     * Validates the stored cell phone number.
     *
     * @return true if stored cell phone number is valid
     */
    public boolean checkCellPhoneNumber() {
        return checkCellPhoneNumber(cellPhoneNumber);
    }

    /**
     * Validates registration details and returns the required status message.
     *
     * @return validation feedback or registration success message
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "Registration successful.";
    }

    /**
     * Verifies entered credentials against the registered user details.
     *
     * @param enteredUsername username supplied at login
     * @param enteredPassword password supplied at login
     * @return true if registration fields are valid and credentials match
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return checkUserName()
                && checkPasswordComplexity()
                && checkCellPhoneNumber()
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    /**
     * Formats the login status response message.
     *
     * @param loginSuccessful result of {@link #loginUser(String, String)}
     * @return welcome message on success, or error prompt on failure
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getCellPhoneNumber() { return cellPhoneNumber; }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}

/**
 * Handles user registration validation and login authentication for QuickChat (Part 1).
 */
public class Login {
    private static final int MAX_USERNAME_LENGTH = 5;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final String SA_PHONE_REGEX = "^\\+27[0-9]{9}$";

    private String firstName = "";
    private String lastName = "";
    private String username = "";
    private String password = "";
    private String cellPhoneNumber = "";

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? "" : firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? "" : lastName.trim();
    }

    public void setUsername(String username) {
        this.username = username == null ? "" : username.trim();
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber == null ? "" : cellPhoneNumber.trim();
    }

    /**
     * Validates that the username contains an underscore and is at most 5 characters long.
     *
     * @return true if username format is valid
     */
    public boolean checkUserName() {
        return username != null
                && username.contains("_")
                && username.length() <= MAX_USERNAME_LENGTH;
    }

    /**
     * Validates password complexity: at least 8 characters, one uppercase letter,
     * one digit, and one special character.
     *
     * @return true if password meets all complexity requirements
     */
    public boolean checkPasswordComplexity() {
        if (password == null) {
            return false;
        }
        return password.length() >= MIN_PASSWORD_LENGTH
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^A-Za-z0-9].*");
    }

    /**
     * Validates South African international cell number format (+27 followed by 9 digits).
     *
     * @param number cell phone number to validate
     * @return true if number matches +27XXXXXXXXX
     */
    public boolean checkCellPhoneNumber(String number) {
        return number != null && number.matches(SA_PHONE_REGEX);
    }

    /**
     * Validates the stored cell phone number.
     *
     * @return true if stored cell phone number is valid
     */
    public boolean checkCellPhoneNumber() {
        return checkCellPhoneNumber(cellPhoneNumber);
    }

    /**
     * Validates registration details and returns the required status message.
     *
     * @return validation feedback or registration success message
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "Registration successful.";
    }

    /**
     * Verifies entered credentials against the registered user details.
     *
     * @param enteredUsername username supplied at login
     * @param enteredPassword password supplied at login
     * @return true if registration fields are valid and credentials match
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return checkUserName()
                && checkPasswordComplexity()
                && checkCellPhoneNumber()
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    /**
     * Formats the login status response message.
     *
     * @param loginSuccessful result of {@link #loginUser(String, String)}
     * @return welcome message on success, or error prompt on failure
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getCellPhoneNumber() { return cellPhoneNumber; }
}