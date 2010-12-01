/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datapath.graph.operations.constValue;

/**
 *
 * @author jh
 */
public class IntegerValue extends Value<Integer> {

    @Override
    public String toHex() {
        return Integer.toHexString(getValue());
    }

}
