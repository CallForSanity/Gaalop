package datapath.graph.operations;

import datapath.graph.OperationVisitor;

public class Cos extends UnaryOperation {

	public Cos() {
		super();
		// TODO Cosinus  implementation missing
		
	}
  @Override
  public void visit(OperationVisitor visitor) {
    visitor.visit(this);
  }

}
