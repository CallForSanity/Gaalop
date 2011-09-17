package de.gaalop.gapp.importing;

import de.gaalop.gapp.GAPP;
import de.gaalop.gapp.importing.irZwei.ExtCalculation;
import de.gaalop.gapp.importing.irZwei.Factors;
import de.gaalop.gapp.importing.irZwei.GAPPValueHolderContainer;
import de.gaalop.gapp.importing.irZwei.ParallelObjectVisitor;
import de.gaalop.gapp.importing.irZwei.Summands;
import de.gaalop.gapp.variables.GAPPVector;

/**
 *
 * @author Christian Steinmetz
 */
public class GAPPCreator implements ParallelObjectVisitor {

    private GAPP gapp;
    private final int bladeCount;
    private int curTmpIndex = 0;

    private GAPPVector createTMP() {
        curTmpIndex++;
        return new GAPPVector("sTmp"+curTmpIndex);
    }

    public GAPPCreator(int bladeCount) {
       this.bladeCount = bladeCount;
    }

    public int getBladeCount() {
        return bladeCount;
    }

    public GAPP getGapp() {
        return gapp;
    }

    public void setGapp(GAPP gapp) {
        this.gapp = gapp;
    }

    @Override
    public Object visitSummands(Summands summands, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitFactors(Factors factors, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitExtCalculation(ExtCalculation extCalculation, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object visitGAPPValueHolderContainer(GAPPValueHolderContainer gAPPValueHolderContainer, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
