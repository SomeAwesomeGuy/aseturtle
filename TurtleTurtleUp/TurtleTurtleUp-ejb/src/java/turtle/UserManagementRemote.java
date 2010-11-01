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

    List<String> getConnectedPlayers();

    UserRecord getUserRecord(String username) throws Exception;

    void changePassword(String oldPassword, String newPassword) throws Exception;

    void joinGame() throws Exception;

    void leaveGame();

    void setServerLock(boolean enable);

    void promoteUser(String username) throws Exception;

    void kickPlayer(String username);

    void deleteUser(String username) throws Exception;

    void resetUserPassword(String username) throws Exception;
}
