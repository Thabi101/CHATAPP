package com.mycompany.chatapp;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
/*
JUnit Team (n.d.) JUnit 5 User Guide. Available at: https://docs.junit.org/5.13.1/user-guide/index.html (Accessed: 19 October 2026).

*/
class MessageTest {
    @TempDir Path tempDir;

    @Test 
    void messageIdIsTenNumericDigits() {
        Message message = new Message("+27838968976", "Hi Mike, can you join us for dinner tonight?", 0);
        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test 
    void recipientAcceptsRequiredSouthAfricanInternationalFormat() {
        Message message = new Message("+27838968976", "Hi Mike, can you join us for dinner tonight?", 0);
        assertTrue(message.checkRecipientCell());
    }

    @Test 
    void recipientRejectsLocalNumberWithoutCountryCode() {
        Message message = new Message("08575975889", "Hello there", 0);
        assertFalse(message.checkRecipientCell());
    }

    @Test 
    void messageLengthLimitIsEnforced() {
        Message valid = new Message("+27838968976", "Hi Mike", 0);
        Message invalid = new Message("+27838968976", "x".repeat(251), 0);
        assertTrue(valid.checkMessageLength());
        assertFalse(invalid.checkMessageLength());
    }

    @Test 
    void blankMessageIsInvalid() {
        Message blankMsg = new Message("+27838968976", "   ", 0);
        assertFalse(blankMsg.checkMessageLength());
    }

    @Test 
    void messageAtExactLimitPasses() {
        String exact250 = "a".repeat(250);
        Message message = new Message("+27838968976", exact250, 0);
        assertTrue(message.checkMessageLength());
    }

    @Test 
    void hashContainsIdPrefixCountAndFirstAndLastWords() {
        Message message = new Message("+27838968976", "Hi Mike, can you join us for dinner tonight?", 0);
        String hash = message.getMessageHash();
        assertTrue(hash.matches("[0-9]{2}:0:HITONIGHT\\?"));
    }

    @Test 
    void storingMessageCreatesJsonArray() throws Exception {
        Path file = tempDir.resolve("messages.json");
        Message message = new Message("+27838968976", "Hi Mike", 0);
        message.storeMessage(file);
        String json = Files.readString(file);
        assertTrue(json.contains("Hi Mike"));
        assertTrue(json.contains("Stored") || json.contains("Created"));
        assertTrue(json.stripLeading().startsWith("["));
    }

    @Test 
    void sendAndDisregardReturnCorrectStatuses() throws Exception {
        Message sent = new Message("+27838968976", "Hi Mike", 0);
        assertEquals("Message sent.", sent.sendMessage("send", tempDir.resolve("sent.json")));
        assertEquals("Sent", sent.getStatus());
        
        Message discarded = new Message("+27838968976", "Hi Mike", 1);
        assertEquals("Message disregarded.", discarded.sendMessage("disregard", tempDir.resolve("discard.json")));
        assertEquals("Disregarded", discarded.getStatus());
    }
    
    @Test
    void totalMessagesIncrementCorrectly() {
        int initialTotal = Message.returnTotalMessages();
        Message msg = new Message("+27838968976", "Test message tracking", 0);
        try {
            msg.sendMessage("send", tempDir.resolve("tracking.json"));
            assertEquals(initialTotal + 1, Message.returnTotalMessages());
        } catch (IOException e) {
            fail("Exception thrown during message send: " + e.getMessage());
        }
    }

    @Test
    void invalidChoiceReturnsErrorPrompt() {
        Message message = new Message("+27838968976", "Hello", 0);
        try {
            String result = message.sendMessage("invalid_choice", tempDir.resolve("invalid.json"));
            assertEquals("Invalid choice. Select send, disregard, or store.", result);
        } catch (IOException e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }
    }

    @Test
    void nullChoiceReturnsInvalidPrompt() {
        Message message = new Message("+27838968976", "Hello", 0);
        try {
            String result = message.sendMessage(null, tempDir.resolve("null_choice.json"));
            assertEquals("Invalid choice. Select send, disregard, or store.", result);
        } catch (IOException e) {
            fail("Exception thrown unexpectedly: " + e.getMessage());
        }
    }

    @Test
    void nullRecipientHandledGracefully() {
        Message message = new Message(null, "Hello", 0);
        assertFalse(message.checkRecipientCell());
    }

    @Test
    void emptyMessageTextIsInvalid() {
        Message message = new Message("+27838968976", "", 0);
        assertFalse(message.checkMessageLength());
    }

    @Test
    void messageHashPrefixUsesFirstTwoDigitsOfID() {
        Message message = new Message("+27838968976", "Test message for hash", 0);
        String id = message.getMessageID();
        String hash = message.getMessageHash();
        assertTrue(hash.startsWith(id.substring(0, 2) + ":0:"));
    }

    @Test
    void numericOptionOneSendsMessage() throws Exception {
        Message message = new Message("+27838968976", "Testing numeric send option", 0);
        String result = message.sendMessage("1", tempDir.resolve("numeric_send.json"));
        assertEquals("Message sent.", result);
        assertEquals("Sent", message.getStatus());
    }

    @Test
    void numericOptionTwoDisregardsMessage() throws Exception {
        Message message = new Message("+27838968976", "Testing numeric disregard option", 0);
        String result = message.sendMessage("2", tempDir.resolve("numeric_disregard.json"));
        assertEquals("Message disregarded.", result);
        assertEquals("Disregarded", message.getStatus());
    }

    @Test
    void numericOptionThreeStoresMessage() throws Exception {
        Message message = new Message("+27838968976", "Testing numeric store option", 0);
        String result = message.sendMessage("3", tempDir.resolve("numeric_store.json"));
        assertEquals("Message successfully stored in JSON.", result);
        assertEquals("Stored", message.getStatus());
    }

    @Test
    void uppercaseSendOptionWorksCorrectly() throws Exception {
        Message message = new Message("+27838968976", "Testing uppercase option", 0);
        String result = message.sendMessage("SEND", tempDir.resolve("upper_send.json"));
        assertEquals("Message sent.", result);
        assertEquals("Sent", message.getStatus());
    }

    @Test
    void initialMessageStatusIsCreated() {
        Message message = new Message("+27838968976", "Initial status test", 0);
        assertEquals("Created", message.getStatus());
    }

    @Test
    void printMessageContainsAllKeyDetails() {
        Message message = new Message("+27838968976", "Check print formatting", 0);
        String printed = message.printMessage();
        
        assertTrue(printed.contains("Message ID:"));
        assertTrue(printed.contains("Message Hash:"));
        assertTrue(printed.contains("Recipient: +27838968976"));
        assertTrue(printed.contains("Message: Check print formatting"));
        assertTrue(printed.contains("Status: Created"));
    }
}