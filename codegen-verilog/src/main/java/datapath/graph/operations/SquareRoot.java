package datapath.graph.operations;

import datapath.graph.OperationVisitor;

public class SquareRoot extends UnaryOperation {

    public SquareRoot() {
        super();
        // TODO Square Root implementation missing

    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getDisplayLabel() {
        return "SQRT";
    }

    @Override
    public int getDelay() {
        return 10;
    }
}
