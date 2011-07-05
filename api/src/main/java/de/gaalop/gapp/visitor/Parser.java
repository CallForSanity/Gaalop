package de.gaalop.gapp.visitor;

import de.gaalop.gapp.instructionSet.GAPPCalculate;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.instructionSet.CalculationType;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.variables.GAPPVector;

/**
 * Implements a visitor that parses string representations of pre-created GAPPInstructions
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

    @Override
    public Object visitCalculate(GAPPCalculate gappCalculate, Object arg) {
        String[] partsEquation = ((String) arg).split("=");

        //Parse left side
        gappCalculate.setTarget(getter.parseMultivector(partsEquation[0]));

        int indexOfBracket = partsEquation[1].indexOf('(');
        String operator = partsEquation[1].substring(0, indexOfBracket);
        String argumentsString = partsEquation[1].substring(indexOfBracket+1,partsEquation[1].indexOf(')'));
        String[] args = argumentsString.split(",");

        gappCalculate.setType(CalculationType.valueOf(operator));
        if (args.length == 1) {
            gappCalculate.setOperand1(getter.parseMultivector(args[0]));
        } else
            if (args.length == 2) {
                gappCalculate.setOperand1(getter.parseMultivector(args[0]));
                gappCalculate.setOperand2(getter.parseMultivector(args[1]));
            } else
                System.err.println("parse error: calculate, not one or two operands specified!");
        
        return null;
    }

    /**
     * Parses a string and returns a congruous multivector.
     * Modifies the returnSelectors arguement, which must be not-null.
     *
     * @param string The string, representing a multivector with selectors
     * @param returnSelectors A initiated Selectorset instance, which holds the selectors after this method
     * @param getter The used variable getter
     * @return The congruous multivector
     */
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

    /**
     * Parses a string which contains selectors
     *
     * @param string The string, representing selectors
     * @param values The values set which is modified in this method
     * @param getter The used variable getter
     */
    protected void parseSelectors(String string, Variableset values, VariableGetter getter) {
        String[] partsSelectors = string.split(",");
        for (String curPart : partsSelectors) {
            values.add(getter.parseVariable(curPart.trim()));
        }

    }

    /**
     * Parses a string and returns a vector
     * @param string The string, representing the vector
     * @param getter The used variable getter
     * @return The congruous vector
     */
    protected GAPPVector parseVector(String string, VariableGetter getter) {
        return getter.parseVector(string);
    }



    

}
