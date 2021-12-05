package datapath.graph.modlib.parameter;

import datapath.graph.modlib.Parameter;

/**
 *
 * @author jh
 */
public class QDEPTH extends Parameter<Integer> {

    @Override
    public String getName() {
        return "QDEPTH";
    }

    public QDEPTH(Integer value) {
        super(value);
    }

}
