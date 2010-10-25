/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Sean
 */
@Stateless
public class TurtleServerBean implements TurtleServer {

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean createNewUser(String username, String password) {            //TODO: Add custom exception here
        return false;
    }

    @Override
    public void pull(String username) {
    }

    @Override
    public GameState pullGame(String username) {
        return null;
    }

    @Override
    public List<String> getConnectedUsers() {
        return null;
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
        return false;
    }

    @Override
    public boolean joinSpectator(String username) {
        return false;
    }

    @Override
    public boolean logOff(String username) {
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
        return false;
    }

    @Override
    public boolean promoteUser(String username) {
        return false;
    }

    @Override
    public boolean kickUser(String username) {
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

    


    
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
 
}
