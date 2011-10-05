package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.PosSelectorset;
import de.gaalop.gapp.Selector;
import de.gaalop.gapp.PosSelector;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Represents the assignMv command in the GAPP IR.
 *
 * Description from the paper:
 * Assigns scalar values or variables val0 , val1 , . . . , valn at
 * blades selected by positive selectors sel0 , sel1 , . . . , seln .
 * Blade selectors are explained below. This is restricted
 * to one source multivector.
 */
public class GAPPAssignMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;
    private PosSelectorset selectors;
    private Variableset values;

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

    public void add(int index, GAPPScalarVariable toAdd) {
        selectors.add(new PosSelector(index));
        values.add(toAdd);
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
