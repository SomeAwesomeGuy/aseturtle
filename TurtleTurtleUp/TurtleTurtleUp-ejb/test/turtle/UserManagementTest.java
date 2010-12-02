/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
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

    private static UserManagementRemote instance;
    private static UserManagementRemote instance2;
    private static InitialContext ic;
    private final String DEFAULT = "normal";
    private final String SHORT = "s";
    private final String LONG = "reallyreallylongstring";
    private final String INVALID = " !@#$%^";
    private final String FAKE = "OTWFFSSENT";

    public UserManagementTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        Properties props = new Properties();

        props.setProperty("java.naming.factory.initial",
                            "com.sun.enterprise.naming.SerialInitContextFactory");

        props.setProperty("java.naming.factory.url.pkgs",
                            "com.sun.enterprise.naming");

        props.setProperty("java.naming.factory.state",
                            "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        ic = new InitialContext(props);
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        instance = (UserManagementRemote) ic.lookup("turtle.UserManagementRemote");
    }

    @After
    public void tearDown() throws Exception {
        loginAdmin();
        instance2.setServerLock(false);
        try {
            instance2.deleteUser(DEFAULT);
        } catch (InvalidUsernameException e) { }
        try {
        instance2.deleteUser(SHORT);
        } catch (InvalidUsernameException e) { }
        try {
        instance2.deleteUser(LONG);
        } catch (InvalidUsernameException e) { }
        try {
        instance2.deleteUser(INVALID);
        } catch (InvalidUsernameException e) { }
    }

    private void loginAdmin() {
        try {
            instance2 = (UserManagementRemote) ic.lookup("turtle.UserManagementRemote");
            //hard coding is bad
            instance2.login("blinnc", "pass");
        } catch (Exception e) {

        }
    }

    private void login() {
        try {
            instance.createNewUser(DEFAULT, DEFAULT);
        } catch (Exception ex) {
            fail();
        }
        try {
            instance.login(DEFAULT, DEFAULT);
        } catch (Exception ex) {
            fail();
        }
    }

    /**
     * Test of createNewUser method, of class UserManagement.
     */
    @Test
    public void testCreateNewUser() {
        System.out.println("createNewUser");

       System.out.println("\tnull user string");
        try {
            instance.createNewUser(null, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tnull password string");
        try {
            instance.createNewUser(DEFAULT, null);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tnull, null");
        try {
            instance.createNewUser(null, null);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tinvalid user name");
        try {
            instance.createNewUser(INVALID, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tinvalid password");
        try {
            instance.createNewUser(DEFAULT, INVALID);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tuser name too short");
        try {
            instance.createNewUser(SHORT, DEFAULT);
            fail();
            // FAILS HERE: no check for string length
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tuser name too long");
        try {
            instance.createNewUser(LONG, DEFAULT);
            fail();
            // FAILS HERE: no check for string length
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tpassword too long");
        try {
            instance.createNewUser(DEFAULT, LONG);
            fail();
            // FAILS HERE: no check for string length
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tpassword too short");
        try {
            instance.createNewUser(DEFAULT, SHORT);
            fail();
            // FAILS HERE: no check for string length
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tnormal case");
        try {
            instance.createNewUser(DEFAULT, DEFAULT);
        } catch (Exception ex) {
            fail();
        }

        System.out.println("\talready created user");
        try {
            instance.createNewUser(DEFAULT, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

    }

    /**
     * Test of login method, of class UserManagement.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");

        System.out.println("\tinvalid username");
        try {
            instance.login(INVALID, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tinvalid password");
        try {
            instance.login(DEFAULT, INVALID);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tusername too short");
        try {
            instance.login(SHORT, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tuser name too long");
        try {
            instance.login(LONG, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tnull user");
        try {
            instance.login(null, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tnull password");
        try {
            instance.login(DEFAULT, null);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tnull null");
        try {
            instance.login(null, null);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tuser doesn't exist");
        try {
            instance.login(FAKE, DEFAULT);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tnormal case");
        login();

        System.out.println("\tlogin as the same user");
        try {
            instance.login(DEFAULT, DEFAULT);
            fail();
            // FAILS HERE: Can log in multiple times without logging out
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tlogin as different user");
        try {
            // hard coding is bad
            instance.login("blinnc", "pass");
            fail();
            // FAILS HERE: Can log in multiple times without logging out
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    /**
     * Test of changePassword method, of class UserManagement.
     */
    @Test
    public void testChangePassword() throws Exception {
        System.out.println("changePassword");

        System.out.println("\tnot logged in");
        try {
            instance.changePassword(DEFAULT, DEFAULT);
            fail();
        } catch (UserNotLoggedInException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        login();
        System.out.println("\tnull normal password");
        try {
            instance.changePassword(null, DEFAULT);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tnull new password");
        try {
            instance.changePassword(DEFAULT, null);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tinvalid string normal password");
        try {
            instance.changePassword(INVALID, DEFAULT);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tinvalid string new password");
        try {
            instance.changePassword(DEFAULT, INVALID);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tincorrect normal password");
        try {
            instance.changePassword(FAKE, DEFAULT);
            fail();
        } catch (InvalidPasswordException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tnormal case");
        try {
            instance.changePassword(DEFAULT, FAKE);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

    }

    /**
     * Test of setServerLock and getServerLock methods, of class UserManagement.
     */
    @Test
    public void testSetServerLock() throws Exception {
        System.out.println("setServerLock");

        System.out.println("\tnot logged in");
        try {
            instance.setServerLock(false);
            fail();
        } catch (UserNotLoggedInException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        login();

        System.out.println("\tinitial lock is true");
        assertTrue(instance.isLocked());
        // FAILS HERE: not sure why, but returns false, could be problem with test

        System.out.println("\tnot logged in as admin");
        try {
            instance.setServerLock(false);
            fail();
        } catch (InsufficientPrivilegeException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        loginAdmin();
        System.out.println("\tset lock false");
        try {
            instance2.setServerLock(false);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        assertFalse(instance.isLocked());

        System.out.println("\tset lock false again");
        try {
            instance2.setServerLock(false);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        assertFalse(instance.isLocked());

        System.out.println("\tset lock true");
        try {
            instance2.setServerLock(true);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        assertTrue(instance.isLocked());

        System.out.println("\tset lock true again");
        try {
            instance2.setServerLock(true);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        assertTrue(instance.isLocked());
    }

    /**
     * Test of promoteUser method, of class UserManagement.
     */
    @Test
    public void testPromoteUser() throws Exception {
        System.out.println("promoteUser");
        System.out.println("\tnot logged in");
        try {
            instance.promoteUser(DEFAULT);
            fail();
        } catch (UserNotLoggedInException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        login();

        System.out.println("\tnot logged in as admin");
        try {
            instance.promoteUser(DEFAULT);
            fail();
        } catch (InsufficientPrivilegeException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        loginAdmin();

        System.out.println("\tnull user name");
        try {
            instance2.promoteUser(null);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tinvalid user string");
        try {
            instance2.promoteUser(INVALID);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tuser does not exist");
        try {
            instance2.promoteUser(FAKE);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        System.out.println("\tnormal case");
        try {
            instance2.promoteUser(DEFAULT);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        System.out.println("\tpromote self");
        try {
            instance2.promoteUser("blinnc");
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        System.out.println("\toriginal user promotes without logging back in");
        try {
            instance.promoteUser(DEFAULT);
            fail();
        } catch (InsufficientPrivilegeException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tpromote other admin");
        try {
            instance2.promoteUser(DEFAULT);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    /**
     * Test of deleteUser method, of class UserManagement.
     */
    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        System.out.println("\tnot logged in");
        try {
            instance.deleteUser(DEFAULT);
            fail();
        } catch (UserNotLoggedInException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        login();

        System.out.println("\tnot logged in as admin");
        try {
            instance.deleteUser(DEFAULT);
            fail();
        } catch (InsufficientPrivilegeException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        loginAdmin();

        System.out.println("\tnull string");
        try {
            instance2.deleteUser(null);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tinvalid string");
        try {
            instance2.deleteUser(INVALID);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tuser does not exist");
        try {
            instance2.deleteUser(FAKE);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tdelete self");
        try {
            instance2.deleteUser("blinnc");
            fail();
            // FAILS HERE - no check for deleting yourself
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tdelete normal user / admin");
        try {
            instance2.promoteUser(DEFAULT);
            instance2.deleteUser(DEFAULT);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    /**
     * Test of resetUserPassword method, of class UserManagement.
     */
    @Test
    public void testResetUserPassword() throws Exception {
        System.out.println("resetUserPassword");
        System.out.println("\tnot logged in");
        try {
            instance.resetUserPassword(DEFAULT);
            fail();
        } catch (UserNotLoggedInException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        login();

        System.out.println("\tnot logged in as admin");
        try {
            instance.resetUserPassword(DEFAULT);
            fail();
        } catch (InsufficientPrivilegeException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
        loginAdmin();

        System.out.println("\tnull string");
        try {
            instance2.resetUserPassword(null);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
            // FAILS HERE: Caused by: java.lang.NullPointerException
        }

        System.out.println("\tinvalid string");
        try {
            instance2.resetUserPassword(INVALID);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tuser does not exist");
        try {
            instance2.resetUserPassword(FAKE);
            fail();
        } catch (InvalidUsernameException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\tnormal");
        try {
            instance2.resetUserPassword(DEFAULT);
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

        System.out.println("\treset your own password");
        try {
            instance2.resetUserPassword("blinnc");
        } catch (Exception ex) {
            Logger.getLogger(UserManagementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }
}