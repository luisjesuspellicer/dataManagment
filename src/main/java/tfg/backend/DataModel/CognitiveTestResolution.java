/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a cognitive test resolution of a subject in one study.
 */
package tfg.backend.DataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tfg.backend.DataModel.Ids.CognitiveTestResolutionID;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CognitiveTestResolution")
public class CognitiveTestResolution implements Serializable{

    @EmbeddedId
    private CognitiveTestResolutionID test_id_resolution;

    @ManyToOne
    @JoinColumn(name = "test_id")
    CognitiveTest test_id;

    public CognitiveTestResolutionID getTest_id_resolution() {
        return test_id_resolution;
    }

    public void setTest_id_resolution(CognitiveTestResolutionID test_id_resolution) {
        this.test_id_resolution = test_id_resolution;
    }

    public CognitiveTest getTest_id() {
        return test_id;
    }

    public void setTest_id(CognitiveTest test_id) {
        this.test_id = test_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CognitiveTestResolution that = (CognitiveTestResolution) o;
        return test_id_resolution == that.test_id_resolution;
    }

    @Override
    public String toString() {
        return "CognitiveTestResolution{" +
                "test_id_resolution=" + test_id_resolution.toString() +
                ", test_id=" + test_id.toString() +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(test_id_resolution);
    }
}
