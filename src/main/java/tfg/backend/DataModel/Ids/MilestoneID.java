/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a complex key with foreign key.
 * The models that use this complex key are:
 * - Milestone
 */
package tfg.backend.DataModel.Ids;




import tfg.backend.DataModel.BaseLine;

import javax.persistence.*;
import java.io.Serializable;


@Embeddable
public class MilestoneID implements Serializable{

    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id_milestone;

    @ManyToOne
    @JoinColumn(name = "id")
    private BaseLine id;

    public Long getId_milestone() {
        return id_milestone;
    }

    public void setId_milestone(Long id_milestone) {
        this.id_milestone = id_milestone;
    }

    public BaseLine getId() {
        return id;
    }

    public void setId(BaseLine id) {
        this.id = id;
    }
}
