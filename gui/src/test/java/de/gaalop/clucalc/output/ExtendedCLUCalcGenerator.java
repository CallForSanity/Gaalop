package de.gaalop.clucalc.output;

import java.util.List;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.FindStoreOutputNodes;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.cfg.StartNode;
import de.gaalop.cfg.StoreResultNode;
import de.gaalop.clucalc.input.CluCalcFileHeader;
import de.gaalop.dfg.Variable;

/**
 * This code generator uses the CLUCalc code generator to produce code which can be accessed via the CLUViz interface
 * for C++ or C#. The entire calculation is wrapped into a "calculate" function. For each output multivector (i.e.
 * marked for optimization) a string representation is generated in another "toString" function.
 * 
 * @author Christian Schwinn
 * 
 */
public class ExtendedCLUCalcGenerator extends CfgVisitor {

	boolean opt;

	public ExtendedCLUCalcGenerator(String suffix, boolean opt) {
		super(suffix);
		codeSuffix = suffix;
		this.opt = opt;
	}

	@Override
	public void visit(StartNode startNode) {
		CluCalcFileHeader header = CluCalcFileHeader.get(startNode);
		if (header != null) {
			if (header.getAlgebraMode() != null) {
				code.append(header.getAlgebraMode().getDefinitionMethod());
				code.append("();\n");
			}
			if (header.getNullSpace() != null) {
				switch (header.getNullSpace()) {
				case IPNS:
					code.append(":IPNS;\n");
					break;
				case OPNS:
					code.append(":OPNS;\n");
					break;
				}
			}
			code.append("\n");

		}
		if (opt) {
			for (Variable localVariable : startNode.getGraph().getLocalVariables()) {
				code.append(localVariable.getName() + codeSuffix);
				code.append(" = List(");
				code.append(startNode.getGraph().getBladeList().length);
				code.append(");\n");
			}
		}
		code.append("\n");

		code.append("if (ToolName == \"calculate\") {\n");
		indent++;
		startNode.getSuccessor().accept(this);
	}
	
	@Override
	public void visit(Macro node) {
		appendIndent();
		code.append(node.getName());
		code.append(" = {\n");
		indent++;
		List<SequentialNode> body = node.getBody();
		if (body != null && body.size() > 0) {
			body.get(0).accept(this);
		}
		if (node.getReturnValue() != null) {
			appendIndent();
			addCode(node.getReturnValue());
			code.append("\n");
		}
		indent--;
		appendIndent();
		code.append("}\n");
		node.getSuccessor().accept(this);
	}

	@Override
	public void visit(EndNode node) {
		super.visit(node);
		code.append("}\n\n");
		indent--;
		createToString(node.getGraph());
		code.append("\n");
	}

	private void createToString(ControlFlowGraph graph) {
		code.append("if (ToolName == \"toString\") {\n");
		indent++;
		FindStoreOutputNodes outputNodes = new FindStoreOutputNodes();
		graph.accept(outputNodes);
		for (StoreResultNode mv : outputNodes.getNodes()) {
			appendIndent();
			String variable = mv.getValue().getName();
			code.append(variable);
			code.append(" = String(");
			code.append(variable);
			code.append(");\n");
		}
		indent--;
		code.append("}\n");
	}

}
