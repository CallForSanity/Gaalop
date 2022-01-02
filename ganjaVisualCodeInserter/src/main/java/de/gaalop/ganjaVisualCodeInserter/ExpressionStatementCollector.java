package de.gaalop.ganjaVisualCodeInserter;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ExpressionStatement;
import java.util.LinkedList;

/**
 * Collects all Expression statements
 * @author Christian Steinmetz
 */
public class ExpressionStatementCollector {

    /**
     * Collects all Expression statements in a graph
     * @param graph The graph
     * @return The list of all expression statements
     */
    public static LinkedList<ExpressionStatement> collectAllStatements(ControlFlowGraph graph) {
        return graph.visualizerExpressions;
    }


}
