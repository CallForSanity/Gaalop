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
		 * FIXME: special treatment in case it is a single line using a math function, which is not support. (Atm only abs is supported)
		 * We dont call Matlab for this, as it cannot handle these correct the mathfunction has to be on a single line, with a
		 * single var parameter like x = sqrt(y);
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
			throw new RuntimeException("Unable to simplify using Maple.", e);
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
		node.getPositive().accept(this);
		node.getNegative().accept(this);
		
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(LoopNode node) {
		node.getBody().accept(this);
		
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
		oldMinVal = new HashMap();
		oldMaxVal = new HashMap();

		/* fill the Maps with the min and maxvalues from the nodes */
		for (Variable v : graph.getInputVariables()) {
			if (v.getMinValue() != null) oldMinVal.put(v.getName(), v.getMinValue());
			if (v.getMaxValue() != null) oldMaxVal.put(v.getName(), v.getMaxValue());
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
			throw new RuntimeException("Unable to simplify using Maple.", e);
		}
	}

	@Override
	public void visit(Macro node) {
		throw new IllegalStateException("Macros should have been inlined.");
	}
}
