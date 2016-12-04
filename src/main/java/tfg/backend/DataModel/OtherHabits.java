/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents other habits of the
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
@Table(name = "OtherHabits")
public class
OtherHabits implements Serializable {

    @EmbeddedId
    private DataID id;
    //@Id
    //private int id;

    @Column(name = "caffeineConsumption", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean caffeineConsumption;

    @Column(name = "caffeineConsumptionGrams")
    private int caffeineConsumptionGr;

    @Column(name = "carbonatedDrinks", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean carbonatedDrinks;

    @Column(name = "carbonatedDrinksLitres")
    private double carbonatedDrinksLitres;

    @Column(name = "consumedInTheLasHours", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean consumedInTheLasHours;

    @Column(name = "whatConsumedInTheLastHours")
    private String whatConsumedInTheLastHours;

    @Column(name = "playSports", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean playSports;

    @Column(name = "kindOfSports")
    private String kindOfSports;

    @Column(name = "timesPerWeekPlaySports")
    private int numberHoursPerWeekPlaySports;

    @Column(name = "playDiving", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean playDiving;

    @Column(name = "experienceDivingAges")
    private int experienceDivingAges;

    @Column(name = "inmersionsPerYear")
    private int inmersionsPerYear;

    // Normal sleep
    @Column(name = "sleepingHours")
    private int sleepingHours;

    // Really be sleeping
    @Column(name = "reallyBeSleep")
    private int reallyBeSleep;

    @Column(name = "difficultySleeping", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean difficultySleeping;

    @Column(name = "difficultySleepingDescription")
    private String difficultySleepingDescription;

    @Column(name = "treatmentForSleepProblems")
    private String treatmentForSleepProblems;


    public boolean isCaffeineConsumption() {
        return caffeineConsumption;
    }

    public void setCaffeineConsumption(boolean caffeineConsumption) {
        this.caffeineConsumption = caffeineConsumption;
    }

    public int getCaffeineConsumptionGr() {
        return caffeineConsumptionGr;
    }

    public void setCaffeineConsumptionGr(int caffeineConsumptionGr) {
        this.caffeineConsumptionGr = caffeineConsumptionGr;
    }

    public boolean isCarbonatedDrinks() {
        return carbonatedDrinks;
    }

    public void setCarbonatedDrinks(boolean carbonatedDrinks) {
        this.carbonatedDrinks = carbonatedDrinks;
    }

    public double getCarbonatedDrinksLitres() {
        return carbonatedDrinksLitres;
    }

    public void setCarbonatedDrinksLitres(double carbonatedDrinksLitres) {
        this.carbonatedDrinksLitres = carbonatedDrinksLitres;
    }

    public boolean isConsumedInTheLasHours() {
        return consumedInTheLasHours;
    }

    public void setConsumedInTheLasHours(boolean consumedInTheLasHours) {
        this.consumedInTheLasHours = consumedInTheLasHours;
    }

    public String getWhatConsumedInTheLastHours() {
        return whatConsumedInTheLastHours;
    }

    public void setWhatConsumedInTheLastHours(String whatConsumedInTheLastHours) {
        this.whatConsumedInTheLastHours = whatConsumedInTheLastHours;
    }

    public boolean isPlaySports() {
        return playSports;
    }

    public void setPlaySports(boolean playSports) {
        this.playSports = playSports;
    }

    public String getKindOfSports() {
        return kindOfSports;
    }

    public void setKindOfSports(String kindOfSports) {
        this.kindOfSports = kindOfSports;
    }

    public int getNumberHoursPerWeekPlaySports() {
        return numberHoursPerWeekPlaySports;
    }

    public void setNumberHoursPerWeekPlaySports(int numberHoursPerWeekPlaySports) {
        this.numberHoursPerWeekPlaySports = numberHoursPerWeekPlaySports;
    }

    public boolean isPlayDiving() {
        return playDiving;
    }

    public void setPlayDiving(boolean playDiving) {
        this.playDiving = playDiving;
    }

    public int getExperienceDivingAges() {
        return experienceDivingAges;
    }

    public void setExperienceDivingAges(int experienceDivingAges) {
        this.experienceDivingAges = experienceDivingAges;
    }

    public int getInmersionsPerYear() {
        return inmersionsPerYear;
    }

    public void setInmersionsPerYear(int inmersionsPerYear) {
        this.inmersionsPerYear = inmersionsPerYear;
    }

    public int getSleepingHours() {
        return sleepingHours;
    }

    public void setSleepingHours(int sleepingHours) {
        this.sleepingHours = sleepingHours;
    }

    public int getReallyBeSleep() {
        return reallyBeSleep;
    }

    public void setReallyBeSleep(int reallyBeSleep) {
        this.reallyBeSleep = reallyBeSleep;
    }

    public boolean isDifficultySleeping() {
        return difficultySleeping;
    }

    public void setDifficultySleeping(boolean difficultySleeping) {
        this.difficultySleeping = difficultySleeping;
    }

    public String getDifficultySleepingDescription() {
        return difficultySleepingDescription;
    }

    public void setDifficultySleepingDescription(String difficultySleepingDescription) {
        this.difficultySleepingDescription = difficultySleepingDescription;
    }

    public String getTreatmentForSleepProblems() {
        return treatmentForSleepProblems;
    }

    public void setTreatmentForSleepProblems(String treatmentForSleepProblems) {
        this.treatmentForSleepProblems = treatmentForSleepProblems;
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
        OtherHabits that = (OtherHabits) o;
        return Objects.equals(id, that.id);

    }

    @Override
    public String toString() {
        return "\nOtherHabits{" +
                "id=" + id +
                ", \ncaffeineConsumption=" + caffeineConsumption +
                ", caffeineConsumptionGr=" + caffeineConsumptionGr +
                ", carbonatedDrinks=" + carbonatedDrinks +
                ", carbonatedDrinksLitres=" + carbonatedDrinksLitres +
                ", \nconsumedInTheLasHours=" + consumedInTheLasHours +
                ", whatConsumedInTheLastHours='" + whatConsumedInTheLastHours + '\'' +
                ", playSports=" + playSports +
                ", kindOfSports='" + kindOfSports + '\'' +
                ", numberHoursPerWeekPlaySports=" + numberHoursPerWeekPlaySports +
                ", \nplayDiving=" + playDiving +
                ", experienceDivingAges=" + experienceDivingAges +
                ", inmersionsPerYear=" + inmersionsPerYear +
                ", \nsleepingHours=" + sleepingHours +
                ", reallyBeSleep=" + reallyBeSleep +
                ", difficultySleeping=" + difficultySleeping +
                ", difficultySleepingDescription='" + difficultySleepingDescription + '\'' +
                ", treatmentForSleepProblems='" + treatmentForSleepProblems + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caffeineConsumption, caffeineConsumptionGr, carbonatedDrinks, carbonatedDrinksLitres, consumedInTheLasHours, whatConsumedInTheLastHours, playSports, kindOfSports, numberHoursPerWeekPlaySports, playDiving, experienceDivingAges, inmersionsPerYear, sleepingHours, reallyBeSleep, difficultySleeping, difficultySleepingDescription, treatmentForSleepProblems);
    }
}