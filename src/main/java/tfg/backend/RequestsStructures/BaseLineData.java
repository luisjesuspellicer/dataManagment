package tfg.backend.RequestsStructures;

import com.fasterxml.jackson.databind.deser.Deserializers;
import tfg.backend.DataModel.Activity;
import tfg.backend.DataModel.BaseLine;
import tfg.backend.DataModel.Milestone;

/**
 * Created by guytili on 17/08/2016.
 */
public class BaseLineData {

    private BaseLine baseLine;

    private Milestone[] milestones;

    public BaseLine getBaseLine() {
        return baseLine;
    }

    public void setBaseLine(BaseLine baseLine) {
        this.baseLine = baseLine;
    }

    public Milestone[] getMilestones() {
        return milestones;
    }

    public void setMilestones(Milestone[] milestones) {
        this.milestones = milestones;
    }
}
