/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sean
 */
@Remote
public interface TurtleServer {

    boolean login(String username, String password);

    boolean createNewUser(String username, String password);

    void pull(String username);

    GameState pullGame(String username);

    List<String> getConnectedUsers();

    Record getStats(String username);

    boolean changePassword(String username, String oldPass, String newPass);

    boolean joinNextGame(String username);

    boolean joinSpectator(String username);

    boolean logOff(String username);

    boolean playTurn(String username, Finger finger);

    boolean leaveGame(String username);

    boolean setServerLock(boolean enable);

    boolean promoteUser(String username);

    boolean kickUser(String username);

    boolean deleteUser(String username);

    boolean resetUserPassword(String username);
    
}
