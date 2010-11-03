/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

/**
 *
 * @author Sean
 */
public class ServerLockException extends Exception {
    public ServerLockException() {
        super("ERROR: Server is locked. The server must be unlocked to perform this function.");
    }
}
