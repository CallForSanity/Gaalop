package datapath.graph.operations;

import datapath.graph.OperationVisitor;

public class Absolut extends UnaryOperation {

	public Absolut() {
		super();
		// TODO Absolut implementation missing
		
	}
  @Override
  public void visit(OperationVisitor visitor) {
    visitor.visit(this);
  }

}
