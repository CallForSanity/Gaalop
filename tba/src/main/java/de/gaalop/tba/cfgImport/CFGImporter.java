package de.gaalop.tba.cfgImport;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.Node;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.tba.UseAlgebra;
import de.gaalop.tba.cfgImport.optimization.CFGVisitorUsedVariables;
import de.gaalop.tba.cfgImport.optimization.ConstantPropagation;
import de.gaalop.tba.cfgImport.optimization.NodeCollectorControlFlowVisitor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class CFGImporter {

    private UseAlgebra usedAlgebra;

    public CFGImporter() {
        //load 5d conformal algebra
        usedAlgebra = new UseAlgebra();
        usedAlgebra.load5dAlgebra();
    }

    public ControlFlowGraph importGraph(ControlFlowGraph graph) {

        DFGVisitorImport vDFG = new DFGVisitorImport(usedAlgebra);
        ControlFlowVisitor vCFG = new CFGVisitorImport(vDFG);
        graph.accept(vCFG);

        while (optimizeGraph(graph)) {
        }

        return graph;
    }

    /**
     * Optimizes the graph (Dead code elimination, ConstantFolding, Constant propagation)
     * Uses the StoreResultNode for output marking of variables
     * and fetch information from the pragma output variables of the graph.
     * Returns, if graph has changed.
     * @param graph The graph, that should be optimized
     * @return <value>true</value> if graph has changed, <value>false</value> otherwise
     */
    private boolean optimizeGraph(ControlFlowGraph graph) {

        boolean result = false;

        // traverse the graph in the opposite direction
        NodeCollectorControlFlowVisitor v = new NodeCollectorControlFlowVisitor();
        graph.accept(v);

        LinkedList<Node> nodeList = v.getNodeList();

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

        if (!cfgVariableVisitor.getNodeRemovals().isEmpty())
            result = true;

        // remove all nodes that are marked for removal
        for (SequentialNode node: cfgVariableVisitor.getNodeRemovals()) 
            graph.removeNode(node);
        


        ConstantPropagation propagation = new ConstantPropagation();
        graph.accept(propagation);

        result = result || propagation.isGraphModified();

        return result;
    }

}
