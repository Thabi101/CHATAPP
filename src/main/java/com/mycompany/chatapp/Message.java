package com.mycompany.chatapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Represents one chat message and provides validation, hash, display and JSON storage. */
public final class Message {
    private static int totalMessages = 0;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MESSAGE_LIST_TYPE = new TypeToken<List<Message>>() {}.getType();

    private final String messageID;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private String status;

    public Message(String recipient, String messageText, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipient = recipient == null ? "" : recipient.trim();
        this.messageText = messageText == null ? "" : messageText.trim();
        this.messageHash = createMessageHash(messageNumber);
        this.status = "Created";
    }

    private static String generateMessageID() {
        // Generate a ten-digit numeric ID; first digit is never zero.
        return Long.toString(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));
    }

    public boolean checkMessageID() { return messageID != null && messageID.matches("[0-9]{1,10}"); }

    public boolean checkRecipientCell() {
        // Part 1's South African format is +27 followed by nine digits.
        return recipient.matches("^\\+27[0-9]{9}$");
    }

    public boolean checkMessageLength() { return !messageText.isBlank() && messageText.length() <= 250; }

    public String createMessageHash(int messageNumber) {
        String[] words = messageText.trim().split("\\s+");
        String first = words.length == 0 || words[0].isBlank() ? "" : words[0];
        String last = words.length == 0 || words[words.length - 1].isBlank() ? "" : words[words.length - 1];
        String idPrefix = messageID == null ? "" : messageID.substring(0, Math.min(2, messageID.length()));
        return (idPrefix + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    /** Marks a message as sent, disregarded, or stored. Only sent messages increase the total.
     * @param choice
     * @param jsonFile
     * @return 
     * @throws java.io.IOException */
   /** Marks a message as sent, disregarded, or stored. Only sent messages increase the total.
     * @param choice
     * @param jsonFile
     * @return 
     * @throws java.io.IOException */
    public String sendMessage(String choice, Path jsonFile) throws IOException {
        if (choice == null) return "Invalid choice. Select send, disregard, or store.";
        
        String formattedChoice = choice.trim().toLowerCase();
        
        switch (formattedChoice) {
            case "send", "1", "sent" -> {
                status = "Sent";
                totalMessages++;
                return "Message sent.";
            }
            case "disregard", "2", "discard" -> {
                status = "Disregarded";
                return "Message disregarded.";
            }
            case "store", "3" -> {
                status = "Stored";
                storeMessage(jsonFile);
                return "Message successfully stored in JSON.";
            }
                
            default -> {
                return "Invalid choice. Select send, disregard, or store.";
            }
        }
    }

    public String printMessage() {
        return "Message ID: " + messageID + System.lineSeparator()
                + "Message Hash: " + messageHash + System.lineSeparator()
                + "Recipient: " + recipient + System.lineSeparator()
                + "Message: " + messageText + System.lineSeparator()
                + "Status: " + status;
    }

    public static int returnTotalMessages() { return totalMessages; }

    /** Appends this message to a JSON array, preserving previously stored messages.
     * @param jsonFile
     * @throws java.io.IOException */
    public void storeMessage(Path jsonFile) throws IOException {
        List<Message> messages = new ArrayList<>();
        if (Files.exists(jsonFile) && Files.size(jsonFile) > 0) {
            String existing = Files.readString(jsonFile, StandardCharsets.UTF_8);
            List<Message> loaded = GSON.fromJson(existing, MESSAGE_LIST_TYPE);
            if (loaded != null) messages.addAll(loaded);
        }
        messages.add(this);
        Path parent = jsonFile.toAbsolutePath().getParent();
        if (parent != null) Files.createDirectories(parent);
        Files.writeString(jsonFile, GSON.toJson(messages, MESSAGE_LIST_TYPE), StandardCharsets.UTF_8);
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
}
