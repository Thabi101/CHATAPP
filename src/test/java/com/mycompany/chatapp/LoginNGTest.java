/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package com.mycompany.chatapp;

import static org.testng.Assert.*;

/**
 *
 * @author Student
 */
public class LoginNGTest {
    
    public LoginNGTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of setFirstName method, of class Login.
     */
    @org.testng.annotations.Test
    public void testSetFirstName() {
        System.out.println("setFirstName");
        String firstName = "";
        Login instance = new Login();
        instance.setFirstName(firstName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLastName method, of class Login.
     */
    @org.testng.annotations.Test
    public void testSetLastName() {
        System.out.println("setLastName");
        String lastName = "";
        Login instance = new Login();
        instance.setLastName(lastName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUsername method, of class Login.
     */
    @org.testng.annotations.Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "";
        Login instance = new Login();
        instance.setUsername(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPassword method, of class Login.
     */
    @org.testng.annotations.Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        Login instance = new Login();
        instance.setPassword(password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCellPhoneNumber method, of class Login.
     */
    @org.testng.annotations.Test
    public void testSetCellPhoneNumber() {
        System.out.println("setCellPhoneNumber");
        String cellPhoneNumber = "";
        Login instance = new Login();
        instance.setCellPhoneNumber(cellPhoneNumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkUserName method, of class Login.
     */
    @org.testng.annotations.Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkUserName();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkPasswordComplexity method, of class Login.
     */
    @org.testng.annotations.Test
    public void testCheckPasswordComplexity() {
        System.out.println("checkPasswordComplexity");
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkPasswordComplexity();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @org.testng.annotations.Test
    public void testCheckCellPhoneNumber_String() {
        System.out.println("checkCellPhoneNumber");
        String number = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber(number);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCellPhoneNumber method, of class Login.
     */
    @org.testng.annotations.Test
    public void testCheckCellPhoneNumber_0args() {
        System.out.println("checkCellPhoneNumber");
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.checkCellPhoneNumber();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerUser method, of class Login.
     */
    @org.testng.annotations.Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        Login instance = new Login();
        String expResult = "";
        String result = instance.registerUser();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loginUser method, of class Login.
     */
    @org.testng.annotations.Test
    public void testLoginUser() {
        System.out.println("loginUser");
        String enteredUsername = "";
        String enteredPassword = "";
        Login instance = new Login();
        boolean expResult = false;
        boolean result = instance.loginUser(enteredUsername, enteredPassword);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of returnLoginStatus method, of class Login.
     */
    @org.testng.annotations.Test
    public void testReturnLoginStatus() {
        System.out.println("returnLoginStatus");
        boolean loginSuccessful = false;
        Login instance = new Login();
        String expResult = "";
        String result = instance.returnLoginStatus(loginSuccessful);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFirstName method, of class Login.
     */
    @org.testng.annotations.Test
    public void testGetFirstName() {
        System.out.println("getFirstName");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getFirstName();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastName method, of class Login.
     */
    @org.testng.annotations.Test
    public void testGetLastName() {
        System.out.println("getLastName");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getLastName();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsername method, of class Login.
     */
    @org.testng.annotations.Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getUsername();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCellPhoneNumber method, of class Login.
     */
    @org.testng.annotations.Test
    public void testGetCellPhoneNumber() {
        System.out.println("getCellPhoneNumber");
        Login instance = new Login();
        String expResult = "";
        String result = instance.getCellPhoneNumber();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
