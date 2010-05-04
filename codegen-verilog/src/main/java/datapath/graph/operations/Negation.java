package datapath.graph.operations;

import datapath.graph.OperationVisitor;

public class Negation extends UnaryOperation {



@Override


public String getDisplayLabel() {
    return "Negation";
}


@Override
public int getOutputBitsize() {
	// TODO Auto-generated method stub
	return this.getData().getOutputBitsize();
}


@Override
public void visit(OperationVisitor visitor) {
    visitor.visit(this);
}
}