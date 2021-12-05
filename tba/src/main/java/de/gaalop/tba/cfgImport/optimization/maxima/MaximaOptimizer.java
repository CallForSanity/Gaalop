package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.OptimizationException;

import de.gaalop.LoggingListenerGroup;
import de.gaalop.api.cfg.AssignmentNodeCollector;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.tba.Plugin;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Defines a facade class for transforming a graph with maxima
 * @author Christian Steinmetz
 */
public class MaximaOptimizer {

    private MaximaConnection connection;
    private AssignmentNodeCollector assignmentNodeCollector;
    private Plugin plugin;
    private StoreResultNodesCollector collector;

    public MaximaOptimizer(MaximaConnection connection, Plugin plugin) {
        this.connection = connection;
        this.plugin = plugin;
    }

    /**
     * Transforms a given ControlFlowGraph using the maxima optimization
     * @param graph The ControlFlowGraph to be transformed
     * @param progressLoggers
     * @throws de.gaalop.OptimizationException
     */
    public void transformGraph(ControlFlowGraph graph, LoggingListenerGroup progressLoggers) throws OptimizationException {
        collector = new StoreResultNodesCollector();
        graph.accept(collector);

        MaximaInput input = new MaximaInput();
        input.add("display2d:false;"); // very important!
        input.add("ratprint:false;"); // very important!
        input.add("keepfloat:true;");
        fillMaximaInput(graph, input);
        input.add("quit();"); // very important!

        connection.setProgressListeners(progressLoggers);
        MaximaOutput output = connection.optimizeWithMaxima(input);
        if (output == null) return;

        //connect in and output
        LinkedList<String> connected = new LinkedList<String>();
        MaximaRoutines.groupMaximaInAndOutputs(connected, output);

        connected.removeFirst(); // remove display2d
        connected.removeFirst(); // remove ratsimp
        connected.removeFirst(); // remove keepfloat

        ListIterator<AssignmentNode> listIterator = assignmentNodeCollector.getAssignmentNodes().listIterator();
        for (String io : connected) {
            Expression exp = MaximaRoutines.getExpressionFromMaximaOutput(io);
            listIterator.next().setValue(exp);
        }
/*
        if (plugin.isOptInserting() && plugin.isScalarFunctions()) {
            removeUnusedAssignments(graph, collector.getVariables());
        }
*/

    }

    /**
     * Fills a given MaximaInput instance with input for maxima, determined from a given graph
     * @param graph The graph to be transformed in MaximaInput
     * @param input The MaximaInput instance to be filled
     */
    private void fillMaximaInput(ControlFlowGraph graph, MaximaInput input) {


        assignmentNodeCollector = new AssignmentNodeCollector();
        graph.accept(assignmentNodeCollector);

        for (AssignmentNode node : assignmentNodeCollector.getAssignmentNodes()) {

            DFGToMaximaCode dfg = new DFGToMaximaCode();
            node.getVariable().accept(dfg);
            String variable = "";



            //using the store result nodes for marking to evaluate immediately is not possible in all cases,
            //reason: consider a large cluscript with only one StoreResultNode add the end
            //all non-marked assignments were inserted in the assignment with the StoreResultNode.getValue() as desitination variable
            //this expression can be very long. Possible too long for the java code limit per method (65535 bytes)
            //Splitting isn't trivial except of splitting the methods between two assignments, so the using of store result nodes can be expensive to compile time.

            dfg = new DFGToMaximaCode();
            node.getValue().accept(dfg);
            String value = "ratsimp("+dfg.getResultString() + ");";
            if (plugin.isOptInserting()) {
                String name = node.getVariable().getName();
                if (!graph.getPragmaOnlyEvaluateVariables().contains(name) && !collector.containsStoreResultVariableName(name)) { // see comment above
                    dfg = new DFGToMaximaCode();
                    node.getVariable().accept(dfg);
                    variable = dfg.getResultString() + "::";
                }
            }

            if (!plugin.isScalarFunctions() & !(node.getVariable() instanceof MultivectorComponent)) {
                variable = "";
            }

            if (plugin.isMaximaExpand())
                input.add(variable + "expand(ratsimp("+value.substring(0, value.length()-1)+"));");
            else
                input.add(variable + value);

        }

    }

}
