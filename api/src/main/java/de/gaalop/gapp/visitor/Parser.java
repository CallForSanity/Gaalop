/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp.visitor;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPVector;

/**
 *
 * @author christian
 */
public class Parser implements GAPPVisitor {

    private VariableGetter getter;

    public Parser(VariableGetter getter) {
        this.getter = getter;
    }

    public VariableGetter getGetter() {
        return getter;
    }

    public void setGetter(VariableGetter getter) {
        this.getter = getter;
    }

    @Override
    public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
        String[] partsEquation = ((String) arg).split("=");

        Selectorset destSelectorset = new Selectorset();
        //Parse left side
        gappAddMv.setSelectorsDest(destSelectorset);
        gappAddMv.setDestinationMv(parseMultivectorWithSelectors(partsEquation[0].trim(), destSelectorset, getter));

        //Parse right side
        Selectorset srcSelectorset = new Selectorset();
        gappAddMv.setSourceMv(parseMultivectorWithSelectors(partsEquation[1].trim(), srcSelectorset, getter));
        return null;
    }

    @Override
    public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
        String[] partsEquation = ((String) arg).split("=");

        //Parse left side
        Selectorset selectors = new Selectorset();
        gappAssignMv.setSelectors(selectors);
        gappAssignMv.setDestinationMv(parseMultivectorWithSelectors(partsEquation[0].trim(), selectors, getter));

        //Parse right side
        Variableset values = new Variableset();
        gappAssignMv.setValues(values);
        parseSelectors(partsEquation[1].trim(), values, getter);
        return null;
    }

    @Override
    public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
        String[] partsEquation = ((String) arg).split("=");

        //Parse left side
        Selectorset selectorsDest = new Selectorset();
        GAPPMultivector destination = parseMultivectorWithSelectors(partsEquation[0].trim(), selectorsDest, getter);
        gappDotVectors.setDestination(destination);
        gappDotVectors.setDestSelector(selectorsDest.get(0));

        //Parse right side

        String rightSide = partsEquation[1].trim();

        if ((rightSide.charAt(0) != '<') || (rightSide.charAt(rightSide.length() - 1)) != '>') {
            System.err.println("Parse error. Wrong syntax in dotVectors instruction:" + (String) arg);
        } else {
            String[] partsDotProduct = rightSide.split(",");
            gappDotVectors.setPart1(parseVector(partsDotProduct[0], getter));
            gappDotVectors.setPart2(parseVector(partsDotProduct[1], getter));
        }
        return null;
    }

    @Override
    public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
         gappResetMv.setDestinationMv(parseMultivectorWithSelectors((String) arg, null, getter));
         return null;
    }

    @Override
    public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
        String[] partsEquation = ((String) arg).split("=");

        //Parse left side
        Selectorset selectorsDest = new Selectorset();
        gappSetMv.setSelectorsDest(selectorsDest);
        gappSetMv.setDestinationMv(parseMultivectorWithSelectors(partsEquation[0].trim(), selectorsDest, getter));

        //Parse right side
        Selectorset selectorsSrc = new Selectorset();
        gappSetMv.setSourceMv(parseMultivectorWithSelectors(partsEquation[1].trim(), selectorsSrc, getter));
        return null;
    }

    @Override
    public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
        String[] partsEquation = ((String) arg).split("=");

        //Parse left side
        GAPPVector destination = parseVector(partsEquation[0].trim(), getter);
        gappSetVector.setDestination(destination);

        //Parse right side
        Selectorset selectorsSrc = new Selectorset();
        gappSetVector.setSelectorsSrc(selectorsSrc);
        GAPPMultivector sourceMv = parseMultivectorWithSelectors(partsEquation[1].trim(), selectorsSrc, getter);
        gappSetVector.setSourceMv(sourceMv);
        return null;
    }

    protected GAPPMultivector parseMultivectorWithSelectors(String string, Selectorset returnSelectors, VariableGetter getter) {
        int indexOfBracket = string.indexOf('[');
        GAPPMultivector mv = getter.parseMultivector(string.substring(0, indexOfBracket));

        String parseSelectors = string.substring(indexOfBracket + 1, string.length() - 1);
        String[] partsSelectors = parseSelectors.split(",");
        for (String curPart : partsSelectors) {
            returnSelectors.add(Integer.parseInt(curPart.trim()));
        }


        return mv;
    }

    protected void parseSelectors(String string, Variableset values2, VariableGetter getter) {
        String[] partsSelectors = string.split(",");
        for (String curPart : partsSelectors) {
            values2.add(getter.parseVariable(curPart.trim()));
        }

    }

    protected GAPPVector parseVector(String string, VariableGetter getter) {
        return getter.parseVector(string);
    }

}
