package com.mycompany.chatapp;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Part 3 message-management layer. Dynamically populates parallel lists for
 * sent, disregarded, and stored messages, message hashes, and message IDs.
 
 * Farrell, J., 2023. Java Programming. 10th ed. Boston: Cengage Learning
 
 * GitHub, 2024. Building and testing Java with Maven. [online] Available at: https://docs.github.com/en/actions/use-cases-and-examples/building-and-testing/building-and-testing-java-with-maven [Accessed 19 November 2026].
 */
public class MessageManager {
    private final Path jsonFile;
    private final List<Message> allMessages = new ArrayList<>();
    private final List<Message> sentMessages = new ArrayList<>();
    private final List<Message> disregardedMessages = new ArrayList<>();
    private final List<Message> storedMessages = new ArrayList<>();
    private final List<String> messageHashes = new ArrayList<>();
    private final List<String> messageIDs = new ArrayList<>();

    public MessageManager(Path jsonFile) throws IOException {
        this.jsonFile = jsonFile;
        loadStoredMessages();
    }

    private void loadStoredMessages() throws IOException {
        for (Message message : Message.readMessages(jsonFile)) {
            if (!containsId(message.getMessageID())) {
                allMessages.add(message);
                storedMessages.add(message);
                messageHashes.add(message.getMessageHash());
                messageIDs.add(message.getMessageID());
            }
        }
    }

    public String processMessage(Message message, String choice) throws IOException {
        String response = message.sendMessage(choice, jsonFile);
        if (!response.startsWith("Invalid choice")) {
            removeFromStatusLists(message);
            if (!allMessages.contains(message)) {
                allMessages.add(message);
                messageHashes.add(message.getMessageHash());
                messageIDs.add(message.getMessageID());
            }
            switch (message.getStatus()) {
                case "Sent" -> sentMessages.add(message);
                case "Disregarded" -> disregardedMessages.add(message);
                case "Stored" -> {
                    storedMessages.add(message);
                    persistStoredMessages();
                }
                default -> { }
            }
        }
        return response;
    }

    private void removeFromStatusLists(Message message) {
        sentMessages.remove(message);
        disregardedMessages.remove(message);
        storedMessages.remove(message);
    }

    private boolean containsId(String id) {
        return messageIDs.contains(id);
    }

    private void persistStoredMessages() throws IOException {
        Message.writeMessages(jsonFile, storedMessages);
    }

    public List<Message> getAllMessages() { return Collections.unmodifiableList(allMessages); }
    public List<Message> getSentMessages() { return Collections.unmodifiableList(sentMessages); }
    public List<Message> getDisregardedMessages() { return Collections.unmodifiableList(disregardedMessages); }
    public List<Message> getStoredMessages() { return Collections.unmodifiableList(storedMessages); }
    public List<String> getMessageHashes() { return Collections.unmodifiableList(messageHashes); }
    public List<String> getMessageIDs() { return Collections.unmodifiableList(messageIDs); }

    /**
     * Requirement 2a: Display the sender and recipient of all sent and stored messages.
     *
     * @return formatted list of senders and recipients
     */
    public String displaySendersAndRecipients() {
        List<Message> activeMessages = new ArrayList<>();
        activeMessages.addAll(sentMessages);
        activeMessages.addAll(storedMessages);

        if (activeMessages.isEmpty()) {
            return "No sent or stored messages available.";
        }

        StringBuilder out = new StringBuilder("Sender and Recipient Details:");
        for (Message message : activeMessages) {
            String senderName = (message.getSender() == null || message.getSender().isBlank())
                    ? "Unknown Sender" : message.getSender();
            out.append(System.lineSeparator())
                    .append("Sender: ").append(senderName)
                    .append(" | Recipient: ").append(message.getRecipient())
                    .append(" (").append(message.getStatus()).append(")");
        }
        return out.toString();
    }

    public String displayStoredMessages() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }
        StringBuilder out = new StringBuilder("Stored messages:");
        for (Message message : storedMessages) {
            out.append(System.lineSeparator()).append(message.printMessage());
            out.append(System.lineSeparator()).append("--------------------");
        }
        return out.toString();
    }

    /**
     * Requirement 2b: Find the longest message among sent and stored messages.
     *
     * @return longest Message object, or null if none exist
     */
    public Message findLongestMessage() {
        Message longest = null;
        for (Message message : allMessages) {
            if ("Disregarded".equalsIgnoreCase(message.getStatus())) {
                continue;
            }
            if (longest == null
                    || message.getMessageText().length() > longest.getMessageText().length()) {
                longest = message;
            }
        }
        return longest;
    }

    /**
     * Requirement 2c: Search for a message ID and return the matching Message.
     *
     * @param id message ID to look up
     * @return matching Message or null if not found
     */
    public Message searchByMessageID(String id) {
        for (Message message : allMessages) {
            if (message.getMessageID().equals(id)) {
                return message;
            }
        }
        return null;
    }

    /**
     * Requirement 2d: Search for all messages sent or stored for a particular recipient.
     *
     * @param recipient recipient cell number
     * @return list of matching sent or stored messages
     */
    public List<Message> searchByRecipient(String recipient) {
        List<Message> matches = new ArrayList<>();
        for (Message message : sentMessages) {
            if (message.getRecipient().equals(recipient)) {
                matches.add(message);
            }
        }
        for (Message message : storedMessages) {
            if (message.getRecipient().equals(recipient) && !matches.contains(message)) {
                matches.add(message);
            }
        }
        return matches;
    }

    /**
     * Requirement 2e: Delete a message using its message hash and return the POE confirmation string.
     *
     * @param hash message hash to delete
     * @return confirmation message if deleted, or not-found notice
     * @throws IOException if updating the JSON file fails
     */
    public String deleteByHash(String hash) throws IOException {
        for (Message message : new ArrayList<>(allMessages)) {
            if (message.getMessageHash().equalsIgnoreCase(hash)) {
                String deletedText = message.getMessageText();
                message.deleteMessage();
                allMessages.remove(message);
                removeFromStatusLists(message);

                int index = messageHashes.indexOf(message.getMessageHash());
                if (index >= 0) {
                    messageHashes.remove(index);
                }
                int idIndex = messageIDs.indexOf(message.getMessageID());
                if (idIndex >= 0) {
                    messageIDs.remove(idIndex);
                }
                persistStoredMessages();
                return "Message \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "No message found with that hash.";
    }

    /**
     * Requirement 2f: Display a report listing all sent messages with Message Hash,
     * Recipient, and Message.
     *
     * @return formatted report string
     */
    public String fullReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages are available for the report.";
        }
        StringBuilder out = new StringBuilder("QUICKCHAT SENT MESSAGES REPORT");
        for (Message message : sentMessages) {
            out.append(System.lineSeparator())
                    .append("Message Hash: ").append(message.getMessageHash())
                    .append(System.lineSeparator())
                    .append("Recipient: ").append(message.getRecipient())
                    .append(System.lineSeparator())
                    .append("Message: ").append(message.getMessageText())
                    .append(System.lineSeparator())
                    .append("--------------------");
        }
        return out.toString();
    }

    int getNextMessageNumber() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<Message> searchByKeyword(String nextLine) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean getStatisticsSummary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}