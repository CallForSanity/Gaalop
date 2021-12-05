package datapath.graph.modlib;

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Jens
 */
public class WireOR extends Wire {

    Wire[] wires;

    private WireOR(String name) {
        super(name);
    }

    public WireOR(Wire... wires) {
        super("and");
        assert wires.length > 0;
        this.wires = wires;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (Iterator<Wire> iter = Arrays.asList(wires).iterator(); iter.hasNext();) {
            Wire w = iter.next();
            buf.append(w);
            if (iter.hasNext()) {
                buf.append('|');
            }
        }
        return buf.toString();
    }
}
