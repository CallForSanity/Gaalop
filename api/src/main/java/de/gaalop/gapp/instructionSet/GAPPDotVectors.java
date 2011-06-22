package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVector;

/**
Perform a scalar multiplication (dot product) on the two
multivector parts (vectors) (part1 and part2 ). Save the
result in multivector mvdest at the location selected by
selector sel.

 */
public class GAPPDotVectors extends GAPPBaseInstruction {

    private GAPPMultivector destination;
    private Integer destSelector;
    private GAPPVector part1;
    private GAPPVector part2;

    public GAPPDotVectors(String parseArguments, VariableGetter getter) {
        parseFromString(parseArguments, getter);
    }

    public GAPPDotVectors(GAPPMultivector destination, Integer destSelector,
            GAPPVector part1, GAPPVector part2) {
        this.destination = destination;
        this.destSelector = destSelector;
        this.part1 = part1;
        this.part2 = part2;
    }

    @Override
    public void parseFromString(String toParse, VariableGetter getter) {
        String[] partsEquation = toParse.split("=");

        //Parse left side
        Selectorset selectorsDest = new Selectorset();
        destination = parseMultivectorWithSelectors(partsEquation[0].trim(), selectorsDest, getter);
        destSelector = selectorsDest.get(0);

        //Parse right side

        String rightSide = partsEquation[1].trim();

        if ((rightSide.charAt(0) != '<') || (rightSide.charAt(rightSide.length() - 1)) != '>') {
            System.err.println("Parse error. Wrong syntax in dotVectors instruction:" + toParse);
        } else {
            String[] partsDotProduct = rightSide.split(",");
            part1 = parseVector(partsDotProduct[0], getter);
            part2 = parseVector(partsDotProduct[1], getter);
        }

    }

    @Override
    public void accept(GAPPVisitor visitor, Object arg) {
        visitor.visitDotVectors(this, arg);
    }

    public Integer getDestSelector() {
        return destSelector;
    }

    public GAPPMultivector getDestination() {
        return destination;
    }

    public GAPPVector getPart1() {
        return part1;
    }

    public GAPPVector getPart2() {
        return part2;
    }
}
