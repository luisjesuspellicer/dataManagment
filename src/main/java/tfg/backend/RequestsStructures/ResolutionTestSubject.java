package tfg.backend.RequestsStructures;

import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.CognitiveTestResolution;
import tfg.backend.DataModel.Serie;
import tfg.backend.DataModel.SerieResolution;

import java.io.Serializable;
import java.util.List;


public class ResolutionTestSubject implements Serializable {

    List<CognitiveTest> tests;
    List<CognitiveTestResolution> solutions;

    public List<CognitiveTest> getTests() {
        return tests;
    }

    public void setTests(List<CognitiveTest> tests) {
        this.tests = tests;
    }

    public List<CognitiveTestResolution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<CognitiveTestResolution> solutions) {
        this.solutions = solutions;
    }
}
