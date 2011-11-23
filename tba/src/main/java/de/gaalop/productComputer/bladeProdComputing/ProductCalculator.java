package de.gaalop.productComputer.bladeProdComputing;

import de.gaalop.api.dfg.DFGMethods;
import de.gaalop.productComputer.transformer.SignedSummandsGetterIndexer;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.dataStruct.TCSum;
import de.gaalop.productComputer.simplification.Simplifier;
import de.gaalop.productComputer.transformer.Summand;
import de.gaalop.productComputer.transformer.SummandsCreator;
import de.gaalop.tba.Algebra;
import java.util.LinkedList;

/**
 * Defines an interface for calculating a product of two blades
 * @author Christian Steinmetz
 */
public abstract class ProductCalculator {

    protected Algebra algebra;
    protected AlgebraDefinitionTC algebraDefinition;
    protected BladeIndexer indexer;

    public ProductCalculator(Algebra algebra, AlgebraDefinitionTC algebraDefinition) {
        this.algebra = algebra;
        this.algebraDefinition = algebraDefinition;
    }

    public BladeIndexer getIndexer() {
        return indexer;
    }

    public void setIndexer(BladeIndexer indexer) {
        this.indexer = indexer;
    }

    /**
     * Calculates a product of two blades
     * @param blade1 The first blade
     * @param blade2 The second blade
     */
    public Expression calculateProduct(Expression blade1, Expression blade2) {
        LinkedList<Summand> summands1 = toSummands(blade1);
        LinkedList<Summand> summands2 = toSummands(blade2);

        LinkedList<Expression> resultSummands = new LinkedList<Expression>();
        

        for (Summand s1: summands1) {
            for (Summand s2: summands2) {
                Expression prod = calculateProductBlades(s1.getBlade(), s2.getBlade());
                float factors = s1.getPrefactor()*s2.getPrefactor();
                if (Math.abs(factors-1) > 10E-7) 
                    prod = new Multiplication(new FloatConstant(factors), prod);
                if (Math.abs(factors) < 10E-7)
                    prod = new FloatConstant(0);

                resultSummands.add(prod);
            }
        }

        return DFGMethods.exprArrToAddition(resultSummands.toArray(new Expression[0]));
    }

    /**
     * This method computes the product of two blades
     * @param blade1 The first blade
     * @param blade2 The second blade
     * @return The result expression
     */
    public abstract Expression calculateProductBlades(TCBlade blade1, TCBlade blade2);

    /**
     * Creates a list of summands from an expanded expression
     * @param expandedExpr The expranded expression
     * @return The list of summands
     */
    private LinkedList<Summand> toSummands(Expression expandedExpr) {
        TCSum signedSummands = SignedSummandsGetterIndexer.getSignedSummands(expandedExpr, indexer);
        LinkedList<Summand> result = new LinkedList<Summand>();
        for (TCExpression object: signedSummands.getExpressions()) 
           result.add(SummandsCreator.getMultivectorFromExpression(object));
        
        return result;
    }

    /**
     * Returns the index of a base in the algebra
     * @param base The base
     * @return The index in the algebra
     */
    protected int getIndexFromBase(String[] base) {
        return algebra.getIndex(new de.gaalop.tba.Blade(base));
    }


}
