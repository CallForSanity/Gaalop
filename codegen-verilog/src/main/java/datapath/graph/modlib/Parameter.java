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
