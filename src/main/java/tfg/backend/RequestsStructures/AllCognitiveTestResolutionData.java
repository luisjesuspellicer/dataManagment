package tfg.backend.RequestsStructures;


import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.SerieResolution;
import tfg.backend.DataModel.Subject;

import java.io.Serializable;
import java.util.List;


public class AllCognitiveTestResolutionData implements Serializable {

    private CognitiveTest cognitiveTest;
    private Subject subject;
    private List<SerieResolution> series;

    public CognitiveTest getCognitiveTest() {
        return cognitiveTest;
    }

    public void setCognitiveTestResolution(CognitiveTest cognitiveTest) {
        this.cognitiveTest = cognitiveTest;
    }

    public List<SerieResolution> getSeries() {
        return series;
    }

    public void setSeries(List<SerieResolution> series) {
        this.series = series;
    }

    public void setCognitiveTest(CognitiveTest cognitiveTest) {
        this.cognitiveTest = cognitiveTest;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "AllCognitiveTestResolutionData{" +
                "cognitiveTest=" + cognitiveTest +
                ", \n subject=" + subject +
                ", \n series=" + series +
                '}';
    }
}
