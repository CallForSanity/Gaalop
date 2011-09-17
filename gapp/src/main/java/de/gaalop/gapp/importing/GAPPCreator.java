package de.gaalop.gapp.importing;

import de.gaalop.gapp.GAPP;
import java.util.LinkedList;

/**
 * Creates GAPP instructions from assignments
 * @author Christian Steinmetz
 */
public class GAPPCreator {

    private GAPP gapp;
    private final int bladeCount;

    public GAPPCreator(int bladeCount) {
        this.bladeCount = bladeCount;
    }

    public GAPP getGapp() {
        return gapp;
    }

    public void setGapp(GAPP gapp) {
        this.gapp = gapp;
    }

    public int getBladeCount() {
        return bladeCount;
    }

    public void createGAPPInstructions(LinkedList<Assignment> assignments) {
        //TODO
    }

}
