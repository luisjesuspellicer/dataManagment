/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents psychological data of the
 * experimental subjects.
 */
package tfg.backend.DataModel;


import tfg.backend.DataModel.Ids.DataID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "PsychologicalData")
public class PsychologicalData implements Serializable {

    @EmbeddedId
    private DataID id;

    @Column(name = "anxietyEpisodes",nullable = false, columnDefinition = "TINYINT(1)")
    private boolean anxietyEpisodes;

    @Column(name = "lowMood", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean lowMood;

    @Column(name = "currentNerves")
    private int currentNerves;

    @Column(name = "currentStat")
    private int currentStat;

    @Column(name = "calm")
    private int calm;



    public int getCurrentStat() {
        return currentStat;
    }


    public void setCurrentStat(int currentStat) {
        this.currentStat = currentStat;
    }

    public boolean isAnxietyEpisodes() {
        return anxietyEpisodes;
    }

    public void setAnxietyEpisodes(boolean anxietyEpisodes) {
        this.anxietyEpisodes = anxietyEpisodes;
    }

    public boolean isLowMood() {
        return lowMood;
    }

    public void setLowMood(boolean lowMood) {
        this.lowMood = lowMood;
    }

    public int getCurrentNerves() {
        return currentNerves;
    }

    public void setCurrentNerves(int currentNerves) {
        this.currentNerves = currentNerves;
    }

    public int getCalm() {
        return calm;
    }

    public void setCalm(int calm) {
        this.calm = calm;
    }

    public DataID getId() {
        return id;
    }

    public void setId(DataID id) {
        this.id = id;
    }

    public PsychologicalData() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PsychologicalData that = (PsychologicalData) o;
        return
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, anxietyEpisodes, lowMood, currentNerves, currentStat, calm);
    }

    @Override
    public String toString() {
        return "\nPsychologicalData{" +
                "id=" + id +
                ", anxietyEpisodes=" + anxietyEpisodes +
                ", lowMood=" + lowMood +
                ", currentNerves=" + currentNerves +
                ", currentStat=" + currentStat +
                ", calm=" + calm +
                '}';
    }
}