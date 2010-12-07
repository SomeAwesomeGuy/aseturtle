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
 * Handles the client-server interaction
 * @author Sean
 */
@Stateful
public class UserManagement implements UserManagementRemote {
    private static final String DEFAULT_PASSWORD = "default";

    @EJB
    private TurtleLogicLocal turtleLogic;

    @PersistenceContext(unitName = "TurtleTurtleUp-ejbPU")
    private EntityManager em;

    private UserEntity user;    // the entity bean representing this user's database entry
    private String username;
    private boolean isAdmin = false;
    private boolean isLoggedIn = false;

    /**
     * Logs a user into the server
     * @param username      The username of the player
     * @param password      The password of the player
     * @return              true if the player is an admin
     * @throws InvalidUsernameException
     * @throws InvalidPasswordException
     */
    @Override
    public boolean login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        // Check if the input contains valid characters
        if(!isValid(username) || isLoggedIn) {
            throw new InvalidUsernameException();
        }
        if(!isValid(password)) {
            throw new InvalidPasswordException();
        }

        user = checkUsername(username); // Check that user exists
        if(!user.getPassword().equals(password)) {  // Input does not match user password
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
     * @throws InvalidUsernameException
     * @throws InvalidPasswordException
     */
    @Override
    public void createNewUser(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        // Check if the input contains valid characters
        if(!isValid(username)) {
            throw new InvalidUsernameException();
        }
        if(!isValid(password)) {
            throw new InvalidPasswordException();
        }
        
        user = em.find(UserEntity.class, username);
        if(user != null) {  // User by this name already exists
            throw new InvalidUsernameException();
        }

        // Create new user record in database
        user = new UserEntity(username,password,false);
        em.persist(user);
    }

    /**
     * Gets the state of the game
     * @return the state of the game as a GameState object
     * @throws UserNotLoggedInException
     * @throws ServerLockException
     */
    @Override
    public GameState poll() throws UserNotLoggedInException, ServerLockException {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        return turtleLogic.getGameState();
    }

    /**
     * Gets the players currently playing or waiting to play
     * @return the list of the usernames of players playing or waiting
     * @throws UserNotLoggedInException
     */
    @Override
    public List<String> getConnectedPlayers() throws UserNotLoggedInException {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        return turtleLogic.getConnectedPlayers();
    }

    /**
     * Gets the player records for a particular user
     * @param username      the username of the player
     * @return              the records for the player
     * @throws InvalidUsernameException
     * @throws UserNotLoggedInException
     */
    @Override
    public UserRecord getUserRecord(String username) throws InvalidUsernameException, UserNotLoggedInException {
        if(!isValid(username)) {
            throw new InvalidUsernameException();
        }
        
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
     * @throws InvalidPasswordException
     * @throws UserNotLoggedInException
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) throws InvalidPasswordException, UserNotLoggedInException {
        if(!isValid(oldPassword) || !isValid(newPassword)) {
            throw new InvalidPasswordException();
        }

        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        
        if(!user.getPassword().equals(oldPassword)) {
            throw new InvalidPasswordException();
        }

        user.setPassword(newPassword);
        em.merge(user);
        em.persist(user);
    }

    /**
     * Adds a player to the waitlist for the next game
     * @throws UserNotLoggedInException
     * @throws ServerLockException
     */
    @Override
    public void joinGame() throws UserNotLoggedInException, ServerLockException {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        turtleLogic.joinGame(username);
    }

    /**
     * Sets the finger played for a player
     * @param finger the finger played
     * @throws UserNotLoggedInException
     * @throws ServerLockException
     */
    @Override
    public void playTurn(Finger finger) throws UserNotLoggedInException, ServerLockException {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        turtleLogic.playTurn(username, finger);
    }

    /**
     * Removes a player from the game
     */
    @Override
    public void leaveGame() {
        if(isLoggedIn) {
            turtleLogic.leaveGame(username);
        }
    }

    /**
     * Sets the server lock, which prevents clients from playing and joining the game
     * @param enable        true to enable server lock
     * @throws UserNotLoggedInException
     * @throws InsufficientPrivilegeException
     */
    @Override
    public void setServerLock(boolean enable) throws UserNotLoggedInException, InsufficientPrivilegeException {
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
     * @throws InvalidUsernameException
     * @throws UserNotLoggedInException
     * @throws InsufficientPrivilegeException
     */
    @Override
    public void promoteUser(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException {
        if(!isValid(username)) {
            throw new InvalidUsernameException();
        }

        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        UserEntity player = checkUsername(username);

        player.setIsAdmin(true);
        em.merge(player);
        em.persist(user);
    }

    /**
     * Kicks a player out of a game
     * @param username      the username of the player
     * @throws InvalidUsernameException
     * @throws UserNotLoggedInException
     * @throws InsufficientPrivilegeException
     */
    @Override
    public void kickPlayer(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException {
        if(!isValid(username)) {
            throw new InvalidUsernameException();
        }
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
     * @throws InvalidUsernameException
     * @throws UserNotLoggedInException
     * @throws InsufficientPrivilegeException
     */
    @Override
    public void deleteUser(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException {
        if(!isValid(username) || username.equals(this.username)) {
            throw new InvalidUsernameException();
        }

        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        turtleLogic.kickPlayer(username);
        UserEntity player = checkUsername(username);
        em.remove(player);
    }

    /**
     * Resets a player's account password to the default password
     * @param username      the username of the player
     * @throws InvalidUsernameException
     * @throws UserNotLoggedInException
     * @throws InsufficientPrivilegeException
     */
    @Override
    public void resetUserPassword(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException {
        if(!isValid(username)) {
            throw new InvalidUsernameException();
        }
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }
        if(!isAdmin) {
            throw new InsufficientPrivilegeException();
        }

        UserEntity player = checkUsername(username);
        player.setPassword(DEFAULT_PASSWORD);
        em.merge(player);
        em.persist(user);
    }

    /**
     * Checks if the server is locked
     * @return true if the server is locked
     * @throws UserNotLoggedInException
     */
    @Override
    public boolean isLocked() throws UserNotLoggedInException {
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

    /**
     * Checks if a player is playing a game or waiting to play
     * @return true if the player is playing or waiting
     * @throws UserNotLoggedInException
     */
    @Override
    public boolean isInGame() throws UserNotLoggedInException {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        return turtleLogic.isInGame(username);
    }

    /**
     * Gets a list of all registered players
     * @return a list of the usernames of all registered players
     * @throws UserNotLoggedInException
     */
    @Override
    public List<String> getAllPlayers() throws UserNotLoggedInException {
        if(!isLoggedIn) {
            throw new UserNotLoggedInException();
        }

        List<String> playerList = em.createNativeQuery("Select username from users", String.class).getResultList();

        return playerList;
    }

    /**
     * Checks if the input contains valid characters
     * @param input the input string
     * @return true if the input is valid
     */
    private boolean isValid(String input) {
        if(null == input || input.length() < 4 || input.length() > 12) {
            return false;
        }
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
