package datapath.graph.display;

/**
 *
 * @author jh
 */
public class DisplayEdge<E extends DisplayNode> {
    
    protected E source;
    protected E target;

    Type type;
    Color color;

    public enum Type {
        Dashed, Dotted, Solid
    }

    public enum Color  {
        RED, BLACK, GREENs, Lightblue
    }


    public DisplayEdge(E source, E target, Type type, Color color) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public E getSource() {
        return source;
    }

    public E getTarget() {
        return target;
    }

    

    

}
