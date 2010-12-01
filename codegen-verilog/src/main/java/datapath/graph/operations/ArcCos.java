package datapath.graph.operations;

import datapath.graph.OperationVisitor;

public class ArcCos extends UnaryOperation {

	public ArcCos() {
		super();
		// TODO ArcCos implementation missing
		
	}
  @Override
  public void visit(OperationVisitor visitor) {
    visitor.visit(this);
    }

}
