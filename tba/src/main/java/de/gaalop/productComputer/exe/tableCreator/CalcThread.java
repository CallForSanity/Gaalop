package de.gaalop.productComputer.exe.tableCreator;

import de.gaalop.dfg.Expression;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.productComputer.bladeOperations.FacadeBaseChange;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.transformer.TCExpressionCreator;
import de.gaalop.productComputer.bladeProdComputing.GeoProductCalculator;
import de.gaalop.productComputer.bladeProdComputing.InnerProductCalculator;
import de.gaalop.productComputer.bladeProdComputing.MultivectorCreator;
import de.gaalop.productComputer.bladeProdComputing.OuterProductCalculator;
import de.gaalop.productComputer.bladeProdComputing.ProductCalculator;
import de.gaalop.productComputer.simplification.Simplifier;
import de.gaalop.tba.Algebra;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.UseAlgebra;


/**
 * Implements a thread that calculates products
 * @author Christian Steinmetz
 */
public class CalcThread extends Thread {

    //input
    public AlgebraDefinitionTC algebraDefinition;
    public BladeIndexer indexer;
    public Expression[] simpBlades;
    public Algebra algebra2;
    public TCBlade[] blades2;
    public int from;
    public int to;
    public Algebra algebra;

    //output
    public UseAlgebra useAlgebra;

    /**
     * Creates products of an interval of an array of simplified blades
     * @param algebra2 The algebra
     * @param indexer The indexer
     * @param bladeCount The number of blades
     * @param simpBlades The array of simplified blades
     * @param from The interval start
     * @param to The interval end
     * @return The created products
     */
    private TCExpression[] createProducts(Algebra algebra2, BladeIndexer indexer, int bladeCount, Expression[] simpBlades, int from, int to) {
        ProductCalculator inner = new InnerProductCalculator(algebra2, algebraDefinition);
        ProductCalculator outer = new OuterProductCalculator(algebra2, algebraDefinition);
        ProductCalculator geo = new GeoProductCalculator(algebra2, algebraDefinition);

        inner.setIndexer(indexer);
        outer.setIndexer(indexer);
        geo.setIndexer(indexer);

        TCExpression[] products = new TCExpression[3*bladeCount*(to-from)];

        int k = 0;
        for (int i=from;i<to;i++) {
            for (int j=0;j<bladeCount;j++) {
                Expression innerExpr = inner.calculateProduct(simpBlades[i], simpBlades[j]);
                products[k++] = TCExpressionCreator.create(innerExpr, algebra2);

                Expression outerExpr = outer.calculateProduct(simpBlades[i], simpBlades[j]);
                products[k++] = TCExpressionCreator.create(outerExpr, algebra2);

                Expression geoExpr = geo.calculateProduct(simpBlades[i], simpBlades[j]);
                products[k++] = TCExpressionCreator.create(geoExpr, algebra2);

            }
        }

        return products;
    }

    /**
     * Changes the base of blades
     * @param blades The blades
     * @param indexer The indexer to be used
     * @return The array of new blades with changed base
     */
    private Expression[] baseChange2(TCExpression[] blades, BladeIndexer indexer) {
        FacadeBaseChange facade = new FacadeBaseChange(algebraDefinition);

        int bladeCount = blades.length;
        Expression[] result = new Expression[bladeCount];
        for (int i=0;i<bladeCount;i++)
             result[i] = Simplifier.simplify(facade.transformToZeroInfBase(blades[i]),indexer);

        return result;
    }

    /**
     * Creates the UseAlgebra from an interval of the products represented by the expression array
     * @param algebra The algebra
     * @param bladeCount The number of blades
     * @param products The products
     * @param indexer The indexer
     * @param from The interval start
     * @param to The interval end
     */
    private void createUseAlgebra(Algebra algebra, int bladeCount, Expression[] products, BladeIndexer indexer, int from, int to) {
        IMultTable tabInner = useAlgebra.getTableInner();
        IMultTable tabOuter = useAlgebra.getTableOuter();
        IMultTable tabGeo = useAlgebra.getTableGeo();

        int k = 0;
        for (int i=from;i<to;i++)
            for (int j=0;j<bladeCount;j++) {
                tabInner.setProduct(i, j, MultivectorCreator.getMultivectorFromExpression(products[k++], algebra, indexer));
                tabOuter.setProduct(i, j, MultivectorCreator.getMultivectorFromExpression(products[k++], algebra, indexer));
                tabGeo.setProduct(i, j, MultivectorCreator.getMultivectorFromExpression(products[k++], algebra, indexer));
            }
    }

    @Override
    public void run() {
        //Create Product calculators
        int bladeCount = blades2.length;
        
        useAlgebra = new UseAlgebra(algebra, bladeCount);

        //for little number of live variables do only single steps
        for (int i=from;i<to;i++) {

            TCExpression[] products = createProducts(algebra2, indexer, bladeCount, simpBlades, i, i+1);

            //Change Base to einf e0
            BladeIndexer indexer2 = new BladeIndexer();

            Expression[] simpBlades2 = baseChange2(products, indexer2);
            //indexer2.printAllBlades();
            //Compute Product Tables and store the Products in a UseAlgebra object
            createUseAlgebra(algebra, bladeCount, simpBlades2, indexer2, i, i+1);
        }
    }

    

}
