package de.gaalop.productComputer.simplification;

import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCConstant;
import de.gaalop.productComputer.dataStruct.visitor.TCTraversalVisitor;

/**
 * Finds factors and a blade in a TCExpression
 * @author Christian Steinmetz
 */
public class FactorFinder extends TCTraversalVisitor {

    private TCBlade blade;
    private float factor = 1;

    public TCBlade getBlade() {
        return blade;
    }

    public float getFactor() {
        return factor;
    }

    @Override
    public Object visitTCBlade(TCBlade tcBlade, Object arg) {
        blade = tcBlade;
        return null;
    }

    @Override
    public Object visitTCConstant(TCConstant tcConstant, Object arg) {
        factor *= tcConstant.getValue();
        return null;
    }


}
