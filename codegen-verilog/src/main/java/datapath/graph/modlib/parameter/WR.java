package datapath.graph.modlib.parameter;

import datapath.graph.modlib.*;

/**
 *
 * @author jh
 */
public class WR extends Parameter<Integer> {

    public WR(Integer value) {
        super(value);
    }
    
    @Override
    public String getName() {
        return "WR";
    }

}
