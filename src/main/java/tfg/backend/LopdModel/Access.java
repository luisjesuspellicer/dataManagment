/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents an access into application.
 */
package tfg.backend.LopdModel;

import tfg.backend.DataModel.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Access")
public class Access implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    // User who make the request.
    @ManyToOne
    User user;

    // Path of the access. Ex. /activity
    @Column(name = "access")
    String access;

    // Can be PUT, DELETE, POST, OR GET
    @Column(name = "typeAccess")
    String typeAccess;

    // Code that server return.
    @Column(name = "result")
    int result;

    @Column(name = "date")
    Date date;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getTypeAccess() {
        return typeAccess;
    }

    public void setTypeAccess(String typeAccess) {
        this.typeAccess = typeAccess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }
}
