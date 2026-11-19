package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

/*
Oracle, 2024a. Java Platform, Standard Edition & Java Development Kit Version 21 API Specification: Class ArrayList. [online] Available at: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html [Accessed 19 November 2026].
Oracle, 2024b. Java Platform, Standard Edition & Java Development Kit Version 21 API Specification: Class Files. [online] Available at: https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html [Accessed 19 November 2026].
*/
class MessageTest {
    @TempDir Path tempDir;

    @Test void generatedIdIsTenDigits() {
        Message message = new Message("+27838968976", "Hello there", 1);
        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test void knownIdCreatesExpectedHash() {
        Message message = new Message("+27834557896", "Hi tonight?", 0, "0012345678");
        assertEquals("00:0:HITONIGHT", message.getMessageHash());
    }

    @Test void hashUsesFirstAndLastWord() {
        Message message = new Message("+27838968976",
                "Hi Mike, can you join us for dinner tonight?", 0, "1212345678");
        assertEquals("12:0:HITONIGHT", message.getMessageHash());
    }

    @Test void recipientValidationWorks() {
        assertTrue(new Message("+27838968976", "Hello", 0).checkRecipientCell());
        assertFalse(new Message("0838968976", "Hello", 0).checkRecipientCell());
    }

    @Test void messageLengthValidationWorks() {
        assertTrue(new Message("+27838968976", "Hello", 0).checkMessageLength());
        assertFalse(new Message("+27838968976", "x".repeat(251), 0).checkMessageLength());
        assertFalse(new Message("+27838968976", "  ", 0).checkMessageLength());
    }

    @Test void sendChangesStatusAndReturnsExpectedText() throws Exception {
        Message message = new Message("+27838968976", "Hello", 0);
        assertEquals("Message sent.", message.sendMessage("send", tempDir.resolve("sent.json")));
        assertEquals("Sent", message.getStatus());
    }

    @Test void numericSendOptionWorks() throws Exception {
        Message message = new Message("+27838968976", "Hello", 0);
        message.sendMessage("1", tempDir.resolve("sent.json"));
        assertEquals("Sent", message.getStatus());
    }

    @Test void disregardChangesStatus() throws Exception {
        Message message = new Message("+27838968976", "Hello", 0);
        assertEquals("Message disregarded.", message.sendMessage("2", tempDir.resolve("ignored.json")));
        assertEquals("Disregarded", message.getStatus());
    }

    @Test void storeWritesJsonArray() throws Exception {
        Path file = tempDir.resolve("messages.json");
        Message message = new Message("+27838968976", "Hello JSON", 0);
        assertEquals("Message successfully stored.", message.sendMessage("3", file));
        String json = Files.readString(file);
        assertTrue(json.stripLeading().startsWith("["));
        assertTrue(json.contains("Hello JSON"));
    }

    @Test void storeAppendsInsteadOfOverwriting() throws Exception {
        Path file = tempDir.resolve("messages.json");
        new Message("+27838968976", "First", 1).storeMessage(file);
        new Message("+27838968976", "Second", 2).storeMessage(file);
        assertEquals(2, Message.readMessages(file).size());
    }

    @Test void nullChoiceIsHandled() throws Exception {
        Message message = new Message("+27838968976", "Hello", 0);
        assertTrue(message.sendMessage(null, tempDir.resolve("x.json")).startsWith("Invalid choice"));
    }

    @Test void deleteChangesStatus() throws Exception {
        Message message = new Message("+27838968976", "Hello", 0);
        message.sendMessage("send", tempDir.resolve("x.json"));
        message.deleteMessage();
        assertEquals("Deleted", message.getStatus());
    }
}
