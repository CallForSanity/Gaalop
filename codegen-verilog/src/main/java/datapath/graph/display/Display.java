package datapath.graph.display;

/**
 *
 * @author jh
 */
public abstract class Display<Edge extends DisplayEdge, Node extends DisplayNode> {

    protected String name;

    protected Display(String name) {
        this.name = name;
    }

    public abstract void addNode(Node node);
    public abstract void addEdge(Edge edge);

    public abstract void display();
    public abstract void display(String stage);


}
