package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVector;

/**
 * Represents the dotVectors command in the GAPP IR.
 *
 * Description from the paper:
 * Performs a scalar multiplication (dot product) on the
 * two vectors (parts of multivectors) part1 and part2 .
 * Saves the result in multivector mvdest at the location
 * selected by selector sel.
 */
public class GAPPDotVectors extends GAPPBaseInstruction {

    private GAPPMultivector destination;
    private Selector destSelector;
    private GAPPVector part1;
    private GAPPVector part2;

    public GAPPDotVectors() {
    }

    public GAPPDotVectors(GAPPMultivector destination, Selector destSelector,
            GAPPVector part1, GAPPVector part2) {
        this.destination = destination;
        this.destSelector = destSelector;
        this.part1 = part1;
        this.part2 = part2;
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitDotVectors(this, arg);
    }

    public Selector getDestSelector() {
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

    public void setDestSelector(Selector destSelector) {
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
