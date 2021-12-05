package datapath.graph.modlib.parameter;

import datapath.graph.modlib.Parameter;

/**
 *
 * @author jh
 */
public class WA extends Parameter<Integer> {

    public WA(Integer value) {
        super(value);
    }

    @Override
    public String getName() {
        return "WA";
    }

}
