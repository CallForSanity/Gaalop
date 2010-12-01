package datapath.graph.operations;

import datapath.graph.OperationVisitor;

public class Sin extends UnaryOperation {

	public Sin() {
		super();
		// TODO Sin implementation missing
		
	}

  @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

}
