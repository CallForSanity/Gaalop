package de.gaalop.gapp.visitor;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPAssignVector;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMvCoeff;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPSetOfVariables;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Implements a visitor,
 * that returns a pretty-formed string of all GAPP members in a ControlFlowGraph
 * @author Christian Steinmetz
 */
public class PrettyPrint extends CFGGAPPVisitor {

    private StringBuilder result;

    public PrettyPrint() {
        result = new StringBuilder();
    }

    @Override
    public void visit(AssignmentNode node) {
        result.append("//");
        result.append(node.getVariable().toString());
        result.append(" = ");
        result.append(node.getValue().toString());
        result.append('\n');

        if (node.getGAPP() != null) {
            node.getGAPP().accept(this, null);
        }

        result.append('\n');
        node.getSuccessor().accept(this);
    }

    /**
     * Clears the result code
     */
    public void clear() {
        result.setLength(0);
    }

    /**
     * Return the result of pretty printing instructions
     * @return The pretty printed string of instructions
     */
    public String getResultString() {
        return result.toString();
    }

    /**
     * Pretty prints a vector at the end of result
     * @param vector The vector
     */
    private void printVector(GAPPVector vector) {
        result.append(vector.prettyPrint());
    }

    /**
     * Pretty prints a multivector at the end of result
     * @param multiVector The multivector
     */
    private void printMultivector(GAPPMultivector multiVector) {
        result.append(multiVector.prettyPrint());
    }

    /**
     * Pretty prints a GAPPSetOfVariables at the end of result
     * @param setOfVariables The GAPPSetOfVariables
     */
    private void printSetOfVariables(GAPPSetOfVariables setOfVariables) {
        result.append(setOfVariables.prettyPrint());
    }

    /**
     * Pretty prints a selector at the end of result
     * @param selector The selector
     */
    private void printSelector(Selector selector) {
        if (selector.getSign() == (byte) -1) {
            result.append('-');
        }
        result.append(selector.getIndex());
    }

    /**
     * Pretty prints a selectorindex at the end of result
     * @param selectorIndex The selectorindex
     */
    private void printSelectorIndex(PosSelector selectorIndex) {
        result.append(selectorIndex.getIndex());
    }

    /**
     * Pretty prints a selectorset at the end of result
     * @param selectorset The selectorset
     */
    private void printSelectors(Selectorset selectorset) {
        result.append("[");
        for (Selector cur : selectorset) {
            printSelector(cur);
            result.append(",");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("]");
    }

    /**
     * Pretty prints a posSelectorset at the end of result
     * @param posSelectorset The posSelectorset
     */
    private void printPosSelectors(PosSelectorset posSelectorset) {
        result.append("[");
        for (PosSelector cur : posSelectorset) {
            printSelectorIndex(cur);
            result.append(",");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("]");
    }

    /**
     * Pretty prints a variableset at the end of result
     * @param variableset The variableset
     */
    private void printVariableSet(Variableset variableset) {
        result.append("[");
        for (GAPPValueHolder cur : variableset) {
            result.append(cur.prettyPrint());
            result.append(",");
        }
        result.deleteCharAt(result.length() - 1);
        result.append("]");
    }

    /**
     * Pretty prints a dot product of vectors at the end of result
     * @param vectors The vectors of the dot product
     */
    private void printDotProduct(LinkedList<GAPPVector> vectors) {
        result.append("<");

        ListIterator<GAPPVector> it = vectors.listIterator();
        while (it.hasNext()) {
            printVector(it.next());
            if (it.hasNext()) {
                result.append(",");
            }
        }

        result.append(">");
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        result.append("assignMv ");
        printMultivector(gappAssignMv.getDestinationMv());
        printPosSelectors(gappAssignMv.getSelectors());
        result.append(" = ");
        printVariableSet(gappAssignMv.getValues());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        result.append("dotVectors ");
        printMultivector(gappDotVectors.getDestination());
        result.append("[");
        printSelector(gappDotVectors.getDestSelector());
        result.append("]");
        result.append(" = ");
        printDotProduct(gappDotVectors.getParts());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        result.append("resetMv ");
        printMultivector(gappResetMv.getDestinationMv());
        result.append("[");
        result.append(gappResetMv.getSize());
        result.append("];\n");
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        result.append("setMv ");
        printMultivector(gappSetMv.getDestination());
        printPosSelectors(gappSetMv.getSelectorsDest());
        result.append(" = ");
        printSetOfVariables(gappSetMv.getSource());
        printSelectors(gappSetMv.getSelectorsSrc());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        result.append("setVector ");
        printVector(gappSetVector.getDestination());
        result.append(" = ");
        printMultivector(gappSetVector.getSource());
        printSelectors(gappSetVector.getSelectorsSrc());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        result.append("calculateMv ");
        printMultivector(gappCalculateMv.getDestination());
        result.append(" = ");
        result.append(gappCalculateMv.getType().toString());
        result.append("(");
        printMultivector(gappCalculateMv.getOperand1());
        if (gappCalculateMv.getOperand2() != null) {
            result.append(",");
            printMultivector(gappCalculateMv.getOperand2());
        }
        result.append(");\n");
        return null;
    }

    @Override
    public Object visitAssignVector(GAPPAssignVector gappAssignVector, Object arg) {
        result.append("assignVector ");
        printVector(gappAssignVector.getDestination());
        result.append(" = ");
        printVariableSet(gappAssignVector.getValues());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitCalculateMvCoeff(GAPPCalculateMvCoeff gappCalculateMvCoeff, Object arg) {
        result.append("calculateMvCoeff ");
        result.append(gappCalculateMvCoeff.getDestination().getName());
        result.append("[");
        result.append(Integer.toString(gappCalculateMvCoeff.getDestination().getBladeIndex()));
        result.append("]");
        result.append(" = ");
        result.append(gappCalculateMvCoeff.getType().toString());
        result.append("(");
        printMultivector(gappCalculateMvCoeff.getOperand1());
        if (gappCalculateMvCoeff.getOperand2() != null) {
            result.append(",");
            printMultivector(gappCalculateMvCoeff.getOperand2());
        }
        result.append(");\n");
        return null;
    }

}
