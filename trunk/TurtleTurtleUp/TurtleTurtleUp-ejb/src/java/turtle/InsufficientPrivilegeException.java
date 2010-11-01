/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public class InsufficientPrivilegeException extends Exception {
    public InsufficientPrivilegeException() {
        super("ERROR: Access denied. The user must be an administrator to use this function.");
    }
}
