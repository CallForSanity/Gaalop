package datapath.graph.operations.constValue;

/**
 *
 * @author jh
 */
public abstract class Value<E> {

    private E value;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public abstract String toHex();

    @Override
    public String toString() {
        return value.toString();
    }


}
