/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
