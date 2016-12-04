/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class represents a request structure for add new cognitive test.
 */
package tfg.backend.RequestsStructures;

import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.Serie;

import java.io.Serializable;
import java.util.List;

public class AllCognitiveTestData implements Serializable {

    private CognitiveTest cognitiveTest;
    private List<Serie> series;

    public CognitiveTest getCognitiveTest() {
        return cognitiveTest;
    }

    public void setCognitiveTest(CognitiveTest cognitiveTest) {
        this.cognitiveTest = cognitiveTest;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }
}
