package datapath.graph.operations;

import datapath.graph.OperationVisitor;

/**
 * Shift operation with constant shift amount
 * @author jh
 */
public class ConstantShift extends UnaryOperation {

    private int shiftAmount;
    private ShiftMode mode;

   public void setMode(ShiftMode mode) {
    this.mode = mode;
  }

    public ConstantShift(int shiftAmount, ShiftMode mode) {
        assert shiftAmount >= 0;
        this.shiftAmount = shiftAmount;
        this.mode = mode;
    }

    @Override
    public int getOutputBitsize() {
        return getData().getOutputBitsize();
    }

    @Override
    public int getDelay() {
        // combinatorical op
        return 0;
    }

    @Override
    public int getStageDelay() {
        // combinatorical op
        return 0;
    }

    @Override
    public String getDisplayLabel() {
        ShiftMode m = mode;
        switch (m) {
            case Right:
                if(isSigned())
                    m = ShiftMode.SignedRight;
                else
                    m = ShiftMode.UnsignedRight;
        }
        switch (m) {
            case Left:
                return "<< " + shiftAmount;
            case SignedRight:
                return ">>> " + shiftAmount;
            case UnsignedRight:
                return ">> " + shiftAmount;
            default:
                return ("shift mode " + mode +
                        " not supported");
        }
    }

  public void setShiftAmount(int shiftAmount) {
    this.shiftAmount = shiftAmount;
  }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    public ShiftMode getMode() {
        return mode;
    }

    public int getShiftAmount() {
        return shiftAmount;
    }

    
}
