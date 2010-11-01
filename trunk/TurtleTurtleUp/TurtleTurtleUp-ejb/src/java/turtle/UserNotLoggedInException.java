/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException() {
        super("ERROR: Access denied. The user must be logged in to use this function.");
    }
}
