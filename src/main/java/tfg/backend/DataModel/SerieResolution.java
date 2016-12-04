/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents one serie of cognitive tests.
 */

package tfg.backend.DataModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SerieResolution")
public class SerieResolution implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numSerieResolution")
    Long numSerieResolution;

    @Column(name = "sequence")
    String sequence;

    @Column(name = "numberOfLetters")
    private int numberOfLetters;

    @Column(name = "numberOfNumbers")
    private int numberOfNumbers;

    @Column(name = "total")
    private int total;

    @ManyToOne
    CognitiveTestResolution test_id_resolution;

    @ManyToOne
    @JoinColumn(name = "numSerie")
    Serie numSerie;

    public String getSequence() {
        return sequence;
    }

    public Long getNumSerieResolution() {
        return numSerieResolution;
    }

    public void setNumSerieResolution(Long numSerieResolution) {
        this.numSerieResolution = numSerieResolution;
    }

    public Serie getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(Serie numSerie) {
        this.numSerie = numSerie;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumberOfLetters() {
        return numberOfLetters;
    }

    public void setNumberOfLetters(int numberOfLetters) {
        this.numberOfLetters = numberOfLetters;
    }

    public int getNumberOfNumbers() {
        return numberOfNumbers;
    }

    public void setNumberOfNumbers(int numberOfNumbers) {
        this.numberOfNumbers = numberOfNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie that = (Serie) o;
        return Objects.equals(sequence, that.sequence);
    }

    @Override
    public String toString() {
        return "SerieResolution{" +
                "numSerieResolution=" + numSerieResolution +
                ", sequence='" + sequence + '\'' +
                ", numberOfLetters=" + numberOfLetters +
                ", numberOfNumbers=" + numberOfNumbers +
                ", total=" + total +
                ", test_id_resolution=" + test_id_resolution +
                ", numSerie=" + numSerie +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(sequence, numberOfLetters, numberOfNumbers);
    }

    public CognitiveTestResolution getTest_id() {
        return test_id_resolution;
    }

    public void setTest_id(CognitiveTestResolution test_id) {
        this.test_id_resolution = test_id;
    }
}

