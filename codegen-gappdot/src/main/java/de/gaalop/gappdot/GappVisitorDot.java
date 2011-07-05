/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappdot;

import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculate;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.visitor.PrettyPrint;

/**
 * Implements the visitor to handle gapp members in Expressions for printing in the GAPPDot plugin
 * @author christian
 */
public class GappVisitorDot implements GAPPVisitor {

    private StringBuilder result;

    private PrettyPrint prettyPrinter;

    public GappVisitorDot() {
        result = new StringBuilder();
        prettyPrinter = new PrettyPrint();
    }

    public String getResultString() {
        return result.toString();
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        prettyPrinter.clear();
        gappAddMv.accept(prettyPrinter, null);
        result.append(prettyPrinter.getResultString());
        return null;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        prettyPrinter.clear();
        gappAssignMv.accept(prettyPrinter, null);
        result.append(prettyPrinter.getResultString());
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        prettyPrinter.clear();
        gappDotVectors.accept(prettyPrinter, null);
        result.append(prettyPrinter.getResultString());
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
        prettyPrinter.clear();
        gappResetMv.accept(prettyPrinter, null);
        result.append(prettyPrinter.getResultString());
        return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        prettyPrinter.clear();
        gappSetMv.accept(prettyPrinter, null);
        result.append(prettyPrinter.getResultString());
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        prettyPrinter.clear();
        gappSetVector.accept(prettyPrinter, null);
        result.append(prettyPrinter.getResultString());
        return null;
    }

    @Override
    public Object visitCalculate(GAPPCalculate gappCalculate, Object arg) {
        prettyPrinter.clear();
        gappCalculate.accept(prettyPrinter, null);
        result.append(prettyPrinter.getResultString());
        return null;
    }



}
