/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtleturtleup;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JFrame;

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
    private final static AdminFrame agui = new AdminFrame();
    private final static StatFrame sgui = new StatFrame();

    private static String userName;
    private static GameState gameState = null;
    private static boolean isAdmin;
    private static boolean isAdminOpen = false;
    private static boolean isStatPageOpen = false;
    private static boolean inGame = false;
    private static boolean isPolling = false;
    private static String roundLeader = "";
    private static String timeRemaining = "";
    private static String nextAction = "";

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
                    System.err.println(e.toString());
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

        while(run) {
            System.out.print("");
            while (isPolling) {
                System.out.print("");
                run = true;
                poll();
            }
        }
    }

    private static void game() {
        gui.setVisible(true);
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
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
                            if (inGame) {
                                try {
                                    if (j == 0) {
                                        userManagement.playTurn(Finger.THUMB);
                                    } else if (j == 1) {
                                        userManagement.playTurn(Finger.INDEX);
                                    } else if (j == 2) {
                                        userManagement.playTurn(Finger.MIDDLE);
                                    } else if (j == 3) {
                                        userManagement.playTurn(Finger.RING);
                                    } else if (j == 4) {
                                        userManagement.playTurn(Finger.PINKIE);
                                    }
                                } catch (Exception e) {
                                    System.err.println(e);
                                    exit();
                                }
                            }
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
                String uid = naf.userField.getText();
                String password = "";
                for (int i = 0; i < naf.passField.getPassword().length; i++) {
                    password += naf.passField.getPassword()[i];
                }

                try {
                    userManagement.createNewUser(uid, password);
                    naf.setVisible(false);
                    start.setVisible(true);
                } catch (InvalidUsernameException e) {
                    naf.infoLabel.setText("Sorry, that name is invalid or taken");
                    naf.userField.setText("");
                    naf.passField.setText("");
                } catch (InvalidPasswordException e) {
                    naf.infoLabel.setText("Invalid password");
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
         if (!isStatPageOpen) {
            isStatPageOpen = true;
            sgui.setVisible(true);
            
            sgui.infoLabel.setFont(NORMAL_FONT);

            sgui.findButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        String u = sgui.userField.getText();
                        UserRecord rec = userManagement.getUserRecord(u);
                        String info = "<html><table border=\"1\"><tr><td><b><font size = 4> Name&nbsp&nbsp&nbsp</b></td>" +
                            "<td><b><font size = 4> Games<br>Played</b></font></td>" +
                            "<td><b><font size = 4> Win %</b></font></td>" +
                            "<td><b><font size = 4> Thumb %</b></font></td>" +
                            "<td><b><font size = 4> Index %</b></font></td>" +
                            "<td><b><font size = 4> Middle %</b></font></td>" +
                            "<td><b><font size = 4> Ring %</b></font></td>" +
                            "<td><b><font size = 4> Pinky %</b></font></td></tr>";

                        info += "<tr><td><font size = 4>" + u + "</td>" +
                            "<td><font size = 4>" + rec.getGamesPlayed() + "</font></td>" +
                            "<td><font size = 4>" + ((int) (rec.getWinPerc() * 100)) + "</font></td>" +
                            "<td><font size = 4>" + ((int) (rec.getThumbPerc() * 100)) + "</font></td>" +
                            "<td><font size = 4>" + ((int) (rec.getIndexPerc() * 100)) + "</font></td>" +
                            "<td><font size = 4>" + ((int) (rec.getMiddlePerc() * 100)) + "</font></td>" +
                            "<td><font size = 4>" + ((int) (rec.getRingPerc() * 100)) + "</font></td>" +
                            "<td><font size = 4>" + ((int) (rec.getPinkiePerc() * 100)) + "</font></td></tr></html>";

                        sgui.infoLabel.setText(info);
                    } catch (InvalidUsernameException e) {
                        sgui.infoLabel.setText("Could not find user");
                    }catch (Exception e) {
                        System.err.print(e);
                        exit();
                    }
                }
            });
            sgui.doneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    statExit();
                }
            });
            sgui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            sgui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    statExit();
                }
            });
        }
    }

    private static void statExit() {
        isStatPageOpen = false;
        sgui.setVisible(false);
    }

    private static void spectate() {
        leaveGame();
        isPolling = true;
    }

    private static void join() {
        isPolling = true;
        inGame = true;
        try {
            userManagement.joinGame();
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            exit();
        }
    }

    private static void admin() {
        if (!isAdminOpen) {
            isAdminOpen = true;
            agui.setVisible(true);
            try {
                agui.lockServerCheckBox.setSelected(userManagement.isLocked());
            } catch (Exception e) {
                System.err.print(e);
                e.printStackTrace();
                exit();
            }

            try {
            userManagement.deleteUser("hi");
            userManagement.kickPlayer("hi");
            userManagement.promoteUser("hi");
            userManagement.resetUserPassword("hi");
            } catch (Exception e) {

            }


            agui.okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        userManagement.setServerLock(agui.lockServerCheckBox.isSelected());
                    } catch (Exception e) {
                        System.err.print(e);
                        exit();
                    }
                    adminExit();
                }
            });
            agui.cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    adminExit();
                }
            });
            agui.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            agui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    adminExit();
                }
            });
        }
    }

    private static void adminExit() {
        isAdminOpen = false;
        agui.setVisible(false);
    }

    private static void poll() {
        try {
            Thread.sleep(250);
            gameState = userManagement.poll();
            String l = gameState.getCurrLeader();
            roundLeader = "Leader: ";
            if (l != null) {
                roundLeader += l;
            }
            if (gameState.getTimeLeft() == 0) {
                resetButtons();
            } else {
                timeRemaining = "Time left in round : " + (gameState.getTimeLeft() - 1);
            }
            if (gameState.getStatus() == GameState.Status.WAITING) {
                nextAction = "Waiting for game to start...";
            } else if (gameState.getStatus() == GameState.Status.WINNER) {
                nextAction = "Game Over! " + l + " won!";
            } else if (userManagement.isInGame()) {
                nextAction = "Pick a finger!";
            } else if (gameState.getEliminated().contains(userName)) {
                inGame = false;
                nextAction = "You were eliminated!";
            } 
            gui.actionLabel.setText("<html>" + roundLeader + "<br>" +
                    timeRemaining + "<br>" +
                    nextAction + "</html>");

            String infoHeader = "<html><tr><td><b><font size = 4> Player&nbsp&nbsp&nbsp</b></td>" +
                    "<td><b><font size = 4> Thumb</b></font></td>" +
                    "<td><b><font size = 4> Index</b></font></td>" +
                    "<td><b><font size = 4> Middle</b></font></td>" +
                    "<td><b><font size = 4> Ring</b></font></td>" +
                    "<td><b><font size = 4> Pinky</b></font></td></tr><tr></tr>";
            for (int i = 0; i < gameState.getPlayedGame().size(); i++) {
                if(gameState.getFingerMap().get(gameState.getPlayedGame().get(i)) == null) {
                    infoHeader += "<tr bgcolor=\"#ff0000\"> <td><font size = 4>" + gameState.getPlayedGame().get(i) + "</td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td></tr>";
                } else if(gameState.getFingerMap().get(gameState.getPlayedGame().get(i)) == Finger.THUMB) {
                    infoHeader += "<tr bgcolor=\"#00ff00\"> <td><font size = 4>" + gameState.getOldPlayers().get(i) + "</td>" +
                            "<td><font size = 4><CENTER>x</CENTER></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td></tr>";
                } else if(gameState.getFingerMap().get(gameState.getPlayedGame().get(i)) == Finger.INDEX) {
                    infoHeader += "<tr bgcolor=\"#00ff00\"> <td><font size = 4>" + gameState.getOldPlayers().get(i) + "</td>" +
                            "<td></td>" +
                            "<td><font size = 4><CENTER>x</CENTER></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td></tr>";
                } else if(gameState.getFingerMap().get(gameState.getPlayedGame().get(i)) == Finger.MIDDLE) {
                    infoHeader += "<tr bgcolor=\"#00ff00\"> <td><font size = 4>" + gameState.getOldPlayers().get(i) + "</td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td><font size = 4><CENTER>x</CENTER></td>" +
                            "<td></td>" +
                            "<td></td></tr>";
                } else if(gameState.getFingerMap().get(gameState.getPlayedGame().get(i)) == Finger.RING) {
                    infoHeader += "<tr bgcolor=\"#00ff00\"> <td><font size = 4>" + gameState.getOldPlayers().get(i) + "</td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td><font size = 4><CENTER>x</CENTER></td>" +
                            "<td></td></tr>";
                } else if(gameState.getFingerMap().get(gameState.getPlayedGame().get(i)) == Finger.PINKIE) {
                    infoHeader += "<tr bgcolor=\"#00ff00\"> <td><font size = 4>" + gameState.getOldPlayers().get(i) + "</td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td></td>" +
                            "<td><font size = 4><CENTER>x</CENTER></td></tr>";
                }
            }
            infoHeader += "</html>";
            gui.infoLabel.setText(infoHeader);
            
        } catch (InterruptedException e) {
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            exit();
        }
    }

    private static void leaveGame() {
        userManagement.leaveGame();
        inGame = false;
    }

    private static void exit() {
        leaveGame();
        System.exit(0);
    }
}
