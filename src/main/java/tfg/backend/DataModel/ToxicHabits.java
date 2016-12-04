/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents toxical habits of the
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
@Table(name = "ToxicHabits")
public class ToxicHabits implements Serializable {

    @EmbeddedId
    private DataID id;

    @Column(name = "smoker", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean smoker;

    @Column(name = "dayCigarettes")
    private int dayCigarettes;

    @Column(name = "otherSubstances", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean otherSubstances;

    @Column(name = "otherSubstancesGr")
    private int otherSubstancesGr;

    @Column(name = "alcoholicDrinks", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean alcoholicDrinks;

    @Column(name = "litresOfAlcoholicDrinks")
    private double litresOfAlcoholicDrinks;

    public DataID getId() {
        return id;
    }

    public void setId(DataID id) {
        this.id = id;
    }

    public int getOtherSubstancesGr() {
        return otherSubstancesGr;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public int getDayCigarettes() {
        return dayCigarettes;
    }

    public void setDayCigarettes(int dayCigarettes) {
        this.dayCigarettes = dayCigarettes;
    }

    public boolean isOtherSubstances() {
        return otherSubstances;
    }

    public void setOtherSubstances(boolean otherSubstances) {
        this.otherSubstances = otherSubstances;
    }

    public int isOtherSubstancesGr() {
        return otherSubstancesGr;
    }

    public void setOtherSubstancesGr(int otherSubstancesGr) {
        this.otherSubstancesGr = otherSubstancesGr;
    }

    public boolean isAlcoholicDrinks() {
        return alcoholicDrinks;
    }

    public void setAlcoholicDrinks(boolean alcoholicDrinks) {
        this.alcoholicDrinks = alcoholicDrinks;
    }

    public double getLitresOfAlcoholicDrinks() {
        return litresOfAlcoholicDrinks;
    }

    public void setLitresOfAlcoholicDrinks(double litresOfAlcoholicDrinks) {
        this.litresOfAlcoholicDrinks = litresOfAlcoholicDrinks;
    }

    public ToxicHabits() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToxicHabits that = (ToxicHabits) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "\nToxicHabits{" +
                "id=" + id +
                ", smoker=" + smoker +
                ", dayCigarettes=" + dayCigarettes +
                ", otherSubstances=" + otherSubstances +
                ", otherSubstancesGr=" + otherSubstancesGr +
                ", alcoholicDrinks=" + alcoholicDrinks +
                ", litresOfAlcoholicDrinks=" + litresOfAlcoholicDrinks +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, smoker, dayCigarettes, otherSubstances, otherSubstancesGr, alcoholicDrinks, litresOfAlcoholicDrinks);
    }
}