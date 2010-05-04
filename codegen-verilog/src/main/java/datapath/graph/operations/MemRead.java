/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
