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

    @Override
    public void login(String username, String password) throws Exception {

    }

    @Override
    public void createNewUser(String username, String password) throws Exception {
        
    }

    // TODO: poll()

    @Override
    public List<String> getConnectedPlayers() {
        return null;
    }

    @Override
    public UserRecord getUserRecord(String username) throws Exception {
        return null;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) throws Exception {
    }

    @Override
    public void joinGame() throws Exception {
    }

    @Override
    public void leaveGame() {
    }

    @Override
    public void setServerLock(boolean enable) {
    }

    @Override
    public void promoteUser(String username) throws Exception {
    }

    @Override
    public void kickPlayer(String username) {
    }

    @Override
    public void deleteUser(String username) throws Exception {
    }

    @Override
    public void resetUserPassword(String username) throws Exception {
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
