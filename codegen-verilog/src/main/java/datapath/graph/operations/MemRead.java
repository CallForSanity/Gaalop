package datapath.graph.operations;

/**
 *
 * @author jh
 */
public class MemRead extends UnaryOperation implements Producer {

    private int outputBitsize;

    @Override
    public void setOutputBitsize(int outputBitsize) {
        this.outputBitsize = outputBitsize;
    }

    @Override
    public int getOutputBitsize() {
        if(getType() == null)
            return outputBitsize;
        return getType().getBitsize();
    }


}
