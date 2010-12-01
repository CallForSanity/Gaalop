/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datapath.graph.modlib;

/**
 *
 * @author jh
 */
public class WireNot extends Wire {
    
    private Wire w;

    public WireNot(Wire w){
        super("not");
        this.w = w;
    }

    private WireNot(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "!"+w;
    }



}
