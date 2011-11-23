package de.gaalop.productComputer.simplification;

import de.gaalop.api.dfg.DFGMethods;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.Multiplication;
import de.gaalop.dfg.Variable;
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * Facade for simplification
 * @author Christian Steinmetz
 */
public class Simplifier {

    public static Expression simplify(TCExpression expression, BladeIndexer indexer) {
        ListTCExpr summands = (ListTCExpr) expression.accept(new SummandsCreator(), null);

        HashMap<TCBlade, Float> groups = new HashMap<TCBlade, Float>();
        for (TCExpression summand: summands) {
            FactorFinder finder = new FactorFinder();
            summand.accept(finder, null);
            TCBlade blade = finder.getBlade();
            float factor = finder.getFactor();
            if (blade == null) blade = new TCBlade(new String[0]);
            if (blade.isNegated()) factor*=-1;
            if (groups.containsKey(blade))
                groups.put(blade, factor+groups.get(blade));
            else
                groups.put(blade, factor);
        }

        if (groups.isEmpty()) return new FloatConstant(0);
        LinkedList<Expression> list = new LinkedList<Expression>();
        
        for (TCBlade blade: groups.keySet()) {
            Variable var = new Variable("G"+indexer.getIndex(blade));
            if (Math.abs(groups.get(blade)) >= 10E-7) {
                Expression e = (Math.abs(1-groups.get(blade)) < 10E-7)
                        ? var
                        : new Multiplication(new FloatConstant(groups.get(blade)), var);
                list.add(e);
            }
        }

        if (list.size() == 0) return new FloatConstant(0);
        return DFGMethods.exprArrToAddition(list.toArray(new Expression[0]));
    }

}
