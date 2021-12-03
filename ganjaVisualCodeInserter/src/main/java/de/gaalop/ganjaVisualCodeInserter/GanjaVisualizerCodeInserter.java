package de.gaalop.ganjaVisualCodeInserter;

import de.gaalop.OptimizationException;
import de.gaalop.VisualCodeInserterStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Variable;
import java.util.LinkedList;

/**
 * Implements a strategy that inserts draw code in the cluscript
 *
 * @author Christian Steinmetz
 */
public class GanjaVisualizerCodeInserter implements VisualCodeInserterStrategy {

    private Plugin plugin;

    public GanjaVisualizerCodeInserter(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        //insert visualizing commands, if needed
        LinkedList<ExpressionStatement> statements = ExpressionStatementCollector.collectAllStatements(graph);

        if (statements.size() > 0) {
            for (ExpressionStatement s : statements) {
                StoreResultNode outputRenderNode = new StoreResultNode(graph, (Variable) (s.getExpression()));
                s.insertAfter(outputRenderNode);
            }
        }
    }
}
