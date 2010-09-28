package de.gaalop.maple;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This strategy uses maple to lower the graph from GA to normal arithmetic using the default clifford base vectors
 * {e<sub>1</sub>, e<sub>2</sub>, e<sub>3</sub>, e<sub>4</sub>, e<sub>5</sub>}. Then, in a second step, it transforms
 * the graph in such a way that it uses the base vectors {e<sub>0</sub>, e<sub>1</sub>, e<sub>2</sub>, e<sub>3</sub>,
 * e<sub>inf</sub>}
 */
public class MapleStrategy implements OptimizationStrategy {
	
	private static class AssignmentCounter extends EmptyControlFlowVisitor {
		
		protected int assignments;
		
		AssignmentCounter() {
		}

		@Override
		public void visit(AssignmentNode node) {
			assignments++;
			node.getSuccessor().accept(this);
		}
		
	}

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
			throw new OptimizationException("Unable to inline macros:\n" + e.getMessage(), e, graph);
		}
		try {
			AssignmentCounter counter = new AssignmentCounter();
			graph.accept(counter);
			simplifier.notifyMaximum(counter.assignments);
		} catch (Exception e) {
			throw new OptimizationException("Unable to count assignments in graph:\n" + e.getMessage(), graph);
		}
		try {
			log.debug("Simplifying graph using maple.");
			log.debug("Graph:\n" + graph.prettyPrint());
			simplifier.simplify(graph);
		} catch (Exception e) {
			throw new OptimizationException("Unable to simplify using maple:\n" + e.getMessage(), e, graph);
		}

		try {
			log.debug("Removing unused variables.");
			RemoveUnusedVariablesVisitor visitor = new RemoveUnusedVariablesVisitor();
			graph.accept(visitor);
		} catch (Exception e) {
			throw new OptimizationException("Unable to remove unused variables from graph:\n" + e.getMessage(), e, graph);
		}
		try {
			log.debug("Killing and folding constants.");
			ConstantKillCrawler crawler = new ConstantKillCrawler();
			graph.accept(crawler);
			ConstantFolding folder = new ConstantFolding();
			graph.accept(folder);
		} catch (Exception e) {
			throw new OptimizationException("Unable to eliminate constants:\n" + e.getMessage(), e, graph);
		}
	}
}
