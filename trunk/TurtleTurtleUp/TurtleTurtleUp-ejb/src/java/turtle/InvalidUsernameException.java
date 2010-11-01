/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public class InvalidUsernameException extends Exception {
    public InvalidUsernameException() {
        super("ERROR: Invalid username. Please try again.");
    }
}
