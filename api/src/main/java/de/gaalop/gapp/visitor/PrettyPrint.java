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
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPVector;

/**
 * Implements a visitor,
 * that returns a pretty-formed string of the visited GAPPInstruction instance
 * @author christian
 */
public class PrettyPrint implements GAPPVisitor {

    private StringBuilder result;

    public PrettyPrint() {
        result = new StringBuilder();
    }

    public void clear() {
        result.setLength(0);
    }

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
     * Pretty prints a selectorset at the end of result
     * @param selectorset The selectorset
     */
    private void printSelectors(Selectorset selectorset) {
        result.append("[");
        for (Integer cur: selectorset) {
            result.append(cur);
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
        for (GAPPScalarVariable cur: variableset) {
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
        result.append(Integer.toString(gappDotVectors.getDestSelector()));
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
    public Object visitCalculate(GAPPCalculate gappCalculate, Object arg) {
        result.append("calculate ");
        printMultivector(gappCalculate.getTarget());
        result.append(" = ");
        result.append(gappCalculate.getType().toString());
        result.append("(");
        printMultivector(gappCalculate.getOperand1());
        if (gappCalculate.getOperand2() != null) {
            result.append(",");
            printMultivector(gappCalculate.getOperand2());
        }
        result.append(");\n");
        return null;
    }

    



}
