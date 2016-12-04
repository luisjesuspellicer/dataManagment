/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a base line of a subject.
 */
package tfg.backend.DataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "BaseLine")
public class BaseLine implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    @Column(name = "anotaciones")
    private String anotaciones;

    @ManyToOne
    @JoinColumn(name = "numSubjectOne")
    private Subject numSubjectOne;


    @ManyToOne
    @JoinColumn(name = "numSubjectTwo")
    private Subject numSubjectTwo;


    public String getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(String anotaciones) {
        this.anotaciones = anotaciones;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getNumSubjectOne() {
        return numSubjectOne;
    }

    public void setNumSubjectOne(Subject numSubjectOne) {
        this.numSubjectOne = numSubjectOne;
    }

    public Subject getNumSubjectTwo() {
        return numSubjectTwo;
    }

    public void setNumSubjectTwo(Subject numSubjectTwo) {
        this.numSubjectTwo = numSubjectTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseLine baseLine = (BaseLine) o;
        return Objects.equals(id, baseLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
