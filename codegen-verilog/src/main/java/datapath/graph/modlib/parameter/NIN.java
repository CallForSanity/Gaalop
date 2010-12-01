/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
