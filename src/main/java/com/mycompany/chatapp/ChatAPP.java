/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class ChatAPP {
        public static void main(String[] args, String cellPhoneNumber) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("======================================");
        System.out.println("      PROG5121 CHAT APP - PART 1      ");
        System.out.println("       REGISTRATION AND LOGIN         ");
        System.out.println("======================================");

        System.out.println("\n--- Registration ---");

        System.out.println("Enter your first name: ");
        login.setFirstName(scanner.nextLine().trim());

        System.out.println("Enter your last name: ");
        login.setLastName(scanner.nextLine().trim());

        System.out.println("Enter a username: ");
        login.setUsername(scanner.nextLine().trim());

        System.out.println("Enter a password: ");
        login.setPassword(scanner.nextLine());

        System.out.println("Enter South African cell phone number (+27...): ");
        login.setCellPhoneNumber(scanner.nextLine().trim());

        if (!login.checkUserName()) {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        } else {
            System.out.println("Username successfully captured.");
        }

        if (!login.checkPasswordComplexity()) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        } else {
            System.out.println("Password successfully captured.");
        }

        if (!login.checkCellPhoneNumber(cellPhoneNumber)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        } else {
            System.out.println("Cell phone number successfully added.");
        }

        String registrationStatus = login.registerUser();

        if (!registrationStatus.equals("Registration successful.")) {
            System.out.println("\nRegistration failed.");
            System.out.println(registrationStatus);
            scanner.close();
            return;
        }

        System.out.println("Registration successful.");

        System.out.println("\n--- Login ---");
        System.out.println("Enter username: ");
        String enteredUsername = scanner.nextLine().trim();

        System.out.println("Enter password: ");
        String enteredPassword = scanner.nextLine();

        boolean loginSuccessful = login.loginUser(enteredUsername, enteredPassword);
        System.out.println(login.returnLoginStatus(loginSuccessful));

        scanner.close();
    }
}
