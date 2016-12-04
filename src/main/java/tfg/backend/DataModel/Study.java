/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents one study and his principal characteristics.
 */
package tfg.backend.DataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Study")
public class Study implements Serializable{

    @Id
    @Column(name = "studyName")
    public String studyName;

    @Column(name = "description")
    public String description;

    @Column(name = "showW", nullable = false, columnDefinition = "TINYINT(1)")
    public boolean showW;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public boolean isShowW() {

        return showW;
    }

    public void setShowW(boolean showW) {

        this.showW = showW;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Study study = (Study) o;
        return Objects.equals(studyName, study.studyName) &&
                Objects.equals(description, study.description);
    }


    @Override
    public String toString() {
        return "Study{" +
                "studyName='" + studyName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
