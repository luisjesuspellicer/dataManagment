package tfg.backend.DataModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "FileInfo")
public class FileInfo implements Serializable{

    @Id
    @Column(name = "nameFile")
    String nameFile;


    @JoinColumn(name = "numSubject")
    @ManyToOne
    Subject numSubject;

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public Subject getNumSubject() {
        return numSubject;
    }

    public void setNumSubject(Subject numSubject) {
        this.numSubject = numSubject;
    }


}
