/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

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
    
}
