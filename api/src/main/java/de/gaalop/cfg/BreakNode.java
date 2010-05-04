package de.gaalop.cfg;

public class BreakNode extends SequentialNode {
	
	public BreakNode(ControlFlowGraph graph) {
		super(graph);
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		visitor.visit(this);
	}

}
