package de.gaalop.tablebased;

import java.util.Stack;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EmptyControlFlowVisitor;
import de.gaalop.cfg.LoopNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This strategy uses maple to lower the graph from GA to normal arithmetic using the default clifford base vectors
 * {e<sub>1</sub>, e<sub>2</sub>, e<sub>3</sub>, e<sub>4</sub>, e<sub>5</sub>}. Then, in a second step, it transforms
 * the graph in such a way that it uses the base vectors {e<sub>0</sub>, e<sub>1</sub>, e<sub>2</sub>, e<sub>3</sub>,
 * e<sub>inf</sub>}
 */
public class TableBasedStrategy implements OptimizationStrategy {
	
	private static class AssignmentCounter extends EmptyControlFlowVisitor {
		
		protected int totalAssignments;
		private int assignmentsInLoop;
		private Stack<LoopNode> loops = new Stack<LoopNode>();
		
		AssignmentCounter() {
		}

		@Override
		public void visit(AssignmentNode node) {
			if (loops.isEmpty()) {
				totalAssignments++;
			} else {
				assignmentsInLoop++;
			}
			node.getSuccessor().accept(this);
		}
		
		@Override
		public void visit(LoopNode node) {
			int iterations = node.getIterations();
			boolean unroll = iterations > 0;
			int previousNumber = assignmentsInLoop;
			if (unroll) {
				assignmentsInLoop = 0;
				loops.push(node);
			}
			node.getBody().accept(this);
			if (unroll) {
				loops.pop();
				assignmentsInLoop *= iterations;
				if (loops.isEmpty()) {
					totalAssignments += assignmentsInLoop;
				} else {
					previousNumber += assignmentsInLoop;
				}
				assignmentsInLoop = previousNumber;
			}
			node.getSuccessor().accept(this);
		}
		
	}

	private Log log = LogFactory.getLog(TableBasedStrategy.class);

	private final TableBasedSimplifier simplifier;

	private final boolean constantFolding;

	TableBasedStrategy(TableBasedSimplifier simplifier, boolean constantFolding) {
		this.simplifier = simplifier;
		this.constantFolding = constantFolding;
	}

	@Override
	public void transform(ControlFlowGraph graph) throws OptimizationException {
		try {
			log.debug("Inlining macros");
			InlineMacrosVisitor visitor = new InlineMacrosVisitor();
			graph.accept(visitor);
			log.debug("Graph after macro inlining:\n" + graph.prettyPrint());
		} catch (Exception e) {
			throw new OptimizationException("Unable to inline macros:\n" + e.getMessage(), e, graph);
		}
		try {
			AssignmentCounter counter = new AssignmentCounter();
			graph.accept(counter);
			simplifier.notifyMaximum(counter.totalAssignments);
		} catch (Exception e) {
			throw new OptimizationException("Unable to count assignments in graph:\n" + e.getMessage(), graph);
		}
		try {
			log.debug("Simplifying graph using Table Based Approach.");
			simplifier.simplify(graph);
		} catch (Exception e) {
			throw new OptimizationException("Unable to simplify using Table Based Approach:\n" + e.getMessage(), e, graph);
		}

		try {
			log.debug("Removing unused variables.");
			RemoveUnusedVariablesVisitor visitor = new RemoveUnusedVariablesVisitor();
			graph.accept(visitor);
		} catch (Exception e) {
			throw new OptimizationException("Unable to remove unused variables from graph:\n" + e.getMessage(), e, graph);
		}
		if (constantFolding) {
			try {
				log.debug("Folding constants.");
				ConstantFolding folder = new ConstantFolding();
				graph.accept(folder);
			} catch (Exception e) {
				throw new OptimizationException("Unable to eliminate constants:\n" + e.getMessage(), e, graph);
			}
		}
		log.debug("Graph after optimizations:\n" + graph.prettyPrint());
	}
}
