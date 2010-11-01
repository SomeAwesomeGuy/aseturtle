/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("ERROR: Invalid password. Please try again.");
    }
}
