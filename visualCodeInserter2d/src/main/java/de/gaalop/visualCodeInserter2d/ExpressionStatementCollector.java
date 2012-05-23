package de.gaalop.visualCodeInserter2d;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.ExpressionStatement;
import java.util.LinkedList;

/**
 * Collects all Expression statements
 * @author Christian Steinmetz
 */
public class ExpressionStatementCollector extends EmptyControlFlowVisitor {

    private LinkedList<ExpressionStatement> statements = new LinkedList<ExpressionStatement>();

    /**
     * Collects all Expression statements in a graph
     * @param graph The graph
     * @return The list of all expression statements
     */
    public static LinkedList<ExpressionStatement> collectAllStatements(ControlFlowGraph graph) {
        ExpressionStatementCollector collector = new ExpressionStatementCollector();
        graph.accept(collector);
        return collector.statements;
    }

    @Override
    public void visit(ExpressionStatement node) {
        statements.add(node);
        super.visit(node);
    }

}
