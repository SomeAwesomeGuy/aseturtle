/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtleturtleup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import turtle.InvalidPasswordException;
import turtle.InvalidUsernameException;
import turtle.UserManagementRemote;


/**
 *
 * @author Sean
 */
public class Main {
    
    private static UserManagementRemote userManagement;
    private static boolean loginWait = true;
    private static final NewAccountFrame naf = new NewAccountFrame();
    private final static LoginFrame start = new LoginFrame();
    private final static TurtleFrame gui = new TurtleFrame();
    private static boolean isAdmin;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        Properties props = new Properties();

        props.setProperty("java.naming.factory.initial",
                            "com.sun.enterprise.naming.SerialInitContextFactory");

        props.setProperty("java.naming.factory.url.pkgs",
                            "com.sun.enterprise.naming");

        props.setProperty("java.naming.factory.state",
                            "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        props.setProperty("org.omg.CORBA.ORBInitialHost", "160.39.194.156");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        InitialContext ic = new InitialContext(props);
        userManagement = (UserManagementRemote) ic.lookup("turtle.UserManagementRemote");

        start.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO: check string for a-zA-z0-9
                String userName = start.userField.getText();
                String password = "";
                for (int i = 0; i < start.passField.getPassword().length; i++) {
                    password += start.passField.getPassword()[i];
                }

                try {
                    isAdmin = userManagement.login(userName, password);
                    start.setVisible(false);
                    gui.setVisible(true);
                } catch (InvalidUsernameException e) {
                    start.infoLabel.setText("Invalid Username");
                    start.userField.setText("");
                    start.passField.setText("");
                } catch (InvalidPasswordException e) {
                    start.infoLabel.setText("Incorrect Password");
                    start.passField.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

            }
        });
        start.signUpButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent arg0) {
                newAccount();
           }
        });

        System.out.println(System.getProperty("user.dir"));

        start.setVisible(true);

        while(loginWait);
    }

    private static void newAccount() {

        start.setVisible(false);
        naf.setVisible(true);

        naf.signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO: check string for a-zA-z0-9
                String userName = naf.userField.getText();
                String password = "";
                for (int i = 0; i < naf.passField.getPassword().length; i++) {
                    password += naf.passField.getPassword()[i];
                }

                try {
                    userManagement.createNewUser(userName, password);
                    naf.setVisible(false);
                    start.setVisible(true);
                } catch (InvalidUsernameException e) {
                    naf.infoLabel.setText("Sorry, that name has been taken");
                    naf.userField.setText("");
                    naf.passField.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
        naf.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                naf.setVisible(false);
                start.setVisible(true);
            }
        });
    }
}
