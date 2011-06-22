package de.gaalop.gapp.variables;

import de.gaalop.dfg.MultivectorComponent;

public class GAPPSignedMultivectorComponent extends GAPPMultivectorComponent {

    public byte sign;

    public GAPPSignedMultivectorComponent() {
        sign = 1;
    }

    public GAPPSignedMultivectorComponent(GAPPMultivector mv, int bladeIndex) {
        parentMultivector = mv;
        this.bladeIndex = bladeIndex;
        this.sign = 1;
    }

    public GAPPSignedMultivectorComponent(GAPPMultivector mv, int bladeIndex, byte sign) {
        parentMultivector = mv;
        this.bladeIndex = bladeIndex;
        this.sign = sign;
    }

    public String prettyPrint() {
        return ((sign == -1) ? "-" : "") + super.prettyPrint();
    }

    public MultivectorComponent getMultivectorComponent() {
        return new MultivectorComponent(parentMultivector.getName(), bladeIndex);
    }
}
