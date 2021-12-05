package datapath.graph.operations;

/**
 *
 * @author jh
 */
public class Predication extends UnaryOperation {

    @Override
    public String getDisplayLabel() {
        return "PREDICATION";
    }

    @Override
    public boolean isHardwareOperation() {
        return false;
    }

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
        return getData().getOutputBitsize();
    }

    @Override
    public int getDelay() {
        // virtual operation -> has no delay
        return 0;
    }




}
