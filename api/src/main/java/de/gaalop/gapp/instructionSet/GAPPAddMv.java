package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;
import de.gaalop.gapp.Selectorset;
import de.gaalop.gapp.VariableGetter;
import de.gaalop.gapp.variables.GAPPMultivector;

/**
Adds the selected blades from multivector mvsrc to multivector mvdest . 
All non-selected blades remain unmodiﬁed. 
This is restricted to one source and one destination multivector.
 */
public class GAPPAddMv extends GAPPBaseInstruction {

    private GAPPMultivector destinationMv;
    private GAPPMultivector sourceMv;
    private Selectorset selectorsDest;
    private Selectorset selectorsSrc;

    public GAPPAddMv(String parseArguments, VariableGetter getter) {
        parseFromString(parseArguments, getter);
    }

    public GAPPAddMv(GAPPMultivector destinationMv, GAPPMultivector sourceMv, Selectorset selectorsDest, Selectorset selectorsSrc) {
        this.destinationMv = destinationMv;
        this.sourceMv = sourceMv;
        this.selectorsDest = selectorsDest;
        this.selectorsSrc = selectorsSrc;
        assert (selectorsDest.size() == selectorsSrc.size());
    }

    @Override
    public void parseFromString(String toParse, VariableGetter getter) {
        String[] partsEquation = toParse.split("=");

        //Parse left side
        selectorsDest = new Selectorset();
        destinationMv = parseMultivectorWithSelectors(partsEquation[0].trim(), selectorsDest, getter);

        //Parse right side
        selectorsSrc = new Selectorset();
        sourceMv = parseMultivectorWithSelectors(partsEquation[1].trim(), selectorsSrc, getter);

    }

    @Override
    public void accept(GAPPVisitor visitor, Object arg) {
        visitor.visitAddMv(this, arg);
    }

    public GAPPMultivector getDestinationMv() {
        return destinationMv;

    }

    public Selectorset getSelectorsDest() {
        return selectorsDest;
    }

    public Selectorset getSelectorsSrc() {
        return selectorsSrc;
    }

    public GAPPMultivector getSourceMv() {
        return sourceMv;
    }
}
