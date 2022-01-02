package datapath.graph.display;

import datapath.graph.operations.Operation;

/**
 *
 * @author jh
 */
public abstract class DisplayFactory<Edge extends DisplayEdge, Node extends DisplayNode> {

    public abstract Edge displayEdge(Operation source, Operation target,
            DisplayEdge.Type type, DisplayEdge.Color color);

    public abstract Node displayNode(Operation op);

    public abstract Display display(String name);
}
