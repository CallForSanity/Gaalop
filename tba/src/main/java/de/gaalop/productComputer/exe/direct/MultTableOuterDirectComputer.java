package de.gaalop.productComputer.exe.direct;

import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.bladeProdComputing.ProductCalculator;
import de.gaalop.productComputer.bladeProdComputing.OuterProductCalculator;

/**
 * Computes the outer product directly
 * @author Christian Steinmetz
 */
public class MultTableOuterDirectComputer extends MultTableAbsDirectComputer {

    public MultTableOuterDirectComputer(AlgebraDefinitionTC algebraDefinition) {
        super(algebraDefinition);
    }

    @Override
    protected ProductCalculator getProductCalculator() {
        return new OuterProductCalculator(algebra2, algebraDefinition);
    }

}
