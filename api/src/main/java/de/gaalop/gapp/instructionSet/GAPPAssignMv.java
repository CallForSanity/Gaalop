package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVariable;
import de.gaalop.gapp.visitor.GAPPVisitor;
import java.util.Vector;

/**
Assign values val0 , val1 , . . . , valn at blades selected by
positive selectors sel0 , sel1 , . . . , seln . Blade selectors are
explained below. This is restricted to one source multivector.
 */
public class GAPPAssignMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;
    private Selectorset selectors;
    private Variableset values;

    public GAPPAssignMv() {
    }

    public GAPPAssignMv(GAPPMultivector destinationMv, Selectorset selectors, Variableset values) {
        this.destinationMv = destinationMv;
        this.selectors = selectors;
        this.values = values;
        assert (selectors.size() == values.size());
    }

    public GAPPAssignMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
        this.selectors = new Selectorset();
        this.values = new Variableset();
    }

    public void add(int selector, GAPPVariable toAdd) {
        selectors.add(selector);
        values.add(toAdd);
    }

    @Override
    public void accept(GAPPVisitor visitor, Object arg) {
        visitor.visitAssignMv(this, arg);
    }

    public GAPPMultivector getDestinationMv() {
        return destinationMv;
    }

    public Selectorset getSelectors() {
        return selectors;
    }

    public Variableset getValues() {
        return values;
    }

    public void setDestinationMv(GAPPMultivector destinationMv) {
        this.destinationMv = destinationMv;
    }

    public void setSelectors(Selectorset selectors) {
        this.selectors = selectors;
    }

    public void setValues(Variableset values) {
        this.values = values;
    }

    


}
