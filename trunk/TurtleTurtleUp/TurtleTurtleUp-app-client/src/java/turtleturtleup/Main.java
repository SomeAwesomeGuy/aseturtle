/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtleturtleup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.swing.JButton;

import turtle.*;


/**
 *
 * @author blinnc
 */
public class Main {

    static final int NUMBER_OF_FINGERS = 5;
    static final Font NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
    static final Font BOLD_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 11);

    private static UserManagementRemote userManagement;
    private static boolean run = true;
    private static final NewAccountFrame naf = new NewAccountFrame();
    private final static LoginFrame start = new LoginFrame();
    private final static TurtleFrame gui = new TurtleFrame();

    private static String userName;
    private static boolean isAdmin;
    private static boolean isStatPageOpen;

    private static JButton[] fingerButtons = new JButton[NUMBER_OF_FINGERS];
    private static int fingerPress = -1;

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

        start.infoLabel.setFont(NORMAL_FONT);
        naf.infoLabel.setFont(NORMAL_FONT);
        start.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO: check string for a-zA-z0-9
                String uid = start.userField.getText();
                String password = "";
                for (int i = 0; i < start.passField.getPassword().length; i++) {
                    password += start.passField.getPassword()[i];
                }

                try {
                    isAdmin = userManagement.login(uid, password);
                    userName = uid;
                    start.setVisible(false);
                    game();
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

        start.setVisible(true);

        while(run);
    }

    private static void game() {
        gui.setVisible(true);
        gui.adminButton.setVisible(isAdmin);

        fingerButtons[0] = gui.thumbButton;
        fingerButtons[1] = gui.indexButton;
        fingerButtons[2] = gui.middleButton;
        fingerButtons[3] = gui.ringButton;
        fingerButtons[4] = gui.pinkyButton;
        resetButtons();

        gui.statButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                stats();
            }
        });

        gui.joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                join();
            }
        });

        gui.spectateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                spectate();
            }
        });

        gui.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(1);
            }
        });

        if (isAdmin) {
            gui.adminButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    admin();
                }
            });
        }

        for (int i = 0; i < NUMBER_OF_FINGERS; i++) {
            fingerButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    resetButtons();
                    for (int j = 0; j < NUMBER_OF_FINGERS; j++) {
                        if (fingerButtons[j].equals(arg0.getSource())) {
                            fingerPress = j;
                        }   
                    }
                    fingerButtons[fingerPress].setFont(BOLD_FONT);
                    fingerButtons[fingerPress].setForeground(Color.darkGray);
                }
            });
        }
    }

    private static void resetButtons() {
        fingerPress = -1;
        for (int i = 0; i < NUMBER_OF_FINGERS; i++) {
            fingerButtons[i].setFont(NORMAL_FONT);
            fingerButtons[i].setForeground(Color.lightGray);
        }
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

    private static void stats() {

    }

    private static void spectate() {

    }

    private static void join() {

    }

    private static void admin() {

    }
}
