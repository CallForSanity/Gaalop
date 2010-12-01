/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datapath.graph;

/**
 *
 * @author jh
 */
public abstract class Optimization {

    protected Graph graph;
    
    public Optimization(Graph graph) {
        this.graph = graph;
    }

    public abstract void perform();



}
