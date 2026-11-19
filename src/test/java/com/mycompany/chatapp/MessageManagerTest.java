package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Part 3 using the official PROG5121 POE Test Data (Messages 1-5)
 * and additional report/search enhancements.
 
 * JUnit Team, 2024. JUnit 5 User Guide. [online] Available at: https://junit.org/junit5/docs/current/user-guide/ [Accessed 19 November 2026].
 */
class MessageManagerTest {
    @TempDir
    Path tempDir;

    private Path jsonPath;
    private MessageManager manager;
    private Message msg1;
    private Message msg2;
    private Message msg3;
    private Message msg4;
    private Message msg5;

    @BeforeEach
    void setUp() throws Exception {
        jsonPath = tempDir.resolve("messages.json");
        manager = new MessageManager(jsonPath);

        // Test Data Message 1: Sent
        msg1 = new Message("+27834557896", "Did you get the cake?", 1, "1100000001");
        msg1.setSender("Kyle Smith");
        manager.processMessage(msg1, "Sent");

        // Test Data Message 2: Stored
        msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2, "2200000002");
        msg2.setSender("Kyle Smith");
        manager.processMessage(msg2, "Store");

        // Test Data Message 3: Disregard
        msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.", 3, "3300000003");
        msg3.setSender("Kyle Smith");
        manager.processMessage(msg3, "Disregard");

        // Test Data Message 4: Sent (Developer entry)
        msg4 = new Message("0838884567", "It is dinner time !", 4, "0838884567");
        msg4.setSender("Kyle Smith");
        manager.processMessage(msg4, "Sent");

        // Test Data Message 5: Stored
        msg5 = new Message("+27838884567", "Ok, I am leaving without you.", 5, "5500000005");
        msg5.setSender("Kyle Smith");
        manager.processMessage(msg5, "Store");
    }

    @Test
    void sentMessagesArrayCorrectlyPopulated() {
        List<Message> sent = manager.getSentMessages();
        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0).getMessageText());
        assertEquals("It is dinner time !", sent.get(1).getMessageText());
    }

    @Test
    void displayLongestMessage() {
        Message longest = manager.findLongestMessage();
        assertNotNull(longest);
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                longest.getMessageText());
    }

    @Test
    void searchForMessageID() {
        Message found = manager.searchByMessageID("0838884567");
        assertNotNull(found);
        assertEquals("It is dinner time !", found.getMessageText());
    }

    @Test
    void searchAllMessagesSentOrStoredForParticularRecipient() {
        List<Message> matches = manager.searchByRecipient("+27838884567");
        assertEquals(3, matches.size());
        assertTrue(matches.stream().anyMatch(m -> m.getMessageText().equals("It is dinner time !")));
        assertTrue(matches.stream().anyMatch(m -> m.getMessageText().equals("Where are you? You are late! I have asked you to be on time.")));
        assertTrue(matches.stream().anyMatch(m -> m.getMessageText().equals("Ok, I am leaving without you.")));
    }

    @Test
    void deleteMessageUsingMessageHash() throws Exception {
        String result = manager.deleteByHash(msg2.getMessageHash());
        assertEquals("Message \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                result);
        assertNull(manager.searchByMessageID("2200000002"));
    }

    @Test
    void displayReport() {
        String report = manager.fullReport();
        assertTrue(report.contains("Message Hash: " + msg1.getMessageHash()));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));

        assertTrue(report.contains("Message Hash: " + msg4.getMessageHash()));
        assertTrue(report.contains("Recipient: 0838884567"));
        assertTrue(report.contains("Message: It is dinner time !"));
    }

    @Test
    void keywordSearchAndPersistentCounterWorkAcrossReload() throws Exception {
        assertEquals(1, manager.searchByKeyword("cake").size());
        MessageManager reloaded = new MessageManager(jsonPath);
        assertEquals(6, reloaded.getNextMessageNumber());
    }
}