/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.io.Serializable;

/**
 *
 * @author Sean
 */
public class UserRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private int gamesPlayed;
    private int gamesWon;
    private double winPerc;
    private double thumbPerc, indexPerc, middlePerc, ringPerc, pinkiePerc;
    private double roundsPerc;

    public UserRecord(UserEntity user) {
        gamesPlayed = user.getGamesPlayed();
        gamesWon = user.getGamesWon();

        if(gamesPlayed == 0) {
            winPerc = 0;
            roundsPerc = 0;
        }
        else {
            winPerc = gamesWon / (double)gamesPlayed;
            roundsPerc = user.getRoundPercentSum().doubleValue() / gamesPlayed;
        }

        double roundsPlayed = user.getRoundsPlayed();
        System.out.println(roundsPlayed);
        if(roundsPlayed == 0) {
            thumbPerc = 0;
            indexPerc = 0;
            middlePerc = 0;
            ringPerc = 0;
            pinkiePerc = 0;
        }
        else {
            thumbPerc = user.getThumbCount() / roundsPlayed;
            indexPerc = user.getIndexCount() / roundsPlayed;
            middlePerc = user.getMiddleCount() / roundsPlayed;
            ringPerc = user.getRingCount() / roundsPlayed;
            pinkiePerc = user.getPinkieCount() / roundsPlayed;
        }
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public double getIndexPerc() {
        return indexPerc;
    }

    public double getMiddlePerc() {
        return middlePerc;
    }

    public double getPinkiePerc() {
        return pinkiePerc;
    }

    public double getRingPerc() {
        return ringPerc;
    }

    public double getRoundsPerc() {
        return roundsPerc;
    }

    public double getThumbPerc() {
        return thumbPerc;
    }

    public double getWinPerc() {
        return winPerc;
    }
}
