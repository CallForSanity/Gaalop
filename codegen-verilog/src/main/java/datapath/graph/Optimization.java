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
