package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.BaseVector;
import de.gaalop.dfg.EmptyExpressionVisitor;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Checks all variables to be no BaseVector in the UseAlgebra,
 * if not it replaces them by real BaseVectorInstances.
 * This step is needed because the CluCalcParser don't recognizes for example e6 as a BaseVector.
 * 
 * @author Christian Steinmetz
 */
public class BaseVectorChecker extends EmptyControlFlowVisitor {

    private HashSet<String> baseVectors;

    public BaseVectorChecker(String[] baseVectors) {
        this.baseVectors = new HashSet<String>(Arrays.asList(baseVectors));
    }

    private class DFGVisitor extends EmptyExpressionVisitor {
        private HashMap<Expression, Expression> toReplace = new HashMap<Expression, Expression>();

        @Override
        public void visit(Variable node) {
            if (baseVectors.contains(node.getName()))
                toReplace.put(node, new BaseVector(node.getName().substring(1)));
            super.visit(node);
        }

    }

    @Override
    public void visit(AssignmentNode node) {
        DFGVisitor dfgVisitor = new DFGVisitor();
        node.getValue().accept(dfgVisitor);

        for (Expression toReplace: dfgVisitor.toReplace.keySet()) {
            Expression replacement = dfgVisitor.toReplace.get(toReplace);
            if (node.getValue() == toReplace) {
                node.setValue(replacement);
            } else {
                node.getValue().replaceExpression(toReplace, replacement);
            }
        }

        super.visit(node);
    }



}
