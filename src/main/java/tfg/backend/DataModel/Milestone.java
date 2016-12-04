/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents the milestone in a base line.
 */
package tfg.backend.DataModel;

import tfg.backend.DataModel.Ids.MilestoneID;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Milestone")
public class Milestone implements Serializable {

    @EmbeddedId
    private MilestoneID id;

    @Column(name = "incidents")
    private String incidents;

    @Column(name = "initialHour")
    private int initialHour;

    @Column(name = "initialMinute")
    private int initialMinute;

    @Column(name = "initialSecond")
    private int initialSecond;

    @Column(name = "finalHour")
    private int finalHour;

    @Column(name = "finalMinute")
    private int finalMinute;

    @Column(name = "finalSecond")
    private int finalSecond;

    @Column(name = "initialMillisecond")
    private int initialMillisecond;

    @Column(name = "finalMillisecond")
    private int finalMillisecond;

    @JoinColumn(name = "numSubject")
    @ManyToOne
    private Subject numSubject;

    @JoinColumn
    @Column(name = "activities")
    private Activity activities;

    public int getInitialMillisecond() {
        return initialMillisecond;
    }

    public Subject getNumSubject() {
        return numSubject;
    }

    public void setNumSubject(Subject numSubject) {
        this.numSubject = numSubject;
    }

    public void setInitialMillisecond(int initialMillisecond) {
        this.initialMillisecond = initialMillisecond;
    }

    public int getFinalMillisecond() {
        return finalMillisecond;
    }

    public void setFinalMillisecond(int finalMillisecond) {
        this.finalMillisecond = finalMillisecond;
    }

    public MilestoneID getId() {
        return id;
    }

    public void setId(MilestoneID id) {
        this.id = id;
    }

    public String getIncidents() {
        return incidents;
    }

    public void setIncidents(String incidents) {
        this.incidents = incidents;
    }


    public Activity getActivities() {
        return activities;
    }

    public void setActivities(Activity activities) {
        this.activities = activities;
    }

    public int getInitialHour() {
        return initialHour;
    }

    public void setInitialHour(int initialHour) {
        this.initialHour = initialHour;
    }

    public int getInitialMinute() {
        return initialMinute;
    }

    public void setInitialMinute(int initialMinute) {
        this.initialMinute = initialMinute;
    }

    public int getInitialSecond() {
        return initialSecond;
    }

    public void setInitialSecond(int initialSecond) {
        this.initialSecond = initialSecond;
    }

    public int getFinalHour() {
        return finalHour;
    }

    public void setFinalHour(int finalHour) {
        this.finalHour = finalHour;
    }

    public int getFinalMinute() {
        return finalMinute;
    }

    public void setFinalMinute(int finalMinute) {
        this.finalMinute = finalMinute;
    }

    public int getFinalSecond() {
        return finalSecond;
    }

    public void setFinalSecond(int finalSecond) {
        this.finalSecond = finalSecond;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Milestone milestone = (Milestone) o;
        return
                Objects.equals(id, milestone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, incidents, activities);
    }
}
