package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import datapath.graph.display.DisplayEdge;

/**
 *
 * @author jh
 */
public class Predicate extends UnaryOperation {

    @Override
    public int getOutputBitsize() {
        return 1;
    }

    public static enum TYPE {

        TRUE, FALSE, INIT;
    }
    private TYPE type;

    public DisplayEdge.Color getEdgeColor() {
        switch (type) {
            case TRUE:
                return DisplayEdge.Color.GREENs;
            case FALSE:
                return DisplayEdge.Color.RED;
            case INIT:
            default:
                return DisplayEdge.Color.Lightblue;
        }

    }

    @Override
    public boolean isHardwareOperation() {
        return false;
    }

    @Override
    public int getExecutionOrdinal() {
        return getData().getExecutionOrdinal();
    }

    public Predicate(TYPE type) {
        this.type = type;
    }

    @Override
    public String getDisplayLabel() {
        return type.toString();
    }

    public TYPE getPredicationType() {
        return type;
    }

    @Override
    public int getDelay() {
        // Predicate is a virtual operation and has no delay
        return 0;
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }


}
