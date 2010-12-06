/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sean
 */
@Remote
public interface UserManagementRemote {

    boolean login(String username, String password) throws InvalidUsernameException, InvalidPasswordException;

    void createNewUser(String username, String password) throws InvalidUsernameException, InvalidPasswordException;

    List<String> getConnectedPlayers() throws UserNotLoggedInException;

    UserRecord getUserRecord(String username) throws InvalidUsernameException, UserNotLoggedInException;

    void changePassword(String oldPassword, String newPassword) throws InvalidPasswordException, UserNotLoggedInException;

    void joinGame() throws UserNotLoggedInException, ServerLockException;

    void leaveGame();

    void setServerLock(boolean enable) throws UserNotLoggedInException, InsufficientPrivilegeException;

    void promoteUser(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException;

    void kickPlayer(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException;

    void deleteUser(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException;

    void resetUserPassword(String username) throws InvalidUsernameException, UserNotLoggedInException, InsufficientPrivilegeException;

    void playTurn(Finger finger) throws UserNotLoggedInException, ServerLockException;

    boolean isLocked() throws UserNotLoggedInException;

    GameState poll() throws UserNotLoggedInException, ServerLockException;

    boolean isInGame() throws UserNotLoggedInException;

    List<String> getAllPlayers() throws UserNotLoggedInException;
}
