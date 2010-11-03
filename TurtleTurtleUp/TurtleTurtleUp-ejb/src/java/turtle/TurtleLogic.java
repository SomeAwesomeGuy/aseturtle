/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.Singleton;

/**
 *
 * @author Sean
 */
@Singleton
public class TurtleLogic implements TurtleLogicLocal {
    private static final int CLOCK_INTERVAL = 1000;
    private static final int ROUND_LENGTH = 10; // In seconds
    
    private Map<String,Player> userMap;
    private List<Player> players;
    private List<Player> eliminated;
    private List<Player> waitingList;

    private Timer timer;

    private GameState gameState;

    private Player roundLeader;
    private int roundNumber;
    private int roundTime;

    private boolean isLocked;
    
    public TurtleLogic() {
        userMap = new HashMap<String,Player>();
        players = new ArrayList<Player>();
        eliminated = new ArrayList<Player>();
        waitingList = new ArrayList<Player>();

        gameState = new GameState();
        timer = new Timer();

        isLocked = true;

        timer.scheduleAtFixedRate(new GameClock(), 0, CLOCK_INTERVAL);
    }

    class GameClock extends TimerTask {
        public void run() {
            if(roundTime > 0) {
                roundTime--;
                gameState.setTimeLeft(roundTime);
            }
            else {
                if(players.isEmpty()) {
                    if(waitingList.size() > 1) {
                        restartGame();
                    }
                    else {
                        roundTime = ROUND_LENGTH;
                        gameState.setRoundNumber(0);
                    }
                }
                else {
                    Finger lead = roundLeader.getFinger();
                    if(lead == null) {
                    // Leader has not submitted finger
                        int index = players.indexOf(roundLeader) + 1;
                        Player nextLeader = index == players.size() ? players.get(0) : players.get(index);
                        players.remove(roundLeader);
                        eliminated.add(roundLeader);
                        waitingList.add(roundLeader);
                        roundLeader = nextLeader;
                    }
                    else {
                        for(Player user : players) {
                            if(user.equals(lead)) {
                                continue;
                            }
                            Finger finger = user.getFinger();
                            if(finger == null) {
                                // User has not submitted finger
                                players.remove(user);
                                eliminated.add(user);
                                waitingList.add(user);
                            }
                            else if(finger == lead) {
                                // TODO: Record finger in gamestate
                                players.remove(user);
                                eliminated.add(user);
                                waitingList.add(user);
                            }
                        }
                    }
                }
            }
        }
    }

    private void restartGame() {
        players.addAll(waitingList);
        waitingList.clear();
        eliminated.clear();
        for(Player user : players) {
            user.setFinger(null);
        }
        Collections.shuffle(players);
        roundLeader = players.get(0);
        roundNumber = 1;
        roundTime = ROUND_LENGTH;

        gameState.setRoundNumber(roundNumber);
        gameState.setTimeLeft(roundTime);
        gameState.setOldPlayers(players);
        gameState.setEliminated(eliminated);
        gameState.setCurrLeader(roundLeader.getUsername());
        gameState.setOldLeader("");
    }

    private void nextRound() {
        // TODO: update gamestate
        eliminated.clear();
        roundNumber++;
        for(Player user : players) {
            user.setFinger(null);
        }
    }

    /**
     * Adds a player to the waiting list for the next game
     * @param username      the username of the player
     * @throws Exception
     */
    public void joinGame(String username) throws Exception {
        if(isLocked) {
            throw new ServerLockException();
        }
        
        Player user = new Player(username);
        userMap.put(username, user);
        
        waitingList.add(user);
    }

    
    
    public void setServerLock(boolean enable) {
        isLocked = enable;

        if(isLocked) {
            userMap.clear();
            players.clear();
            waitingList.clear();

            // TODO: If true, kick people out of the game
        }
    }

    public void playTurn(String username, Finger finger) throws Exception {
        if(isLocked) {
            throw new ServerLockException();
        }

        Player user = userMap.get(username);
        if(!user.isIsInGame()) {
            throw new Exception("ERROR: User can not play in this game. Please wait for the next game.");
        }

        user.setFinger(finger);
    }

    public GameState getGameState() throws Exception {
        if(isLocked) {
            throw new ServerLockException();
        }

        return null;
    }

    
 
}
