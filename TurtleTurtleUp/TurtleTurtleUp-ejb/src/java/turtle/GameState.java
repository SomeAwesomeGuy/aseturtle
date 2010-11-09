/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Sean
 */
public class GameState implements Serializable {
    public enum Status {
        WAITING, NEW, OLD, WINNER
    }

    private int roundNumber;
    private int timeLeft;
    private String oldLeader, currLeader;
    private List<String> oldPlayers, eliminated, playedGame;
    private Map<String,Finger> fingerMap;
    private Status status;

    public GameState() {
        oldPlayers = new ArrayList<String>();
        eliminated = new ArrayList<String>();
        playedGame = new ArrayList<String>();
        fingerMap = new HashMap<String,Finger>();
        roundNumber = 0;
    }

    public void setCurrLeader(String currLeader) {
        this.currLeader = currLeader;
    }

    public void setOldLeader(String oldLeader) {
        this.oldLeader = oldLeader;
    }

    public void updateLeader(String currLeader) {
        oldLeader = this.currLeader;
        this.currLeader = currLeader;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
    
    public void setOldPlayers(List<Player> players) {
        oldPlayers.clear();
        fingerMap.clear();
        for(Player player : players) {
            oldPlayers.add(player.getUsername());
            fingerMap.put(player.getUsername(), player.getFinger());
        }
    }

    public void setEliminated(List<Player> players) {
        eliminated.clear();
        for(Player player : players) {
            eliminated.add(player.getUsername());
        }
    }

    public void setPlayedGame(List<String> players) {
        playedGame.addAll(players);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCurrLeader() {
        return currLeader;
    }

    public List<String> getEliminated() {
        return eliminated;
    }

    public Map<String, Finger> getFingerMap() {
        return fingerMap;
    }

    public String getOldLeader() {
        return oldLeader;
    }

    public List<String> getOldPlayers() {
        return oldPlayers;
    }

    public List<String> getPlayedGame() {
        return playedGame;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getTimeLeft() {
        return timeLeft;
    }


}
