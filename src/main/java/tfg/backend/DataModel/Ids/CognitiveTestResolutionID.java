package tfg.backend.DataModel.Ids;
/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a complex key with foreign key.
 * The models that use this complex key are:
 * - CognitiveTestResolution
 */

import tfg.backend.DataModel.Subject;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class CognitiveTestResolutionID implements Serializable{

    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn
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
    public String toString() {
        return "CognitiveTestResolutionID{" +
                "id=" + id +
                ", numSubject=" + numSubject +
                '}';
    }
}
