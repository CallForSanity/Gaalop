package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVector;

/**
 * Represents the dotVectors command in the GAPP IR.
 *
 * Description from the paper:
 * Perform a scalar multiplication (dot product) on the two
 * multivector parts (vectors) (part1 and part2 ). Save the
 * result in multivector mvdest at the location selected by
 * selector sel.

 */
public class GAPPDotVectors extends GAPPBaseInstruction {

    private GAPPMultivector destination;
    private Integer destSelector;
    private GAPPVector part1;
    private GAPPVector part2;

    public GAPPDotVectors() {
    }

    public GAPPDotVectors(GAPPMultivector destination, Integer destSelector,
            GAPPVector part1, GAPPVector part2) {
        this.destination = destination;
        this.destSelector = destSelector;
        this.part1 = part1;
        this.part2 = part2;
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

    public void setDestSelector(Integer destSelector) {
        this.destSelector = destSelector;
    }

    public void setDestination(GAPPMultivector destination) {
        this.destination = destination;
    }

    public void setPart1(GAPPVector part1) {
        this.part1 = part1;
    }

    public void setPart2(GAPPVector part2) {
        this.part2 = part2;
    }

    


}
