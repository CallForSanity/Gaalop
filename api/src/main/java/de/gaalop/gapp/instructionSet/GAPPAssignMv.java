package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Represents the assignMv command in the GAPP IR.
 */
public class GAPPAssignMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;
    private PosSelectorset selectors;
    private Variableset values; //TODO chs This can be facilitated to a LinkedList<Float>!

    public GAPPAssignMv(GAPPMultivector destinationMv, PosSelectorset selectors, Variableset values) {
        this.destinationMv = destinationMv;
        this.selectors = selectors;
        this.values = values;
        assert (selectors.size() == values.size());
    }

    public GAPPAssignMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
        this.selectors = new PosSelectorset();
        this.values = new Variableset();
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitAssignMv(this, arg);
    }

    public GAPPMultivector getDestinationMv() {
        return destinationMv;
    }

    public PosSelectorset getSelectors() {
        return selectors;
    }

    public Variableset getValues() {
        return values;
    }

    public void setDestinationMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
    }

    public void setSelectors(PosSelectorset selectors) {
        this.selectors = selectors;
    }

    public void setValues(Variableset values) {
        this.values = values;
    }
}
