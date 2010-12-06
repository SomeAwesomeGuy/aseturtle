/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 * Keeps track of a change to the database that needs to be made
 * @author Sean
 */
public class DBChange {
    public enum Type {
        FINGER,GAME,WIN
    }

    public String username;
    public Type type;
    public Finger finger;

    public DBChange(String username, Type type) {
        this.username = username;
        this.type = type;
        System.out.println("SERVER: Recording change of type " + type);
    }

    public DBChange(String username, Type type, Finger finger) {
        this.username = username;
        this.type = type;
        this.finger = finger;
        System.out.println("SERVER: Recording change of type " + type + " - " + finger);
    }
}
