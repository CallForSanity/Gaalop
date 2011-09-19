package de.gaalop.gapp.importing;

import de.gaalop.gapp.GAPP;
import java.util.LinkedList;

/**
 * Creates GAPP instructions from assignments
 * @author Christian Steinmetz
 */
public abstract class GAPPBaseCreator implements IGAPPCreator {

    protected GAPP gapp;
    protected int bladeCount;

    public GAPPBaseCreator(GAPP gapp, int bladeCount) {
        this.gapp = gapp;
        this.bladeCount = bladeCount;
    }

    @Override
    public GAPP getGapp() {
        return gapp;
    }

    @Override
    public void setGapp(GAPP gapp) {
        this.gapp = gapp;
    }

    @Override
    public int getBladeCount() {
        return bladeCount;
    }

    @Override
    public void setBladeCount(int bladeCount) {
        this.bladeCount = bladeCount;
    }

    public void createGAPPInstructionsFromAssignments(LinkedList<Assignment> assignments) {
        for (Assignment assignment: assignments)
            createGAPPInstructionsFromAssignment(assignment);
    }

    public abstract void createGAPPInstructionsFromAssignment(Assignment assignment);

}
