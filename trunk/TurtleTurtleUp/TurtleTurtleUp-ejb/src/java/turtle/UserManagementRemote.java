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

    void login(String username, String password) throws Exception;

    void createNewUser(String username, String password) throws Exception;

    List<String> getConnectedPlayers() throws Exception;

    UserRecord getUserRecord(String username) throws Exception;

    void changePassword(String oldPassword, String newPassword) throws Exception;

    void joinGame() throws Exception;

    void leaveGame() throws Exception;

    void setServerLock(boolean enable) throws Exception;

    void promoteUser(String username) throws Exception;

    void kickPlayer(String username) throws Exception;

    void deleteUser(String username) throws Exception;

    void resetUserPassword(String username) throws Exception;

    void playTurn(Finger finger) throws Exception;
}
