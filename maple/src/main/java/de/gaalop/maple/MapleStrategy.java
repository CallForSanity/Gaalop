package de.gaalop.maple;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This strategy uses maple to lower the graph from GA to normal arithmetic
 * using the default clifford base vectors {e<sub>1</sub>, e<sub>2</sub>, e<sub>3</sub>, e<sub>4</sub>, e<sub>5</sub>}. Then, in a second step,
 * it transforms the graph in such a way that it uses the base vectors {e<sub>0</sub>, e<sub>1</sub>, e<sub>2</sub>, e<sub>3</sub>, e<sub>inf</sub>}
 */
public class MapleStrategy implements OptimizationStrategy {

    private Log log = LogFactory.getLog(MapleStrategy.class);

    private final MapleSimplifier simplifier;

    MapleStrategy(MapleSimplifier simplifier) {
        this.simplifier = simplifier;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
    	try {
    		log.debug("Inlining macros");
    		InlineMacrosVisitor visitor = new InlineMacrosVisitor();
    		graph.accept(visitor);
    	} catch (Exception e) {
    		throw new OptimizationException("Unable to inline macros.", e, graph);
    	}
        try {
            log.debug("Simplifying graph using maple.");
            simplifier.simplify(graph);
        } catch (Exception e) {
            throw new OptimizationException("Unable to simplify using maple.", e, graph);
        }

        try {
            log.debug("Removing unused variables.");
            RemoveUnusedVariablesVisitor visitor = new RemoveUnusedVariablesVisitor();
            graph.accept(visitor);
        } catch (Exception e) {
            throw new OptimizationException("Unable to remove unused variables from graph.", e, graph);
        }
    }
}
