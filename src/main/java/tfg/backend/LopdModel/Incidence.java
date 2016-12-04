/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents an incidence into system.
 */
package tfg.backend.LopdModel;

import tfg.backend.DataModel.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Incidence")
public class Incidence implements Serializable{

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "typeIncidence")
    private String typeIncidence;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    private User user;

    @Column(name = "efectsAndProcedures")
    private String efectsAndProcedures;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getTypeIncidence() {

        return typeIncidence;
    }

    public void setTypeIncidence(String typeIncidence) {

        this.typeIncidence = typeIncidence;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    public String getEfectsAndProcedures() {

        return efectsAndProcedures;
    }

    public void setEfectsAndProcedures(String efectsAndProcedures) {

        this.efectsAndProcedures = efectsAndProcedures;
    }

}
