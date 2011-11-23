package de.gaalop.productComputer.bladeOperations;

import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.dataStruct.visitor.TCBaseChangeVisitor;
import java.util.HashMap;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCSum;

/**
 * Defines a facade for changing base
 * @author Christian Steinmetz
 */
public class FacadeBaseChange {

    public BladeIndexer indexer;

    private HashMap<String, TCSum> mapZeroInfToPlusMinus = new HashMap<String, TCSum>();
    private HashMap<String, TCSum> mapPlusMinusToZeroInf = new HashMap<String, TCSum>();

    private AlgebraDefinitionTC algebraDefinition;

    public FacadeBaseChange(AlgebraDefinitionTC algebraDefinition) {
        this.algebraDefinition = algebraDefinition;
        mapPlusMinusToZeroInf = algebraDefinition.mapPlusMinusToZeroInf;
        mapZeroInfToPlusMinus = algebraDefinition.mapZeroInfToPlusMinus;
    }
    
    /**
     * Transforms a TCExpression to the plus-minus base
     * @param expression The TCExpression
     * @return The transformed TCExpression
     */
    public TCExpression transformToPlusMinusBase(TCExpression expression) {
        return changeBase(expression, mapZeroInfToPlusMinus);
    }

    /**
     * Transforms a TCExpression to the zero-inf base
     * @param expression The TCExpression
     * @return The transformed TCExpression
     */
    public TCExpression transformToZeroInfBase(TCExpression expression) {
        return changeBase(expression, mapPlusMinusToZeroInf);
    }

    /**
     * Changes the base of a TCExpression using a map
     * @param expression The expression
     * @param map The map
     * @return The expression with cnanged bases
     */
    private TCExpression changeBase(TCExpression expression, HashMap<String, TCSum> map) {
        TCExpression result = new TCBaseChangeVisitor(map).replace(expression);
        result = new BladeZeroIdentifier().replace(result);
        BladeNormaliser.normalize(result, algebraDefinition);
        return result;
    }



}
