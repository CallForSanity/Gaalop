package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selector;
import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPMultivectorComponent;
import de.gaalop.gapp.variables.GAPPSetOfVariables;
import de.gaalop.gapp.variables.GAPPVector;
import java.util.LinkedList;

/**
 * Represents the dotVectors command in the GAPP IR.
 */
public class GAPPDotVectors extends GAPPBaseInstruction {

    private GAPPSetOfVariables destination;
    private Selector destSelector; //TODO chs allowing only PosSelector?
    private LinkedList<GAPPVector> parts;

    public GAPPDotVectors(GAPPMultivector destination, Selector destSelector,
            LinkedList<GAPPVector> parts) {
        this.destination = destination;
        this.destSelector = destSelector;
        this.parts = parts;
    }

    public GAPPDotVectors(GAPPMultivectorComponent destination, LinkedList<GAPPVector> parts, String bladeName) {
        this.destination = new GAPPMultivector(destination.getName());
        this.destSelector = new Selector(destination.getBladeIndex(), (byte) 1, bladeName);
        this.parts = parts;
    }

    public GAPPDotVectors(GAPPVector destination, LinkedList<GAPPVector> parts) {
        this.destination = destination;
        this.destSelector = new Selector(0, (byte) 1, "1.0");
        this.parts = parts;
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitDotVectors(this, arg);
    }

    public Selector getDestSelector() {
        return destSelector;
    }

    public GAPPSetOfVariables getDestination() {
        return destination;
    }

    public LinkedList<GAPPVector> getParts() {
        return parts;
    }

    public void setDestSelector(Selector destSelector) {
        this.destSelector = destSelector;
    }

    public void setDestination(GAPPMultivector destination) {
        this.destination = destination;
    }

    public void setDestination(GAPPVector destination) {
        this.destination = destination;
        this.destSelector = new Selector(0, (byte) 1, "1.0");
    }

    public void setParts(LinkedList<GAPPVector> parts) {
        this.parts = parts;
    }
}
