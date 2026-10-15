/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class ChatAPP {
        public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        /*Oracle, 2026. Pattern (Java SE 24 & JDK 24). [Online]. Available at: https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/util/regex/Pattern.html [Accessed 17 September 2026]. 
        */
   /*Satzinger, J.W., Jackson, R.B. and Burd, S.D., 2016. Systems analysis and design in a changing world. 7th ed. Boston, MA: Cengage Learning.
        
        */
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
        
        // Validate individual fields and output required specific messages
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

        if (!login.checkCellPhoneNumber(login.getCellPhoneNumber())) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        } else {
            System.out.println("Cell phone number successfully added.");
        }

        // Check overall registration status
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

        if (!loginSuccessful) {
            scanner.close();
            return;
        }

        // ==========================================
        // PART 2: SENDING MESSAGES TRANSITION
        // ==========================================
        System.out.println("\nWelcome to QuickChat.");
        
        System.out.println("How many messages would you like to enter?");
        int totalMessagesToEnter = 0;
        try {
            totalMessagesToEnter = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Exiting.");
            scanner.close();
            return;
        }

        Path jsonFile = Paths.get("messages.json");

        for (int i = 0; i < totalMessagesToEnter; i++) {
            System.out.println("\n--- Enter Message " + (i + 1) + " ---");
            
            System.out.println("Enter recipient cell phone number (+27...): ");
            String recipient = scanner.nextLine().trim();
            
            Message tempMsgForValidation = new Message(recipient, "temp", i);
            if (!tempMsgForValidation.checkRecipientCell()) {
                System.out.println("Cell phone number incorrectly formatted or does not contain international code. Please retry this message.");
                i--; // Repeat this loop iteration
                continue;
            }

            System.out.println("Enter message text (max 250 characters): ");
            String messageText = scanner.nextLine().trim();
            
            Message message = new Message(recipient, messageText, i);
            if (!message.checkMessageLength()) {
                System.out.println("Message exceeds 250 characters or is blank. Please retry.");
                i--; // Repeat this loop iteration
                continue;
            }

            System.out.println("\n" + message.printMessage());
            
            boolean validChoice = false;
            while (!validChoice) {
                System.out.println("\nChoose an option:");
                System.out.println("1) Send Messages (or type 'send')");
                System.out.println("2) Show recently sent messages - Coming Soon (or type 'disregard')");
                System.out.println("3) Store message in JSON (or type 'store')");
                System.out.println("Enter your choice: ");
                String choice = scanner.nextLine().trim();

                try {
                    String result = message.sendMessage(choice, jsonFile);
                    System.out.println(result);
                    validChoice = true;
                } catch (IOException e) {
                    System.out.println("An error occurred while processing your choice: " + e.getMessage());
                }
            }
        }

        System.out.println("\nTotal messages processed successfully: " + Message.returnTotalMessages());
        System.out.println("Exiting QuickChat. Goodbye!");
        scanner.close();
    }
}
