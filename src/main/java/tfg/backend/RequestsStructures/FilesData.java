package tfg.backend.RequestsStructures;

import java.io.Serializable;


public class FilesData implements Serializable{

    String nameFile;

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
}
