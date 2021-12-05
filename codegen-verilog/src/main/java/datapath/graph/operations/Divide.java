package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import datapath.graph.type.FixedPoint;
import datapath.graph.type.Type;

/**
 *
 * @author jh
 */
public class Divide extends BinaryOperation {

    private boolean isNormalization = false;

  public boolean isNormalization() {
    return isNormalization;
  }

  public void setNormalization(boolean isNormalization) {
    this.isNormalization = isNormalization;
  }

    @Override
    public String getDisplayLabel() {
        return "DIV";
    }

    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getDelay() {
        Type type = getType();
        int delay = getLhs().getOutputBitsize() + 2 + (isSigned() ? 2 : 0); //- getStageDelay();
        if(type instanceof FixedPoint && getOutputBitsize() == 64)
            delay = 32 + 32 + 4;
        if (type instanceof datapath.graph.type.Float) {
            delay = (type.getBitsize() == 32 ? 28 : 57); //- getStageDelay();
        }
        return delay;
    }
}
