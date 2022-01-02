package datapath.graph.modlib.parameter;

import datapath.graph.modlib.Parameter;

/**
 *
 * @author jh
 */
public class NIN extends Parameter<Integer> {

    public NIN(Integer value) {
        super(value);
    }

    

    @Override
    public String getName() {
        return "NIN";
    }

}
