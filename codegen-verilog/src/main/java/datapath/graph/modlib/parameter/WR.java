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
public class WR extends Parameter<Integer> {

    public WR(Integer value) {
        super(value);
    }
    
    @Override
    public String getName() {
        return "WR";
    }

}
