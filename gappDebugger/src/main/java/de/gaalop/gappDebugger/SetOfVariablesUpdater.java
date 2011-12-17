package de.gaalop.gappDebugger;

import de.gaalop.gapp.PairSetOfVariablesAndIndices;
import de.gaalop.gapp.SetVectorArgument;
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Updates all setOfVariables in GAPP instructions.
 * This must be done because a setOfVariables variable can be either a GAPPMultivector or a GAPPVector
 * @author Christian Steinmetz
 */
public class SetOfVariablesUpdater implements GAPPVisitor {

    private HashSet<String> multivectors = new HashSet<String>();
    private HashSet<String> vectors = new HashSet<String>();

    private SetOfVariablesUpdater() {//make use of static method mandatory
    }

    /**
     * Updates all setOfVariables
     * @param instructions The GAPP instructions
     */
    public static void updateSetOfVariables(LinkedList<GAPPBaseInstruction> instructions) {
        SetOfVariablesUpdater updater = new SetOfVariablesUpdater();
        for (GAPPBaseInstruction i: instructions)
            i.accept(updater, null);
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        multivectors.add(gappAssignMv.getDestinationMv().getName());
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        multivectors.add(gappDotVectors.getDestination().getName());
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        multivectors.add(gappResetMv.getDestinationMv().getName());
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        String name = gappSetMv.getSource().getName();
        if (multivectors.contains(name)) {
            gappSetMv.setSource(new GAPPMultivector(name));
        } else {
            if (vectors.contains(name)) {
                gappSetMv.setSource(new GAPPVector(name));
            } else {
                System.err.println("Multivector/Vector "+name+" is not declared!");
            }
        }
        multivectors.add(gappSetMv.getDestination().getName());
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        LinkedList<SetVectorArgument> entries = gappSetVector.getEntries();
        for (SetVectorArgument entry: entries) {
            if (!entry.isConstant()) {
                PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) entry;
                String name = pair.getSetOfVariable().getName();
                if (multivectors.contains(name)) {
                    pair.setSetOfVariable(new GAPPMultivector(name));
                } else {
                    if (vectors.contains(name)) {
                        pair.setSetOfVariable(new GAPPVector(name));
                    } else {
                        System.err.println("Multivector/Vector "+name+" is not declared!");
                    }
                }
            }
        }
        vectors.add(gappSetVector.getDestination().getName());
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        multivectors.add(gappCalculateMv.getDestination().getName());
        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        multivectors.add(gappCalculateMvCoeff.getDestination().getName());
        return null;
    }

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gAPPAssignInputsVector, Object arg) {
        vectors.add("inputsVector");
        return null;
    }

}
