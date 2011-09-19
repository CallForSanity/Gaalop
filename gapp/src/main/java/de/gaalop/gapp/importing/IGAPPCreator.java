package de.gaalop.gapp.importing;

import de.gaalop.gapp.GAPP;

/**
 *
 * @author Christian Steinmetz
 */
public interface IGAPPCreator {

    public GAPP getGapp();

    public void setGapp(GAPP gapp);

    public int getBladeCount();

    public void setBladeCount(int bladeCount);

    public void createGAPPInstructionsFromAssignment(Assignment assignment);

}
