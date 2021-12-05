package datapath.graph.modlib.parameter;

import datapath.graph.modlib.Parameter;

/**
 *
 * @author jh
 */
public class ADDR_WIDTH extends Parameter<Integer> {

    @Override
    public String getName() {
        return "ADDR_WIDTH";
    }

    public ADDR_WIDTH(Integer value) {
        super(value);
    }

}
