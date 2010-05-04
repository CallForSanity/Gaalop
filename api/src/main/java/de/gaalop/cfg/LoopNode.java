package de.gaalop.cfg;

public class LoopNode extends SequentialNode {
	
	private Node body;

	public LoopNode(ControlFlowGraph graph) {
		super(graph);
	}
	
	public void setBody(Node body) {
		this.body = body;
	}
	
	public Node getBody() {
		return body;
	}

	@Override
	public void accept(ControlFlowVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "loop { " + body + " ... }";
	}

}
