package de.gaalop.visualCodeInserter2d;

import de.gaalop.OptimizationException;
import de.gaalop.VisualCodeInserterStrategy;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.Variable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Implements a strategy that inserts draw code in the cluscript
 * @author Christian Steinmetz
 */
public class VisualizerCodeInserter2d implements VisualCodeInserterStrategy {

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        //insert visualizing commands, if needed
        LinkedList<ExpressionStatement> statements = ExpressionStatementCollector.collectAllStatements(graph);

        String prefix = "_V_";
        
        //2d
        HashMap<String, Expression> renderingExpressions = graph.getRenderingExpressions();
        int i = 0;
        for (ExpressionStatement s : statements) {
            String productName = prefix + "PRODUCT" + i;
            AssignmentNode renderNode = new AssignmentNode(graph, new Variable(productName), s.getExpression());
            graph.addLocalVariable(new Variable(productName));
            s.insertAfter(renderNode);

            StoreResultNode outputRenderNode = new StoreResultNode(graph, new Variable(productName));
            graph.addLocalVariable(new Variable(productName));
            renderNode.insertAfter(outputRenderNode);
            renderingExpressions.put(productName, s.getExpression());
            i++;
        }
    }
}
