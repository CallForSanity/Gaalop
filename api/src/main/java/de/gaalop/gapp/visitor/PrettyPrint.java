/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp.visitor;

import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVariable;
import de.gaalop.gapp.variables.GAPPVector;

/**
 *
 * @author christian
 */
public class PrettyPrint implements GAPPVisitor {

    private StringBuilder result;

    public PrettyPrint() {
        result = new StringBuilder();
    }

    public String getResultString() {
        return result.toString();
    }

    private void printVector(GAPPVector vector) {
        result.append(vector.getName());
    }

    private void printMultivector(GAPPMultivector multiVector) {
        result.append(multiVector.getName());
    }

    private void printSelectors(Selectorset selectorset) {
        result.append("[");
        for (Integer cur: selectorset) {
            result.append(",");
            result.append(cur);
        }
        result.append("]");
    }

    private void printVariableSet(Variableset variableset) {
        result.append("[");
        for (GAPPVariable cur: variableset) {
            result.append(",");
            result.append(cur.toString());
        }
        result.append("]");
    }

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



}
