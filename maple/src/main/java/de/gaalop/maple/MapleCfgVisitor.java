package de.gaalop.maple;

import de.gaalop.cfg.*;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MathFunction;
import de.gaalop.dfg.MathFunctionCall;
import de.gaalop.dfg.Variable;
import de.gaalop.maple.engine.MapleEngine;
import de.gaalop.maple.engine.MapleEngineException;
import de.gaalop.maple.parser.MapleLexer;
import de.gaalop.maple.parser.MapleParser;
import de.gaalop.maple.parser.MapleTransformer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * This visitor creates code for Maple.
 */
public class MapleCfgVisitor implements ControlFlowVisitor {

	/**
	 * Simple helper visitor used to inline parts of conditional statements.
	 * 
	 * @author Christian Schwinn
	 * 
	 */
	private static class InlineBlockVisitor extends EmptyControlFlowVisitor {

		private final IfThenElseNode root;
		private final Node branch;
		private final Node successor;

		/**
		 * Creates a new visitor with given root and branch.
		 * 
		 * @param root root node from which to inline a branch
		 * @param branch first node of branch to be inlined
		 */
		public InlineBlockVisitor(IfThenElseNode root, Node branch) {
			this.root = root;
			this.branch = branch;
			successor = root.getSuccessor();
		}

		private void replaceSuccessor(Node oldSuccessor, Node newSuccessor) {
			Set<Node> predecessors = new HashSet<Node>(oldSuccessor.getPredecessors());
			for (Node p : predecessors) {
				p.replaceSuccessor(oldSuccessor, newSuccessor);
			}
		}

		@Override
		public void visit(IfThenElseNode node) {
			// we peek only to next level of nested statements
			if (node == root) {
				if (node.getPositive() == branch) {
					replaceSuccessor(node, branch);
					node.getPositive().accept(this);
				} else if (node.getNegative() == branch) {
					replaceSuccessor(node, branch);
					node.getNegative().accept(this);
				}
				node.getGraph().removeNode(node);
			}
			node.getSuccessor().accept(this);
		}

		@Override
		public void visit(BlockEndNode node) {
			// this relies on the fact that nested statements are being ignored in visit(IfThenElseNode),
			// otherwise successor could be the wrong one
			replaceSuccessor(node, successor);
		}

	}

	private Log log = LogFactory.getLog(MapleCfgVisitor.class);

	private MapleEngine engine;

	private HashMap<String, String> oldMinVal;
	private HashMap<String, String> oldMaxVal;

	private Plugin plugin;

	public MapleCfgVisitor(MapleEngine engine, Plugin plugin) {
		this.engine = engine;
		this.plugin = plugin;
	}

	private String generateCode(Expression expression) {
		MapleDfgVisitor visitor = new MapleDfgVisitor();
		expression.accept(visitor);
		return visitor.getCode();
	}

	@Override
	public void visit(StartNode startNode) {
		plugin.notifyStart();
		startNode.getSuccessor().accept(this);
	}

	@Override
	public void visit(AssignmentNode assignmentNode) {
		String variableCode = generateCode(assignmentNode.getVariable());
		// If you want to simplify (and keep) the last assignment to every variable
		// uncomment the following statement:
		// simplifyBuffer.add(variableCode);

		StringBuilder codeBuffer = new StringBuilder();
		/*
		 * FIXME: special treatment in case it is a single line using a math function, which is not support. (Atm only
		 * abs is supported) We dont call Matlab for this, as it cannot handle these correct the mathfunction has to be
		 * on a single line, with a single var parameter like x = sqrt(y);
		 */
		if (assignmentNode.getValue() instanceof MathFunctionCall) {
			MathFunction func = ((MathFunctionCall) (assignmentNode.getValue())).getFunction();
			if ((func != MathFunction.ABS)) {
				assignmentNode.getSuccessor().accept(this);
				return;
			}
		}

		codeBuffer.append(variableCode);
		codeBuffer.append(" := ");
		codeBuffer.append(generateCode(assignmentNode.getValue()));
		codeBuffer.append(";\n");

		try {
			engine.evaluate(codeBuffer.toString());
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to simplify assignment " + assignmentNode + " in Maple.", e);
		}

		Node successor = assignmentNode.getSuccessor();
		assignmentNode.getGraph().removeNode(assignmentNode);
		successor.accept(this);

		// notify observables about progress
		plugin.notifyProgress();
	}

	@Override
	public void visit(StoreResultNode node) {
		String evalResult = simplify(node.getValue());
		log.debug("Maple simplification of " + node.getValue() + ": " + evalResult);

		List<SequentialNode> newNodes = parseMapleCode(node.getGraph(), evalResult);

		for (SequentialNode newNode : newNodes) {
			node.insertBefore(newNode);
		}

		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(IfThenElseNode node) {
		StringBuilder codeBuffer = new StringBuilder();
		codeBuffer.append("evalb(");
		codeBuffer.append(generateCode(node.getCondition()));
		codeBuffer.append(");\n");
		try {
			String result = engine.evaluate(codeBuffer.toString());
			log.debug("Maple simplification of IF condition " + node.getCondition() + ": " + result);
			if ("true\n".equals(result)) {
				node.accept(new InlineBlockVisitor(node, node.getPositive()));
				node.getPositive().accept(this);
			} else if ("false\n".equals(result)) {
				node.accept(new InlineBlockVisitor(node, node.getNegative()));
				node.getNegative().accept(this);
			} else {
				node.getSuccessor().accept(this);
			}
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to check condition " + node.getCondition() + " in if-statement " + node,
					e);
		}
	}

	@Override
	public void visit(LoopNode node) {
		// TODO: handle loop body
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(BreakNode node) {
		// nothing to do
	}

	@Override
	public void visit(BlockEndNode node) {
		// nothing to do
	}

	@Override
	public void visit(EndNode endNode) {
	}

	/**
	 * Parses a snippet of maple code and returns a list of CFG nodes that implement the returned maple expressions.
	 * 
	 * @param graph The control flow graph the new nodes should be created in.
	 * @param mapleCode The code returned by Maple.
	 * @return A list of control flow nodes modeling the returned code.
	 */
	private List<SequentialNode> parseMapleCode(ControlFlowGraph graph, String mapleCode) {
		oldMinVal = new HashMap<String, String>();
		oldMaxVal = new HashMap<String, String>();

		/* fill the Maps with the min and maxvalues from the nodes */
		for (Variable v : graph.getInputVariables()) {
			if (v.getMinValue() != null)
				oldMinVal.put(v.getName(), v.getMinValue());
			if (v.getMaxValue() != null)
				oldMaxVal.put(v.getName(), v.getMaxValue());
		}

		MapleLexer lexer = new MapleLexer(new ANTLRStringStream(mapleCode));
		MapleParser parser = new MapleParser(new CommonTokenStream(lexer));
		try {
			MapleParser.program_return result = parser.program();
			MapleTransformer transformer = new MapleTransformer(new CommonTreeNodeStream(result.getTree()));
			return transformer.script(graph, oldMinVal, oldMaxVal);
		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Simplifies a single Expression node.
	 * 
	 * @param expression The data flow graph that should be simplified
	 * @return The code Maple returned as the simplification.
	 */
	private String simplify(Expression expression) {
		StringBuilder codeBuffer = new StringBuilder();
		codeBuffer.append("gaalop(");
		codeBuffer.append(generateCode(expression));
		codeBuffer.append(");\n");

		try {
			return engine.evaluate(codeBuffer.toString());
		} catch (MapleEngineException e) {
			throw new RuntimeException("Unable to apply gaalop() function on expression " + expression + " in Maple.",
					e);
		}
	}

	@Override
	public void visit(Macro node) {
		throw new IllegalStateException("Macros should have been inlined.");
	}
}
