/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtleturtleup;

import javax.ejb.EJB;
import turtle.UserManagementRemote;


/**
 *
 * @author Sean
 */
public class Main {
    @EJB
    private static UserManagementRemote userManagement;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println(userManagement.getConnectedPlayers());
    }

}
