/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a subject of the experiments.
 */
package tfg.backend.DataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import tfg.backend.DataAccessImplementation.SubjectDaoImplementation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Subject")
public class Subject extends SubjectDaoImplementation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "numSubject")
    public Long numSubject;

    @Column(name = "gender")
    public String gender;

    @Column(name = "age")
    public int age;

    @Column(name = "personalCode")
    public String personalCode;

    // The time when the subject do the experiment (milliseconds)
    @Column(name = "checkIn")
    public Long checkIn;

    @Column(name = "balanced")
    public String balanced;

    @Column(name = "device")
    public String device;

    @ManyToOne
    @JoinColumn(name = "studyName")
    public Study studyName;

    @Column(name = "showW", nullable = false, columnDefinition = "TINYINT(1)")
    public boolean showW;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getNumSubject() {
        return numSubject;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setNumSubject(Long numSubject) {
        this.numSubject = numSubject;
    }

    public String getGenero() {
        return gender;
    }

    public void setGenero(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public Long getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Long checkIn) {
        this.checkIn = checkIn;
    }

    public String getBalanced() {
        return balanced;
    }

    public void setBalanced(String balanced) {
        this.balanced = balanced;
    }

    public Study getStudyName() {
        return studyName;
    }

    public void setStudyName(Study studyName) {
        this.studyName = studyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(numSubject, subject.numSubject) &&
                Objects.equals(studyName, subject.studyName);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "numSubject=" + numSubject +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", personalCode='" + personalCode + '\'' +
                ", checkIn=" + checkIn +
                ", balanced='" + balanced + '\'' +
                ", device='" + device + '\'' +
                ", studyName=" + studyName +
                '}' + "\n";
    }

    public boolean isShowW() {

        return showW;
    }

    public void setShowW(boolean showW) {

        this.showW = showW;
    }
}
