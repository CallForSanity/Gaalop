package de.gaalop.productComputer.transformer;

import de.gaalop.dfg.Expression;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.tba.Algebra;

/**
 * Facade class for creating TCExpression from expressions
 * @author Christian Steinmetz
 */
public class TCExpressionCreator {

    /**
     * Facade method for creating TCExpression from an expression
     * @param expr The expression
     * @param algebra The algebra
     * @return The created TCExpression
     */
    public static TCExpression create(Expression expr, Algebra algebra) {
        return SignedSummandsGetter.getSignedSummands(expr, algebra);
    }

}
