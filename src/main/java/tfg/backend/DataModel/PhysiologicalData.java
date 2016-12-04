/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents physiological data of the
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
@Table(name = "PhysiologicalData")
public class PhysiologicalData implements Serializable {

    @EmbeddedId
    private DataID id;

    @Column(name = "heightCm")
    private int heightCm;

    @Column(name = "weigthKg")
    private double weigthKg;

    @Column(name = "medication", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean medication;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "menstruation", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean menstruation;

    @Column(name = "painfulMenstruation")
    private String painfulMenstruation;

    @Column(name = "menstruationTreatment")
    private String menstruationTreatment;

    @Column(name = "pulsations")
    private int pulsations;

    @Column(name = "bloodPressure")
    private int bloodPressure;

    @Column(name = "sensoryDifficulties", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean sensoryDifficulties;

    @Column(name = "sensoryDifficultiesDescription")
    private String sensoryDifficultiesDescription;

    @Column(name = "pain" , nullable = false, columnDefinition = "TINYINT(1)")
    private boolean pain;

    @Column(name = "periodPain")
    private String periodPain;

    @Column(name = "painZone")
    private String painZone;

    @Column(name = "degreeOfPain")
    private String degreeOfPain;

    @Column(name = "feelingOfHealth")
    private String feelingOfHealth;

    @Column(name = "nuisanceDescription")
    private String nuisanceDescription;


    public PhysiologicalData() {
    }



    public int getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(int heightCm) {
        this.heightCm = heightCm;
    }

    public double getWeigthKg() {
        return weigthKg;
    }

    public void setWeigthKg(double weigthKg) {
        this.weigthKg = weigthKg;
    }

    public boolean isMedication() {
        return medication;
    }

    public void setMedication(boolean medication) {
        this.medication = medication;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public boolean isMenstruation() {
        return menstruation;
    }

    public void setMenstruation(boolean menstruation) {
        this.menstruation = menstruation;
    }

    public String getPainfulMenstruation() {
        return painfulMenstruation;
    }

    public void setPainfulMenstruation(String painfulMenstruation) {
        this.painfulMenstruation = painfulMenstruation;
    }

    public String getMenstruationTreatment() {
        return menstruationTreatment;
    }

    public void setMenstruationTreatment(String menstruationTreatment) {
        this.menstruationTreatment = menstruationTreatment;
    }

    public int getPulsations() {
        return pulsations;
    }

    public void setPulsations(int pulsations) {
        this.pulsations = pulsations;
    }

    public int getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(int bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public boolean isSensoryDifficulties() {
        return sensoryDifficulties;
    }

    public void setSensoryDifficulties(boolean sensoryDifficulties) {
        this.sensoryDifficulties = sensoryDifficulties;
    }

    public String getSensoryDifficultiesDescription() {
        return sensoryDifficultiesDescription;
    }

    public void setSensoryDifficultiesDescription(String sensoryDifficultiesDescription) {
        this.sensoryDifficultiesDescription = sensoryDifficultiesDescription;
    }

    public boolean isPain() {
        return pain;
    }

    public void setPain(boolean pain) {
        this.pain = pain;
    }

    public String getPeriodPain() {
        return periodPain;
    }

    public void setPeriodPain(String periodPain) {
        this.periodPain = periodPain;
    }

    public String getPainZone() {
        return painZone;
    }

    public void setPainZone(String painZone) {
        this.painZone = painZone;
    }

    public String getDegreeOfPain() {
        return degreeOfPain;
    }

    public void setDegreeOfPain(String degreeOfPain) {
        this.degreeOfPain = degreeOfPain;
    }

    public String getFeelingOfHealth() {
        return feelingOfHealth;
    }

    public void setFeelingOfHealth(String feelingOfHealth) {
        this.feelingOfHealth = feelingOfHealth;
    }

    public String getNuisanceDescription() {
        return nuisanceDescription;
    }

    public void setNuisanceDescription(String nuisanceDescription) {
        this.nuisanceDescription = nuisanceDescription;
    }

    public DataID getId() {
        return id;
    }

    public void setId(DataID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysiologicalData that = (PhysiologicalData) o;
        return Objects.equals(id, that.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heightCm, weigthKg, medication, treatment, menstruation, painfulMenstruation, menstruationTreatment, pulsations, bloodPressure, sensoryDifficulties, sensoryDifficultiesDescription, pain, periodPain, painZone, degreeOfPain, feelingOfHealth, nuisanceDescription);
    }

    @Override
    public String toString() {
        return "\nPhysiologicalData{" +
                "id=" + id +
                ", heightCm=" + heightCm +
                ", weigthKg=" + weigthKg +
                ", medication=" + medication +
                ", treatment='" + treatment + '\'' +
                ", \nmenstruation=" + menstruation +
                ", painfulMenstruation='" + painfulMenstruation + '\'' +
                ", menstruationTreatment='" + menstruationTreatment + '\'' +
                ", pulsations=" + pulsations +
                ", bloodPressure=" + bloodPressure +
                ", \nsensoryDifficulties=" + sensoryDifficulties +
                ", sensoryDifficultiesDescription='" + sensoryDifficultiesDescription + '\'' +
                ", pain=" + pain +
                ", \nperiodPain='" + periodPain + '\'' +
                ", painZone='" + painZone + '\'' +
                ", degreeOfPain='" + degreeOfPain + '\'' +
                ", feelingOfHealth='" + feelingOfHealth + '\'' +
                ", nuisanceDescription='" + nuisanceDescription + '\'' +
                '}';
    }
}
