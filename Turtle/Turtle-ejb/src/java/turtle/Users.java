/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package turtle;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Sean
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByAdmin", query = "SELECT u FROM Users u WHERE u.admin = :admin"),
    @NamedQuery(name = "Users.findByGamesPlayed", query = "SELECT u FROM Users u WHERE u.gamesPlayed = :gamesPlayed"),
    @NamedQuery(name = "Users.findByGamesWon", query = "SELECT u FROM Users u WHERE u.gamesWon = :gamesWon"),
    @NamedQuery(name = "Users.findByRoundsPlayed", query = "SELECT u FROM Users u WHERE u.roundsPlayed = :roundsPlayed"),
    @NamedQuery(name = "Users.findByThumbCount", query = "SELECT u FROM Users u WHERE u.thumbCount = :thumbCount"),
    @NamedQuery(name = "Users.findByIndexCount", query = "SELECT u FROM Users u WHERE u.indexCount = :indexCount"),
    @NamedQuery(name = "Users.findByMiddleCount", query = "SELECT u FROM Users u WHERE u.middleCount = :middleCount"),
    @NamedQuery(name = "Users.findByRingCount", query = "SELECT u FROM Users u WHERE u.ringCount = :ringCount"),
    @NamedQuery(name = "Users.findByPinkieCount", query = "SELECT u FROM Users u WHERE u.pinkieCount = :pinkieCount"),
    @NamedQuery(name = "Users.findByRoundPercentSum", query = "SELECT u FROM Users u WHERE u.roundPercentSum = :roundPercentSum")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "admin")
    private boolean admin;
    @Column(name = "games_played")
    private Integer gamesPlayed;
    @Column(name = "games_won")
    private Integer gamesWon;
    @Column(name = "rounds_played")
    private Integer roundsPlayed;
    @Column(name = "thumb_count")
    private Integer thumbCount;
    @Column(name = "index_count")
    private Integer indexCount;
    @Column(name = "middle_count")
    private Integer middleCount;
    @Column(name = "ring_count")
    private Integer ringCount;
    @Column(name = "pinkie_count")
    private Integer pinkieCount;
    @Column(name = "round_percent_sum")
    private BigDecimal roundPercentSum;

    public Users() {
    }

    public Users(String username) {
        this.username = username;
    }

    public Users(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Integer getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(Integer roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public Integer getThumbCount() {
        return thumbCount;
    }

    public void setThumbCount(Integer thumbCount) {
        this.thumbCount = thumbCount;
    }

    public Integer getIndexCount() {
        return indexCount;
    }

    public void setIndexCount(Integer indexCount) {
        this.indexCount = indexCount;
    }

    public Integer getMiddleCount() {
        return middleCount;
    }

    public void setMiddleCount(Integer middleCount) {
        this.middleCount = middleCount;
    }

    public Integer getRingCount() {
        return ringCount;
    }

    public void setRingCount(Integer ringCount) {
        this.ringCount = ringCount;
    }

    public Integer getPinkieCount() {
        return pinkieCount;
    }

    public void setPinkieCount(Integer pinkieCount) {
        this.pinkieCount = pinkieCount;
    }

    public BigDecimal getRoundPercentSum() {
        return roundPercentSum;
    }

    public void setRoundPercentSum(BigDecimal roundPercentSum) {
        this.roundPercentSum = roundPercentSum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "turtle.Users[username=" + username + "]";
    }

}
