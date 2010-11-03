/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public class Player {
    private String username;
    private boolean isInGame;
    private Finger finger;

    public Player(String username) {
        this.username = username;
        isInGame = false;
    }

    public String getUsername() {
        return username;
    }

    public boolean isIsInGame() {
        return isInGame;
    }

    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }

    public Finger getFinger() {
        return finger;
    }

    public void setFinger(Finger finger) {
        this.finger = finger;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Player)) {
            return false;
        }
        Player user = (Player)obj;
        return user.getUsername().equals(username);
    }
}
