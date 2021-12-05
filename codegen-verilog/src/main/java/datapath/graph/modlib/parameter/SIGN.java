package datapath.graph.modlib.parameter;

import datapath.graph.modlib.Parameter;

/**
 *
 * @author jh
 */
public class SIGN extends Parameter<Boolean> {

    @Override
    public String getName() {
        return "SIGN";
    }

    public SIGN(Boolean value) {
        super(value);
    }

    @Override
    public String getValue() {
        return value ? "1" : "0";
    }
}
