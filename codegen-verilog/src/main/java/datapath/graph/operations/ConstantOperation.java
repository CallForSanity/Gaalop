package datapath.graph.operations;

import datapath.graph.OperationVisitor;
import datapath.graph.operations.constValue.Value;

/**
 *
 * @author jh
 */
public class ConstantOperation extends NaryOperation {

    //private LiteralExpr expr;

    private Value value;

    private String stringConst;

    @Override
    public String getDisplayLabel() {
        return "CONST: "+getValue();
    }



    public ConstantOperation(Value value, String valStr){
        //this.expr = expr;
        this.value = value;
        this.stringConst = valStr;
    }

    public Value getValue() {
        return value;
        //return expr.getConstantValue().getGenericValue();
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public int getStageDelay() {
        return 0;
    }

    public String toString() {
      return stringConst;
    }

//    @Override
//    public boolean isSigned() {
//        return expr.getType().isSigned();
//    }

    public String toHex() {
      return getType().toHex(value);
    }



}
