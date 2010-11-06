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

    void joinGame(String username) throws Exception;

    void setServerLock(boolean enable);

    void playTurn(String username, Finger finger) throws Exception;

    GameState getGameState() throws Exception;

    void kickPlayer(String username) throws Exception;

    void leaveGame(String username);

    List<String> getConnectedPlayers();

    boolean isLocked();

    boolean isInGame(String username);

    void recordFinger(String username, Finger f);
    
}
