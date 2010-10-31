/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sean
 */
@Stateful
public class UserManagement implements UserManagementRemote {
    @PersistenceContext(unitName = "TurtleTurtleUp-ejbPU")
    private EntityManager em;

    public void login(String username, String password) throws Exception {

    }

    public void createNewUser(String username, String password) throws Exception {
        
    }

    // TODO: poll()

    public List<String> getConnectedPlayers() {
        return null;
    }

    public UserRecord getUserRecord(String username) throws Exception {
        return null;
    }

    public void changePassword(String oldPassword, String newPassword) throws Exception {
    }

    public void joinGame() throws Exception {
    }

    public void leaveGame() {
    }

    public void setServerLock(boolean enable) {
    }

    public void promoteUser(String username) throws Exception {
    }

    public void kickPlayer(String username) {
    }

    public void deleteUser(String username) throws Exception {
    }

    public void resetUserPassword(String username) throws Exception {
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
