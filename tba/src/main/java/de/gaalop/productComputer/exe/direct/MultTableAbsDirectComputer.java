package de.gaalop.productComputer.exe.direct;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.Multivector;
import de.gaalop.productComputer.simplification.Simplifier;
import de.gaalop.dfg.Expression;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.BladeArrayRoutines;
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.productComputer.bladeOperations.FacadeBaseChange;
import de.gaalop.productComputer.bladeProdComputing.ProductCalculator;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.bladeProdComputing.MultivectorCreator;
import de.gaalop.productComputer.transformer.TCExpressionCreator;
import de.gaalop.tba.Algebra;
import de.gaalop.tba.Blade;
import de.gaalop.tba.IMultTable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides methods for direct computation of the product of two blades
 * @author Christian Steinmetz
 */
public abstract class MultTableAbsDirectComputer implements IMultTable {

    /**
     * This method must be implemented from all ProductDirectComputers
     * @return The ProductCalculator that must be used
     */
    protected abstract ProductCalculator getProductCalculator();

    protected AlgebraDefinitionTC algebraDefinition;
    protected Algebra algebra2;
    
    private Expression[] simpBlades;
    private Algebra algebra;
    private BladeIndexer indexer;

    public Algebra getAlgebra() {
        return algebra;
    }

    


    public MultTableAbsDirectComputer(AlgebraDefinitionTC algebraDefinition) {
        this.algebraDefinition = algebraDefinition;
    }

    @Override
    public void createTable(int dimension) {
        //Create Algebra einf e0
        algebra = new Algebra();
        TCBlade[] blades = createAlgebra(algebraDefinition.base, algebra);
        indexer = new BladeIndexer();
        simpBlades = baseChange(blades, indexer);
        //Create Algebra2 ep em
        algebra2 = new Algebra();
        createAlgebra(algebraDefinition.base2, algebra2);
    }
    
    @Override
    public Multivector getProduct(Integer factor1, Integer factor2) {
        //Create Product calculators
        
        ProductCalculator inner = getProductCalculator();
        inner.setIndexer(indexer);
        Expression innerExpr = inner.calculateProduct(simpBlades[factor1], simpBlades[factor2]);
        TCExpression product = TCExpressionCreator.create(innerExpr, algebra2);

        //Change Base to einf e0
        BladeIndexer indexer2 = new BladeIndexer();

        //Perpare first Maxima input
        FacadeBaseChange facade = new FacadeBaseChange(algebraDefinition);
        
        Expression simpBlades2 = Simplifier.simplify(facade.transformToZeroInfBase(product),indexer2);

        //Compute Product Tables and store the Products in a UseAlgebra object
        return MultivectorCreator.getMultivectorFromExpression(simpBlades2, algebra, indexer2);
    }

    @Override
    public void setProduct(Integer factor1, Integer factor2, Multivector product) {
        //Do nothing
    }

    /**
     * Creates an array of TCBlades
     * @param base The base
     * @param algebra The algebra
     * @return The array of TCBlades
     */
    private TCBlade[] createAlgebra(String[] base, Algebra algebra) {
        TCBlade[] blades = BladeArrayRoutines.createBlades(Arrays.copyOfRange(base, 1, base.length));
        int bladeCount = blades.length;
        algebra.setBase(base);
        for (int i=0;i<bladeCount;i++) {
            algebra.setBlade(i, new Blade(blades[i].getBase()));
        }
        return blades;
    }

    /**
     * Changes the base and simplify the blades
     * @param blades The blades
     * @param indexer The indexer to be used
     * @return The simplified Expressions with changed base
     */
    private Expression[] baseChange(TCExpression[] blades, BladeIndexer indexer) {
        FacadeBaseChange facade = new FacadeBaseChange(algebraDefinition);

        int bladeCount = blades.length;
        Expression[] result = new Expression[bladeCount];
        for (int i=0;i<bladeCount;i++)
             result[i] = Simplifier.simplify(facade.transformToPlusMinusBase(blades[i]),indexer);

        return result;
    }
    
}
