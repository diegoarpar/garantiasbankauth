/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itec.db;

import com.itec.pojo.User;
import com.mongodb.DBCollection;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iTech-Pc
 */
public class DBFactoryUserTest {
    
    public DBFactoryUserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCollection method, of class DBFactoryUser.
     */
    //@Test
    public void testGetCollection() {
        System.out.println("getCollection");
        DBFactoryUser instance = new DBFactoryUser();
        DBCollection expResult = null;
        //DBCollection result = instance.getCollection();
       // assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUser method, of class DBFactoryUser.
     */
    //@Test
    public void testGetUser() {
        System.out.println("getUser");
        DBFactoryUser instance = new DBFactoryUser();
        List<User> expResult = null;
        //List<User> result = instance.getUser();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insertUser method, of class DBFactoryUser.
     */
    //@Test
    public void testInsertUser() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("insertUser");
        User u = new User();
        u.setUser("diego");
        u.setPassword("diego");
        
        DBFactoryUser instance = new DBFactoryUser();
        String expResult = "";
        //String result = instance.insertUser(u);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of isValidUser method, of class DBFactoryUser.
     */
    //
    
    //@Test
    public void testIsValidUser() {
        System.out.println("isValidUser");
        User ui = new User();
        ui.setUser("diego");
        ui.setPassword("00e48a815525529ba9d33f8761a167588fe00c47bc82f515cf791c482ed99ecc");
        DBFactoryUser instance = new DBFactoryUser();
        Boolean expResult = null;
        //Boolean result = instance.isValidUser(ui);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    

    /**
     * Test of hash256 method, of class DBFactoryUser.
     */
    //@Test
    public void testHash256() throws Exception {
        System.out.println("hash256");
        String password = "";
        byte[] expResult = null;
        byte[] result = DBFactoryUser.hash256(password);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
