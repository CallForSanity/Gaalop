package datapath.graph.modlib.parameter;

import datapath.graph.modlib.*;

/**
 *
 * @author jh
 */
public class VALUE extends Parameter<String> {

    @Override
    public String getName() {
        return "VALUE";
    }

    public VALUE(String value) {
        super(value);
    }
}
