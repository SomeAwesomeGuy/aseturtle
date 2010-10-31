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
    @NamedQuery(name = "DBUsers.findAll", query = "SELECT d FROM DBUsers d"),
    @NamedQuery(name = "DBUsers.findByUsername", query = "SELECT d FROM DBUsers d WHERE d.username = :username"),
    @NamedQuery(name = "DBUsers.findByPassword", query = "SELECT d FROM DBUsers d WHERE d.password = :password"),
    @NamedQuery(name = "DBUsers.findByIsAdmin", query = "SELECT d FROM DBUsers d WHERE d.isAdmin = :isAdmin"),
    @NamedQuery(name = "DBUsers.findByGamesPlayed", query = "SELECT d FROM DBUsers d WHERE d.gamesPlayed = :gamesPlayed"),
    @NamedQuery(name = "DBUsers.findByGamesWon", query = "SELECT d FROM DBUsers d WHERE d.gamesWon = :gamesWon"),
    @NamedQuery(name = "DBUsers.findByRoundsPlayed", query = "SELECT d FROM DBUsers d WHERE d.roundsPlayed = :roundsPlayed"),
    @NamedQuery(name = "DBUsers.findByThumbCount", query = "SELECT d FROM DBUsers d WHERE d.thumbCount = :thumbCount"),
    @NamedQuery(name = "DBUsers.findByIndexCount", query = "SELECT d FROM DBUsers d WHERE d.indexCount = :indexCount"),
    @NamedQuery(name = "DBUsers.findByMiddleCount", query = "SELECT d FROM DBUsers d WHERE d.middleCount = :middleCount"),
    @NamedQuery(name = "DBUsers.findByRingCount", query = "SELECT d FROM DBUsers d WHERE d.ringCount = :ringCount"),
    @NamedQuery(name = "DBUsers.findByPinkieCount", query = "SELECT d FROM DBUsers d WHERE d.pinkieCount = :pinkieCount"),
    @NamedQuery(name = "DBUsers.findByRoundPercentSum", query = "SELECT d FROM DBUsers d WHERE d.roundPercentSum = :roundPercentSum")})
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "isAdmin")
    private boolean isAdmin;
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

    public UserEntity() {
    }

    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
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
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "turtle.DBUsers[username=" + username + "]";
    }

}
