package de.gaalop.productComputer.exe.direct;

import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.bladeProdComputing.InnerProductCalculator;
import de.gaalop.productComputer.bladeProdComputing.ProductCalculator;

/**
 * Computes the inner product directly
 * @author Christian Steinmetz
 */
public class MultTableInnerDirectComputer extends MultTableAbsDirectComputer {

    public MultTableInnerDirectComputer(AlgebraDefinitionTC algebraDefinition) {
        super(algebraDefinition);
    }

    @Override
    protected ProductCalculator getProductCalculator() {
        return new InnerProductCalculator(algebra2, algebraDefinition);
    }

}
