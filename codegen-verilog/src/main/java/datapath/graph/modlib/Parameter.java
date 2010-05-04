/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datapath.graph.modlib;

/**
 *
 * @author jh
 */
public abstract class Parameter<E> {

    protected E value;

    public Parameter(E value) {
        this.value = value;
    }

    public abstract String getName();

    public String getValue() {
        return value.toString();
    }
}
