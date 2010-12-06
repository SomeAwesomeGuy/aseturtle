package turtle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Controls the logic and the state of the Turtle Turtle Up game
 * @author Sean
 */
@Startup
@Singleton
public class TurtleLogic implements TurtleLogicLocal {
    private static final int CLOCK_INTERVAL = 1000; // Milliseconds between logic calculations
    private static final int ROUND_LENGTH = 12; // Round length in clock ticks
    
    private Map<String,Player> userMap; // Maps username to Player object
    private List<Player> players;       // List of active players in current game
    private List<Player> eliminated;    // List of Players recently eliminated
    private List<Player> waitingList;   // List of Players waiting to join the next game
    private List<String> gamePlayers;   // List of usernames of players who were in the game during the first round
    private List<DBChange> changeList;  // List of changes that need to be made to the database

    private GameState state;    // Keeps track of the state of the game

    private Player roundLeader;
    private int roundNumber;
    private int roundTime;      // Number of clock ticks until the next round

    private boolean isLocked;
    
    @PersistenceContext(unitName = "TurtleTurtleUp-ejbPU")
    private EntityManager em;

    /**
     * Initializes the class variables
     */
    public TurtleLogic() {
        userMap = new HashMap<String,Player>();
        players = Collections.synchronizedList(new ArrayList<Player>());
        eliminated = Collections.synchronizedList(new ArrayList<Player>());
        waitingList = Collections.synchronizedList(new ArrayList<Player>());
        gamePlayers = Collections.synchronizedList(new ArrayList<String>());
        changeList = Collections.synchronizedList(new ArrayList<DBChange>());

        state = new GameState();

        roundNumber = 0;
        roundTime = 0;

        isLocked = true;
        
        new Timer().scheduleAtFixedRate(new GameClock(), 0, CLOCK_INTERVAL);    // Launches the game clock
        System.out.println("SERVER: TurtleLogic initialized");
    }

    /**
     * The game clock class, responsible for changing the state of the game at
     * regular intervals
     */
    private class GameClock extends TimerTask {
        @Override
        public void run() {
            if(isLocked) {
                // If the server is locked, don't do anything
                return;
            }
            if(roundTime > 0) { // Still time left on the clock
                roundTime--;
                state.setTimeLeft(roundTime);
            }
            else {  // Time's up
                if(players.isEmpty()) { // No players in the game
                    if(waitingList.size() > 1) {
                        // Start the game if 2 or more players are waiting to play
                        restartGame();
                    }
                    else {  // Not enough players to start a game
                        roundTime = ROUND_LENGTH;   // Check again later
                        roundNumber = 0;
                        state.setRoundNumber(roundNumber);
                        state.setTimeLeft(roundTime);
                        state.setStatus(GameState.Status.WAITING);
                        System.out.println("SERVER: Not enough players waiting. Checking again in...");
                    }
                }
                else {  // A game is ongoing
                    state.setOldPlayers(players);
                    Finger leadFinger = roundLeader.getFinger();

                    if(leadFinger == null) {  // Leader has not submitted finger
                        System.out.println("SERVER: Leader has not picked a finger and has been eliminated");

                        // Eliminate leader and assign next leader
                        int index = players.indexOf(roundLeader) + 1;
                        Player nextLeader = index == players.size() ? players.get(0) : players.get(index);
                        eliminate(roundLeader);
                        roundLeader = nextLeader;

                        // Queue player's fingers to be recorded in the database
                        for(Player user : players) {
                            Finger finger = user.getFinger();
                            changeList.add(new DBChange(user.getUsername(), DBChange.Type.FINGER, finger));
                        }
                    }
                    else {  // Leader has picked a finger
                        System.out.println("SERVER: Leader has picked " + leadFinger);
                        List<Player> garbage = new ArrayList<Player>(); // List of players to be eliminated

                        for(Player user : players) {
                            Finger finger = user.getFinger();
                            changeList.add(new DBChange(user.getUsername(), DBChange.Type.FINGER, finger)); // Queue finger to be recorded in the database

                            if(user.equals(roundLeader)) {
                                // Skip if this player is the round leader
                                continue;
                            }

                            if(finger == null) {    // Player has not picked a finger
                                System.out.println("SERVER: " + user.getUsername() + " has not picked a finger");
                                System.out.println("SERVER: " + user.getUsername() + " has been eliminated");
                                garbage.add(user);
                            }
                            else {  // Player has picked a finger
                                System.out.println("SERVER: " + user.getUsername() + " has picked " + finger);
                                if(finger == leadFinger) {  // Player has picked the same finger as the leader
                                    System.out.println("SERVER: " + user.getUsername() + " has been eliminated");
                                    garbage.add(user);
                                }
                            }
                        }

                        // Remove the eliminated players from the game
                        for(Player user : garbage) {
                            eliminate(user);
                        }

                        // Assign next leader
                        int index = players.indexOf(roundLeader) + 1;
                        roundLeader = index == players.size() ? players.get(0) : players.get(index);
                    }
                    nextRound();
                }
            }
        }
    }

    /**
     * Eliminates a player from the game
     * @param player the player to be eliminated
     */
    private void eliminate(Player player) {
        players.remove(player);
        userMap.remove(player.getUsername());
        eliminated.add(player);
    }

    /**
     * Re-initializes variables and wipes lists to prepare for a new game
     */
    private void restartGame() {
        players.addAll(waitingList);
        waitingList.clear();
        eliminated.clear();
        gamePlayers.clear();
        for(Player user : players) {
            gamePlayers.add(user.getUsername());
            user.setFinger(null);
            changeList.add(new DBChange(user.getUsername(), DBChange.Type.GAME));
        }
        Collections.shuffle(players);   // Randomly order the players

        roundLeader = players.get(0);
        roundNumber = 1;
        roundTime = ROUND_LENGTH;

        // Update the game state
        state.setRoundNumber(roundNumber);
        state.setTimeLeft(roundTime);
        state.setOldPlayers(players);
        state.setEliminated(eliminated);
        state.setCurrLeader(roundLeader.getUsername());
        state.setOldLeader("");
        state.setPlayedGame(gamePlayers);
        state.setStatus(GameState.Status.NEW);

        System.out.println("\n\nSERVER: Starting new game with " + players.size() + " players");
        System.out.println("SERVER: Round 1 - leader: " + roundLeader.getUsername());
    }

    /**
     * Prepares for the next round in a game
     */
    private void nextRound() {
        if(players.size() == 1) {   // There's a winner
            System.out.println("SERVER: " + roundLeader.getUsername() + " wins!");
            state.setStatus(GameState.Status.WINNER);
            players.clear();
            userMap.clear();
            changeList.add(new DBChange(roundLeader.getUsername(), DBChange.Type.WIN));
        }
        else {  // Continue the game
            state.setStatus(GameState.Status.NEW);
            roundNumber++;
            state.setRoundNumber(roundNumber);
            System.out.println("\n\nSERVER: Round " + roundNumber + " - leader: " + roundLeader.getUsername());
        }
        
        roundTime = ROUND_LENGTH;

        // Update game state
        state.setTimeLeft(roundTime);
        state.updateLeader(roundLeader.getUsername());
        state.setEliminated(eliminated);

        eliminated.clear();
        
        for(Player user : players) {
            user.setFinger(null);
        }
    }

    /**
     * Adds a player to the waiting list for the next game
     * @param username      the username of the player
     * @throws Exception
     */
    @Override
    public void joinGame(String username) throws ServerLockException {
        if(isLocked) {
            throw new ServerLockException();
        }
        
        Player user = userMap.get(username);
        if(user == null) {
            user = new Player(username);
            userMap.put(username, user);

            waitingList.add(user);
        }
    }

    /**
     * Removes a player from the game
     * @param username the username of the player to be removed
     */
    @Override
    public void leaveGame(String username) {
        Player user = userMap.get(username);

        if(user != null) {  // Check if the player is actually in the game
            players.remove(user);
            waitingList.remove(user);
            userMap.remove(username);
        }
    }

    /**
     * Sets the lock on the game
     * @param enable true to lock the server, false to unlock the server
     */
    @Override
    public void setServerLock(boolean enable) {
        isLocked = enable;

        if(isLocked) {  // Reset the game when the server is unlocked
            userMap.clear();
            players.clear();
            waitingList.clear();
            roundNumber = 0;
            roundTime = 0;
            state = new GameState();
        }
    }

    /**
     * Sets the finger played for a player
     * @param username the username of the player
     * @param finger the finger played
     * @throws ServerLockException
     */
    @Override
    public synchronized void playTurn(String username, Finger finger) throws ServerLockException {
        if(isLocked) {
            throw new ServerLockException();
        }

        Player user = userMap.get(username);
        if(user != null) {
            user.setFinger(finger);
        }
    }

    /**
     * Gets the state of the game and persists all pending database changes
     * @return the state of a game, as a GameState object
     * @throws Exception
     */
    @Override
    public synchronized GameState getGameState() throws ServerLockException {
        if(isLocked) {
            throw new ServerLockException();
        }

        // Persist all pending database changes
        for(DBChange change : changeList) {
            UserEntity user = em.find(UserEntity.class, change.username);
            if(change.type == DBChange.Type.FINGER && change.finger != null) {
                switch(change.finger) {
                    case THUMB:
                        user.setThumbCount(user.getThumbCount() + 1);
                        break;
                    case INDEX:
                        user.setIndexCount(user.getIndexCount() + 1);
                        break;
                    case MIDDLE:
                        user.setMiddleCount(user.getMiddleCount() + 1);
                        break;
                    case RING:
                        user.setRingCount(user.getRingCount() + 1);
                        break;
                    case PINKIE:
                        user.setPinkieCount(user.getPinkieCount() + 1);
                        break;
                }
                user.setRoundsPlayed(user.getRoundsPlayed() + 1);
            }
            else if(change.type == DBChange.Type.GAME) {
                user.setGamesPlayed(user.getGamesPlayed() + 1);
            }
            else if(change.type == DBChange.Type.WIN) {
                user.setGamesWon(user.getGamesWon() + 1);
            }

            em.merge(user);
        }
        changeList.clear();

        return state;
    }

    /**
     * Removes a player from the game
     * @param username the username of the player to be removed
     */
    @Override
    public void kickPlayer(String username) {
        leaveGame(username);
    }

    /**
     * Gets a list of players who are player or waiting to play
     * @return a list of the usernames of playing or waiting players
     */
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

    /**
     * Checks if the game is locked
     * @return true if the game is locked
     */
    @Override
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Checks if a player is playing or waiting to play
     * @param username the username of the player
     * @return true if the player is playing or waiting
     */
    @Override
    public boolean isInGame(String username) {
        Player user = userMap.get(username);
        if(user == null) {
            return false;
        }
        return players.contains(user);
    }
}
