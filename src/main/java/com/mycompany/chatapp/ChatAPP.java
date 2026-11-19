/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * Main console application for PROG5121 QuickChat Parts 1, 2 and 3.
 */
/*
/**
 * Reads stored messages from a JSON file into a dynamic List using Google Gson.
 *
 * Reference (IIE Harvard Anglia):
 * Google, 2024. Gson User Guide: Serializing and Deserializing Collection with TypeToken.
 * [online] Available at: <https://github.com/google/gson/blob/main/UserGuide.md>
 * [Accessed 19 November 2026].
 */
public class ChatAPP {
    private static final Path JSON_FILE = Path.of("messages.json");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Login login = registerAndLogin(scanner);
            if (login == null) {
                return;
            }

            System.out.println();
            System.out.println("Welcome to QuickChat.");

            MessageManager manager = new MessageManager(JSON_FILE);
            runMainMenu(scanner, manager, login);
        } catch (IOException ex) {
            System.out.println("A file error occurred: " + ex.getMessage());
        }
    }

    private static Login registerAndLogin(Scanner scanner) {
        Login login = new Login();

        System.out.println("======================================");
        System.out.println("       PROG5121 QUICKCHAT APP         ");
        System.out.println("      REGISTRATION AND LOGIN          ");
        System.out.println("======================================");

        System.out.println("\n--- Registration ---");
        System.out.print("Enter your first name: ");
        login.setFirstName(scanner.nextLine());
        System.out.print("Enter your last name: ");
        login.setLastName(scanner.nextLine());

        while (true) {
            System.out.print("Enter a username (underscore, max 5 characters): ");
            login.setUsername(scanner.nextLine());
            if (login.checkUserName()) {
                System.out.println("Username successfully captured.");
                break;
            }
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.");
        }

        while (true) {
            System.out.print("Enter a password: ");
            login.setPassword(scanner.nextLine());
            if (login.checkPasswordComplexity()) {
                System.out.println("Password successfully captured.");
                break;
            }
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
        }

        while (true) {
            System.out.print("Enter South African cell number (+27...): ");
            login.setCellPhoneNumber(scanner.nextLine());
            if (login.checkCellPhoneNumber()) {
                System.out.println("Cell phone number successfully added.");
                break;
            }
            System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
        }

        System.out.println("Registration successful.");

        System.out.println("\n--- Login ---");
        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            boolean success = login.loginUser(username, password);
            System.out.println(login.returnLoginStatus(success));
            if (success) {
                return login;
            }
        }
    }

    private static void runMainMenu(Scanner scanner, MessageManager manager, Login login) throws IOException {
        boolean running = true;
        while (running) {
            System.out.println("\n========== QUICKCHAT MENU ==========");
            System.out.println("1. Send Messages");
            System.out.println("2. Display Recently Sent Messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages & Reports");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> sendMessages(scanner, manager, login);
                case "2" -> displaySentMessages(manager);
                case "3" -> {
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    System.out.println("Thank you for using QuickChat.");
                    running = false;
                }
                case "4" -> reportsMenu(scanner, manager);
                default -> System.out.println("Invalid option. Please choose 1, 2, 3 or 4.");
            }
        }
    }

    private static void sendMessages(Scanner scanner, MessageManager manager, Login login) throws IOException {
        System.out.print("How many messages would you like to process? ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Please enter a valid whole number.");
            return;
        }
        if (count <= 0) {
            System.out.println("The number of messages must be greater than zero.");
            return;
        }

        String senderFullName = login.getFirstName() + " " + login.getLastName();

        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + count + " ---");
            System.out.print("Enter recipient number (+27 followed by nine digits): ");
            String recipient = scanner.nextLine().trim();
            System.out.print("Enter your message (maximum 250 characters): ");
            String text = scanner.nextLine();

            int nextNum = manager.getNextMessageNumber();
            Message message = new Message(recipient, text, nextNum);
            message.setSender(senderFullName);

            if (!message.checkMessageID()) {
                System.out.println("Message ID could not be generated. Please try again.");
                i--;
                continue;
            }
            if (!message.checkRecipientCell()) {
                System.out.println("Invalid recipient number. Use +27 followed by nine digits.");
                i--;
                continue;
            }
            if (!message.checkMessageLength()) {
                System.out.println("Please enter a message containing 1 to 250 characters.");
                i--;
                continue;
            }

            System.out.println("\n" + message.printMessage());
            System.out.println("\n1. Send");
            System.out.println("2. Disregard");
            System.out.println("3. Store in JSON");
            System.out.print("Choose an action: ");
            String action = scanner.nextLine();
            System.out.println(manager.processMessage(message, action));
            if ("Sent".equals(message.getStatus())) {
                System.out.println(message.printMessage());
            }
        }
    }

    private static void displaySentMessages(MessageManager manager) {
        List<Message> sent = manager.getSentMessages();
        if (sent.isEmpty()) {
            System.out.println("No sent messages are available in this session.");
            return;
        }
        for (Message message : sent) {
            System.out.println(message.printMessage());
            System.out.println("--------------------");
        }
    }

    private static void reportsMenu(Scanner scanner, MessageManager manager) throws IOException {
        boolean inReports = true;
        while (inReports) {
            System.out.println("\n======= STORED MESSAGES & REPORTS =======");
            System.out.println("1. Display sender and recipient of all sent/stored messages");
            System.out.println("2. Display the longest message");
            System.out.println("3. Search message by ID");
            System.out.println("4. Search sent or stored messages by recipient");
            System.out.println("5. Delete a message using its hash");
            System.out.println("6. Display sent messages report");
            System.out.println("7. Display all stored JSON messages");
            System.out.println("8. Search messages by keyword");
            System.out.println("9. View message statistics summary");
            System.out.println("0. Return to main menu");
            System.out.print("Choose an option: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> System.out.println(manager.displaySendersAndRecipients());
                case "2" -> {
                    Message longest = manager.findLongestMessage();
                    System.out.println(longest == null
                            ? "No messages available."
                            : "Longest message: " + longest.getMessageText());
                }
                case "3" -> {
                    System.out.print("Enter message ID: ");
                    Message found = manager.searchByMessageID(scanner.nextLine().trim());
                    if (found == null) {
                        System.out.println("Message not found.");
                    } else {
                        System.out.println("Recipient: " + found.getRecipient());
                        System.out.println("Message: " + found.getMessageText());
                    }
                }
                case "4" -> {
                    System.out.print("Enter recipient number: ");
                    List<Message> matches = manager.searchByRecipient(scanner.nextLine().trim());
                    if (matches.isEmpty()) {
                        System.out.println("No sent or stored messages found for that recipient.");
                    } else {
                        for (Message message : matches) {
                            System.out.println(message.getMessageText());
                            System.out.println("--------------------");
                        }
                    }
                }
                case "5" -> {
                    System.out.print("Enter the message hash to delete: ");
                    System.out.println(manager.deleteByHash(scanner.nextLine().trim()));
                }
                case "6" -> System.out.println(manager.fullReport());
                case "7" -> System.out.println(manager.displayStoredMessages());
                case "8" -> {
                    System.out.print("Enter keyword to search: ");
                    List<Message> matches = manager.searchByKeyword(scanner.nextLine());
                    if (matches.isEmpty()) {
                        System.out.println("No matching messages found.");
                    } else {
                        for (Message message : matches) {
                            System.out.println(message.printMessage());
                            System.out.println("--------------------");
                        }
                    }
                }
                case "9" -> System.out.println(manager.getStatisticsSummary());
                case "0" -> inReports = false;
                default -> System.out.println("Invalid option. Choose 0 to 9.");
            }
        }
    }
}