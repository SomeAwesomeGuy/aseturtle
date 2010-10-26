/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public class User {
    enum STATUS {
        LOBBY, INGAME, SPECTATOR
    }

    private String username;



    public User(String username) {
        this.username = username;

    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
