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

    private GameState state;

    private Player roundLeader;
    private int roundNumber;
    private int roundTime;

    private boolean isLocked;
    
    public TurtleLogic() {
        userMap = new HashMap<String,Player>();
        players = new ArrayList<Player>();
        eliminated = new ArrayList<Player>();
        waitingList = new ArrayList<Player>();

        state = new GameState();
        timer = new Timer();

        isLocked = true;

        roundNumber = 0;
        roundTime = 0;

        timer.scheduleAtFixedRate(new GameClock(), 0, CLOCK_INTERVAL);
    }

    class GameClock extends TimerTask {
        @Override
        public void run() {
            if(isLocked) {
                return;
            }
            if(roundTime > 0) {
                // Still time left on the clock
                roundTime--;
                state.setStatus(GameState.Status.OLD);
                state.setTimeLeft(roundTime);
            }
            else {
                // Time's up
                if(players.isEmpty()) {
                    // No players in the game
                    if(waitingList.size() > 1) {
                        // Start the game if 2 or more players waiting to play
                        restartGame();
                    }
                    else {
                        // Check again later
                        roundTime = ROUND_LENGTH;
                        roundNumber = 0;
                        state.setRoundNumber(roundNumber);
                        state.setTimeLeft(roundTime);
                        state.setStatus(GameState.Status.WAITING);
                    }
                }
                else {
                    state.setOldPlayers(players);
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
                            if(user.equals(roundLeader)) {
                                continue;
                            }
                            Finger finger = user.getFinger();
                            if(finger == null || finger == lead) {
                                // User has not submitted finger
                                players.remove(user);
                                eliminated.add(user);
                                waitingList.add(user);
                            }
                        }
                        int index = players.indexOf(roundLeader) + 1;
                        roundLeader = index == players.size() ? players.get(0) : players.get(index);
                    }
                    nextRound();
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

        state.setRoundNumber(roundNumber);
        state.setTimeLeft(roundTime);
        state.setOldPlayers(players);
        state.setEliminated(eliminated);
        state.setCurrLeader(roundLeader.getUsername());
        state.setOldLeader("");
        state.setStatus(GameState.Status.NEW);
        System.out.println("SERVER: Starting new game");
        System.out.println("SERVER: Round 1");
    }

    private void nextRound() {
        if(players.size() == 1) {
            state.setStatus(GameState.Status.WINNER);
            players.clear();
            waitingList.add(roundLeader);
        }
        else {
            state.setStatus(GameState.Status.NEW);
            roundNumber++;
            state.setRoundNumber(roundNumber);
        }
        
        roundTime = ROUND_LENGTH;
        state.setTimeLeft(roundTime);
        state.updateLeader(roundLeader.getUsername());
        state.setEliminated(eliminated);

        eliminated.clear();
        
        for(Player user : players) {
            user.setFinger(null);
        }
        System.out.println("SERVER: Round " + roundNumber);
    }

    /**
     * Adds a player to the waiting list for the next game
     * @param username      the username of the player
     * @throws Exception
     */
    @Override
    public void joinGame(String username) throws Exception {
        if(isLocked) {
            throw new ServerLockException();
        }
        
        Player user = new Player(username);
        userMap.put(username, user);
        
        waitingList.add(user);
    }

    @Override
    public void leaveGame(String username) throws Exception {
        Player user = userMap.get(username);

        if(user == null) {
            throw new Exception("ERROR: User is not playing in this game");
        }

        players.remove(user);
        waitingList.remove(user);
    }
    
    @Override
    public void setServerLock(boolean enable) {
        isLocked = enable;

        if(isLocked) {
            userMap.clear();
            players.clear();
            waitingList.clear();
            roundNumber = 0;
            roundTime = 0;
            state = new GameState();
        }
    }

    @Override
    public void playTurn(String username, Finger finger) throws Exception {
        if(isLocked) {
            throw new ServerLockException();
        }

        Player user = userMap.get(username);

        if(user == null || !user.isIsInGame()) {
            throw new Exception("ERROR: User can not play in this game. Please wait for the next game.");
        }

        user.setFinger(finger);
    }

    @Override
    public GameState getGameState() throws Exception {
        if(isLocked) {
            throw new ServerLockException();
        }

        return state;
    }

    @Override
    public void kickPlayer(String username) throws Exception {
        if(isLocked) {
            throw new ServerLockException();
        }

        Player user = userMap.get(username);

        if(user == null) {
            throw new Exception("ERROR: User is not playing in this game");
        }

        players.remove(user);
        waitingList.remove(user);
    }

    @Override
    public List<String> getConnectedPlayers() {
        List<String> list = new ArrayList<String>();
        for(Player p : players) {
            list.add(p.getUsername());
        }
        for(Player p : waitingList) {
            list.add(p.getUsername());
        }
        Collections.sort(list);
        return list;
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }
}
