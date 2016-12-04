/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a request structure for add new subject of study.
 */
package tfg.backend.RequestsStructures;

import tfg.backend.DataModel.*;

import java.io.Serializable;

public class AllPersonData implements Serializable {

    private Subject subject;

    private ToxicHabits toxicHabits;

    private OtherHabits otherHabits;

    private PhysiologicalData physiologicalData;

    private PsychologicalData psychologicalData;

    public OtherHabits getOtherHabits() {
        return otherHabits;
    }

    public void setOtherHabits(OtherHabits otherHabits) {
        this.otherHabits = otherHabits;
    }

    public PhysiologicalData getPhysiologicalData() {
        return physiologicalData;
    }

    public void setPhysiologicalData(PhysiologicalData physiologicalData) {
        this.physiologicalData = physiologicalData;
    }

    public PsychologicalData getPsychologicalData() {
        return psychologicalData;
    }

    public void setPsychologicalData(PsychologicalData psychologicalData) {
        this.psychologicalData = psychologicalData;
    }

    public ToxicHabits getToxicHabits() {
        return toxicHabits;
    }

    public void setToxicHabits(ToxicHabits toxicHabits) {
        this.toxicHabits = toxicHabits;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
