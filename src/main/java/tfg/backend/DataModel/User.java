/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents user's information.
 */
package tfg.backend.DataModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "User")
public class User implements Serializable {

    @Id
    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "expiredPassword")
    private Date expiredPassword;

    @Column(name = "privileges")
    private String privileges;

    @Column(name = "token")
    public String token;

    @Column(name = "email")
    public String email;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getExpiredPassword() {
        return expiredPassword;
    }

    public void setExpiredPassword(Date expiredPassword) {
        this.expiredPassword = expiredPassword;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(userName, password, expiredPassword, privileges);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", privileges='" + privileges + '\'' +
                ", expiredPassword=" + expiredPassword +
                '}';
    }
}
