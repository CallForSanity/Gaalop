package datapath.graph.operations;

import datapath.graph.OperationVisitor;


/**
 * Input Register
 * @author jh
 */
public class HWInput extends Input {

    private Object declaration;

    public HWInput(Object decl){
        this.declaration = decl;
    }

    @Override
    public String getDisplayLabel() {
        return "HW INPUT "; //+ declaration.toString();
    }

    @Override
    public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }



}
