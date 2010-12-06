/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sean
 */
@Local
public interface TurtleLogicLocal {

    void joinGame(String username) throws ServerLockException;

    void setServerLock(boolean enable);

    void playTurn(String username, Finger finger) throws ServerLockException;

    GameState getGameState() throws ServerLockException;

    void kickPlayer(String username);

    void leaveGame(String username);

    List<String> getConnectedPlayers();

    boolean isLocked();

    boolean isInGame(String username);    
}
