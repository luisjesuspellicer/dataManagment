/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a support.
 */
package tfg.backend.LopdModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Support")
public class Support implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name="name", unique = true)
    private String name;

    @Column(name = "typeInformation")
    private String typeInformation;

    @Column(name ="characteristics")
    private String characteristics;


    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getTypeInformation() {

        return typeInformation;
    }

    public void setTypeInformation(String typeInformation) {

        this.typeInformation = typeInformation;
    }

    public String getCharacteristics() {

        return characteristics;
    }

    public void setCharacteristics(String characteristics) {

        this.characteristics = characteristics;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
