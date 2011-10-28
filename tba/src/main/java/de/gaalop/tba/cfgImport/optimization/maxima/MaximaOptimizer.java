package de.gaalop.tba.cfgImport.optimization.maxima;

import de.gaalop.OptimizationException;
import de.gaalop.api.cfg.AssignmentNodeCollector;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.tba.Plugin;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaLexer;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaParser;
import de.gaalop.tba.cfgImport.optimization.maxima.parser.MaximaTransformer;
import java.util.LinkedList;
import java.util.ListIterator;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

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
     * @throws RecognitionException
     */
    public void transformGraph(ControlFlowGraph graph) throws RecognitionException, OptimizationException {
        collector = new StoreResultNodesCollector();
        graph.accept(collector);

        MaximaInput input = new MaximaInput();
        input.add("display2d:false;"); // very important!
        fillMaximaInput(graph, input);
        input.add("quit();"); // very important!

        MaximaOutput output = connection.optimizeWithMaxima(input);



        //connect in and output
        LinkedList<String> connected = new LinkedList<String>();
        groupMaximaInAndOutputs(connected, output);

        connected.removeFirst(); // remove display2d

        ListIterator<AssignmentNode> listIterator = assignmentNodeCollector.getAssignmentNodes().listIterator();
        for (String io : connected) {
            Expression exp = getExpressionFromMaximaOutput(io);
            listIterator.next().setValue(exp);
        }
/*
        if (plugin.isOptInserting() && plugin.isScalarFunctions()) {
            removeUnusedAssignments(graph, collector.getVariables());
        }
*/

    }

    /**
     * Returns an expression from a maxima output string.
     * @param maximaOut The maxima output string
     * @return The according expression
     * @throws RecognitionException
     */
    public static Expression getExpressionFromMaximaOutput(String maximaOut) throws RecognitionException {
        ANTLRStringStream inputStream = new ANTLRStringStream(maximaOut);
        MaximaLexer lexer = new MaximaLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        MaximaParser parser = new MaximaParser(tokenStream);
        MaximaParser.program_return parserResult = parser.program();

        CommonTreeNodeStream treeNodeStream = new CommonTreeNodeStream(parserResult.getTree());
        MaximaTransformer transformer = new MaximaTransformer(treeNodeStream);

        return transformer.expression();
    }

    /**
     * Fills a given list of MaximaInOut from an output of maxima
     * @param connected The list of MaximaInOut to be filled
     * @param output The output of maxima
     */
    private void groupMaximaInAndOutputs(LinkedList<String> connected, MaximaOutput output) {

        StringBuilder sb = new StringBuilder();

        boolean input = false;
        for (String o : output) {
            if (o.startsWith("(%i")) {
                input = false;
                if (sb.length() != 0) {
                    connected.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                if (o.startsWith("(%o")) {
                    sb.append(o.substring(o.indexOf(")") + 2));
                    input = true;
                } else {
                    if (input) {
                        sb.append(o);
                    }
                }
            }
        }

        if (input && sb.length() > 0) {
            connected.add(sb.toString());
        }

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
            String value = dfg.getResultString() + ";";
            if (plugin.isOptInserting()) {

                if (!collector.containsStoreResultVariableName(node.getVariable().getName())) { // see comment above
                    dfg = new DFGToMaximaCode();
                    node.getVariable().accept(dfg);
                    variable = dfg.getResultString() + "::";
                }
            }

            if (!plugin.isScalarFunctions() & !(node.getVariable() instanceof MultivectorComponent)) {
                variable = "";
            }

            if (plugin.isMaximaExpand())
                input.add(variable + "expand("+value.substring(0, value.length()-1)+");");
            else
                input.add(variable + value);

        }

    }

}
