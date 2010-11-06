/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sean
 */
@Stateful
public class UserManagement implements UserManagementRemote {
    private static final String DEFAULT_PASSWORD = "default";

    @EJB
    private TurtleLogicLocal turtleLogic;

    @PersistenceContext(unitName = "TurtleTurtleUp-ejbPU")
    private EntityManager em;

    private UserEntity user;
    private String username;
    private boolean isAdmin, isLoggedIn;

    /**
     * Logs a user into the server
     * @param username      The username of the player
     * @param password      The password of the player
     * @return              true if the player is an admin
     * @throws Exception
     */
    @Override
    public boolean login(String username, String password) throws Exception {
        // TODO: username/password validation to prevent SQL injection?
        user = checkUsername(username);
        if(!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        this.username = username;
        isAdmin = user.getIsAdmin();
        isLoggedIn = true;
        return isAdmin;
    }

    /**
     * Creates a new user account
     * @param username      the client's username
     * @param password      the client's password
     * @throws Exception    if the username is taken
     */
    @Override
    public void createNewUser(String username, String password) throws Exception {
        // TODO: username/password validation to prevent SQL injection?
        user = em.find(UserEntity.class, username);
        if(user != null) {
            throw new InvalidUsernameException();
        }

        user = new UserEntity(username,password,false);
        em.persist(user);
    }

    @Override
    public GameState poll() throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        return turtleLogic.getGameState();
    }

    @Override
    public List<String> getConnectedPlayers() throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        return turtleLogic.getConnectedPlayers();
    }

    /**
     * Gets the player records for a particular user
     * @param username      the username of the player
     * @return              the records for the player
     * @throws Exception    
     */
    @Override
    public UserRecord getUserRecord(String username) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        UserEntity player = checkUsername(username);

        UserRecord record = new UserRecord(player);
        return record;
    }

    /**
     * Changes the password for the client's account
     * @param oldPassword   the current password, for verification
     * @param newPassword   the new password
     * @throws Exception
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!user.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException();
        }

        // TODO: username/password validation

        user.setPassword(newPassword);
        em.merge(user);
    }

    @Override
    public void joinGame() throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        turtleLogic.joinGame(username);
    }

    @Override
    public void playTurn(Finger finger) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        turtleLogic.playTurn(username, finger);
    }

    @Override
    public void leaveGame() {
        if(isLoggedIn) {
            turtleLogic.leaveGame(username);
        }
    }

    /**
     * Sets the server lock, which prevents clients from playing and joining the game
     * @param enable        true to enable server lock
     * @throws Exception
     */
    @Override
    public void setServerLock(boolean enable) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        turtleLogic.setServerLock(enable);
    }

    /**
     * Promotes a player to an administrator
     * @param username      the username of the player
     * @throws Exception
     */
    @Override
    public void promoteUser(String username) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        UserEntity player = checkUsername(username);

        player.setIsAdmin(true);
        em.merge(player);
    }

    /**
     * Kicks a player out of a game
     * @param username      the username of the player
     * @throws Exception
     */
    @Override
    public void kickPlayer(String username) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        turtleLogic.kickPlayer(username);
    }

    /**
     * Deletes a player's account
     * @param username      the username of the player
     * @throws Exception
     */
    @Override
    public void deleteUser(String username) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        // TODO: kick player from game if necessary
        UserEntity player = checkUsername(username);
        em.remove(player);
    }

    /**
     * Resets a player's account password to the default password
     * @param username      the username of the player
     * @throws Exception
     */
    @Override
    public void resetUserPassword(String username) throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        UserEntity player = checkUsername(username);
        player.setPassword(DEFAULT_PASSWORD);
        em.merge(player);
    }

    /**
     * Checks if the server is locked
     * @return              true if the server is locked
     * @throws Exception
     */
    @Override
    public boolean isLocked() throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        return turtleLogic.isLocked();
    }

    /**
     * Checks if a username exists in the user database
     * @param username      the username being searched
     * @return              the UserEntity of the user
     * @throws InvalidUsernameException
     */
    private UserEntity checkUsername(String username) throws InvalidUsernameException {
        UserEntity player = em.find(UserEntity.class, username);
        if(player == null) {
            throw new InvalidUsernameException();
        }
        return player;
    }

    public boolean isInGame() throws Exception {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        return turtleLogic.isInGame(username);
    }
}
