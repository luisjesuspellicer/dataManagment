/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a cognitive test.
 */
package tfg.backend.DataModel;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "CognitiveTest")
public class CognitiveTest  implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "test_id")
    private Long test_id;

    @Column(name = "nombre")
    private String nombre;

    public long getId() {
        return test_id;
    }

    public void setId(long id) {
        this.test_id = id;
    }


    public Long getTest_id() {
        return test_id;
    }

    public void setTest_id(long test_id) {
        this.test_id = test_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CognitiveTest that = (CognitiveTest) o;
        return Objects.equals(test_id, that.test_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(test_id);
    }
}
