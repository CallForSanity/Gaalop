package de.gaalop.gapp.visitor;

import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculate;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSignedMultivectorComponent;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVariableCopier;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.Vector;

/**
 * Implements a visitor for (deep) copying instructions
 * @author christian
 */
public class GAPPCopier implements GAPPVisitor {

    /**
     * Copies a multivector
     * @param mv The multivector to be copied
     * @return The copy
     */
    private GAPPMultivector copyMultivector(GAPPMultivector mv) {
        return new GAPPMultivector(mv.getName()); // Strings are immutable!
    }

    /**
     * Copies a selectorset
     * @param sel The selectorset to be copied
     * @return The copy
     */
    private Selectorset copySelector(Selectorset sel) {
        Selectorset copy = new Selectorset();
        for (Integer cur: sel)
            copy.add(new Integer(cur));
        return copy;
    }

     /**
     * Copies a variableset
     * @param var The variableset to be copied
     * @return The copy
     */
    private Variableset copyVariableset(Variableset var) {
        Variableset copy = new Variableset();

        GAPPVariableCopier copier = new GAPPVariableCopier();
        for (GAPPValueHolder cur: var)
            copy.add((GAPPValueHolder) cur.accept(copier, null));
  
        return copy;
    }

    /**
     * Copies a vector
     * @param vec The vector to be copied
     * @return The copy
     */
    private GAPPVector copyVector(GAPPVector vec) {
        Vector<GAPPSignedMultivectorComponent> slots = new Vector<GAPPSignedMultivectorComponent>();
        
        for (GAPPSignedMultivectorComponent cur: vec.slots)
            slots.add(new GAPPSignedMultivectorComponent(copyMultivector(cur.getParent()), cur.getBladeIndex(), cur.getSign()));

        return new GAPPVector(vec.getName(), slots);
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        return new GAPPAddMv(
                copyMultivector(gappAddMv.getDestinationMv()),
                copyMultivector(gappAddMv.getSourceMv()),
                copySelector(gappAddMv.getSelectorsDest()),
                copySelector(gappAddMv.getSelectorsSrc())
                );
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        return new GAPPAssignMv(
                copyMultivector(gappAssignMv.getDestinationMv()),
                copySelector(gappAssignMv.getSelectors()),
                copyVariableset(gappAssignMv.getValues())
                );
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        return new GAPPDotVectors(
                copyMultivector(gappDotVectors.getDestination()),
                new Integer(gappDotVectors.getDestSelector()),
                copyVector(gappDotVectors.getPart1()),
                copyVector(gappDotVectors.getPart2())
                );
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        return new GAPPResetMv(
                copyMultivector(gappResetMv.getDestinationMv())
                );
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        return new GAPPSetMv(
                copyMultivector(gappSetMv.getDestinationMv()),
                copyMultivector(gappSetMv.getSourceMv()),
                copySelector(gappSetMv.getSelectorsDest()),
                copySelector(gappSetMv.getSelectorsSrc())
                );
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        return new GAPPSetVector(
                copyVector(gappSetVector.getDestination()),
                copyMultivector(gappSetVector.getSourceMv()),
                copySelector(gappSetVector.getSelectorsSrc())
                );
    }

    @Override
    public Object visitCalculate(GAPPCalculate gappCalculate, Object arg) {
        return new GAPPCalculate(gappCalculate.getType(),
                copyMultivector(gappCalculate.getTarget()),
                copyMultivector(gappCalculate.getOperand1()),
                copyMultivector(gappCalculate.getOperand2()),
                copySelector(gappCalculate.getUsed1()),
                copySelector(gappCalculate.getUsed2())
                );
    }

}
