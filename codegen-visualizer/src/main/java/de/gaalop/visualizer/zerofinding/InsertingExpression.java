package de.gaalop.visualizer.zerofinding;

import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.ReplaceVisitor;
import java.util.HashMap;

/**
 * Inserts an Expression A into Expression B, if A is located before B
 * @author Christian Steinmetz
 */
public class InsertingExpression extends EmptyControlFlowVisitor {

    public static void insertExpressions(ControlFlowGraph graph) {
        InsertingExpression insertingExpression = new InsertingExpression();
        graph.accept(insertingExpression);
    }
    
    private HashMap<Variable, Expression> mapExpressions = new HashMap<Variable, Expression>();

    @Override
    public void visit(AssignmentNode node) {
        ReplaceVisitor replaceVisitor = new ReplaceVisitor() {

            @Override
            public void visit(Variable node) {
                if (mapExpressions.containsKey(node)) 
                    result = mapExpressions.get(node);
            }

            @Override
            public void visit(MultivectorComponent node) {
                if (mapExpressions.containsKey(node)) 
                    result = mapExpressions.get(node);
            }

            
            
        };
        node.setValue(replaceVisitor.replace(node.getValue()));
        mapExpressions.put(node.getVariable(), node.getValue());
        super.visit(node);
    }
    
    

}
