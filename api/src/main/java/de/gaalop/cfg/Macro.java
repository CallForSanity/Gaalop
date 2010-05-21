package de.gaalop.cfg;

import java.util.ArrayList;
import java.util.List;

import de.gaalop.dfg.Expression;

public class Macro extends SequentialNode {

	private String name;
	private List<SequentialNode> body;
	private Expression returnValue;

	public Macro(ControlFlowGraph graph, String name, List<SequentialNode> body, Expression returnValue) {
		super(graph);
		this.name = name;
		this.body = body;
		this.returnValue = returnValue;
	}

	public String getName() {
		return name;
	}

	public List<SequentialNode> getBody() {
		return body;
	}

	public Expression getReturnValue() {
		return returnValue;
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Macro copyElements() {
		List<SequentialNode> bodyCopy = new ArrayList<SequentialNode>();
		for (SequentialNode node : body) {
			bodyCopy.add(node.copy());
			// XXX: do successors have to be replaced?
		}
		return new Macro(getGraph(), name, bodyCopy, returnValue.copy());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Macro[");
		sb.append("name=");
		sb.append(name);
		sb.append(",body={");
		for (SequentialNode node : body) {
			sb.append(node);
			sb.append(";");
		}
		sb.append("}");
		if (returnValue != null) {
			sb.append(",returns ");
			sb.append(returnValue);
		}
		sb.append("]");
		return sb.toString();
	}

}
