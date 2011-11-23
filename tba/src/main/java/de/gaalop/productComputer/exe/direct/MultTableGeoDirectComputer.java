package de.gaalop.productComputer.exe.direct;

import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.bladeProdComputing.ProductCalculator;
import de.gaalop.productComputer.bladeProdComputing.GeoProductCalculator;

/**
 * Computes the geometric product directly
 * @author Christian Steinmetz
 */
public class MultTableGeoDirectComputer extends MultTableAbsDirectComputer {

    public MultTableGeoDirectComputer(AlgebraDefinitionTC algebraDefinition) {
        super(algebraDefinition);
    }

    @Override
    protected ProductCalculator getProductCalculator() {
        return new GeoProductCalculator(algebra2, algebraDefinition);
    }

}
