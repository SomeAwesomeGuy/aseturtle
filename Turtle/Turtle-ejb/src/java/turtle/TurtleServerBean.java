/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;

/**
 *
 * @author Sean
 */
@Stateless
public class TurtleServerBean implements TurtleServer {

    private boolean locked;

    private Map<String,User> connectedUsers;
    private List<User> playerList;

    public TurtleServerBean() {
        locked = false; // TODO: should be initialized to true when administrator functionality is implemented

        connectedUsers = new HashMap<String,User>();
        playerList = new ArrayList<User>();
    }


    @Override
    public boolean login(String username, String password) {    // TODO: Add custom exception here
        if(locked) {
            // TODO: Throw exception
            return false;
        }

        if(connectedUsers.containsKey(username)) {
            // TODO: Throw exception
            return false;
        }

        // TODO: Authenticate user info with database, throw exception
        
        User newUser = new User(username);
        connectedUsers.put(username, newUser);

        return true;
    }

    @Override
    public boolean createNewUser(String username, String password) {    // TODO: Add custom exception here
        // TODO: Check if username is taken in database, throw exception

        return false;
    }

    @Override
    public boolean pull(String username) {
        if(connectedUsers.containsKey(username)) {
            return true;
        }
        return false;
    }

    @Override
    public GameState pullGame(String username) {
        return null;
    }

    @Override
    public List<String> getConnectedUsers() {
        // ???: Should list be sorted?
        Set usernames = connectedUsers.keySet();
        List userList = new ArrayList(usernames);
        return userList;
    }

    @Override
    public Record getStats(String username) {
        return null;
    }

    @Override
    public boolean changePassword(String username, String oldPass, String newPass) {
        return false;
    }

    @Override
    public boolean joinNextGame(String username) {
        User user = connectedUsers.get(username);


        return false;
    }

    @Override
    public boolean joinSpectator(String username) {
        // ???: Distinction between spectator and lobby may just be on the client side
        return false;
    }

    @Override
    public boolean logOff(String username) {
        
        // TODO: Remove player from game
        connectedUsers.remove(username);

        return false;
    }

    @Override
    public boolean playTurn(String username, Finger finger) {
        return false;
    }

    @Override
    public boolean leaveGame(String username) {
        return false;
    }

    @Override
    public boolean setServerLock(boolean enable) {
        locked = enable;
        if(enable) {
            // TODO: Clear game
            connectedUsers.clear();
        }

        return false;
    }

    @Override
    public boolean promoteUser(String username) {
        return false;
    }

    @Override
    public boolean kickUser(String username) {
        // ???: What differentiates kickUser from logOff()?
        logOff(username);
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }

    @Override
    public boolean resetUserPassword(String username) {
        return false;
    }
}
