package de.gaalop.visualCodeInserter;

import de.gaalop.OptimizationException;
import de.gaalop.VisualizerStrategy;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ExpressionStatement;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.InnerProduct;
import de.gaalop.dfg.MacroCall;
import de.gaalop.dfg.Variable;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class VisualizerCodeInserter implements VisualizerStrategy {

    private Plugin plugin;

    public VisualizerCodeInserter(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        //insert visualizing commands, if needed
        LinkedList<ExpressionStatement> statements = ExpressionStatementCollector.collectAllStatements(graph);


        if (statements.size() > 0) {
            LinkedList<Expression> args = new LinkedList<Expression>();

            String prefix = "_V_";

            args.add(new Variable(prefix+"X"));
            args.add(new Variable(prefix+"Y"));
            args.add(new Variable(prefix+"Z"));
            graph.addInputVariable(new Variable(prefix+"X"));
            graph.addInputVariable(new Variable(prefix+"Y"));
            graph.addInputVariable(new Variable(prefix+"Z"));

            Variable visualizationPoint = new Variable(prefix+"POINT");
            graph.addLocalVariable(new Variable(prefix+"POINT"));

            AssignmentNode pointNode = new AssignmentNode(graph, visualizationPoint, new MacroCall("createPoint", args));
            graph.getStartNode().insertAfter(pointNode);

            int i=0;
            for (ExpressionStatement s: statements) {
                AssignmentNode renderNode = new AssignmentNode(graph, new Variable(prefix+"PRODUCT"+i), new InnerProduct(s.getExpression(), visualizationPoint));
                graph.addLocalVariable(new Variable(prefix+"PRODUCT"+i));
                s.insertAfter(renderNode);

                StoreResultNode outputRenderNode = new StoreResultNode(graph, new Variable(prefix+"PRODUCT"+i));
                graph.addLocalVariable(new Variable(prefix+"PRODUCT"+i));
                renderNode.insertAfter(outputRenderNode);
                i++;
            }

        }
    }

}
