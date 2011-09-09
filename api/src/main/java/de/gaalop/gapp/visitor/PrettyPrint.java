package de.gaalop.gapp.visitor;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculateMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPValueHolder;
import de.gaalop.gapp.variables.GAPPVector;

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

        if (node.getGAPP() != null)
            node.getGAPP().accept(this, null);
        
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
     * Pretty prints a selector at the end of result
     * @param selector The selector
     */
    private void printSelector(Selector selector) {
        if (selector.getSign() == (byte) -1) result.append('-');
        result.append(selector.getIndex());
    }

    /**
     * Pretty prints a selectorset at the end of result
     * @param selectorset The selectorset
     */
    private void printSelectors(Selectorset selectorset) {
        result.append("[");
        for (Selector cur: selectorset) {
            printSelector(cur);
            result.append(",");
        }
        result.deleteCharAt(result.length()-1);
        result.append("]");
    }

   /**
     * Pretty prints a variableset at the end of result
     * @param variableset The variableset
     */
    private void printVariableSet(Variableset variableset) {
        result.append("[");
        for (GAPPValueHolder cur: variableset) {
            result.append(cur.prettyPrint());
            result.append(",");
        }
        result.deleteCharAt(result.length()-1);
        result.append("]");
    }

   /**
     * Pretty prints a dot product of two vectors at the end of result
     * @param vector1 The first vector of the dot product
     * @param vector2 The second vector of the dot product
     */
    private void printDotProduct(GAPPVector vector1, GAPPVector vector2) {
        result.append("<");
        printVector(vector1);
        result.append(",");
        printVector(vector2);
        result.append(">");
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        result.append("addMv ");
        printMultivector(gappAddMv.getDestinationMv());
        printSelectors(gappAddMv.getSelectorsDest());
        result.append(" = ");
        printMultivector(gappAddMv.getSourceMv());
        printSelectors(gappAddMv.getSelectorsSrc());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        result.append("assignMv ");
        printMultivector(gappAssignMv.getDestinationMv());
        printSelectors(gappAssignMv.getSelectors());
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
        printDotProduct(gappDotVectors.getPart1(), gappDotVectors.getPart2());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        result.append("resetMv ");
        printMultivector(gappResetMv.getDestinationMv());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        result.append("setMv ");
        printMultivector(gappSetMv.getDestinationMv());
        printSelectors(gappSetMv.getSelectorsDest());
        result.append(" = ");
        printMultivector(gappSetMv.getSourceMv());
        printSelectors(gappSetMv.getSelectorsSrc());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        result.append("setVector ");
        printVector(gappSetVector.getDestination());
        result.append(" = ");
        printMultivector(gappSetVector.getSourceMv());
        printSelectors(gappSetVector.getSelectorsSrc());
        result.append(";\n");
        return null;
    }

    @Override
    public Object visitCalculateMv(GAPPCalculateMv gappCalculateMv, Object arg) {
        result.append("calculateMv ");
        printMultivector(gappCalculateMv.getTarget());
        result.append(" = ");
        result.append(gappCalculateMv.getType().toString());
        result.append("(");
        printMultivector(gappCalculateMv.getOperand1());
        if (gappCalculateMv.getOperand2() != null) {
            result.append(",");
            printMultivector(gappCalculateMv.getOperand2());
        }
        result.append(",");
        printSelectors(gappCalculateMv.getUsed1());
        result.append(",");
        if (gappCalculateMv.getUsed2()==null)
            result.append("null"); 
        else
            printSelectors(gappCalculateMv.getUsed2());
        result.append(");\n");
        return null;
    }

    



}
