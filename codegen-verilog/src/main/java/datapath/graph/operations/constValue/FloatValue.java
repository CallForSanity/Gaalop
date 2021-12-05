package datapath.graph.operations.constValue;

/**
 *
 * @author jh
 */
public class FloatValue extends Value<Float> {

    @Override
    public String toHex() {
        return Integer.toHexString(Float.floatToRawIntBits(getValue()));
    }

}
