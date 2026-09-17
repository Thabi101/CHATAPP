/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import java.util.regex.Pattern;

/**
 *
 * @author Student
 */
class Login {
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    /*
     * Cell-phone regular expression research reference:
     * Oracle Java Documentation, "Regular Expressions":
     * https://docs.oracle.com/javase/tutorial/essential/regex/intro.html
     * The Pattern.matches approach is described in:
     * https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/util/regex/Pattern.html
     *
     * The task requires a South African international format. The expression
     * accepts +27 followed by exactly nine digits, matching the supplied
     * valid test value +27838968976.
     */
    private static final String SOUTH_AFRICAN_CELL_REGEX = "^\\+27[0-9]{9}$";

    /**
     * Checks whether the username contains an underscore and is no more
     * than five characters long.
     *
     * @return true when the username is valid
     */
    public boolean checkUserName() {
        return username != null
                && username.length() <= 5
                && username.contains("_");
    }

    /**
     * Checks the required password complexity.
     *
     * @return true when the password is at least eight characters and
     * contains a capital letter, number and special character
     */
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char character : password.toCharArray()) {
            if (Character.isUpperCase(character)) {
                hasCapitalLetter = true;
            } else if (Character.isDigit(character)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    /**
     * Checks the South African cell phone number using a regular expression.
     *
     * @return true when the number is in the required international format
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null
                && Pattern.matches(SOUTH_AFRICAN_CELL_REGEX, cellPhoneNumber);
    }

    /**
     * Validates the registration information and returns the required status.
     *
     * @return registration status message
     */
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }

        return "Registration successful.";
    }

    /**
     * Verifies the supplied login details against the stored registration data.
     *
     * @param enteredUsername username entered during login
     * @param enteredPassword password entered during login
     * @return true when both details match
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername != null
                && enteredPassword != null
                && enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }

    /**
     * Returns the authentication message required by the task.
     *
     * @param loginSuccessful result from loginUser()
     * @return login status message
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
}
