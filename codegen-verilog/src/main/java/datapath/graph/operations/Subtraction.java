package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import datapath.graph.type.Type;



public class Subtraction extends BinaryOperation {

	@Override
    public String getDisplayLabel() {
        return "SUB";
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getDelay() {
        Type type = getType();
        int delay = super.getDelay();
        if (type instanceof datapath.graph.type.Float) {
            delay = 8;
        }
        return delay;
    }


	

}
