package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVector;
import de.gaalop.gapp.visitor.GAPPVisitor;

public abstract class GAPPBaseInstruction {

    public abstract void parseFromString(String toParse, VariableGetter getter);

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

    public abstract void accept(GAPPVisitor visitor, Object arg);
}
