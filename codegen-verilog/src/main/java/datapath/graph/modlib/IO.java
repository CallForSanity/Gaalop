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
