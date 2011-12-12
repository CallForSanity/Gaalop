package de.gaalop.gapp.visitor;

import de.gaalop.gapp.ConstantSetVectorArgument;
import de.gaalop.gapp.PairSetOfVariablesAndIndices;
import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.SetVectorArgument;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignInputsVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSetOfVariables;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPValueHolderCopier;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.LinkedList;

/**
 * Implements a visitor for (deep) copying instructions
 * @author Christian Steinmetz
 */
public class GAPPCopier implements GAPPVisitor {

    /**
     * Copies a selectorset
     * @param sel The selectorset to be copied
     * @return The copy
     */
    private Selectorset copySelectorset(Selectorset sel) {
        Selectorset copy = new Selectorset();
        for (Selector cur : sel) {
            copy.add(new Selector(cur.getIndex(), cur.getSign(), cur.getBladeName()));
        }
        return copy;
    }

    /**
     * Copies a posSelectorset
     * @param sel The posSelectorset to be copied
     * @return The copy
     */
    private PosSelectorset copyPosSelectorset(PosSelectorset sel) {
        PosSelectorset copy = new PosSelectorset();
        for (PosSelector cur : sel) {
            copy.add(new PosSelector(cur.getIndex(), cur.getBladeName()));
        }
        return copy;
    }

    /**
     * Copies a selector
     * @param sel The selector to be copied
     * @return The copy
     */
    private Selector copySelector(Selector sel) {
        return new Selector(sel.getIndex(), sel.getSign(), sel.getBladeName());
    }

    /**
     * Copies a SetVectorArgument instance
     * @param arg The argument
     * @return The copied argument
     */
    private SetVectorArgument copyArgument(SetVectorArgument arg) {
        if (arg.isConstant()) {
            ConstantSetVectorArgument c = (ConstantSetVectorArgument) arg;
            return new ConstantSetVectorArgument(c.getValue());
        } else {
            PairSetOfVariablesAndIndices pair = (PairSetOfVariablesAndIndices) arg;
            return new PairSetOfVariablesAndIndices((GAPPSetOfVariables) GAPPValueHolderCopier.copyValueHolder(pair.getSetOfVariable()),
                copySelectorset(pair.getSelectors()));
        }
    }

    /**
     * Copies a list of SetVectorArguments
     * @param list The list
     * @return The (deep) copy
     */
    private LinkedList<SetVectorArgument> copyListOfArgs(LinkedList<SetVectorArgument> list) {
        LinkedList<SetVectorArgument> result = new LinkedList<SetVectorArgument>();
        for (SetVectorArgument cur: list)
            result.add(copyArgument(cur));
        return result;
    }

    /**
     * Copies a variableset
     * @param var The variableset to be copied
     * @return The copy
     */
    private Variableset copyVariableset(Variableset var) {
        Variableset copy = new Variableset();

        for (GAPPValueHolder cur : var) {
            copy.add(GAPPValueHolderCopier.copyValueHolder(cur));
        }

        return copy;
    }

    /**
     * Copies a list of vectors (deep copy)
     * @param vectors The list of vectors to be copied
     * @return The copied list
     */
    private LinkedList<GAPPVector> copyVectors(LinkedList<GAPPVector> vectors) {
        LinkedList<GAPPVector> result = new LinkedList<GAPPVector>();
        for (GAPPVector part : vectors) {
            result.add((GAPPVector) GAPPValueHolderCopier.copyValueHolder(part));
        }
        return result;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        return new GAPPAssignMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappAssignMv.getDestinationMv()),
                copyPosSelectorset(gappAssignMv.getSelectors()),
                copyVariableset(gappAssignMv.getValues()));
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        return new GAPPDotVectors(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappDotVectors.getDestination()),
                copySelector(gappDotVectors.getDestSelector()),
                copyVectors(gappDotVectors.getParts()));
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        return new GAPPResetMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(
                gappResetMv.getDestinationMv()),
                gappResetMv.getSize());
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        return new GAPPSetMv(
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappSetMv.getDestination()),
                (GAPPSetOfVariables) GAPPValueHolderCopier.copyValueHolder(gappSetMv.getSource()),
                copyPosSelectorset(gappSetMv.getSelectorsDest()),
                copySelectorset(gappSetMv.getSelectorsSrc()));
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        return new GAPPSetVector(
                (GAPPVector) GAPPValueHolderCopier.copyValueHolder(gappSetVector.getDestination()),
                copyListOfArgs(gappSetVector.getEntries())
                );
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        return new GAPPCalculateMv(gappCalculateMv.getType(),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMv.getDestination()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMv.getOperand1()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMv.getOperand2()));
    }

    @Override
    public Object visitAssignInputsVector(GAPPAssignInputsVector gappAssignInputsVector, Object arg) {
        return new GAPPAssignInputsVector(copyVariableset(gappAssignInputsVector.getValues()));
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        return new GAPPCalculateMv(gappCalculateMvCoeff.getType(),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMvCoeff.getDestination()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMvCoeff.getOperand1()),
                (GAPPMultivector) GAPPValueHolderCopier.copyValueHolder(gappCalculateMvCoeff.getOperand2()));
    }




}
