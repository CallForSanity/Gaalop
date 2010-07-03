package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 *
 * @author jh
 */
public class ConstantMultiplication extends Multiplication {

    @Override
    public int getDelay() {
        int delay = 0;
        if (getType() instanceof datapath.graph.type.Float) {
            delay = (getType().getBitsize() == 32 ? 8 : 10);
        }
        return delay;
    }



    @Override
    public String getDisplayLabel() {
        return "Const MULT";
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }


}
