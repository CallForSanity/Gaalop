package datapath.graph.display.dot;

import datapath.graph.display.Display;
import datapath.graph.display.DisplayEdge.Color;
import datapath.graph.display.DisplayEdge.Type;
import datapath.graph.display.DisplayFactory;
import datapath.graph.operations.Operation;
import java.util.HashMap;

/**
 *
 * @author jh
 */
public class DotDisplayFactory extends DisplayFactory<DisplayEdge,DisplayNode> {

    HashMap<Operation,DisplayNode> nodes = new HashMap();

    @Override
    public DisplayEdge displayEdge(Operation source, Operation target, Type type,
            Color color) {
        return new DisplayEdge(displayNode(source), displayNode(target), type, color);
    }

    @Override
    public DisplayNode displayNode(Operation op) {
        DisplayNode node = nodes.get(op);
        if(node == null) {
            node = new DisplayNode(op);
            nodes.put(op, node);
        }
        return node;
    }

    @Override
    public Display display(String name) {
        return new DotDisplay(name);
    }

}
