package datapath.graph.operations;

import datapath.graph.OperationVisitor;
/**
 *
 * @author jh
 */
public class Output extends UnaryOperation {
    private String name;

    @Override
    public int getExecutionOrdinal() {
        int max = getData().getExecutionOrdinal();
        for(Predicate p : getPredicates()){
            int n = p.getExecutionOrdinal();
            if(n > max)
                max = n;
        }
        return max;
    }

    @Override
    public int getOutputBitsize() {
        if(getType() != null){
            return getType().getBitsize();
        }
        return getData().getOutputBitsize();
    }


   public void visit(OperationVisitor visitor) {
        visitor.visit(this);
    }

  public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
