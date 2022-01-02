package datapath.graph.operations.constValue;

/**
 *
 * @author jh
 */
public class IntegerValue extends Value<Integer> {

    @Override
    public String toHex() {
        return Integer.toHexString(getValue());
    }

}
