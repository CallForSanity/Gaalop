package de.gaalop.cfg;

public class BreakNode extends SequentialNode {
	
	public BreakNode(ControlFlowGraph graph) {
		super(graph);
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public BreakNode copyElements(ControlFlowGraph graph) {
		return new BreakNode(graph);
	}
	
	@Override
	public String toString() {
		return "break";
	}

}
