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
@Table(name = "Serie")
public class Serie implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numSerie")
    Long numSerie;

    @Column(name = "sequence")
    String sequence;

    @Column(name = "numberOfLetters")
    private int numberOfLetters;

    @Column(name = "numberOfNumbers")
    private int numberOfNumbers;

    @Column(name = "total")
    private int total;

    @ManyToOne
    @JoinColumn(name = "test_id")
    CognitiveTest test_id;

    public String getSequence() {
        return sequence;
    }

    public Long getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(Long numSerie) {
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

    public CognitiveTest getTest_id() {
        return test_id;
    }

    public void setTest_id(CognitiveTest test_id) {
        this.test_id = test_id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(sequence, numberOfLetters, numberOfNumbers);
    }

    public CognitiveTest getTests() {
        return test_id;
    }

    public void setTests(CognitiveTest tests) {
        this.test_id = tests;
    }
}

