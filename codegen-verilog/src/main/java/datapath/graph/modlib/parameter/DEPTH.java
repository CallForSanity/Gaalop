package datapath.graph.modlib.parameter;

import datapath.graph.modlib.Parameter;

/**
 *
 * @author jh
 */
public class DEPTH extends Parameter<Integer> {

    public DEPTH(Integer value) {
        super(value);
    }

    @Override
    public String getName() {
        return "DEPTH";
    }

    public Integer getV() {
        return value;
    }

}
