/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datapath.graph.modlib;

/**
 *
 * @author jh
 */
public abstract class IO<E> {
    
    private E value;

    public IO(E value) {
        this.value = value;
    }

    public abstract String getName();
    public E getValue() {
        return value;
    }

}
