package de.gaalop.tba.cfgImport.optimization;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.tba.UseAlgebra;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author christian
 */
public class OptUnusedAssignmentsRemoval implements OptimizationStrategyWithModifyFlag {


    @Override
    public boolean transform(ControlFlowGraph graph, UseAlgebra usedAlgebra) {
        // traverse the graph in the opposite direction
        NodeCollectorControlFlowVisitor v = new NodeCollectorControlFlowVisitor();
        graph.accept(v);
        LinkedList<Node> nodeList = v.getNodeList();

        // list output blades
        HashMap<String,LinkedList<Integer>> outputBlades = new HashMap<String,LinkedList<Integer>>();

        for (String output: graph.getPragmaOutputVariables()) {
            String[] parts = output.split("_");
            LinkedList<Integer> list;
            if (outputBlades.containsKey(parts[0])) {
                list = outputBlades.get(parts[0]);
            } else {
                list = new LinkedList<Integer>();
                outputBlades.put(parts[0], list);
            }

            list.add(Integer.parseInt(parts[1]));
        }

        Iterator<Node> descendingIterator = nodeList.descendingIterator();

        CFGVisitorUsedVariables cfgVariableVisitor = new CFGVisitorUsedVariables(outputBlades,usedAlgebra);

        while (descendingIterator.hasNext()) {
            Node cur = descendingIterator.next();
            cur.accept(cfgVariableVisitor);
        }

        // remove all nodes that are marked for removal
        for (SequentialNode node: cfgVariableVisitor.getNodeRemovals())
            graph.removeNode(node);

        return !cfgVariableVisitor.getNodeRemovals().isEmpty();
    }

}
