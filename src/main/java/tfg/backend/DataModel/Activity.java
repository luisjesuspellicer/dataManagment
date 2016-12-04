/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents an activity (in base line).
 */
package tfg.backend.DataModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Activity")
public class Activity implements Serializable{


    @Column(name="typeActivity")
    private String typeActivity;

    @Column(name="description")
    private String description;


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="numActivity")
    private Long numActivity;

    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(typeActivity, activity.typeActivity) &&
                Objects.equals(description, activity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeActivity, description);
    }
}
