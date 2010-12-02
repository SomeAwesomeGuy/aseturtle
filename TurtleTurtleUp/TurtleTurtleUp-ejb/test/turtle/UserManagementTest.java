/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sean
 */
public class UserManagementTest {

    public UserManagementTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class UserManagement.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String username = "";
        String password = "";
        UserManagement instance = (UserManagement)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/UserManagement");
        boolean expResult = false;
        boolean result = instance.login(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNewUser method, of class UserManagement.
     */
    @Test
    public void testCreateNewUser() throws Exception {
        System.out.println("createNewUser");
        String username = "";
        String password = "";
        UserManagement instance = (UserManagement)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/UserManagement");
        instance.createNewUser(username, password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changePassword method, of class UserManagement.
     */
    @Test
    public void testChangePassword() throws Exception {
        System.out.println("changePassword");
        String oldPassword = "";
        String newPassword = "";
        UserManagement instance = (UserManagement)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/UserManagement");
        instance.changePassword(oldPassword, newPassword);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setServerLock method, of class UserManagement.
     */
    @Test
    public void testSetServerLock() throws Exception {
        System.out.println("setServerLock");
        boolean enable = false;
        UserManagement instance = (UserManagement)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/UserManagement");
        instance.setServerLock(enable);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of promoteUser method, of class UserManagement.
     */
    @Test
    public void testPromoteUser() throws Exception {
        System.out.println("promoteUser");
        String username = "";
        UserManagement instance = (UserManagement)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/UserManagement");
        instance.promoteUser(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteUser method, of class UserManagement.
     */
    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        String username = "";
        UserManagement instance = (UserManagement)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/UserManagement");
        instance.deleteUser(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetUserPassword method, of class UserManagement.
     */
    @Test
    public void testResetUserPassword() throws Exception {
        System.out.println("resetUserPassword");
        String username = "";
        UserManagement instance = (UserManagement)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/UserManagement");
        instance.resetUserPassword(username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}