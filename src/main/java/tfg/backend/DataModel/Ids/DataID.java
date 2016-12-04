/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a complex key with foreign key.
 * The models that use this complex key are:
 * - ToxicHabits
 * - OtherHabits
 * - PsychologicalData
 * - PhysiologicalData
 */
package tfg.backend.DataModel.Ids;

import tfg.backend.DataModel.Subject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class DataID implements Serializable {

    @Column(name = "id")
    @GeneratedValue(generator="system-uuid")
    private Long id;


    @OneToOne
    @JoinColumn (name = "numSubject")
    private Subject numSubject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getNumSubject() {
        return numSubject;
    }

    public void setNumSubject(Subject numSubject) {
        this.numSubject = numSubject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataID dataID = (DataID) o;
        return Objects.equals(id, dataID.id) &&
                Objects.equals(numSubject, dataID.numSubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numSubject);
    }
}
