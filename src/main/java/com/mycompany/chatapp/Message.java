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

/**
 * Represents a single QuickChat message with validation, hashing, display, and JSON storage.
 *
 * Reference (IIE Harvard Anglia):
 * Google, 2024. Gson User Guide: Serializing and Deserializing Collection with TypeToken.
 * [online] Available at: <https://github.com/google/gson/blob/main/UserGuide.md>
 * [Accessed 19 November 2026].
 */
public final class Message {
    public static final int MAX_MESSAGE_LENGTH = 250;
    private static final String MESSAGE_ID_REGEX = "[0-9]{10}";
    private static final String SA_RECIPIENT_REGEX = "^\\+27[0-9]{9}$";

    private static int totalMessages = 0;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MESSAGE_LIST_TYPE = new TypeToken<List<Message>>() { }.getType();

    private String messageID;
    private int messageNumber;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String status;
    private boolean countedAsSent;

    /** Default no-arg constructor required by Gson for JSON deserialisation. */
    public Message() {
        this("", "", 0, generateMessageID());
    }

    public Message(String recipient, String messageText, int messageNumber) {
        this(recipient, messageText, messageNumber, generateMessageID());
    }

    /**
     * Constructor with a known ID for deterministic unit testing.
     *
     * @param recipient     recipient cell number
     * @param messageText   message body (1–250 characters)
     * @param messageNumber sequential message number
     * @param messageID     10-digit unique message identifier
     */
    public Message(String recipient, String messageText, int messageNumber, String messageID) {
        this.messageID = messageID == null ? "" : messageID.trim();
        this.messageNumber = messageNumber;
        this.recipient = recipient == null ? "" : recipient.trim();
        this.messageText = messageText == null ? "" : messageText.trim();
        this.messageHash = createMessageHash(messageNumber);
        this.status = "Created";
        this.sender = "";
        this.countedAsSent = false;
    }

    private static String generateMessageID() {
        return Long.toString(ThreadLocalRandom.current()
                .nextLong(1_000_000_000L, 10_000_000_000L));
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.matches(MESSAGE_ID_REGEX);
    }

    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches(SA_RECIPIENT_REGEX);
    }

    public boolean checkMessageLength() {
        return messageText != null
                && !messageText.isBlank()
                && messageText.length() <= MAX_MESSAGE_LENGTH;
    }

    public String createMessageHash(int messageNumber) {
        if (messageID == null || messageID.length() < 2
                || messageText == null || messageText.isBlank()) {
            return "";
        }
        String[] words = messageText.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":"
                + first + last).toUpperCase();
    }

    public String sendMessage(String choice, Path jsonFile) throws IOException {
        if (choice == null) {
            return "Invalid choice. Select send, disregard, or store.";
        }

        switch (choice.trim().toLowerCase()) {
            case "send":
            case "1":
            case "sent":
                if (!countedAsSent) {
                    totalMessages++;
                    countedAsSent = true;
                }
                status = "Sent";
                return "Message sent.";

            case "disregard":
            case "2":
            case "discard":
                status = "Disregarded";
                return "Message disregarded.";

            case "store":
            case "3":
                status = "Stored";
                storeMessage(jsonFile);
                return "Message successfully stored.";

            default:
                return "Invalid choice. Select send, disregard, or store.";
        }
    }

    public String printMessage() {
        return "Message ID: " + messageID + System.lineSeparator()
                + "Message Hash: " + messageHash + System.lineSeparator()
                + "Recipient: " + recipient + System.lineSeparator()
                + "Message: " + messageText + System.lineSeparator()
                + "Status: " + status;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    /**
     * Appends this message to the JSON storage file using Google Gson.
     * Attribution: Google (2024), https://github.com/google/gson/blob/main/UserGuide.md
     */
    public void storeMessage(Path jsonFile) throws IOException {
        if (jsonFile == null) {
            throw new IllegalArgumentException("JSON file path cannot be null.");
        }
        List<Message> messages = readMessages(jsonFile);
        messages.add(this);
        writeMessages(jsonFile, messages);
    }

    /**
     * Reads stored messages from a JSON file into a dynamic List using Google Gson.
     * Attribution: Google (2024), https://github.com/google/gson/blob/main/UserGuide.md
     */
    public static List<Message> readMessages(Path jsonFile) throws IOException {
        if (jsonFile == null || !Files.exists(jsonFile) || Files.size(jsonFile) == 0) {
            return new ArrayList<>();
        }
        String json = Files.readString(jsonFile, StandardCharsets.UTF_8);
        List<Message> loaded = GSON.fromJson(json, MESSAGE_LIST_TYPE);
        return loaded == null ? new ArrayList<>() : new ArrayList<>(loaded);
    }

    /**
     * Serialises a list of messages to a JSON file using Google Gson pretty printing.
     * Attribution: Google (2024), https://github.com/google/gson/blob/main/UserGuide.md
     */
    public static void writeMessages(Path jsonFile, List<Message> messages) throws IOException {
        if (jsonFile == null) {
            throw new IllegalArgumentException("JSON file path cannot be null.");
        }
        Path parent = jsonFile.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.writeString(jsonFile, GSON.toJson(messages, MESSAGE_LIST_TYPE), StandardCharsets.UTF_8);
    }

    public void deleteMessage() {
        if (countedAsSent && totalMessages > 0) {
            totalMessages--;
            countedAsSent = false;
        }
        status = "Deleted";
    }

    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
    public String getStatus() { return status; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender == null ? "" : sender.trim(); }
    public boolean isCountedAsSent() { return countedAsSent; }
    public void setStatus(String status) { this.status = status; }
}